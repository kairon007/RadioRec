package com.rothconsulting.android.radiorec.sqlitedb;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class FavouriteDAO {
	private final SQLiteDatabase db;

	public FavouriteDAO(FavouriteDatabase dbManager) {
		db = dbManager.getWritableDatabase();
	}

	public ArrayList<FavouriteVO> findAll() {
		Cursor cursor = db.query(DbConstants.T_FAVOURITE, new String[] {
				DbConstants.ID, DbConstants.STATION }, null, null, "", "", "");
		ArrayList<FavouriteVO> list = new ArrayList<FavouriteVO>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				FavouriteVO favouriteVO = new FavouriteVO();
				favouriteVO.setId(cursor.getString(0));
				favouriteVO.setStation(cursor.getString(1));
				list.add(favouriteVO);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

	public ArrayList<FavouriteVO> findById(String id) {
		String[] value = new String[] { id };
		String[] attr = new String[] { DbConstants.ID };
		return findByAttributes(attr, value);
	}

	public ArrayList<FavouriteVO> findByStation(String station) {
		String[] value = new String[] { station };
		String[] attr = new String[] { DbConstants.STATION };
		return findByAttributes(attr, value);
	}

	public ArrayList<FavouriteVO> findByAttributes(String[] attributes,
			String[] values) {
		String attr = "";
		for (int i = 0; i < attributes.length; i++) {
			attr = attr + attributes[i] + "=?";
			if (!(i - attributes.length == -1)) {
				attr = attr + ", ";
			}
		}
		Cursor cursor = db
				.query(DbConstants.T_FAVOURITE, new String[] { DbConstants.ID,
						DbConstants.STATION }, attr, values, "", "", "");
		ArrayList<FavouriteVO> list = new ArrayList<FavouriteVO>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				FavouriteVO favouriteVO = new FavouriteVO();
				favouriteVO.setId(cursor.getString(0));
				favouriteVO.setStation(cursor.getString(1));
				list.add(favouriteVO);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

	public FavouriteVO save(Object obj) {
		FavouriteVO favouriteVO = (FavouriteVO) obj;
		if ((favouriteVO.getId() == null || favouriteVO.getId().trim()
				.equals("0"))) {
			SQLiteStatement stmtInsert = db
					.compileStatement("insert into T_PREIS (PREIS,HAENDLER_ID,ARTIKEL_ID) values (?,?,?)");
			stmtInsert.bindString(1, favouriteVO.getId());
			stmtInsert.bindString(2, favouriteVO.getStation());
			stmtInsert.execute();
			stmtInsert.executeInsert();
			return favouriteVO;
		} else {
			SQLiteStatement stmtUpdate = db.compileStatement("update "
					+ DbConstants.T_FAVOURITE + " set " + DbConstants.STATION
					+ "='" + favouriteVO.getStation() + "' where ID="
					+ favouriteVO.getId());
			stmtUpdate.execute();
			return favouriteVO;
		}
	}

	public boolean deleteById(String id) {
		try {
			SQLiteStatement stmtDelete = db.compileStatement("delete "
					+ DbConstants.T_FAVOURITE + " where ID=?");
			stmtDelete.bindString(1, id);
			stmtDelete.execute();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

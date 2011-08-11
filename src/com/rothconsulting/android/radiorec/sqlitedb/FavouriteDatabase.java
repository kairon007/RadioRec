package com.rothconsulting.android.radiorec.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouriteDatabase extends SQLiteOpenHelper {

	private static String TAG = FavouriteDatabase.class.getName();
	private static final String DATABASE_NAME = "radiorecfavourites.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DbConstants.T_FAVOURITE
			+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, STATION TEXT NOT NULL);";

	public FavouriteDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + DbConstants.T_FAVOURITE);
		onCreate(database);
	}
}
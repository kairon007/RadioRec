package com.rothconsulting.android.radiorec.sqlitedb;

import java.util.ArrayList;

public interface DefaultDAO {
	
	public ArrayList<?> findAll();
	
	public ArrayList<?> findByAttributes(String []attributes, String []values);
	
	public Object findById(String id);
	
	public Object save(Object obj);
	
	public boolean deleteById(String id);
}

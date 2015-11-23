package com.nowa.com.database;

import android.database.sqlite.SQLiteDatabase;

public class Database {

	protected static final String DATABASE_NAME = "NowASigaa";
	protected static final int DATABASE_VERSION = 12;
	protected SQLiteDatabase db;
	
	/** Closes database connections */
	public void close(){
		if(db != null){
			db.close();
		}
	}
	
}
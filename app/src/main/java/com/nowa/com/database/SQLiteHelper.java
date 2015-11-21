package com.nowa.com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{
	
	private static final String CATEGORY = "sigaa";
	private String[] scriptSQLCreate;
	private String[] scriptSQLDelete;

	public SQLiteHelper(Context ctx, String databaseName, int databaseVersion, String[] scriptSQLCreate, String[] scriptSQLDelete){
		super(ctx, databaseName, null, databaseVersion);
		this.scriptSQLDelete = scriptSQLDelete;
		this.scriptSQLCreate = scriptSQLCreate;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(CATEGORY, "Creating SQL database");
		int qtdScripts = scriptSQLCreate.length;
		for(int i = 0; i < qtdScripts; ++i){
			String sql = scriptSQLCreate[i];
			Log.i(CATEGORY, sql);
			db.execSQL(sql);
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CATEGORY, "Updating version from " + oldVersion + " to " + newVersion + ". All registers will be deleted.");
		int qtyScripts = scriptSQLDelete.length;
		for(int i=0;i<qtyScripts;++i) {
			Log.i(CATEGORY, scriptSQLDelete[i]);
			db.execSQL(scriptSQLDelete[i]);
		}
		onCreate(db);
	}
}
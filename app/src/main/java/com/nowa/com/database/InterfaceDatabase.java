package com.nowa.com.database;

import com.parse.ParseObject;

import android.content.ContentValues;
import android.content.Context;


public interface InterfaceDatabase {

	public long save(Object obj, boolean persistCloud);
	public long insert(Object obj, boolean persistCloud);
	public long insert(Context ctx, String table_name, ContentValues values, boolean persistClour);
	public void update(ParseObject obj, Context ctx);
	public void insertCloud(Context ctx, String table_name, ContentValues values);
	public int update(Context ctx, String nome_tabela, ContentValues values, String where, String[] whereArgs, boolean persistCloud);
}

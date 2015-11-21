package com.nowa.com.database;

import java.util.HashMap;
import java.util.Set;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.domain.Entity;
import com.parse.ParseObject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

public abstract class AbstractDatabase extends Database implements InterfaceDatabase {

	/**
	 * Abstract method that should be implemented to save values on database
	 * @param obj
	 * @param persistCloud
	 * @return
	 * */
	@Override
	public abstract long save(Object obj, boolean persistCloud);

	/**
	 * Abstract method that should be implemented to insert values on database
	 * @param obj
	 * @param persistCloud
	 * @return
	 * */
	@Override
	public abstract long insert(Object obj, boolean persistCloud);

	/**
	 * Método abstrato para ser implementado para atualizar os valores no banco.
	 * @param obj
	 * @return
	 */ 
	@Override
	public abstract void update(ParseObject obj, Context ctx);
	
	/**
	 * Method responsable for insert values from HashMap on database
	 * @param ctx
	 * @param table_name
	 * @param values
	 * @param persistCloud
	 * @return
	 * */
	@Override
	public long insert(Context ctx, String table_name, ContentValues values, boolean persistCloud){
		long id = db.insert(table_name, null, values);
		values.put(Entity._ID, id);
		if(persistCloud)
			insertCloud(ctx, table_name, values);
		return id;
	}
	
	/**
	 * Método responsável por inserir os valores do HashMap no banco na nuvem.
	 * @param ctx
	 * @param table_name
	 * @param values
	 */
	@SuppressLint("NewApi")
	@Override
	public void insertCloud(Context ctx, String table_name, ContentValues values){
		//Converting from ContentValues to HashMap
		Set<String> keys = values.keySet();
		HashMap<String, String> keyParameters = new HashMap<String, String>();
		for(String key : keys)
			keyParameters.put(key, String.valueOf(values.get(key)));
		//Saving the object on cloud database
		CloudQueries cloud = new CloudQueries(ctx);
		cloud.saveInBackGround(table_name, keyParameters);
	}
	
	/**
	 * Método responsável por atualizar os valores do HashMap no banco.
	 * @param ctx
	 * @param table_name
	 * @param values
	 * @param where
	 * @param whereArgs
	 * @return
	 * */
	@Override
	public int update(Context ctx, String table_name, ContentValues values, String where, String[] whereArgs, boolean persistCloud){
		int count = db.update(table_name, values, where, whereArgs);
		if(persistCloud)
			updateCloud(ctx, table_name, values, whereArgs[0]);
		return count;
	}

	/**
	 * Método responsável por atualizar os valores do HashMap no banco na nuvem.
	 * @param ctx
	 * @param table_name
	 * @param values
	 * */
	@SuppressLint("NewApi")
	public void updateCloud(Context ctx, String table_name, ContentValues values, String id){
		//Converting from ContentValues to HashMap
		Set<String> keys = values.keySet();
		HashMap<String, String> keyParameters = new HashMap<String, String>();
		for(String key : keys)
			keyParameters.put(key, String.valueOf(values.get(key)));
		//Saving the object on the cloud database
		CloudQueries cloud = new CloudQueries(ctx);
		//Updating the object's values on the cloud database
		cloud.updateInBackground(table_name, keyParameters, id);
	}
}

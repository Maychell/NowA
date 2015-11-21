package com.nowa.com.database;

import android.content.Context;

public class DatabaseScript extends Database {

    private static final String[] SCRIPT_DATABASE_DELETE = new String[] {
            "DROP TABLE IF EXISTS post",
            "DROP TABLE IF EXISTS subject",
            "DROP TABLE IF EXISTS user"
    };
	private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
            "CREATE TABLE user (id VARCHAR(240) PRIMARY KEY, login VARCHAR(100) NOT NULL, token VARCHAR(240) NOT NULL, name VARCHAR(240) NOT NULL, course VARCHAR(240) NOT NULL, registerNumber VARCHAR(240) NOT NULL, email VARCHAR(240) NOT NULL);",
            //Subject
            "CREATE TABLE subject (id VARCHAR(240) PRIMARY KEY, subject_code VARCHAR(8) NOT NULL, name VARCHAR(240) NOT NULL, name_sigaa VARCHAR(240) NOT NULL, picture VARCHAR(240) NOT NULL);",
            //Post
            "CREATE TABLE post (id VARCHAR(240) PRIMARY KEY, date VARCHAR(15) NOT NULL, time VARCHAR(8) NOT NULL, id_user VARCHAR(240) NOT NULL, message VARCHAR(240) NOT NULL, id_subject VARCHAR(240), FOREIGN KEY (id_user) REFERENCES user(id), FOREIGN KEY (id_subject) REFERENCES subject(id));",
			//Hashtag
			"CREATE TABLE hashtag (id VARCHAR(240) PRIMARY KEY, name VARCHAR(240) NOT NULL);",
			//Hashtag_post
			"CREATE TABLE hashtag_post (id_post VARCHAR(240) NOT NULL, id_hashtag VARCHAR(240), FOREIGN KEY (id_post) REFERENCES post(id), FOREIGN KEY (id_hashtag) REFERENCES hashtag(id));",
	};

	private SQLiteHelper dbHelper;
	
	public DatabaseScript(Context ctx){
		dbHelper = new SQLiteHelper(ctx, DatabaseScript.DATABASE_NAME, DatabaseScript.DATABASE_VERSION, DatabaseScript.SCRIPT_DATABASE_CREATE, DatabaseScript.SCRIPT_DATABASE_DELETE);
		db = dbHelper.getWritableDatabase();
	}
	
	/** Closes database connection */
	public void close(){
		super.close();
		if(dbHelper != null){
			dbHelper.close();
		}
	}
}
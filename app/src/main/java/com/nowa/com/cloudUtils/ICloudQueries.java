package com.nowa.com.cloudUtils;

import java.util.HashMap;
import java.util.List;

import com.parse.ParseObject;

public interface ICloudQueries {

	public void saveInBackGround(String className, HashMap<String, String> keyParameters);
	public void save(String className, HashMap<String, String> keyParameters);
	public void updateInBackground(String className, final HashMap<String, String> keyParameters, String id);
	public List<ParseObject> getObjectInBackground(String className, String key, String value);
	public List<ParseObject> getObject(String className, String key, String value);
	public List<ParseObject> getAllObjects(String className);
	public void deleteObject(String className, String key, String value);
	
}

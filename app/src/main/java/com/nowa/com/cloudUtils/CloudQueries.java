package com.nowa.com.cloudUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;

import com.nowa.com.dao.GeneralDao;
import com.nowa.com.domain.Entity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class CloudQueries implements ICloudQueries {

	public interface ILocalPersist {
		public void localInsert(String className, HashMap<String, String> params);
	}
	
	private List<ParseObject> object = null;
	private Context ctx;
	
	public CloudQueries(Context ctx) {
		this.ctx = ctx;
	}

    @Override
	public void saveInBackGround(String className, HashMap<String, String> keyParameters){
		final ParseObject object = new ParseObject(className);
		Set<String> keys = keyParameters.keySet();
		for(String key : keys)
			object.put(key, keyParameters.get(key));
		try {
			object.saveInBackground();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public void save(final String className, final HashMap<String, String> keyParameters){
		final ParseObject object = new ParseObject(className);
		Set<String> keys = keyParameters.keySet();
		for(String key : keys)
			object.put(key, keyParameters.get(key) == null ? "" : keyParameters.get(key));
        object.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    if (keyParameters.containsKey(Entity._ID))
                        keyParameters.put(Entity._ID, object.getObjectId());
                    ILocalPersist localPersist = new GeneralDao(ctx) {
                        @Override
                        public Map<String, String> parseObjectToMap(Object obj) {
                            return null;
                        }
                    };
                    localPersist.localInsert(className, keyParameters);
                }
            }
        });
	}

    @Override
	public void updateInBackground(String className, final HashMap<String, String> keyParameters, String id){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
		query.getInBackground(id, new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					Set<String> keys = keyParameters.keySet();
					for (String key : keys)
						object.put(key, keyParameters.get(key));
					object.saveInBackground();
				}
			}
		});
	}

    @Override
	public List<ParseObject> getObjectInBackground(String className, String key, String value){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
		query.whereEqualTo(key, value);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {
					object = scoreList;
				} else {
					e.printStackTrace();
				}
			}
		});
		return object;
	}

    @Override
	public List<ParseObject> getObject(String className, String key, String value){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
		query.whereEqualTo(key, value);
		try {
			object = query.find();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return object;
	}

	public List<ParseObject> getObject(String className, String key, String[] value){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.whereContainedIn(key, Arrays.asList(value));
		try {
			object = query.find();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return object;
	}

    @Override
	public List<ParseObject> getAllObjects(String className) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
		try {
			object = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return object;
	}

    @Override
	public void deleteObject(String className, String key, String value) {
		getObject(className, key, value);

		if(object != null && !object.isEmpty())
			object.get(0).deleteInBackground();
	}
	
}
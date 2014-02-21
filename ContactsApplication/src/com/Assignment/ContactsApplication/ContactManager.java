package com.Assignment.ContactsApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ContactManager {

	public static final String KEY_ROWID = "_id";
    public static final String KEY_FIRSTNAME = "first_name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_HOBBY = "hobby";
    public static final String KEY_LOCATION = "location";
    
    private static final String DATABASE_NAME = "Contacts";
    private static final String DATABASE_TABLE = "Contact_Details";
    private static final int DATABASE_VERSION = 1;

    // 
    private static final String DATABASE_CREATE =
        "create table Contact_Details (_id integer primary key autoincrement, " +
        			 "first_name text not null, " +
        			 "surname text null, " +
        			 "mobile text null, " +
        			 "phone text null, " +
        			 "hobby text null, " +
        			 "location text null);";
    
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private String[] columns;
    private String[] personColumns;
    private String[] mobile;

    // 
    public ContactManager(Context ctx) 
    {
		// 
	this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        columns = new String [] {KEY_ROWID, KEY_FIRSTNAME, KEY_SURNAME, KEY_MOBILE, KEY_PHONE, KEY_HOBBY, KEY_LOCATION};
        personColumns  = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_SURNAME};
        mobile = new String[] {KEY_MOBILE};
    }
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {

	// 
	DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
	// 
	public void onCreate(SQLiteDatabase db) 
    {
       	db.execSQL(DATABASE_CREATE);
    }

        @Override

	// 
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
		// whatever is to be changed on dB structure
   
	}
    }   // 

    
   // 
    public ContactManager open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // 
    public void close() 
    {
        DBHelper.close();
    }
    
    //
    public long insertPerson(String firstName, String surname, String mobile, String phone, String hobby, String location) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRSTNAME, firstName);
        initialValues.put(KEY_SURNAME, surname);
        initialValues.put(KEY_MOBILE, mobile);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_HOBBY, hobby);
        initialValues.put(KEY_LOCATION, location);
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //
    public boolean deletePerson(long rowId) 
    {
	// 
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }

    //
    public Cursor getAllPeople() 
    {
        return db.query(DATABASE_TABLE, new String[] 
		{
        		KEY_ROWID, 
        		KEY_FIRSTNAME,
        		KEY_SURNAME}, 
                null,  
                null, 
                null, 
                null, 
                null);
    }

    //
    public Cursor getPerson(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_FIRSTNAME, 
                		KEY_SURNAME,
                		KEY_MOBILE,
                		KEY_PHONE
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public boolean updatePerson(long rowId, String firstName, String surname, String mobile, String phone, String hobby, String location) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ROWID, rowId);
        args.put(KEY_FIRSTNAME, firstName);
        args.put(KEY_SURNAME, surname);
        args.put(KEY_MOBILE, mobile);
        args.put(KEY_PHONE, phone);
        args.put(KEY_HOBBY, hobby);
        args.put(KEY_LOCATION, location);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public String [] getColumns() 
    {
		return new String [] {KEY_FIRSTNAME, KEY_SURNAME, KEY_MOBILE , KEY_PHONE, KEY_HOBBY, KEY_LOCATION};
	}
    
    public Cursor getAllContacts() 
    {
		return db.query(DATABASE_TABLE, columns, null, null, null, null, null);
	}
    
    public Cursor getSavedPeople()
    {
    	System.out.println(db.query(DATABASE_TABLE, personColumns, null, null, null, null, null));
    	return db.query(DATABASE_TABLE, personColumns, null, null, null, null, null);
    }
    
    public String [] getSavedPeopleColumns() 
    {
		return new String [] {KEY_FIRSTNAME, KEY_SURNAME};
	}
    
    public Cursor getPhoneNumer()
    {
    	return db.query(DATABASE_TABLE, mobile, null, null, null, null, null);
    }
    
    public String[] getDetails()
    {
    	return new String[] {KEY_FIRSTNAME, KEY_SURNAME, KEY_MOBILE, KEY_PHONE, KEY_HOBBY, KEY_LOCATION};
    }
}


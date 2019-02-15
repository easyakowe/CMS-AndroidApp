package com.example.precious_lord.aedc_cms;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper DBhelper;
    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "MainTable.db";

    // Users table
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "user_id";
    private static final String USER_FULL_NAME = "user_fullname";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_PASSWORD = "user_password";

    // User queries
    String queryCreateUser = "CREATE TABLE " + USER_TABLE + "(" + USER_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_FULL_NAME +
            " TEXT," + USER_EMAIL + " TEXT," + USER_PHONE + " INTEGER, "
            + USER_PASSWORD + " TEXT" + ")";

    String queryCreateStaff = "CREATE TABLE " + StaffContract.StaffEntry.STAFF_TABLE + "(" +
            StaffContract.StaffEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + StaffContract.StaffEntry.STAFF_ID + " TEXT," +
            StaffContract.StaffEntry.STAFF_FULL_NAME + " TEXT," +
            StaffContract.StaffEntry.STAFF_EMAIL + " TEXT," +
            StaffContract.StaffEntry.STAFF_PHONE + " TEXT," +
            StaffContract.StaffEntry.STAFF_PASSWORD + " TEXT" + ")";

    String queryCreateComplaint = "CREATE TABLE " + ComplaintContract.ComplaintEntry.COMPLAINT_TABLE + "(" +
            ComplaintContract.ComplaintEntry.COMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ComplaintContract.ComplaintEntry.COMP_EMAIL + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_LOCATION + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_CATEGORY + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_SUB_CATEGORY + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_TYPE + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_NATURE + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_DETAIL + " TEXT," +
            ComplaintContract.ComplaintEntry.COMP_REPLY + " TEXT" + ")";

    String queryDrop = "DROP TABLE IF EXISTS " + USER_TABLE;

    String queryDropComplaint = "DROP TABLE IF EXISTS " + ComplaintContract.ComplaintEntry.COMPLAINT_TABLE;

    String queryDropStaff = "DROP TABLE IF EXISTS " + StaffContract.StaffEntry.STAFF_TABLE;

    // Complaint query

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreateUser);
        db.execSQL(queryCreateStaff);
        db.execSQL(queryCreateComplaint);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(queryDrop);
        db.execSQL(queryDropComplaint);
        db.execSQL(queryDropStaff);
        onCreate(db);
    }

    //-- Opens the database
    public DatabaseHelper open() throws SQLException{
        db = DBhelper.getWritableDatabase();
        return this;
    }

    //-- Closes the database
    public void close(){
        DBhelper.close();
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_FULL_NAME, user.getFullname());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_PASSWORD, user.getPassword());

        db.insert(USER_TABLE, null, values);
        db.close();
    }

    public void addStaff(Staff staff){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StaffContract.StaffEntry.STAFF_ID, staff.getStaff_id());
        values.put(StaffContract.StaffEntry.STAFF_FULL_NAME, staff.getFullname());
        values.put(StaffContract.StaffEntry.STAFF_EMAIL, staff.getEmail());
        values.put(StaffContract.StaffEntry.STAFF_PHONE, staff.getPhone());
        values.put(StaffContract.StaffEntry.STAFF_PASSWORD, staff.getPassword());

        db.insert(StaffContract.StaffEntry.STAFF_TABLE, null, values);
        db.close();
    }

    public void addComplaint(Complaint complaint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ComplaintContract.ComplaintEntry.COMP_EMAIL, complaint.getEmail());
        values.put(ComplaintContract.ComplaintEntry.COMP_LOCATION, complaint.getLocation());
        values.put(ComplaintContract.ComplaintEntry.COMP_CATEGORY, complaint.getCategory());
        values.put(ComplaintContract.ComplaintEntry.COMP_SUB_CATEGORY, complaint.getSub_category());
        values.put(ComplaintContract.ComplaintEntry.COMP_TYPE, complaint.getComplaint_type());
        values.put(ComplaintContract.ComplaintEntry.COMP_NATURE, complaint.getComplaint_nature());
        values.put(ComplaintContract.ComplaintEntry.COMP_DETAIL, complaint.getComplaint_detail());

        db.insert(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, null, values);
        db.close();
    }

    public int updateEmail(String oldEmail, String newEmail, String password){
        //UPDATE USER_TABLE SET email='ikj@kjs.com' where email=?test
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, newEmail);
        String[] args = {oldEmail, password};
        int count = db.update(USER_TABLE, values, USER_EMAIL+ "=?"
                + " AND " + USER_PASSWORD + "=?", args);

        return count;
    }

    public int deleteUser(String email){
        //DELETE * FROM USER_TABLE Where email=?
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {email};
        int count = db.delete(USER_TABLE, USER_EMAIL + "=?", args);
        return count;
    }

    public int deleteComplaint(){
        String[] columns = {
                ComplaintContract.ComplaintEntry.COMP_EMAIL,
                ComplaintContract.ComplaintEntry.COMP_LOCATION,
                ComplaintContract.ComplaintEntry.COMP_CATEGORY,
                ComplaintContract.ComplaintEntry.COMP_NATURE,
                ComplaintContract.ComplaintEntry.COMP_DETAIL
        };

        SQLiteDatabase db = getReadableDatabase();
        int count = db.delete(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, ComplaintContract.ComplaintEntry.COMP_ID + "=?", columns);
        return count;
    }

    public int replyComplaint(String message, String reply){
        //UPDATE USER_TABLE SET email='ikj@kjs.com' where email=?test
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ComplaintContract.ComplaintEntry.COMP_REPLY, reply);
        String[] args = {message};
        int count = db.update(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, values,
                ComplaintContract.ComplaintEntry.COMP_DETAIL+ "=?", args);

        return count;
    }

    public int editComplaint(String subject, String message){
        //UPDATE USER_TABLE SET email='ikj@kjs.com' where email=?test
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ComplaintContract.ComplaintEntry.COMP_DETAIL, message);

        String[] args = {subject};
        int count = db.update(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, values,
                ComplaintContract.ComplaintEntry.COMP_NATURE + "=?", args);

        return count;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {USER_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(USER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkStaff(String staff_id, String password){
        String[] columns = {StaffContract.StaffEntry.ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = StaffContract.StaffEntry.STAFF_ID + " = ?" + " AND " + StaffContract.StaffEntry.STAFF_PASSWORD + " =?";
        String[] selectionArgs = { staff_id, password };

        Cursor cursor = db.query(StaffContract.StaffEntry.STAFF_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser1(String email){
        String[] columns = {USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(USER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public List<Complaint> getAllComplaint(){
        // array of columns to fetch
        String [] columns = {

                ComplaintContract.ComplaintEntry.COMP_EMAIL,
                ComplaintContract.ComplaintEntry.COMP_LOCATION,
                ComplaintContract.ComplaintEntry.COMP_CATEGORY,
                ComplaintContract.ComplaintEntry.COMP_NATURE,
                ComplaintContract.ComplaintEntry.COMP_DETAIL,
                ComplaintContract.ComplaintEntry.COMP_REPLY
        };
        //Sorting orders
        String sortOrder = ComplaintContract.ComplaintEntry.COMP_EMAIL + " ASC";
        List<Complaint> complaintList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, columns,
                null,
                null,
                null,
                null,
                sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Complaint complaint = new Complaint();
                complaint.setEmail(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_EMAIL)));
                complaint.setLocation(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_LOCATION)));
                complaint.setCategory(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_CATEGORY)));
                complaint.setComplaint_nature(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_NATURE)));
                complaint.setComplaint_detail(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_DETAIL)));
                complaint.setComplaint_reply(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_REPLY)));


                // Adding the complaints to list
                complaintList.add(complaint);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list
        return complaintList;
    }

    public List<Complaint> getMyComplaint(String email){
        // array of columns to fetch
        String [] columns = {

                ComplaintContract.ComplaintEntry.COMP_EMAIL,
                ComplaintContract.ComplaintEntry.COMP_LOCATION,
                ComplaintContract.ComplaintEntry.COMP_CATEGORY,
                ComplaintContract.ComplaintEntry.COMP_NATURE,
                ComplaintContract.ComplaintEntry.COMP_DETAIL,
                ComplaintContract.ComplaintEntry.COMP_REPLY
        };
        //Sorting orders
        String sortOrder = ComplaintContract.ComplaintEntry.COMP_EMAIL + " ASC";
        List<Complaint> complaintList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = ComplaintContract.ComplaintEntry.COMP_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(ComplaintContract.ComplaintEntry.COMPLAINT_TABLE, columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Complaint complaint = new Complaint();
                complaint.setEmail(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_EMAIL)));
                complaint.setLocation(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_LOCATION)));
                complaint.setCategory(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_CATEGORY)));
                complaint.setComplaint_nature(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_NATURE)));
                complaint.setComplaint_detail(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_DETAIL)));
                complaint.setComplaint_reply(cursor.getString(cursor.getColumnIndex(ComplaintContract.ComplaintEntry.COMP_REPLY)));


                // Adding the complaints to list
                complaintList.add(complaint);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list
        return complaintList;
    }
}

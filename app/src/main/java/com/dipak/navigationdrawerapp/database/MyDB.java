package com.dipak.navigationdrawerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dipak.navigationdrawerapp.model.Contact;
import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {

    public static final int DB_VERSION=1;
    public static final String DB_NAME="demo";
    public static final String TABLE_USER="users";


    //customer table
    private static final String TABLE_CONTACT = "contact";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_IMAGE = "image";

    // SQL query to create the customer table
    private static final String CREATE_TABLE_CUSTOMER =
            "CREATE TABLE " + TABLE_CONTACT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT  , " +
                    COLUMN_NUMBER + " TEXT , " +
                    COLUMN_IMAGE + " BLOB" +
                    ")";

    public MyDB(Context context)
    {
        super(context,  DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+ TABLE_USER+"(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "email TEXT, "
                + "phone TEXT, "
                + "islogin INTEGER, "
                + "password TEXT"+")";

        sqLiteDatabase.execSQL(query);    //imp
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);    //imp
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query="DROP TABLE IF EXISTS "+ TABLE_USER;
        db.execSQL(query);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public boolean register(User obj){
        String name=obj.getName();
        String email=obj.getEmail();
        String phone=obj.getPhone();
        String password=obj.getPassword();
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("islogin",0);
        contentValues.put("password",password);

        long l=sqLiteDatabase.insert(TABLE_USER, null,contentValues );
        sqLiteDatabase.close();

        if(l>0)
        {
            return true;
        }else{
            return false;
        }

    }

    public boolean addContact(Contact contact ){

            int id=contact.getId();
            String name=contact.getName();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String number=contact.getPhone();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, name);
            contentValues.put(COLUMN_NUMBER, number);

            if(id<=0) {

                long l = sqLiteDatabase.insert(TABLE_CONTACT, null, contentValues);
                sqLiteDatabase.close();

                if (l > 0) {
                    return true;
                } else {
                    return false;
                }
            }else{

                String whereClause = "id=?";
                String[] whereArgs = {String.valueOf(id)};

                sqLiteDatabase.update(TABLE_CONTACT, contentValues, whereClause, whereArgs);

                // Close the database connection

                sqLiteDatabase.close();
                return true;
            }

    }

    public List<Contact> getAllCustomers() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(  TABLE_CONTACT,null, null, null, null, null, null );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID ));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));

                Contact contact = new Contact(id,name,phoneNumber, R.drawable.baseline_account_circle_24);

                contactList.add(contact);
            }
            cursor.close();
        }
        return contactList;
    }

    public boolean deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = TABLE_CONTACT;
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(contact.getId())};

        // Perform the delete operation
        int i=db.delete(tableName, selection, selectionArgs);
        System.out.println("db rows: "+i);

        // Close the database connection
        db.close();
        return  true;
    }

    public Contact getContact(int pid){

        SQLiteDatabase db = this.getReadableDatabase();

        // Specify the conditions for selecting
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(pid)};

        // Perform the query
        Cursor cursor = db.query(TABLE_CONTACT, null, selection, selectionArgs, null, null, null);

        Contact contact = null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));

            // Create a Contact object with retrieved data
            contact = new Contact(id,name,phoneNumber);
            cursor.close();
        }

        return contact;
    }

    public User loginValid(String email, String pass)
    {


        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM users WHERE email = '"+email+"' AND password = '"+pass+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String  dbEmail= cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String dbpass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            return new User(id,name,dbEmail,phoneNumber,dbpass);
        }else{
           return null;
        }

//      BELOW CODE IS FOR OTHER WAY

//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_EMAIL};
//        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
//        String[] selectionArgs = {email, password};
//        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
//
//        int count = cursor.getCount();
//        cursor.close();
//        return count > 0;

    }


    public boolean changeLoginStatus(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("islogin", 1); // Assuming 1 represents a logged-in state

        // Specify the conditions for updating
        String whereClause = "email = ? AND password = ?";
        String[] whereArgs = {email, pass};

        // Perform the update
        int cnt=db.update("users", values, whereClause, whereArgs);
        if(cnt>0)
        {
            return true;
        }else{
            return false;
        }
    }

    public int isLogin(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // Specify the columns you want to retrieve
        String[] columns = {"islogin"};

        // Specify the conditions for selecting
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        // Perform the query
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        int isLoginValue = 0; // Default value, you can use any appropriate default value

        try {
            if (cursor.moveToFirst()) {
                // Extract the value from the cursor
                int columnIndex = cursor.getColumnIndex("islogin");
                if (columnIndex != -1) {
                    isLoginValue = cursor.getInt(columnIndex);
                } else {
                    // Handle the case where the column doesn't exist
                    // You might want to log an error or use a default value
                    // For now, we'll log an error
                    Log.e("DatabaseHelper", "Column 'islogin' not found in the result set");
                }
            }
        } finally {
            cursor.close();
        }

        return isLoginValue;
    }
}

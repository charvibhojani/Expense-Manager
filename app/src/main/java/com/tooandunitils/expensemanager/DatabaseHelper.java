package com.tooandunitils.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my_database_1.db";

    public static final String TABLE_NAME = "income";
    public static final String COLUMN_INC_CATEGORY = "category";
    public static final String COLUMN_INC_PRICE = "price";

    public static final String TABLE_NAME_2 = "expense";
    public static final String COLUMN_EXP_CATEGORY = "category";
    public static final String COLUMN_EXP_PRICE = "price";

    public static final String TABLE_NAME_3 = "recent_transaction";
    public static final String COLUMN_TRANS_CATEGORY = "category";
    public static final String COLUMN_TRANS_PRICE = "price";
    public static final String COLUMN_TRANS_TIME = "time";
    public static final String COLUMN_TRANS_TYPE = "type";

    public static final String TABLE_NAME_4 = "Sign_In_Data";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_NAME_5 = "addcategory";
    public static final String COLUMN_CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_INC_CATEGORY + " TEXT ," + COLUMN_INC_PRICE + " TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXP_CATEGORY + " TEXT ," + COLUMN_EXP_PRICE + " TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_NAME_3 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TRANS_CATEGORY + " TEXT ," + COLUMN_TRANS_PRICE + " TEXT," + COLUMN_TRANS_TIME + " TEXT, " + COLUMN_TRANS_TYPE + " TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_NAME_4 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT ," + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME_5 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CATEGORY + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_5);

        onCreate(db);
    }

    public long insertdata(String category, String price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INC_CATEGORY, category);
        values.put(COLUMN_INC_PRICE, price);
        long rowId = db.insert(TABLE_NAME, null, values);

        return rowId;

    }

    public long insertdata_exp(String category, String price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXP_CATEGORY, category);
        values.put(COLUMN_EXP_PRICE, price);
        long rowId = db.insert(TABLE_NAME_2, null, values);
        db.close();
        return rowId;
    }

    public long recent_trans(String category, String price, String time, String type) {

        long rowId = 0;

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANS_CATEGORY, category);
            values.put(COLUMN_TRANS_PRICE, price);
            values.put(COLUMN_TRANS_TIME, time);
            values.put(COLUMN_TRANS_TYPE, type);
            rowId = db.insert(TABLE_NAME_3, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowId;

    }

    public boolean adddata(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long id = db.insert(TABLE_NAME_4, null, values);
        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean fatchemail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_4 + " WHERE " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean emailExists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return emailExists;
    }

    public String getPasswordandemail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME_4 + " WHERE " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        String password = null;
        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            cursor.close();
        }
        return password;
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        int rowsAffected = db.update(TABLE_NAME_4, values, COLUMN_EMAIL + " = ?", new String[]{email});
        return rowsAffected > 0;
    }

    public Cursor readdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "select * from " + TABLE_NAME_3 + " ORDER BY " + COLUMN_TRANS_CATEGORY + " DESC";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(qry, null);
        }
        return cursor;
    }

    public boolean adddata_trans(String category) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);

        long id = db.insert(TABLE_NAME_5, null, values);
        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void UpdateCategory(String category, String price, String type, int pos) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_INC_CATEGORY, category);
        values.put(COLUMN_INC_PRICE, price);

        if (type.equals("income")) {

            db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(pos)});

        } else if (type.equals("expense")) {

            db.update(TABLE_NAME_2, values, "id=?", new String[]{String.valueOf(pos)});

        }

        db.close();

    }

    public void trans_data(String category, String price, int pos) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_INC_CATEGORY, category);
        values.put(COLUMN_INC_PRICE, price);

        db.update(TABLE_NAME_3, values, "id=?", new String[]{String.valueOf(pos)});

        db.close();

    }

    public Boolean DeleteCategory_expense(Context ctx, int pos) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from expense where id = ?", new String[]{String.valueOf(pos)});

        if (cursor.getCount() > 0) {

            long result = db.delete("expense", "id = ?", new String[]{String.valueOf(pos)});

            if (result == -1) {

                return false;

            } else {

                return true;

            }
        } else {

            return false;
        }
    }

    public Boolean DeleteCategory_income(Context ctx, int pos) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from income where id = ?", new String[]{String.valueOf(pos)});

        if (cursor.getCount() > 0) {

            long result = db.delete("income", "id = ?", new String[]{String.valueOf(pos)});

            if (result == -1) {

                return false;

            } else {

                return true;

            }
        } else {

            return false;
        }
    }
}
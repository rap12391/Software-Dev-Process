package seclass.gatech.edu.sdpvocabquiz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDO extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "username";
    public static final String COL_2 = "major";
    public static final String COL_3 = "seniority";
    public static final String COL_4 = "email";

    public StudentDO(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creating data base");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username TEXT, major TEXT, seniority TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        System.out.println("Dropping table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void persistStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, student.m_username);
        values.put(COL_2, student.m_major);
        values.put(COL_3, student.m_seniority);
        values.put(COL_4, student.m_email);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean isStudentPresent(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String searchQuery="Select * from " + TABLE_NAME + " where username =" +"'"+ username+"'";
        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.getCount() > 0){

            cursor.moveToFirst();
            String str = cursor.getString(cursor.getColumnIndex("username"));
            System.out.println("Found it");
            System.out.println(str);
            cursor.close();
            return true;

        }else
        {
            cursor.close();
            System.out.println("Not here");
            return false;
        }
    }
}

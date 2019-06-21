package seclass.gatech.edu.sdpvocabquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class QuizDO extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="Quiz.db";
    public static final String TABLE_NAME = "quiz_table";
    public static final String COL_1 = "quiz_name";
    public static final String COL_2 = "username";
    public static final String COL_3="correct_definition";;
    public static final String COL_4="incorrect_definition";


    public QuizDO(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        System.out.println("Called constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        System.out.println("Creating data base");
        db.execSQL("create table if not exists " +TABLE_NAME+" (quiz_name TEXT, username TEXT, word TEXT, correct_definition TEXT, incorrect_definition TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void persistQuiz(Quiz quiz){
        //TODO: Store Quiz data in DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, quiz.getQuizName());
        values.put(COL_2, quiz.getUsername());
        values.put(COL_3,new JSONObject(quiz.getShuffledWords()).toString() );
        JSONArray jsArray = new JSONArray(quiz.getIncorrectDefs());
        String arrayList = jsArray.toString();
        values.put(COL_4,arrayList);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void removeQuiz(String quizName) {
        //TODO: Remove Quiz data in DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"quiz_name = ?", new String[] {quizName});
    }

    public Map<String, Quiz> getQuizzes() {
        //TODO: Return Quiz list from DB by most recent
        SQLiteDatabase db = this.getWritableDatabase();
        String searchQuery="Select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(searchQuery, null);

        Map<String,Quiz> returnedEntry = new HashMap<String,Quiz>();
        if(cursor.getCount() > 0) {
            System.out.println("The size is " + cursor.getCount());
            cursor.moveToFirst();
            do {
                final String queriedQuizName = cursor.getString(cursor.getColumnIndex("quiz_name"));
                final String queriedUser = cursor.getString(cursor.getColumnIndex("username"));
                String queriedCorrect = cursor.getString(cursor.getColumnIndex("correct_definition"));
                String queriedInCorrect = cursor.getString(cursor.getColumnIndex("incorrect_definition"));


                String[] splits = queriedInCorrect.replace("[", "").replace("]", "").split(",");
                ArrayList<String> inCorrectList = new ArrayList<>(Arrays.asList(splits));

                Map<String, String> correctMap =  new HashMap<String,String>();
                try{
                    correctMap = jsonToMap(queriedCorrect);
                }catch ( JSONException e)
                {}

                final Quiz quizObject = new Quiz(queriedQuizName, "Test", correctMap, inCorrectList, queriedUser);
                returnedEntry.put(queriedQuizName, quizObject);
            }while(cursor.moveToNext());
        }
        return returnedEntry;
    }

    // Converts the json string into a map
    public Map<String,String> jsonToMap(String t) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }
}

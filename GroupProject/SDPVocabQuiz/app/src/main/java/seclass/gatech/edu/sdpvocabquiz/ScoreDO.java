package seclass.gatech.edu.sdpvocabquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScoreDO extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Score.db";
    public static final String TABLE_NAME = "score_table";
    public static final String COL_1 = "quiz_name";
    public static final String COL_2 = "username";
    public static final String COL_3 = "date";;
    public static final String COL_4 = "score";


    public ScoreDO(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        System.out.println("Called constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        System.out.println("Creating data base");
        db.execSQL("create table if not exists " +TABLE_NAME+" (quiz_name TEXT, username TEXT, date TEXT, score FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void persistScore(Score score) {
        //ToDo: Store Stats data in DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, score.quizName);
        values.put(COL_2, score.username);
        values.put(COL_3, score.date.toString());
        values.put(COL_4, score.scoreAsPercentage);
        db.insert(TABLE_NAME, null, values);
    }

    public void removeQuizStats(String quizName) {
        //TODO: Remove Quiz data from Scores table
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"quiz_name = ?", new String[] {quizName});
    }


    public Pair<Score, Score> getFirstAndHighestScores(String quizName, String username) {
        //TODO: Get scores by quizName and username. At most two.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_first = db.query(TABLE_NAME, null, "quiz_name = ? AND " + "username = ?", new String[] {quizName, username}, null, null, COL_3+" ASC", "1"); //filter by quiz name and username, order by date
        Cursor cursor_highest = db.query(TABLE_NAME, null, "quiz_name = ? AND " + "username = ?", new String[] {quizName, username}, null, null, COL_4+" DESC", "1"); //filter by quiz name and username, order by score

        cursor_first.moveToFirst();
        if(cursor_first.getCount() > 0 && cursor_highest.getCount() > 0) {
            String first_quiz = cursor_first.getString(cursor_first.getColumnIndex("quiz_name"));
            String first_user = cursor_first.getString(cursor_first.getColumnIndex("username"));
            Float first_score = cursor_first.getFloat(cursor_first.getColumnIndex("score"));

            cursor_highest.moveToFirst();

            String highest_quiz = cursor_highest.getString(cursor_first.getColumnIndex("quiz_name"));
            String highest_user = cursor_highest.getString(cursor_first.getColumnIndex("username"));
            Float highest_score = cursor_highest.getFloat(cursor_first.getColumnIndex("score"));

            return new Pair<>(new Score(first_quiz, first_user, new Date(), first_score),
                    new Score(highest_quiz, highest_user, new Date(), highest_score));
        }
        else
        {
            return new Pair <>(new Score("","",new Date(),0.0f),
                    new Score("","",new Date(),0.0f));
        }
    }

    public ArrayList<String> getFirstThreePerfect(String quizName) {
        //TODO: Get scores by quizName and username. At most two.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "quiz_name = ?", new String[] {quizName}, null, null, COL_4+" DESC," + COL_2+" DESC","3"); //sort by score and then name and limit to top 3 in sort
        ArrayList<String> topScoresList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && topScoresList.size()<3) {
            final Float queriedScore = Float.parseFloat(cursor.getString(cursor.getColumnIndex("score")));
            final String queriedUser = cursor.getString(cursor.getColumnIndex("username"));
            if (queriedScore == 100.f) {
                topScoresList.add(queriedUser);
            } else {
                topScoresList.add("");
            }
            cursor.moveToNext();
        }
        return topScoresList;

    }
}

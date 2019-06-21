package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizStatisticsActivity extends AppCompatActivity {
    public ScoreDO scoreDatabase;
    public TextView tvOut1;
    public TextView tvOut2;
    public TextView tvOut3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_statistics);

        scoreDatabase = new ScoreDO(this);

        Intent intent = getIntent();
        String quizName = intent.getStringExtra("quizname");
        String username = intent.getStringExtra("username");
        Log.i("stats", quizName+" "+username);

        System.out.println("Quiz Stats: ");
                System.out.println(quizName);
        System.out.println(username);
        scoreDatabase.getFirstAndHighestScores(quizName,username);

        Pair<Score, Score> pair = scoreDatabase.getFirstAndHighestScores(quizName, username);
        Score firstScore = pair.first;
        Score highestScore = pair.second;

        System.out.println("score 1: "+firstScore.getScore());
                System.out.println("score 2: "+highestScore.getScore());
        TextView firstScoreField = (TextView) findViewById(R.id.tv1);
        firstScoreField.setText(Float.toString(firstScore.getScore()));

        TextView highestScoreField = (TextView) findViewById(R.id.tv2);
        highestScoreField.setText(Float.toString(highestScore.getScore()));
        System.out.println("This is what should be in here "+Float.toString(highestScore.getScore()));

        ArrayList<String> names = new ArrayList<String>();
        names = scoreDatabase.getFirstThreePerfect(quizName);

        String displayNames="";
        for(String name : names)
        {
          displayNames+=name+",";
        }

        TextView topThreeField = (TextView) findViewById(R.id.tv3);
        topThreeField.setText(displayNames);

//
//        Log.i("stats", firstScore.toString());
//
//        tvOut1 = (TextView) findViewById(R.id.tv1);
//        if (firstScore == null) {
//            tvOut1.setText("No records for now.");
//        }else{
//            tvOut1.setText("Date: " + firstScore.date + ", Score: " + firstScore.scoreAsPercentage);
//        }
//

//        ArrayList<String> top3List = scoreDatabase.getFirstThreePerfect(quizName);
//
//        tvOut3 = (TextView) findViewById(R.id.tv3);
//        tvOut3.setText("");
//        for (String s: top3List){
//            tvOut3.append(s + "\n");
//        }

//
//        Pair<Score,Score> firstAndHighestScores = scoreDatabase.getFirstAndHighestScores(quizName, username);
//
//        String firstScore = String.format("%.2f", firstAndHighestScores.first.scoreAsPercentage);
//
//        Date firstDate = firstAndHighestScores.first.date;
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
//        String firstDateString = dateFormat.format(firstDate);
//
//        //TODO: Display First score with formatted Date
//
//        String secondScore = String.format("%.2f", firstAndHighestScores.second.scoreAsPercentage);
//
//        Date secondDate = firstAndHighestScores.second.date;
//        String secondDateString = dateFormat.format(secondDate);
//
//        //TODO: Display Second score with formatted Date
//
//        ArrayList<String> firstThreePerfect = scoreDatabase.getFirstThreePerfect(quizName);
//        for (String s: firstThreePerfect) {
//            String perfUsername = s;
//            //TODO: Populate each perfect score in UI
//
//        }
//
//        firstscorename = (EditText)findViewById(R.id.editText_firstscore);
//        firstscorename.setText(firstScore);
//        firstscoredate = (EditText)findViewById(R.id.editText_firstscoredate);
//        firstscoredate.setText(firstDateString);
//
//        highscorename = (EditText)findViewById(R.id.editText_highscore);
//        highscorename.setText(secondScore);
//        highscoredate = (EditText)findViewById(R.id.editText_highscoredate);
//        highscoredate.setText(secondDateString);
//
//        String perfectuser1 = firstThreePerfect.get(0);
//        String perfectuser2 = firstThreePerfect.get(1);
//        String perfectuser3 = firstThreePerfect.get(2);
//
//        perfectscore1 = (EditText)findViewById(R.id.editText_perfect1);
//        perfectscore1.setText(perfectuser1);
//
//        perfectscore2 = (EditText)findViewById(R.id.editText_perfect2);
//        perfectscore2.setText(perfectuser2);
//
//        perfectscore3 = (EditText)findViewById(R.id.editText_perfect3);
//        perfectscore3.setText(perfectuser3);
    }
}

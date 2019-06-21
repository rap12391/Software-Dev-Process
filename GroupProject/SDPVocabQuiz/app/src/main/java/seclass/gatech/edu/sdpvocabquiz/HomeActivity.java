package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static seclass.gatech.edu.sdpvocabquiz.LoginManagerActivity.LOGIN_MESSAGE_USERNAME;

public class HomeActivity extends AppCompatActivity {
    public static final String SELECT_QUIZ_USERNAME = "seclass.gatech.edu.sdpvocabquiz.SELECT_QUIZ_USERNAME";
//    public static final String USERNAME = "seclass.gatech.edu.sdpvocabquiz.USERNAME";
    String username;
    public QuizDO quizDatabase;
    public ScoreDO scoreDatabase;
    Map<String, Quiz> quizList;
    public Spinner quizSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Create quiz database instance
        quizDatabase = new QuizDO(this);
        scoreDatabase = new ScoreDO(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // when going back from SelectPracticeQuizActivity, username will be null
        if (username == null){
            username = intent.getStringExtra(SelectPracticeQuizActivity.SELECT_QUIZ_USERNAME);
        }
        if (username == null){
            username = intent.getStringExtra(SelectReviewQuizActivity.SELECT_QUIZ_USERNAME);
        }

//        Log.i("username@HomeActivity", username);


        // Set up practice quiz button
        Button practiceQuizButton = (Button) findViewById(R.id.pr);
        practiceQuizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                practice();
            }
        });

        // Set up add quiz button
        Button addQuizButton = (Button) findViewById(R.id.homeAddQuiz);
        addQuizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addQuiz();
            }
        });

        // Set up the view quiz stats button
        Button viewQuizStatsButton = (Button) findViewById(R.id.viewStatsButton);
        viewQuizStatsButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewQuizStatistics();
            }
        });

        // Set up remove quiz button
        Button removeQuizButton = (Button) findViewById(R.id.remove_button);
        removeQuizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeQuiz();
            }
        });

        // load spinner data upon creation
        loadSpinnerData();
    }

    public void practice() {
        Intent selectQuizIntent = new Intent(this, SelectPracticeQuizActivity.class);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        System.out.println("practice() name "+username);
        selectQuizIntent.putExtra("username", username);
        startActivity(selectQuizIntent);
    }

    public void viewQuizStatistics(){
        Intent intent = new Intent(this, SelectReviewQuizActivity.class);
        Intent now = getIntent();
        String username = now.getStringExtra("username");
        System.out.println("viewquizstatistics "+username);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void addQuiz() {

        Intent quiz = new Intent(this, QuizzActivityDefs.class);

        // THIS IS JUST A TEST TO SEE IF IT CAN PERSIST IN THE DATABASE
        //addQuizInstance();

        //TODO: ADD THIS ACTIVITY TRANSITION BACK
        System.out.println("Value before going into activity"+username);
        quiz.putExtra("LOGIN_MESSAGE_USERNAME", username);
        startActivity(quiz);
    }

    public void removeQuiz() {

        // Check if the spinner is empty
        if(quizSpinner.getSelectedItem() == null)
        {
            // Return since there is anything to remove
            return;
        }
        // Get the quiz name from the spinner
        String quizName = quizSpinner.getSelectedItem().toString();

        quizDatabase.removeQuiz(quizName);
        scoreDatabase.removeQuizStats(quizName);
        loadSpinnerData();
    }

    public void pr2(){
        Intent pr = new Intent(this, PracticeActivity.class);
        Quiz q = new Quiz("testQuiz", "Quiz used in Practice Session", new HashMap<String,String>(){{put("a","1");put("b","2");}},
                new ArrayList<String>(){{add("66");add("67");add("68");add("69");add("70");add("71");}}, "dguzman34");
        pr.putExtra(SelectPracticeQuizActivity.QUIZ, q);
        startActivity(pr);
    }

//    // NOTE: THIS IS A TEST FUNCTION WILL BE REMOVED LATER
//    public void addQuizInstance() {
//        Map<String, String> correctDefs = new HashMap<String, String>() {{
//            put("Earth", "Third planet of the solar system");
//            put("GeorgiaTech", "Best college in the world!");
//        }};
//        ArrayList<String> incorrectDefs = new ArrayList<String>();
//        incorrectDefs.addAll(Arrays.asList("Best location for Reality Shows", "A planet-shaped insane asylum, containing mostly insects", "Not Mars",
//                "Second best college in the world :(", "What is it again?", "A place with full of jackets"));
//        Quiz quizItem = new Quiz("testQuiz", "This is a test Quiz", correctDefs, incorrectDefs, username);
//        Quiz quizItem2 = new Quiz("testQuiz1", "This is a test Quiz", correctDefs, incorrectDefs, username);
//        Quiz quizItem3 = new Quiz("testQuiz2", "This is a test Quiz", correctDefs, incorrectDefs, username);
//        Quiz quizItem4 = new Quiz("testQuiz3", "This is a test Quiz", correctDefs, incorrectDefs, username);
//        quizDatabase.persistQuiz(quizItem);
//        quizDatabase.persistQuiz(quizItem2);
//        quizDatabase.persistQuiz(quizItem3);
//        quizDatabase.persistQuiz(quizItem4);
//        loadSpinnerData();
//    }

    // Call to update spinner base on database data
    public void loadSpinnerData() {
        // STUFF FOR LOADING SPINNER
        // GETTING SPINNER VIEW
        quizSpinner = (Spinner) findViewById(R.id.seniority_spinner3);

        //Extract the quiz name
        Map<String,Quiz> quizMap = quizDatabase.getQuizzes();

        List<String> spinnerArray =  new ArrayList<String>();
        for (String key : quizMap.keySet()){
            // Populate spinner
            spinnerArray.add(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quizSpinner.setAdapter(adapter);
    }
}

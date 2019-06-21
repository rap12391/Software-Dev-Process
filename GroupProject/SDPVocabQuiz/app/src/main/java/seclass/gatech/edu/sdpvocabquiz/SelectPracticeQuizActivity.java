package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPracticeQuizActivity extends AppCompatActivity {
    public static final String QUIZ = "seclass.gatech.edu.sdpvocabquiz.QUIZ_NAME";
    public static final String SELECT_QUIZ_USERNAME = "seclass.gatech.edu.sdpvocabquiz.SELECT_QUIZ_USERNAME";
    public Spinner quizSpinner;
    public QuizDO quizDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("NameInPractice");

        quizDatabase = new QuizDO(this);
        addQuizInstance();



        // Set up go to practice button
        Button goToPracticeButton = (Button) findViewById(R.id.startQuizButton);
        goToPracticeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                quizSpinner = (Spinner) findViewById(R.id.selectQuizSpinner);
                if (quizSpinner.getSelectedItem() != null) {
                    if(quizSpinner.getSelectedItem() == null)
                    {
                        return;
                    }
                    String quizName = quizSpinner.getSelectedItem().toString();

                    Map<String, Quiz> quizMap = quizDatabase.getQuizzes();
                    Quiz quiz = quizMap.get(quizName);

                    goToPractice(quiz);
                }
                else{
                    // do nothing
                }
            }
        });

        // Set up back to homeActivity button
        Button backToHomeActivityButton = (Button) findViewById(R.id.backToHomeActivityFromSeclectQuiz);
        backToHomeActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBackToHomeActivity(username);
            }
        });
    }

    private void goToPractice(Quiz quiz) {
        Intent practiceQuiz = new Intent(SelectPracticeQuizActivity.this, PracticeActivity.class);

        Intent intent = getIntent();
        System.out.println("GoToPracticename is "+intent.getStringExtra("username"));
        practiceQuiz.putExtra("username",intent.getStringExtra("username"));
        practiceQuiz.putExtra(QUIZ, quiz);
        startActivity(practiceQuiz);
    }

    private void goBackToHomeActivity(String username) {
        Intent homeActivityIntent = new Intent(SelectPracticeQuizActivity.this, HomeActivity.class);
        homeActivityIntent.putExtra("username", username);
        startActivity(homeActivityIntent);
    }

//    private String getQuizFromSpinner(QuizDO quizDatabase){
//
//        Map<String, Quiz> quizMap = quizDatabase.getQuizzes("testQuiz");
//        return quizMap;
//    }

    // NOTE: THIS IS A TEST FUNCTION WILL BE REMOVED LATER
    public void addQuizInstance() {
        Map<String, String> correctDefs = new HashMap<String, String>() {{
            put("Earth", "Third planet of the solar system");
            put("GeorgiaTech", "Best college in the world!");
        }};
        ArrayList<String> incorrectDefs = new ArrayList<String>();
        incorrectDefs.addAll(Arrays.asList("Best location for Reality Shows", "A planet-shaped insane asylum, containing mostly insects", "Not Mars",
                "Second best college in the world :(", "What is it again?", "A place with full of jackets"));
        Intent intent = getIntent();
        String usernameInstance = intent.getStringExtra("username");
        Quiz quizItem = new Quiz("testQuiz", "This is a test Quiz", correctDefs, incorrectDefs, usernameInstance);
        quizDatabase.persistQuiz(quizItem);
        loadSpinnerData();
    }

    // Call to update spinner base on database data
    public void loadSpinnerData() {
        // STUFF FOR LOADING SPINNER
        // GETTING SPINNER VIEW
        quizSpinner = (Spinner) findViewById(R.id.selectQuizSpinner);

        //Extract the quiz by username
        String username = getIntent().getStringExtra("username");
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

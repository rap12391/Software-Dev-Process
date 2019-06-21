package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectReviewQuizActivity extends AppCompatActivity {
    public static final String QUIZ_NAME = "seclass.gatech.edu.sdpvocabquiz.QUIZ_NAME";
    public static final String USERNAME = "seclass.gatech.edu.sdpvocabquiz.USERNAME";
    public static final String SELECT_QUIZ_USERNAME = "seclass.gatech.edu.sdpvocabquiz.SELECT_QUIZ_USERNAME";

    public Spinner quizSpinner;
    public QuizDO quizDatabase;

    private Map<String, Quiz> quizzes;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_review_quiz);

        quizDatabase = new QuizDO(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // back button
        Button backToHomeButton = findViewById(R.id.backToHomeFromSelectQuizStats);
        backToHomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goHome(username);
            }
        });

        // next button
        Button nextButton = findViewById(R.id.goToSelectReviewQuizButton);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToStats(username);
            }
        });


        loadSpinnerData();
    }

    private void goToStats(String username) {
        Intent intent = new Intent(this, QuizStatisticsActivity.class);
        intent.putExtra("username", username);
        System.out.println("name in gotostats"+username);
        if(quizSpinner.getSelectedItem() == null) {
            return;
        }
        String quizName = quizSpinner.getSelectedItem().toString();
        intent.putExtra("quizname",quizName);
        startActivity(intent);
    }

    private void goHome(String username) {
        Intent homeActivityIntent = new Intent(SelectReviewQuizActivity.this, HomeActivity.class);
        homeActivityIntent.putExtra("username", username);
        startActivity(homeActivityIntent);
    }

    public void loadSpinnerData() {
        // STUFF FOR LOADING SPINNER
        // GETTING SPINNER VIEW
        quizSpinner = (Spinner) findViewById(R.id.selectSpinner);

        //Extract the quiz by username
        String username = getIntent().getStringExtra("username");
        Map<String, Quiz> quizMap = quizDatabase.getQuizzes();

        List<String> spinnerArray = new ArrayList<String>();
        for (String key : quizMap.keySet()) {
            // Populate spinner
            spinnerArray.add(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quizSpinner.setAdapter(adapter);
    }

//    public void quizSelect(){
//        //TODO: Get quiz selection from UI
//        String quizName = "testQuiz";
//        Intent viewQuizStats = new Intent(this, QuizStatisticsActivity.class);
//        viewQuizStats.putExtra(QUIZ_NAME, quizName);
//        viewQuizStats.putExtra(USERNAME, username);
//        startActivity(viewQuizStats);
//    }
}

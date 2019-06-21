package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PracticeActivity extends AppCompatActivity {
            public static final String SCORE = "seclass.gatech.edu.sdpvocabquiz.SCORE";
    private PracticeSession session;
    private Pair<String,String> currentWord;
    public ScoreDO scoreDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        scoreDatabase = new ScoreDO(this);
        Intent intent = getIntent();
        Quiz quiz = intent.getParcelableExtra(SelectPracticeQuizActivity.QUIZ);
        String pulledUsername = intent.getStringExtra("username");
        System.out.println("The quiz name is "+quiz.getQuizName());
        System.out.println("The user name is "+pulledUsername);
        session = new PracticeSession(quiz.getQuizName(), pulledUsername, quiz.getShuffledWords(), quiz.getIncorrectDefs());

        displayWord();

        Button practiceBack = (Button) findViewById(R.id.PracticeBack);
        practiceBack.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RadioGroup wordChoices = (RadioGroup) findViewById(R.id.wordChoices);
        int count = wordChoices.getChildCount();
        for (int i=0;i<count;i++) {
            View o = wordChoices.getChildAt(i);
            if (o instanceof RadioButton) {
                final RadioButton b = (RadioButton)o;
                b.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        selectedDefinition(b.getText());
                    }
                });
            }
        }
    }

    public void nextWord(){
        if(session.isNextWordPresent()){
            displayWord();
        }else{
            Intent completedPracticeQuiz = new Intent(this, CompletedPracticeActivity.class);
            completedPracticeQuiz.putExtra(SCORE, session.getScore());
            completedPracticeQuiz.putExtra("username",session.getUsername());
            scoreDatabase.persistScore(session.getFinalScore());
            startActivity(completedPracticeQuiz);
        }
        RadioGroup wordChoices = (RadioGroup) findViewById(R.id.wordChoices);
        wordChoices.clearCheck();
    }

    private void displayWord() {
        currentWord  = session.getAndRemoveWord();
        List<String> incorrectDefs = session.getAnyThreeIncorrectDefs();
        List<String> allDefs = new ArrayList<>();
        allDefs.addAll(incorrectDefs);
        allDefs.add(currentWord.second);

        Collections.shuffle(allDefs);

        TextView editTextPW = (TextView) findViewById(R.id.editTextPW);
        editTextPW.setText(currentWord.first);

        RadioGroup wordChoices = (RadioGroup) findViewById(R.id.wordChoices);

        int count = wordChoices.getChildCount();
        for (int i=0;i<count;i++) {
            View o = wordChoices.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton c = (RadioButton)o;
                String s = allDefs.get(i);
                c.setText(s);
            }
        }

        TextView practiceWordResult = (TextView) findViewById(R.id.PracticeWordResult);
        practiceWordResult.setText("");
    }

    public void selectedDefinition(CharSequence text){
        TextView practiceWordResult = (TextView) findViewById(R.id.PracticeWordResult);
        if(currentWord.second.equals(text.toString())){
            session.incrementCorrect();
            practiceWordResult.setText("Correct :)");
        }else{
            practiceWordResult.setText("Incorrect :(");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                nextWord();
            }
        }, 800);
    }
}

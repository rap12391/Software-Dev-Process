package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizzActivityIncorrect extends AppCompatActivity {
    public ArrayList<String> inCorrectDefinitionsList;
    public QuizDO quizDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_incorrect_defs);

        //Initialize globals
        inCorrectDefinitionsList = new ArrayList<String>();
        quizDatabase = new QuizDO(this);

        // Getting the button view from the back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button defsAdd = (Button) findViewById(R.id.add_button);
        defsAdd.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                EditText definitionField = (EditText) findViewById(R.id.incorrect_definition);
                String inCorrectDefinition = definitionField.getText().toString();
                if(inCorrectDefinition != null)
                {
                    inCorrectDefinitionsList.add(inCorrectDefinition);
                    // Clear the definition field for the next input
                    definitionField.setText("");
                }

            }
        });

        Button finishButton = (Button) findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveQuiz();
            }
        });
    }

    // Saves the quiz into the quiz database table
    public void saveQuiz()
    {
        Quiz quiz = (Quiz) getIntent().getParcelableExtra("PassedInQuiz");
        if(inCorrectDefinitionsList.size() < (quiz.getShuffledWords().size()*3))
        {
            int difference = quiz.getShuffledWords().size()*3 - inCorrectDefinitionsList.size();
            EditText definitionField = (EditText) findViewById(R.id.incorrect_definition);
            definitionField.setError("Not enough incorrect answers. Please add "+difference+" more");
            return;
        }
        // set the incorrect definitions
        quiz.setIncorrectDefs(inCorrectDefinitionsList);

        // save the quiz
        quizDatabase.persistQuiz(quiz);

        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username",quiz.getUsername());
        startActivity(intent);
    }
}

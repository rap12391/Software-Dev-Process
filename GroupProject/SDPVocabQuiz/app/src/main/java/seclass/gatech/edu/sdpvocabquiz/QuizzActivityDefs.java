package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.addAll;

public class QuizzActivityDefs extends AppCompatActivity {
    public QuizDO quizDatabase;
    public EditText word1;
    public EditText correctDefinition1;
    public Map<String,String> correctDefinitionsMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_words_defs);

        // Create database instance
        quizDatabase = new QuizDO(this);

        // Create correct definitions mapping
        correctDefinitionsMap = new HashMap<String,String>();

        Button defsNext = (Button) findViewById(R.id.add_quiz_defs_next);
        defsNext.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goNext();
            }
        });

        Button defsAdd = (Button) findViewById(R.id.add_next_def);
        defsAdd.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                EditText wordField = (EditText) findViewById(R.id.word1_text);
                EditText definitionField = (EditText) findViewById(R.id.correct_definition1);
                String word = wordField.getText().toString();
                String correctDefinition = definitionField.getText().toString();
                System.out.println("output");
                System.out.println(word);
                System.out.println(correctDefinition);
                if(word != null && correctDefinition != null)
                {
                    correctDefinitionsMap.put(word,correctDefinition);
                    wordField.setText("");
                    definitionField.setText("");
                }

            }
        });


        Button defsBack = (Button) findViewById(R.id.add_quiz_defs_back);
        defsBack.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get the first word and definition
        word1 = (EditText) findViewById(R.id.word1_text);
        correctDefinition1 = (EditText) findViewById(R.id.correct_definition1);

    }

    private void goNext() {
        Intent incorrectDefs = new Intent(this, QuizzActivityIncorrect.class);

        // Form quiz object that will get passed into the next activity
        String quizName = ((EditText) findViewById(R.id.quiz_name_field)).getText().toString();
        String quizDescription = ((EditText) findViewById(R.id.quiz_description_field)).getText().toString();
        ArrayList<String> emptyIncorrectDefinitions = new ArrayList<String>();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("LOGIN_MESSAGE_USERNAME");
        System.out.println("Name before going into incorrect"+userName);
        Quiz newQuiz = new Quiz(quizName, quizDescription,correctDefinitionsMap, emptyIncorrectDefinitions,userName);

        // Pass quiz into next activity
        incorrectDefs.putExtra("PassedInQuiz", newQuiz);

        startActivity(incorrectDefs);
    }

    public void addQuiz(){
        //TODO: Get Quiz attributes from UI
        //TODO: Validate against empty quiz entries
        Map<String, String> correctDefs = new HashMap<String, String>()
        {{
            put("Earth", "Third planet of the solar system");
            put("GeorgiaTech", "Best college in the world!");
        }};
        ArrayList<String> incorrectDefs = new ArrayList<String>();
        incorrectDefs.addAll(Arrays.asList("Best location for Reality Shows", "A planet-shaped insane asylum, containing mostly insects", "Not Mars",
            "Second best college in the world :(", "What is it again?", "A place with full of jackets"));
        Quiz quiz = new Quiz("testQuiz", "This is a test Quiz", correctDefs, incorrectDefs, "david");
        quizDatabase.persistQuiz(quiz);
    }
}

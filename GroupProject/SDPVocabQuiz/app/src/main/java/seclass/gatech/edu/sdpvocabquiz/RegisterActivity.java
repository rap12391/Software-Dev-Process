package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    // Create student database
    private StudentDO studentDatabase;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        studentDatabase = new StudentDO(this);

        // Getting the button view from the finish button
        finishButton = (Button) findViewById(R.id.Finish_button);

        // Create button handler for logging in
        finishButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: Avoid registering empty fields
                registerStudent();
                finish();
            }
        });

        // Getting the button view from the back button
        Button backButton = (Button) findViewById(R.id.Back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void registerStudent(){
        // Get EditText Field from user name input
        EditText usernameField = (EditText) findViewById(R.id.username_inp);
        // Get EditText Field from major input
        EditText majorField = (EditText) findViewById(R.id.major_inp);
        // Get EditText Field from seniority
        Spinner seniorityField = (Spinner) findViewById(R.id.seniority_spinner);
        // Get EditText Field from email input
        EditText emailField = (EditText) findViewById(R.id.email_inp);

        String username = usernameField.getText().toString();
        String seniority = seniorityField.getSelectedItem().toString();
        String major = majorField.getText().toString();
        String email = emailField.getText().toString();
        Student student = new Student(username, seniority, major, email);
        studentDatabase.persistStudent(student);
    }
}

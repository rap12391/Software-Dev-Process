package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class LoginManagerActivity extends AppCompatActivity {
    // Following example from https://developer.android.com/training/basics/firstapp/starting-activity#java
    public static final String LOGIN_MESSAGE_USERNAME = "seclass.gatech.edu.sdpvocabquiz.LOGIN_USERNAME";

    StudentDO studentDatabase;
    // Create login and register buttons
    public EditText usernameField;
    public Button loginButton;
    public Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentDatabase = new StudentDO(this);

        // Get EditText Field from user name input
        usernameField = (EditText) findViewById(R.id.Username_inp);

        // Get button view
        loginButton = (Button) findViewById(R.id.login_button);

        // Create button handler for logging in
        loginButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                login(username);
            }
        });

        // Get register button view
        registerButton = (Button) findViewById(R.id.Reg_button);

        registerButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void login(String username){
        boolean studentPresent = studentDatabase.isStudentPresent(username);
        if(studentPresent){
            Intent homeIntent = new Intent(LoginManagerActivity.this, HomeActivity.class);
            homeIntent.putExtra("username", username);
            startActivity(homeIntent);
        }else{
            usernameField.setError("Username does not exist. Please register.");
        }
    }

    public void register(){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

}

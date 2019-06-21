package seclass.gatech.edu.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static seclass.gatech.edu.sdpvocabquiz.LoginManagerActivity.LOGIN_MESSAGE_USERNAME;

public class CompletedPracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_practice);

        Intent intent = getIntent();
        float score = intent.getFloatExtra(PracticeActivity.SCORE, 0F);

        TextView completePScore = (TextView) findViewById(R.id.CompletePScore);
        completePScore.setText(String.format("%.1f", score));

        Button practiceCompleteBack = (Button) findViewById(R.id.PracticeCompleteBack);
        practiceCompleteBack.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                home();
            }
        });
    }

    public void home(){
        Intent home = new Intent(this, HomeActivity.class);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        System.out.println("username in cometed is "+username);
        home.putExtra("username",username);
        startActivity(home);
    }
}

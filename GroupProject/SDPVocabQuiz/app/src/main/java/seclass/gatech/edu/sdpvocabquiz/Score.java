package seclass.gatech.edu.sdpvocabquiz;

import java.util.Date;

class Score {
    String quizName;
    String username;
    Date date;
    float scoreAsPercentage;

    public Score(String quizName, String username, Date date, float scoreAsPercentage) {
        this.quizName = quizName;
        this.username = username;
        this.date = date;
        this.scoreAsPercentage = scoreAsPercentage;
    }

    public float getScore()
    {
        return scoreAsPercentage;
    }

    @Override
    public String toString() {
        return "Score{" +
                "quizName='" + quizName + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", scoreAsPercentage=" + scoreAsPercentage +
                '}';
    }
}

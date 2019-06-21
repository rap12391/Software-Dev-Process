package seclass.gatech.edu.sdpvocabquiz;

import android.util.Pair;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class PracticeSession {
    private String quizName;
    private String username;
    private float scoreAsPercentage;
    private int numberOfCorrectWords;
    private int numberOfWords;
    private Map<String,String> shuffledWords;
    private List<String> incorrectDefinitions;

    public PracticeSession(String quizName, String username, Map<String,String> shuffledWords, List<String> incorrectDefinitions){
        scoreAsPercentage = 0f;
        numberOfCorrectWords = 0;
        this.quizName = quizName;
        this.username = username;
        this.shuffledWords = shuffledWords;
        this.incorrectDefinitions = incorrectDefinitions;
        this.numberOfWords = shuffledWords.size();
    }

    public boolean isNextWordPresent(){
        return shuffledWords.keySet().iterator().hasNext();
    }

    public Pair<String, String> getAndRemoveWord() {
        String key = shuffledWords.keySet().iterator().next();
        return new Pair<>(key, shuffledWords.remove(key));
    }

    public List<String> getAnyThreeIncorrectDefs() {
        int bound = incorrectDefinitions.size();
        int i1 = ThreadLocalRandom.current().nextInt(bound);
        int i2 = i1;
        while(i2 == i1){
            i2 = ThreadLocalRandom.current().nextInt(bound);
        }

        int i3 = i1;
        while(i3 == i1 || i3 == i2){
            i3 = ThreadLocalRandom.current().nextInt(bound);
        }

        return Arrays.asList(incorrectDefinitions.get(i1), incorrectDefinitions.get(i2), incorrectDefinitions.get(i3));
    }

    public float getScore() {
        return scoreAsPercentage;
    }

    public Score getFinalScore(){
        Date date = new Date();

        return new Score(quizName, username, date, scoreAsPercentage);
    }

    public String getUsername()
    {
        return username;
    }

    public void incrementCorrect() {
        numberOfCorrectWords++;
        updateScore();
    }

    private void updateScore() {
        scoreAsPercentage = numberOfCorrectWords * 100.0f / numberOfWords;
    }
}

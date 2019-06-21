package seclass.gatech.edu.sdpvocabquiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Quiz implements Parcelable {
    private final String quizName;
    private final String description;
    private final Map<String, String> correctDefs;
    private ArrayList<String> incorrectDefs;
    private final String username;

    public Quiz(Parcel in){
        this.quizName = in.readString();
        this.description = in.readString();
        this.correctDefs = in.readHashMap(String.class.getClassLoader());
        this.incorrectDefs = in.readArrayList(String.class.getClassLoader());
        this.username = in.readString();
    }

    public Quiz(String quizName, String description, Map<String,String> correctDefs, ArrayList<String> incorrectDefs, String username){
        this.quizName = quizName;
        this.description = description;
        this.correctDefs = correctDefs;
        this.incorrectDefs = incorrectDefs;
        this.username = username;
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public String getQuizName() {
        return quizName;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, String> getShuffledWords(){
        Map<String, String> shuffled = new HashMap<>();
        List<String> keys = new ArrayList(correctDefs.keySet());
        Collections.shuffle(keys);

        for (String n : keys) {
            shuffled.put(n, correctDefs.get(n));
        }
        return shuffled;
    }

    public ArrayList<String> getIncorrectDefs(){
        return  incorrectDefs;
    }

    public void setIncorrectDefs(ArrayList<String> list)
    {
        incorrectDefs = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quizName);
        dest.writeString(description);
        dest.writeMap(correctDefs);
        dest.writeList(incorrectDefs);
        dest.writeString(username);
    }
}

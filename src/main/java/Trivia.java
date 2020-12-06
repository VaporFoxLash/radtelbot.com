import java.util.HashMap;
import java.util.Map;

public class Trivia extends Radtelbot {

    private String question;
    private int score;
    Map<String, String> questionsMap = new HashMap<>();

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore() {
        return score;
    }

//    public
}

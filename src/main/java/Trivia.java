import java.util.HashMap;
import java.util.Map;

public interface Trivia {

    String question = "";
    int score = 0;
    Map<String, String> questionsMap = new HashMap<>();

    void setScore(int score);

    int getScore();

    void ask();

}

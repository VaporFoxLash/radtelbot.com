import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Trivia {

    String question = "";
    int score = 0;
    List<String> quizRawList = new ArrayList<>();
    Map<String, String> questionsMap = new HashMap<>();

    void setScore(int score);

    int getScore();

    void ask();

}

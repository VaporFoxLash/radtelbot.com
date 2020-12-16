import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Quiz {
    String question,
            op1,
            op2,
            correct_answer;

    public Quiz () {
        this.question = question ;
        this.op1 = op1;
        this.op2 = op2;
        this.correct_answer = correct_answer;
    }


    public String getQuestions() throws IOException, FileNotFoundException {
        final InputStream inputStream = this.getClass().getResourceAsStream("/Quiz.txt");
        String newLine = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String line; (line = reader.readLine()) != null; ) {
            result.append(flag? newLine: "").append(line);
            flag = true;
        }
        return result.toString();
    }

    public List<String> quizList() throws IOException {
        Trivia.quizRawList.addAll(Arrays.asList(getQuestions().split("\\r?\\n")));
        return Trivia.quizRawList;
    }

}

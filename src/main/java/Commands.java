import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Commands {
    String searchWiki(String sub);
    Map<String, String> command_map = new  HashMap<>();
    ArrayList command_list = new ArrayList();
    void addCommand(String[] cmd);
    void addMap(String def, String cmd);
}

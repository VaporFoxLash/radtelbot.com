import java.lang.reflect.Array;
import java.util.*;

public interface Commands {
    String searchWiki(String sub);
    Map<String, String> command_map = new  LinkedHashMap<>();
    List<String> command_list = new ArrayList<>();
    void addCommand(String[] cmd);
    void addMap(String def, String cmd);
}

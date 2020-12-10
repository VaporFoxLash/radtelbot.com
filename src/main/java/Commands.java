import org.telegram.telegrambots.api.objects.Update;

import java.lang.reflect.Array;
import java.util.*;

public interface Commands {
    void getName(Update update, long chat_id);

    void getFullName(Update update, long chat_id);

    String searchWiki(String sub);
    Map<String, String> command_map = new  LinkedHashMap<>();
    List<String> command_list = new ArrayList<>();
    void addCommand(String[] cmd);
    void addMap(String def, String cmd);
}

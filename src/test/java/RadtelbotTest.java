import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.api.objects.Update;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class RadtelbotTest {
    private Radtelbot bot = new Radtelbot();

    @Test
    void test_getUsername() {
        assertEquals("BotName", bot.getBotUsername());
    }


    @Test
    void test_searchWiki() {
        assertEquals("#REDIRECT [[Java (programming language)]]\n" +
                        " link:https://en.wikipedia.org/w/index.php?action=raw&title=Java_programming",
                bot.searchWiki("Java programming"));
    }


    @Test
    void test_addCommands() {
        String[] command = {"search"};
        bot.addCommand(command);
        assertEquals("search", Commands.command_list.get(0));
    }


    @Test
    void test_addMap() {
        bot.addMap("searching", "search");
        assertEquals("search", Commands.command_map.get("searching"));
    }



    @Test
    void test_getName() {
        Update update = new Update();
        Radtelbot radtelbot = mock(Radtelbot.class);
        radtelbot.getName(update,1);

        verify(radtelbot, times(1)).getName(update,1);
    }



    @Test
    void test_getFullName() {
        Update update = new Update();
        Radtelbot radtelbot = mock(Radtelbot.class);
        radtelbot.getFullName(update,6);

        verify(radtelbot, times(1)).getFullName(update,1);
    }



    @Test
    void test_onUpdateRecieved() {
        Update update = new Update();
        Radtelbot radtelbot = mock(Radtelbot.class);
        radtelbot.onUpdateReceived(update);

        verify(radtelbot, times(1)).onUpdateReceived(update);
    }



}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class RadtelbotTest {
    private Radtelbot bot = new Radtelbot();

    @Test
    void test_getUsername() {
        assertEquals("BotName", bot.getBotUsername());
    }

    @Test
    void test_getTokenName() {
        assertEquals("1486539422:AAH2Po2rRY--tVsizLegJXlcO0KJ-2I5s38", bot.getBotToken());
    }

    @Test
    void test_getUsername_false() {
        assertNotEquals("Name", bot.getBotUsername());
    }

    @Test
    void test_searchWiki() {
        assertEquals("#REDIRECT [[Java (programming language)]]\n" +
                " link:https://en.wikipedia.org/w/index.php?action=raw&title=Java_programming",
                bot.searchWiki("Java programming"));
    }


}
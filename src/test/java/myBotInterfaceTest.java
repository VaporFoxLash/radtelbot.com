import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



public class myBotInterfaceTest {

    @Test
    void test_addCommands_size() {
        assertEquals(3, myBotInterface.addCommands().size());
    }

}

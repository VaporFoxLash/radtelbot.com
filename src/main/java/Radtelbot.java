import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Telegram bot
 * @author Radebe Donald
 * @author Rayan Tamim
 * @version 1.0
 * @since 23.11.2020
 */


public class Radtelbot extends TelegramLongPollingBot implements Commands{

    String[] quiz = { "",
             "What is the default value of String variable?",
            "What is the default value of Boolean variable?", "What is an immutable object?"};
    String[][][] answers = {{{ "8 bit", "False", "null"}}};

    Quiz[] quizObj = {
            new Quiz("A progrmming language", "Russkey", "Blisp", "Java", "Java"),
            new Quiz("What is the size of byte variable?", "8 bit", "", "", "")
    };

    /**
     * Method for creating a message and sending it.
     * @param chat_id chat id
     * @param str The String that you want to send as a message.
     */
    public synchronized void sendMsg(Update update, long chat_id, String str) {
        SendMessage messag = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(str);
        try {
            sendMessage(messag);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method returns the bot's name, which was specified during registration.
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return "BotName";
    }

    /**
     * This method returns the bot's token for communicating with the Telegram server
     * @return the bot's token
     */
    @Override
    public String getBotToken() {
        return "1486539422:AAH2Po2rRY--tVsizLegJXlcO0KJ-2I5s38";
    }

    @Override
    public void addMap(String def, String cmd){
        command_map.put(def, cmd);
    }

    /**
     * This method takes an array of commands and add them to the command_list arraylist.
     * @param cmd
     */
    @Override
    public void addCommand(String[] cmd) {
        Collections.addAll(command_list, cmd);
    }


    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {

        String command = update.getMessage().getText();

        SendMessage message = new SendMessage();

        long chat_id = update.getMessage().getChatId();

        addMap("For help", "/start");
        addMap("Display your username>", "/name");
        addMap("Display your fullname", "/fullname");
        addMap("For wiki search", "/wiki_search");
        addMap("To test your java and OOP knowledge", "/Trivia");

        String[] cmd = {"/start", "/name", "/lastname", "/fullname", "/Trivia", "/wiki_search",
                "Java", "OOP", "Polymorphism", "Inheritance", "Encapsulation", "Home"};
        addCommand(cmd);

//        System.out.println(command_list);
        switch(command_list.indexOf(command)) {
            case 0:
                ArrayList<String> details = new ArrayList<>();
                for (Map.Entry<String, String> entry : command_map.entrySet()) {
                    details.add(entry.getKey() + " ----> " + entry.getValue());
                }
                sendMsg(update, chat_id, "To control the bot use the following commands: \n" +
                        details.get(0)+"\n" +
                        details.get(1)+"\n" +
                        details.get(2)+"\n" +
                        details.get(3)+"\n" +
                        details.get(4));
                break;
            case 1:
                sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName());
                break;
            case 2:
                sendMsg(update, chat_id, update.getMessage().getFrom().getLastName());
                break;
            case 3:
                sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName()+" "
                        +update.getMessage().getFrom().getLastName());
                break;
            case 4:
                triviaVariants(chat_id);
                break;
            case 5:
                wikiKeyBoard(chat_id);
                break;
            case 7:
                System.out.println(command);
                sendMsg(update, chat_id, searchWiki("Java programming"));
                System.out.println(searchWiki("Java programming"));
                break;
            case 8:
                System.out.println(command);
                sendMsg(update, chat_id, searchWiki("Object-oriented programming"));
                break;
            case 9:
                System.out.println(command);
                sendMsg(update, chat_id, searchWiki("Polymorphism (computer science)"));
                break;
            case 10:
                sendMsg(update, chat_id, searchWiki("Inheritance (object-oriented programming)"));
                break;
            case 11:
                sendMsg(update, chat_id, searchWiki("Encapsulation (computer programming)"));
                break;
            default:
                System.out.println(command);
                sendMsg(update, chat_id, "Welcome, to get started type /start");
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        command_list.clear();
        command_map.clear();

    }

    public void triviaVariants(long chat_id){
        SendMessage messag = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Question:\n"+quizObj[0].question);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op1).setCallbackData(quizObj[0].op1));
        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op2).setCallbackData(quizObj[0].op2));
        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op3).setCallbackData(quizObj[0].op3));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        messag.setReplyMarkup(markupInline);
        try {
            execute(messag); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void wikiKeyBoard(long chat_id) {
        SendMessage messag = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Select what to search from wikipedia");
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button
        for (int i = 6; i < 9; i++) {
            row.add(command_list.get(i));
        }
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        for (int i = 9; i < 12; i++) {
            row.add(command_list.get(i));
        }
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        messag.setReplyMarkup(keyboardMarkup);

        try {
            sendMessage(messag);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for taking a string to search in wikipedia and return results
     * @param subject The String that we use as key to search.
     */
    @Override
    public String searchWiki(String subject) {
        URL url = null;
        try {
            url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String text = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line = null;
            while (null != (line = br.readLine())) {
                line = line.trim();
                if (!line.startsWith("|")
                        && !line.startsWith("{")
                        && !line.startsWith("}")
                        && !line.startsWith("<center>")
                        && !line.startsWith("---")) {
                    text += line;
                }
                if (text.length() > 200) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text + "\n link:"+url;
    }

}
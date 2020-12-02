import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


interface myBotInterface {
    HashMap command_list = new  HashMap<>();
    static HashMap addCommands(){
        command_list.put("Display your username", "/name");
        command_list.put("Display your fullname", "/fullname");
        command_list.put("For help", "/help");
        return command_list;
    }

}

public class radtelbot extends TelegramLongPollingBot implements myBotInterface{

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

    public String searchWiki(String subject){
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


    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {

        String command=update.getMessage().getText();

        SendMessage message = new SendMessage();

        long chat_id = update.getMessage().getChatId();


        if(command.equals("/start")){
            sendMsg(update, chat_id, "To control the bot use the following commands: \n" +
                    "Display your username-> \"/name\"\n" +
                    "Display your lastname-> /lastname\n" +
                    "Display your fullname-> /fullname\n" +
                    "Get a quick wikisearch-> /wiki_search");
        }

        //command to get user first name
        if(command.equals("/name")){
            sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName());
        }

        //command to get user last name
        if(command.equals("/lastname")){
            sendMsg(update, chat_id, update.getMessage().getFrom().getLastName());
        }

        //command to get user full name
        if (command.equals("/fullname")){
            sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName()+" "
                            +update.getMessage().getFrom().getLastName());
        }


        //command to get objects to search on wikipedia, with buttons assign to each object
        if (command.equals("/wiki_search")){
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
            row.add("Java");
            row.add("OOP");
            row.add("Polymorphism");
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
            // Set each button for the second line
            row.add("Inheritance");
            row.add("Encapsulation");
            row.add("Home");
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

        //command to get Wikipedia page for Java
        if (command.equals("Java")){
            sendMsg(update, chat_id, searchWiki("Java programming"));
        }

        //command to get Wikipedia page for OOP
        if (command.equals("OOP")){
            sendMsg(update, chat_id, searchWiki("Object-oriented programming"));
        }

        //command to get Wikipedia page for polymorphism
        if (command.equals("Polymorphism")){
            sendMsg(update, chat_id, searchWiki("Polymorphism (computer science)"));
        }

        //command to get Wikipedia page for
        if (command.equals("Inheritance")){
            sendMsg(update, chat_id, searchWiki("Inheritance (object-oriented programming)"));
        }

        //command to get Wikipedia page for Encapsulation
        if (command.equals("Encapsulation")){
            sendMsg(update, chat_id, searchWiki("Encapsulation (computer programming)"));
        }

        //command to get back to home and make it easy to navigate
        if (command.equals("Home")){
            sendMsg(update, chat_id, "Welcome, to get started type /start");
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));

//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
}


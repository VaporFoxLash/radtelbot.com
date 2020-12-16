import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.*;
import java.net.URL;

import static java.lang.Math.toIntExact;

/**
 * Telegram bot
 * @author Radebe Donald
 * @author Rayan Tamim
 * @version 1.0
 * @since 23.11.2020
 */


public class Radtelbot extends TelegramLongPollingBot implements Commands{
    int count = 0;
    /**
     * Method for creating a message and sending it.
     * @param chat_id chat id
     * @param str The String that you want to send as a message.
     */
    public synchronized void sendMsg(Update update, long chat_id, String str) {
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();


        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setText(str);

        try {
            editMessageText(new_message);
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

    @Override
    public void getName(Update update, long chat_id) {
        sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName());
    }

    @Override
    public void getFullName(Update update, long chat_id) {
        sendMsg(update, chat_id, update.getMessage().getFrom().getFirstName()+" "
                +update.getMessage().getFrom().getLastName());
    }


//    public void quizButton(Update update) throws IOException {
//        //update
//        String call_data = update.getCallbackQuery().getData();
//        long message_id = update.getCallbackQuery().getMessage().getMessageId();
//        long chat_id = update.getCallbackQuery().getMessage().getChatId();
//        int count = 0;
//
//        Quiz quiz = new Quiz();
//
//        String str = quiz.quizList().get(count);
//        List<String> questions = new ArrayList<>();
//        questions.addAll(Arrays.asList(str.split(", ")));
//        quiz.question = questions.get(0);
//        quiz.op1 = questions.get(1);
//        quiz.op2 = questions.get(2);
//        quiz.correct_answer = questions.get(3);
//
//        String squiz = questions.get(0);
//        EditMessageText new_message = new EditMessageText()
//                .setChatId(chat_id)
//                .setMessageId(toIntExact(message_id))
//                .setText(squiz);
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//        rowInline.add(new InlineKeyboardButton().setText(questions.get(1)).setCallbackData(questions.get(1)));
//        rowInline.add(new InlineKeyboardButton().setText(questions.get(2)).setCallbackData(questions.get(2)));
//        rowsInline.add(rowInline);
//
//        // Add it to the message
//        markupInline.setKeyboard(rowsInline);
//        new_message.setReplyMarkup(markupInline);
//        try {
//            editMessageText(new_message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        Quiz quiz = new Quiz();

        String str = null;
        try {
            str = quiz.quizList().get(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> questions = new ArrayList<>();
        questions.addAll(Arrays.asList(str.split(", ")));
        quiz.question = questions.get(0);
        quiz.op1 = questions.get(1);
        quiz.op2 = questions.get(2);
        quiz.correct_answer = questions.get(3);

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            addMap("For help", "/start");
            addMap("Display your username>", "/name");
            addMap("Display your fullname", "/fullname");
            addMap("For wiki search", "/wiki_search");
            addMap("To test your java and OOP knowledge", "/Trivia");

            String[] cmd = {"/start", "/name", "/lastname", "/fullname", "/Trivia", "/wiki_search",
                    "Java", "OOP", "Polymorphism", "Inheritance", "Encapsulation", "Home"};
            addCommand(cmd);

            if (update.getMessage().getText().equals("/start")) {
                ArrayList<String> details = new ArrayList<>();
                for (Map.Entry<String, String> entry : command_map.entrySet()) {
                    details.add(entry.getKey() + " ----> " + entry.getValue());
                }
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("To control the bot use the following commands: \n" +
                                details.get(0)+"\n" +
                                details.get(1)+"\n" +
                                details.get(2));
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText("Wiki search").setCallbackData("wiki_search"));
                rowInline.add(new InlineKeyboardButton().setText("Trivia").setCallbackData("trivia"));
//                rowInline.add(new InlineKeyboardButton().setText("My Score").setCallbackData("score"));
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
                try {
                    sendMessage(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().equals("true")){
                sendMsg(update, chat_id, "Corr");
            }

        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("wiki_search")) {
                String answer = "Select what to search below";
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText("Java").setCallbackData("java"));
                rowInline.add(new InlineKeyboardButton().setText("OOP").setCallbackData("OOP"));
                rowInline.add(new InlineKeyboardButton().setText("Maven").setCallbackData("maven"));
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                try {
                    editMessageText(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if (call_data.equals("trivia")){
                String squiz = questions.get(0);
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(squiz);
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText(questions.get(1)).setCallbackData(questions.get(1)));
                rowInline.add(new InlineKeyboardButton().setText(questions.get(2)).setCallbackData(questions.get(2)));
                rowsInline.add(rowInline);

                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                try {
                    editMessageText(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if (update.hasCallbackQuery()){
                String quiz_call = update.getCallbackQuery().getData();
                long message_idc = update.getCallbackQuery().getMessage().getMessageId();
                long chat_idc = update.getCallbackQuery().getMessage().getChatId();

                if (call_data.equals(quiz.correct_answer)) {
                    String answers = "Correct";
                    EditMessageText new_messages = new EditMessageText()
                            .setChatId(chat_idc)
                            .setMessageId(toIntExact(message_idc))
                            .setText(answers);
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    rowInline.add(new InlineKeyboardButton().setText("Continue").setCallbackData("trivia"));
                    rowsInline.add(rowInline);
                    // Add it to the message
                    markupInline.setKeyboard(rowsInline);
                    new_messages.setReplyMarkup(markupInline);
                    try {
                        editMessageText(new_messages);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
//                if (quiz_call.equals(quiz.correct_answer)){
//                    System.out.println("Woooj");
//                    EditMessageText new_message = new EditMessageText()
//                            .setChatId(chat_id)
//                            .setMessageId(toIntExact(message_idc))
//                            .setText("Correct!");
//                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
//                    rowInline.add(new InlineKeyboardButton().setText("Continue").setCallbackData("trivia"));
//                    rowsInline.add(rowInline);
//
//                    // Add it to the message
//                    markupInline.setKeyboard(rowsInline);
//                    new_message.setReplyMarkup(markupInline);
//                    count++;
//                }
            }
        }
    }

    public void triviaVariants(long chat_id){
//        SendMessage messag = new SendMessage() // Create a message object object
//                .setChatId(chat_id)
//                .setText("Question:\n"+quizObj[0].question);
//
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op1).setCallbackData(quizObj[0].op1));
//        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op2).setCallbackData(quizObj[0].op2));
//        rowInline.add(new InlineKeyboardButton().setText(quizObj[0].op3).setCallbackData(quizObj[0].op3));
//        // Set the keyboard to the markup
//        rowsInline.add(rowInline);
//        // Add it to the message
//        markupInline.setKeyboard(rowsInline);
//        messag.setReplyMarkup(markupInline);
//        try {
//            execute(messag); // Sending our message object to user
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
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
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class radtelbot extends TelegramLongPollingBot{


    public String getBotUsername() {
        return "RadteleBot";
    }

    public String getBotToken() {
        return "1486539422:AAH2Po2rRY--tVsizLegJXlcO0KJ-2I5s38";
    }


    public void onUpdateReceived(Update update) {

        String command=update.getMessage().getText();

        SendMessage message = new SendMessage();

        LinkedHashMap<String, String> command_list =
                new LinkedHashMap<String, String>();
        command_list.put("Display your username", "/name");
        command_list.put("Display your fullname", "/fullname");
        command_list.put("For help", "/help");


        System.out.println("Welcome message");


        if(command.equals("/start")){

            message.setText("To control the bot use the following commands: \n" +
                    "Display your username-> \"/name\"\n" +
                    "Display your lastname-> /lastname\n" +
                    "Display your fullname-> /fullname\n" +
                            "Display your help-> /help");
//            Iterator iterator = command_list.entrySet().iterator();
//            while (iterator.hasNext()) {
//                command_list.ntry me2 = (command_list.Entry) iterator.next();
//                System.out.println("Key: "+me2.getKey() + " & Value: " + me2.getValue());
//            }

            String s = command_list.toString();
//            message.setText( s );

        }

        if(command.equals("/name")){

            System.out.println(update.getMessage().getFrom().getFirstName());

            message.setText(update.getMessage().getFrom().getFirstName());
        }

        if (command.equals("/fullname")){
            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
        }

        if (command.equals("/help")){

            // To Do
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

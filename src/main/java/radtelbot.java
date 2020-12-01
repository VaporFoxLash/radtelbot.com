import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


interface myBotInterface {
    HashMap command_list = new  HashMap<>();
    static void addCommands(){
        command_list.put("Display your username", "/name");
        command_list.put("Display your fullname", "/fullname");
        command_list.put("For help", "/help");
    }

    static String getBotUsername() {
        return "RadteleBot";
    }

    static String getBotToken() {
        return "1486539422:AAH2Po2rRY--tVsizLegJXlcO0KJ-2I5s38";
    }
}

public class radtelbot extends TelegramLongPollingBot implements myBotInterface{


    myBotInterface.getBotUsername();

    public String getBotToken() {
        return "1486539422:AAH2Po2rRY--tVsizLegJXlcO0KJ-2I5s38";
    }


    public void onUpdateReceived(Update update) {

        String command=update.getMessage().getText();

        SendMessage message = new SendMessage();


        System.out.println("Welcome message");
//        command_list.entrySet().forEach( entry -> {
//            System.out.println( entry.getKey() + " => " + entry.getValue() );
//        });


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

//            String s = command_list.toString();
//            message.setText( s );
////            message.setText( s );

        }

        if(command.equals("/name")){

            System.out.println(update.getMessage().getFrom().getFirstName());

            message.setText(update.getMessage().getFrom().getFirstName());
        }

        if(command.equals("/lastname")){

            if (update.getMessage().getFrom().getLastName() ==null){
                message.setText("no last name, are you trying to be anonymous? :)");
                System.out.println("no last name");

            }

            else{
                message.setText(update.getMessage().getFrom().getLastName());
                System.out.println(update.getMessage().getFrom().getLastName());

            }

        }

        if (command.equals("/fullname")){
            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
        }

        if (command.equals("/help")){

            message.setText("How can I help you?");
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


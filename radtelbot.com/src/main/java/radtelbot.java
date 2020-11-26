import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class radtelbot extends org.telegram.telegrambots.bots.TelegramLongPollingBot{


    public String getBotUsername() {
        return "radtelebjdjfot";
    }

    public String getBotToken() {
        return "428789405:AAHeQGtTmq34PYdijLUEb16SVgARgLDXPNA";
    }


    public void onUpdateReceived(Update update) {
        String command=update.getMessage().getText();


        SendMessage message = new SendMessage();


        if(command.equals("/myname")){

            System.out.println(update.getMessage().getFrom().getFirstName());

            message.setText(update.getMessage().getFrom().getFirstName());
        }

        if (command.equals("/mylastname")){

            System.out.println(update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getLastName());
        }

        if (command.equals("/myfullname")){
            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

package FinalHikeFinder.FinalHikeFinder.telegram_bot;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Scope("singleton")
public class TelegramBot extends TelegramLongPollingBot implements DisposableBean/* , ApplicationListener<ContextClosedEvent> */{
    private volatile boolean isRunning = true; // Track if the bot is running

    @Autowired BotLogic botLogic;//initated a botLogic object, this object will store the state of the botLogic

    private final String botName;

    public TelegramBot(String botName, String botToken){
        super(botToken);
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!isRunning) {
            return; // Ignore updates if bot is shutting down
        }
        
        if (update.hasMessage() && update.getMessage().hasText()){
            Message message = update.getMessage();
            try {
                execute(botLogic.getOutput(message));
            } 
            catch (TelegramApiException e) {
                
            }

         //   try {
         //       execute(new SendMessage(chatId.toString(), botLogic.getOutput(messageText)));
         //   } catch (TelegramApiException e) {
         //       //e.printStackTrace();
         //       //can log this instead
         //   }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroying telegram bot");
        isRunning = false; // Stop processing updates
    }
    
}

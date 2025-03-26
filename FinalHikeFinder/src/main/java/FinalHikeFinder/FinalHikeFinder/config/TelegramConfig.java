package FinalHikeFinder.FinalHikeFinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import FinalHikeFinder.FinalHikeFinder.telegram_bot.TelegramBot;

@Configuration
public class TelegramConfig {

    private static TelegramBot registeredBot = null; // Store instance to unregister before restart
    
    @Bean
    public TelegramBot telegramBot(@Value("${bot.name}") String botName, 
                                   @Value("${bot.token}") String botToken) {
        TelegramBot telegrambot = new TelegramBot(botName, botToken);
        return telegrambot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws Exception {
        
        /* if (registeredBot != null) {
            registeredBot.destroy();  // Gracefully stop old bot
        } */

        //to register
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            TelegramConfig.registeredBot = telegramBot;  // Save reference to prevent duplicate registrations
            return telegramBotsApi; 
        } catch (TelegramApiException e) {
            //System.out.println(e.getMessage());
            throw new RuntimeException("Failed to register Telegram bot", e);
        }
    }
}

package FinalHikeFinder.FinalHikeFinder.telegram_bot;
import FinalHikeFinder.FinalHikeFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import FinalHikeFinder.FinalHikeFinder.model.Hike;
import FinalHikeFinder.FinalHikeFinder.model.HikeSpot;
import FinalHikeFinder.FinalHikeFinder.service.GeminiService;
import FinalHikeFinder.FinalHikeFinder.service.HikeService;
import FinalHikeFinder.FinalHikeFinder.service.HikeSpotService;
import FinalHikeFinder.FinalHikeFinder.service.TeleLogicService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotLogic {


    @Autowired UserService userService;
    @Autowired HikeService hikeService;
    @Autowired HikeSpotService hikeSpotService;
    @Autowired TeleLogicService teleLogicService;
    @Autowired GeminiService geminiService;


    public BotApiMethodMessage getOutput(Message message){

        String inputText = message.getText();
        String chatId = message.getChatId().toString();
        String teleUsername = message.getFrom().getUserName();

        Integer botState = teleLogicService.getBotState(chatId);
        System.out.println(botState);


        if (botState == 0){ //press /start
            if (inputText.equals("/start")){
                teleLogicService.setBotState(chatId, 1);

                SendMessage sendMessage = new SendMessage(chatId, "Hello There " + teleUsername + "! Welcome to HikeFinder Telegram Bot!");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Login");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else{
                SendMessage sendMessage = new SendMessage(chatId, "Enter \"/start\" to start");
                return sendMessage;
            }
        }


        else if (botState == 1){ //press login
            if (inputText.equals("Restart") || inputText.equals("/start")){
                teleLogicService.setBotState(chatId, 0);

                SendMessage sendMessage = new SendMessage(chatId, "Chat has be restarted, Enter \"/start\" to start");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("/start");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else if (inputText.equals("Login")){
                teleLogicService.setBotState(chatId, 2);

                SendMessage sendMessage = new SendMessage(chatId, "Please enter your HikeFinder username");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Restart");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else{
                SendMessage sendMessage = new SendMessage(chatId, "Please choose a valid input from the button pannel");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Login");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }
        }


        else if (botState == 2){ //enter your hikefinder username
            if (inputText.equals("Restart") || inputText.equals("/start")){
                teleLogicService.setBotState(chatId, 0);

                SendMessage sendMessage = new SendMessage(chatId, "Chat has be restarted, Enter \"/start\" to start");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("/start");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }
            
            else{ //any text (username entered)
                Boolean usernameExists = userService.checkUserExists(inputText);

                if (usernameExists){
                    teleLogicService.setBotState(chatId, 3);

                    teleLogicService.insertTempUsernameMatch(inputText, teleUsername);

                    SendMessage sendMessage = new SendMessage(chatId, "Thank You, now please get an OTP from your HikeFinder Website and enter it in this chat");
                    ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }
                else{
                    SendMessage sendMessage = new SendMessage(chatId, "Please enter your HikeFinder username");
                    ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }
                
            }
        }


        else if (botState == 3){//enter the OTP from HikeFinder
            if (inputText.equals("Restart") || inputText.equals("/start")){
                teleLogicService.setBotState(chatId, 0);

                SendMessage sendMessage = new SendMessage(chatId, "Chat has be restarted, Enter \"/start\" to start");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("/start");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else{
                String username = teleLogicService.getTempUsernameMatch(teleUsername);

                Boolean checkOTPValid = teleLogicService.checkUniqueIdentifierMatch(inputText, username);

                teleLogicService.deleteTempUsernameMatch(teleUsername);

                if (checkOTPValid){
                    teleLogicService.setBotState(chatId, 4);

                    userService.updateTelegramInfo("@"+teleUsername, chatId, username);

                    SendMessage sendMessage = new SendMessage(chatId, "Perfect! Hi there " + teleUsername + ", you are signed in to HikeFinder as " + username);
                    ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips", "Upcoming Hike Location", "View My Hikes","Logout/Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }
                else{
                    SendMessage sendMessage = new SendMessage(chatId, "Sorry, wrong OTP, Please enter the right OTP or Restart");
                    ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }
            }
        }


        else if (botState == 4){ //check upcoming hike
            if (inputText.equals("Logout/Restart")|| inputText.equals("/start")){
                teleLogicService.setBotState(chatId, 0);

                SendMessage sendMessage = new SendMessage(chatId, "Chat has be restarted, Enter \"/start\" to start");
                ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("/start");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else if (inputText.equals("View My Hikes")){
                List<Hike> hikeList = hikeService.getHikesJoined("@" + teleUsername);
                String hikeListString = hikeService.convertHikeListToString(hikeList);

                SendMessage sendMessage = new SendMessage(chatId, hikeListString);
                ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips", "Upcoming Hike Location", "View My Hikes","Logout/Restart");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }

            else if (inputText.equals("Upcoming Hike Tips")){
                List<Hike> hikeList = hikeService.getHikesJoined("@" + teleUsername);

                if (hikeList.size() <=0){
                    SendMessage sendMessage = new SendMessage(chatId, "You do not have any Upcoming Hikes");
                    ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips", "Upcoming Hike Location", "View My Hikes","Logout/Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }
                else {
                    List<Hike> firstHikeList = hikeList.stream().limit(1).toList();
                    String hikeListString = hikeService.convertHikeListToString(firstHikeList);
                
                    String geminiResponse = geminiService.getGeminiResponse("How to prepare for the following hike in 100 words \n\n" + hikeListString);
                    
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(hikeListString + "\n" + geminiResponse);
                    return sendMessage;
                }
            }

            else if (inputText.equals("Upcoming Hike Location")){
                List<Hike> hikeList = hikeService.getHikesJoined("@" + teleUsername);

                if (hikeList.size() <=0){
                    SendMessage sendMessage = new SendMessage(chatId, "You do not have any Upcoming Hikes");
                    ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips", "Upcoming Hike Location", "View My Hikes","Logout/Restart");
                    sendMessage.setReplyMarkup(buttonPannel);
                    return sendMessage;
                }

                else{
                    List<Hike> firstHikeList = hikeList.stream().limit(1).toList();
                    Hike firstHike = firstHikeList.getFirst();
                    System.out.println(firstHikeList.size() + "SIZE");
                    System.out.println(firstHike.getHikeSpotName());
                    HikeSpot hikeSpot = hikeSpotService.getHikeSpotTele(firstHike.getHikeSpotName());
                    System.out.println(hikeSpot.getName());
                    System.out.println(hikeSpot.getLat());
                    System.out.println(hikeSpot.getLng());
                    SendLocation sendLocation = new SendLocation(chatId,hikeSpot.getLat(), hikeSpot.getLng());
                    ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips", "Upcoming Hike Location", "View My Hikes","Logout/Restart");
                    sendLocation.setReplyMarkup(buttonPannel);
                    return sendLocation;
                }
            }

            else {
                SendMessage sendMessage = new SendMessage(chatId, "Choose an input from the button pannel");
                ReplyKeyboardMarkup buttonPannel = getQuadButtonPannel("Upcoming Hike Tips","Upcoming Hike Location","View My Hikes","Logout/Restart");
                sendMessage.setReplyMarkup(buttonPannel);
                return sendMessage;
            }
        }


        else{
            teleLogicService.setBotState(chatId, 0);

            SendMessage sendMessage = new SendMessage(chatId, "Something went wrong, we will restart your bot. Please enter \"/start\" to start");
            ReplyKeyboardMarkup buttonPannel = getSingleButtonPannel("/start");
            sendMessage.setReplyMarkup(buttonPannel);
            return sendMessage;
        }
        
    }



    private ReplyKeyboardMarkup getSingleButtonPannel(String input1){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();

        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText(input1);

        keyboardRow1.add(keyboardButton1);
        keyboardRowList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }


    private ReplyKeyboardMarkup getDoubleButtonPannel(String input1, String input2){

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow keyboardRow1 = new KeyboardRow();

            KeyboardButton keyboardButton1 = new KeyboardButton();
            keyboardButton1.setText(input1);

            KeyboardButton keyboardButton2 = new KeyboardButton();
            keyboardButton2.setText(input2);

            keyboardRow1.add(keyboardButton1);
            keyboardRow1.add(keyboardButton2);
            keyboardRowList.add(keyboardRow1);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);

            return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getTripleButtonPannel(String input1, String input2, String input3){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();

        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText(input1);

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText(input2);

        KeyboardButton keyboardButton3 = new KeyboardButton();
        keyboardButton3.setText(input3);

        keyboardRow1.add(keyboardButton1);
        keyboardRow1.add(keyboardButton2);
        keyboardRow1.add(keyboardButton3);
        keyboardRowList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getQuadButtonPannel(String input1, String input2, String input3, String input4){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText(input1);

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText(input2);

        KeyboardButton keyboardButton3 = new KeyboardButton();
        keyboardButton3.setText(input3);

        KeyboardButton keyboardButton4 = new KeyboardButton();
        keyboardButton4.setText(input4);

        keyboardRow1.add(keyboardButton1);
        keyboardRow1.add(keyboardButton3);
        keyboardRow2.add(keyboardButton2);
        keyboardRow2.add(keyboardButton4);

        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }


    public SendLocation getLocation(Message message){
        SendLocation sendLocation = new SendLocation(message.getChatId().toString(), 1.3548, 103.77);
        return sendLocation;
    }
}

package FinalHikeFinder.FinalHikeFinder.model;

import java.util.ArrayList;
import java.util.List;


public class User {
    private String username;
    private String password;
    private String role;
    private String telegramUsername;
    private String chatId;
    private List<Integer> hostedHikes;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.hostedHikes = new ArrayList<>();
    }

    public User(String username, String password, String role, List<Integer> hostedHikes) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.hostedHikes = hostedHikes;
    }

    public User(String username, String password, String role, String telegramUsername, String chatId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.telegramUsername = telegramUsername;
        this.chatId = chatId;
        this.hostedHikes = new ArrayList<>();
    }

    public User(String username, String password, String role, String telegramUsername, String chatId,
            List<Integer> hostedHikes) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.telegramUsername = telegramUsername;
        this.chatId = chatId;
        this.hostedHikes = hostedHikes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<Integer> getHostedHikes() {
        return hostedHikes;
    }

    public void setHostedHikes(List<Integer> hostedHikes) {
        this.hostedHikes = hostedHikes;
    }

    



    
    
    
    
}


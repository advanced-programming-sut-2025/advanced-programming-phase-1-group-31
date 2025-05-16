package model;

import model.enums.general.Menus;
import model.enums.general.TileType;
import model.Tools.Tool;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Player {
    // maybe delete
    private boolean gender;// IMPORTANT!!!!!!!!!! : false is male, true is female
    private Farm farm;
    private Map<String, String> backup;
    private Skill skills;
    public ArrayList<FriendshipWithNPC> NPCFriendships = new ArrayList<>();
    public ArrayList<Trade> tradeHistory = new ArrayList<>();

    //details of the Crafting recipes must be determined
    // private HashMap<Craftable, Boolean> craftingRecipes;
    // //details of the Cooking recipes must be determined
    // private HashMap<Food, Boolean> cookingRecipes;

    private int highScore = 0;
    private int gameCount = 0;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Energy energy;
    private Point place;
    private int gold;
    private static Menus currentMenu = Menus.MainMenu;
    private TileType type;


    // SMS----------------------------------------------------
    private final ArrayList<SMS> SMSs = new ArrayList<>();

    public ArrayList<SMS> getSMSs() {
        return SMSs;
    }

    public void addSMS(SMS sms){
        SMSs.add(sms);
    }

    public String showConversationWith(String otherName) {
        StringBuilder result = new StringBuilder();
        boolean hasAny = false;

        result.append("Conversation between You and ").append(otherName).append(":\n");

        for (SMS sms : SMSs) {
            boolean sentByActive = sms.getSender().equals(username) && sms.getReceiver().equals(otherName);
            boolean receivedByActive = sms.getReceiver().equals(username) && sms.getSender().equals(otherName);

            if (sentByActive || receivedByActive) {
                hasAny = true;

                String senderDisplay = sms.getSender().equals(username) ? "You" : sms.getSender();
                String receiverDisplay = sms.getReceiver().equals(username) ? "You" : sms.getReceiver();

                result.append("From: ").append(senderDisplay).append(" → ")
                        .append("To: ").append(receiverDisplay).append("\n")
                        .append("Message: ").append(sms.getMessage()).append("\n------------\n");

                if (!sms.isRead() && sms.getReceiver().equals(username)) {
                    sms.setRead(true);
                }
            }
        }

        if (!hasAny) {
            return "You have no messages with " + otherName + ".";
        }

        return result.toString();
    }

    public String showUnreadMessages() {
        StringBuilder result = new StringBuilder();
        boolean hasUnread = false;

        result.append("Your Unread Messages:\n");

        for (SMS sms : SMSs) {
            if (!sms.isRead() && sms.getReceiver().equals(username)) {
                hasUnread = true;

                String senderDisplay = sms.getSender().equals(username) ? "You" : sms.getSender();

                result.append("From: ").append(senderDisplay).append("\n")
                        .append("Message: ").append(sms.getMessage()).append("\n------------\n");

                sms.setRead(true);
            }
        }

        if (!hasUnread) {
            return "You don't have any unread messages.";
        }

        return result.toString();
    }
    //--------------------------------------------------------------


    // Gift----------------------------------------------------
    private final ArrayList<Gift> gifts = new ArrayList<>();

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
    }

    public Gift getGiftOrNull(int Id){
        for (Gift gift : gifts){
            if (gift.getId() == Id) return gift;
        }
        return null;
    }

    public String showGiftsWith(String otherName) {
        StringBuilder result = new StringBuilder();
        boolean hasAny = false;

        result.append("Gifts between You and ").append(otherName).append(":\n");

        for (Gift gift : gifts) {
            boolean sentByYou = gift.getSender().equals(username) && gift.getReceiver().equals(otherName);
            boolean receivedByYou = gift.getReceiver().equals(username) && gift.getSender().equals(otherName);

            if (sentByYou || receivedByYou) {
                hasAny = true;

                String senderDisplay = gift.getSender().equals(username) ? "You" : gift.getSender();
                String receiverDisplay = gift.getReceiver().equals(username) ? "You" : gift.getReceiver();

                result.append("From: ").append(senderDisplay).append(" → ")
                        .append("To: ").append(receiverDisplay).append("\n")
                        .append("Gift: ").append(gift.getAmount()).append(" of ").
                        append(gift.getMaterial().getName()).append("\n")
                        .append("Rate: ").append(gift.getRate()).append("/5\n------------\n");
            }
        }

        if (!hasAny) {
            return "You have no gift with " + otherName + ".";
        }

        return result.toString();
    }

    public String showUnratedGifts() {
        StringBuilder result = new StringBuilder();
        int tempId = 1;
        boolean hasUnrated = false;

        result.append("Your Unrated Gifts:\n");

        for (Gift gift : gifts) {
            if (gift.getRate() == 0 && gift.getReceiver().equals(username)) {
                hasUnrated = true;

                result.append("Gift ID: ").append(tempId).append("\n")
                        .append("From: ").append(gift.getSender()).append("\n")
                        .append("Gift: ").append(gift.getAmount()).append(" of ").
                        append(gift.getMaterial().getName()).append("\n")
                        .append("------------\n");

                gift.setId(tempId);
                tempId++;
            }
        }

        if (!hasUnrated) {
            return "You don't have any unrated gifts.";
        }

        return result.toString();
    }

    //--------------------------------------------------------------


    // FriendShip----------------------------------------------------
    private final ArrayList<Friendship> friendships = new ArrayList<>();

    public ArrayList<Friendship> getFriendships() {
        return friendships;
    }

    public Friendship friendshipWithPlayer(String name){
        for (Friendship friendship : friendships){
            if (friendship.getFriend().username.equals(name)) return friendship;
        }
        return null;
    }
    //--------------------------------------------------------------


    // backpack------------------------------------------------------
    private final Backpack inventory = new Backpack();

    public Backpack getInventory() {
        return inventory;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }
    //---------------------------------------------------------------


    // tool inHand------------------------------------------------------
    private Tool inHand;

    public Tool getInHand() {
        return inHand;
    }

    public void setInHand(Tool inHand) {
        this.inHand = inHand;
    }
    //---------------------------------------------------------------


    public Player(String username,
                  String password,
                  String nickname,
                  String email,
                  boolean gender,
                  Map<String, String> backup) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.backup = backup;
    }


    public int getHighScore() {
        return highScore;
    }

    public int getGameCount() {
        return gameCount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Point getPlace() {
        return place;
    }

    public boolean isGender() {
        return gender;
    }

    public Farm getFarm() {
        return farm;
    }

    public Map<String, String> getBackup() {
        return backup;
    }

    public Skill getSkills() {
        return skills;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }


    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setPlace(Point place) {
        this.place = place;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void setBackup(Map<String, String> backup) {
        this.backup = backup;
    }


    public static Menus getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menus menu) {
        currentMenu = menu;
    }
}
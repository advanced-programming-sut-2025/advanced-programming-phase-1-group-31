package model;

import model.Tools.Tool;
import model.enums.general.Menus;
import model.enums.general.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Player {
    private int highScore = 0;
    private int gameCount = 0;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Energy energy;
    private Point place;
    private String backupQuestion;
    private String backupAnswer;
    private boolean stayLoggedIn = false;
    private Menus currentMenu = Menus.MainMenu;
    private TileType type;
    private final ArrayList<SMS> SMSs = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private Tool inHand;
    private final Backpack inventory = new Backpack();
    private final ArrayList<Gift> gifts = new ArrayList<>();

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
    }

    public Backpack getInventory() {
        return inventory;
    }

    public ArrayList<Friendship> getFriendships() {
        return friendships;
    }

    public ArrayList<SMS> getSMSs() {
        return SMSs;
    }

    public void addSMS(SMS sms){
        SMSs.add(sms);
    }


    public void setEnergy(Energy energy) {
        this.energy = energy;
    }
    // maybe delete
    private boolean gender;// IMPORTANT!!!!!!!!!! : false is male, true is female
    // private Tools inHand;
    private Farm farm;
    private Map<String, String> backup;

    private final Skill skills = new Skill();
    public ArrayList<FriendshipWithNPC> NPCFriendships = new ArrayList<>();
    public ArrayList<Trade> tradeHistory = new ArrayList<>();
    private double money = 0;
    // details of the Crafting recipes must be determined
    // private HashMap<Craftable, Boolean> craftingRecipes;
    // //details of the Cooking recipes must be determined
    // private HashMap<Food, Boolean> cookingRecipes;


    public Player(String username, String password, String nickname, String email, boolean gender, String question, String answer){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.backupQuestion = question;
        this.backupAnswer = answer;
    }

    public double getMoney() {
        return money;
    }

    public boolean deductMoney(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (money < amount) return false;
        money -= amount;
        return true;
    }

    public void addMoney(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        money += amount;
    }

    public boolean hasEnoughMoney(double amount) {
        return money >= amount;
    }
    public void setMoney(double money) {
        if (money < 0) throw new IllegalArgumentException("Money cannot be negative");
        this.money = money;
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

    public boolean getGender() {
        return gender;
    }


    public Tool getInHand() {
        return inHand;
    }

    public void setInHand(Tool inHand) {
        this.inHand = inHand;
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
    // public HashMap<Craftable, Boolean> getCraftingRecipes() {


    public TileType getType() {
        return type;
    }
    public void setType(TileType type) {
        this.type = type;
    }
    // return craftingRecipes;
    // }

    // public HashMap<Food, Boolean> getCookingRecipes() {
    // return cookingRecipes;
    // }


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
    // public void setInHand(Tools inHand) {
    // this.inHand = inHand;
    // }


    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void setBackup(Map<String, String> backup) {
        this.backup = backup;
    }
    // public void setCraftingRecipes(HashMap<Craftable, Boolean> craftingRecipes) {
    // this.craftingRecipes = craftingRecipes;
    // }

    // public void setCookingRecipes(HashMap<Food, Boolean> cookingRecipes) {
    // this.cookingRecipes = cookingRecipes;
    // }

    public Menus getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menus menu) {
        this.currentMenu = menu;
    }
    public String getBackupQuestion() {
        return backupQuestion;
    }

    public void setBackupQuestion(String backupQuestion) {
        this.backupQuestion = backupQuestion;
    }

    public String getBackupAnswer() {
        return backupAnswer;
    }

    public void setBackupAnswer(String backupAnswer) {
        this.backupAnswer = backupAnswer;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
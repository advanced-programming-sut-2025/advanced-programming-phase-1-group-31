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
    public ArrayList<Friendship> friendships = new ArrayList<>();
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
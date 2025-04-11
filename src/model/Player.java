package model;

import model.enums.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int highScore;
    private int gameCount;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Energy energy;
    private Place place;
    private refrigerator refrigerator;
    private boolean isPlaying = false;
    private boolean gender;
    private Tools inHand;
    private Farm farm;

    private HashMap<String, String> backup;
    private Skill skills;
    public ArrayList<Friendship> friendships = new ArrayList<>();
    public ArrayList<FriendshipWithNPC> NPCFriendships = new ArrayList<>();
    public ArrayList<Trade> tradeHistory = new ArrayList<>();
    //details of the Crafting recipes must be determined
    private HashMap<Craftable, Boolean> craftingRecipes;
    //details of the Cooking recipes must be determined
    private HashMap<Food, Boolean> cookingRecipes;


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

    public Place getPlace() {
        return place;
    }

    public model.refrigerator getRefrigerator() {
        return refrigerator;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isGender() {
        return gender;
    }

    public Tools getInHand() {
        return inHand;
    }

    public Farm getFarm() {
        return farm;
    }

    public HashMap<String, String> getBackup() {
        return backup;
    }

    public Skill getSkills() {
        return skills;
    }

    public HashMap<Craftable, Boolean> getCraftingRecipes() {
        return craftingRecipes;
    }

    public HashMap<Food, Boolean> getCookingRecipes() {
        return cookingRecipes;
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

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setRefrigerator(model.refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setInHand(Tools inHand) {
        this.inHand = inHand;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void setBackup(HashMap<String, String> backup) {
        this.backup = backup;
    }

    public void setCraftingRecipes(HashMap<Craftable, Boolean> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void setCookingRecipes(HashMap<Food, Boolean> cookingRecipes) {
        this.cookingRecipes = cookingRecipes;
    }
}


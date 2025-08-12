package io.github.some_example_name.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import common.Lobby;
import common.UserInfo;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.materials.Industrial;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.Seed;
import io.github.some_example_name.model.materials.Tools.Tool;

import java.util.ArrayList;

public class Player {
    private UserInfo userInfo = new UserInfo(null, null, null, null);
    private int id;
    private Material currentCoopOrBarn;
    private MapType currentMapType;
    private int highScore = 0;
    private int gameCount = 0;
    private String email;
    private Energy energy;
    //    private final Boolean gender;// IMPORTANT!!!!!!!!!! : false is male, true is female
    private Vector2 place;
    private Rectangle playerRectangle;
    private String backupQuestion;
    private String backupAnswer;
    private TileType type;
    private Tool inHand;
    private Seed seedInHand;
    private final Backpack inventory = new Backpack();
    private Farm farm;
    private final Skill skills = new Skill();
    private double money = 1000000;
    private CharacterPlacer characterPlacer;


    private final ArrayList<Gift> gifts = new ArrayList<>();
    private final ArrayList<SMS> SMSs = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private final ArrayList<FriendshipWithNPC> friendshipWithNPCS = new ArrayList<>();
    private final ArrayList<CraftingRecipe> craftingRecipes = new ArrayList<>();
    private final ArrayList<CookingRecipe> cookingRecipes = new ArrayList<>();
    private final ArrayList<Industrial> industrials = new ArrayList<>();
    private final ArrayList<Trade> tradeHistory = new ArrayList<>();


    public Player(String username, String password, String nickname, String email, Boolean gender, String question, String answer) {
        this.userInfo.setUsername(username);
        this.userInfo.setPassword(password);
        this.userInfo.setNickName(nickname);
        this.email = email;
//        this.gender = gender;
//        this.backupQuestion = question;
//        this.backupAnswer = answer;
    }

    public Player(UserInfo userInfo) {
        this.setUserInfo(userInfo);
//        delete
        this.energy = new Energy();
        energy.setEnergyAmount(energy.getMaxEnergy());
        currentMapType = MapType.FARM;
//        player.setEnergy(new Energy());
//        player.getEnergy().setEnergyAmount(player.getEnergy().getMaxEnergy());
    }



    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public ArrayList<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public ArrayList<SMS> getSMSs() {
        return SMSs;
    }

    public ArrayList<CookingRecipe> getCookingRecipes() {
        return cookingRecipes;
    }

    public ArrayList<FriendshipWithNPC> getFriendshipWithNPCS() {
        return friendshipWithNPCS;
    }

    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public ArrayList<Industrial> getIndustrials() {
        return industrials;
    }

    public ArrayList<Friendship> getFriendships() {
        return friendships;
    }


    //
    public Backpack getInventory() {
        return inventory;
    }
    public double getMoney() {
        return money;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getGameCount() {
        return gameCount;
    }

    public String getUsername() {
        return userInfo.getUsername();
    }

    public String getPassword() {
        return userInfo.getPassword();
    }

    public String getNickname() {
        return userInfo.getNickName();
    }

    public String getEmail() {
        return email;
    }

    public Vector2 getPlace() {
        return place;
    }

    //    public Boolean getGender() {
//        return gender;
//    }
    public Tool getInHand() {
        return inHand;
    }
    public Energy getEnergy() {
        return energy;
    }

    //    public String getBackupQuestion() {
//        return backupQuestion;
//    }
    public Farm getFarm() {
        return farm;
    }

    public Skill getSkills() {
        return skills;
    }

    public TileType getType() {
        return type;
    }
    public String getBackupAnswer () {
        return backupAnswer;
    }


    public void addGift (Gift gift){
        gifts.add(gift);
    }
    public void addSMS (SMS sms){
        SMSs.add(sms);
    }
    public void addCraftingRecipes (CraftingRecipe recipes){
        this.craftingRecipes.add(recipes);
    }
    public void addCookingRecipes (CookingRecipe cookingRecipes){
        this.cookingRecipes.add(cookingRecipes);
    }
    public void addIndustrials (Industrial industrials){
        this.industrials.add(industrials);
    }
    public void addTrade (Trade trade){
        tradeHistory.add(trade);
    }
    public void addMoney ( double amount){
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        money += amount;
    }
    public Result deductMoney(double amount) {
        if (amount < 0) return new Result(false, "Amount can't be negative.");
        if (money < amount) return new Result(false, "You haven't enough money.");
        money -= amount;
        return new Result(true, "");
    }

    public void setEnergy (Energy energy){
        this.energy = energy;
    }
    public void setInHand(Tool inHand) {
        this.inHand = inHand;
    }
    public void setType(TileType type) {
        this.type = type;
    }
    public void setHighScore ( int highScore){
        this.highScore = highScore;
    }
    public void setGameCount ( int gameCount){
        this.gameCount = gameCount;
    }
    public void setUsername (String username){
        this.userInfo.setUsername(username);
    }
    public void setPassword (String password){
        this.userInfo.setPassword(password);
    }
    public void setNickname (String nickname){
        this.userInfo.setNickName(nickname);
    }
    public void setEmail (String email){
        this.email = email;
    }
    public void setPlace (Vector2 place){
        this.place = place;
    }
    public void setFarm (Farm farm){
        this.farm = farm;
    }


    public CharacterPlacer getCharacterPlacer() {
        return characterPlacer;
    }

    public void setCharacterPlacer(CharacterPlacer characterPlacer) {
        this.characterPlacer = characterPlacer;
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public void setPlayerRectangle(Rectangle playerRectangle) {
        this.playerRectangle = playerRectangle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MapType getCurrentMapType() {
        return currentMapType;
    }

    public void setCurrentMapType(MapType currentMapType) {
        this.currentMapType = currentMapType;
    }

    public Material getCurrentCoopOrBarn() {
        return currentCoopOrBarn;
    }

    public void setCurrentCoopOrBarn(Material currentCoopOrBarn) {
        this.currentCoopOrBarn = currentCoopOrBarn;
    }

    public Seed getSeedInHand() {
        return seedInHand;
    }

    public void setSeedInHand(Seed seedInHand) {
        this.seedInHand = seedInHand;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Lobby getLobby(){
        return userInfo.getLobby();
    }

    public void setLobby(Lobby lobby){
        this.userInfo.setLobby(lobby);
    }
}

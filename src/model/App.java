package model;


import java.util.ArrayList;

import model.enums.Menus;

public class App {
    private static Player playerLoggedIn;
    private static Menus currentMenu = Menus.LoginMenu;
    private static Game currentGame;
    private static final ArrayList<Player> registeredPlayers = new ArrayList<>();
    private static final ArrayList<String> registeredUsernames = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static void addRegisteredUsername(String username){
        registeredUsernames.add(username);
    }

    public static ArrayList<String> getRegisteredUsernames(){
        return registeredUsernames;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGames(Game game) {
        App.games.add(game);
    }

    public static void addRegisteredPlayer(Player player) {
        registeredPlayers.add(player);
    }

    public static ArrayList<Player> getRegisteredPlayers() {
        return registeredPlayers;
    }

    

    public static void setPlayerLoggedIn(Player player) {
        playerLoggedIn = player;
    }

    public static Player getPlayerLoggedIn() {
        return playerLoggedIn;
    }
    public static Menus getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menus currentMenu) {
        App.currentMenu = currentMenu;
    }

}

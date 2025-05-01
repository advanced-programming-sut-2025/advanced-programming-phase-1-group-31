package model;

import view.Menu;

import java.util.ArrayList;

public class App {
    private static Player playerLoggedIn;
    private static Game currentGame;
    private static ArrayList<Player> registeredPlayers;
    private static ArrayList<String> registeredUsernames;
    private static ArrayList<Map> maps;
    private static ArrayList<Game> games = new ArrayList<>();

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static void setRegisteredPlayers(ArrayList<Player> registeredPlayers) {
        App.registeredPlayers = registeredPlayers;
    }

    public static void setRegisteredUsernames(ArrayList<String> registeredUsernames) {
        App.registeredUsernames = registeredUsernames;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static void setMaps(ArrayList<Map> maps) {
        App.maps = maps;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGames(Game game) {
        App.games.add(game);
    }

    public static void addRegisteredPlayer(Player player){
        registeredPlayers.add(player);
    }

    public static ArrayList<Player> getRegisteredPlayers(){
        return registeredPlayers;
    }

    public static void addRegisteredUsername(String username){
        registeredUsernames.add(username);
    }

    public static ArrayList<String> getRegisteredUsernames(){
        return registeredUsernames;
    }

    public static void setPlayerLoggedIn(Player player){
        playerLoggedIn = player;
    }

    public static Player getPlayerLoggedIn(){
        return playerLoggedIn;
    }
    
}

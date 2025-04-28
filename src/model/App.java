package model;

import view.Menu;

import java.util.ArrayList;

public class App {
    private static Player playerLoggedIn;
    private static Game currentGame;
    private static ArrayList<Player> registeredPlayers;
    private static ArrayList<String> registeredUsernames;
    private static ArrayList<Map> maps;
    private static ArrayList<Game> games;

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

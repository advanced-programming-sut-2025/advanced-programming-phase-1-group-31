package model;

import model.enums.npc.Shops;

import java.util.ArrayList;

public class Game {

    private static final TimeAndDate timeAndDate = new TimeAndDate();
    private static Player playerLoggedIn;
    private static Map mainMap;
    private static Player adminPlayer;
    private static Player activePlayer;
    private static TimeAndDate time;

    public static TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }

    public static Player getPlayerLoggedIn() {
        return playerLoggedIn;
    }

    public static void setPlayerLoggedIn(Player playerLoggedIn) {
        Game.playerLoggedIn = playerLoggedIn;
    }

    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Shops> shops = new ArrayList<>();
    public static Player getActivePlayer() {
        return activePlayer;
    }
    public static void changeTurn(){
        int playerIndex = players.indexOf(activePlayer) + 1;
        if(playerIndex >= players.size())
            playerIndex = 0;
        activePlayer = players.get(playerIndex);
    }


}

package model;

import model.enums.npc.Shops;
import model.materials.ShoppingBin;

import java.util.ArrayList;

public class Game {

    private static final TimeAndDate timeAndDate = new TimeAndDate();
    private static Player playerLoggedIn;
    private static Map mainMap;
    private static Player adminPlayer;
    private static Player activePlayer;
    private static final ShoppingBin shoppingBin = new ShoppingBin();
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Shops> shops = new ArrayList<>();



    public static TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }

    public static ShoppingBin getShoppingBin(){
        return shoppingBin;
    }

    public static Player getPlayerLoggedIn() {
        return playerLoggedIn;
    }

    public static void setPlayerLoggedIn(Player playerLoggedIn) {
        Game.playerLoggedIn = playerLoggedIn;
    }

    public static Player getActivePlayer() {
        return activePlayer;
    }

    public static void changeTurn(){
        int playerIndex = players.indexOf(activePlayer) + 1;
        if(playerIndex >= players.size())
            playerIndex = 0;
        activePlayer = players.get(playerIndex);
    }

    public static Map getMainMap() {
        return mainMap;
    }

    public static Player findPlayerByUsername(String name){
        for (Player player : players){
            if (player.getUsername().equals(name)) return player;
        }
        return null;
    }

    public static void addFriendShip() {
        Game.players.forEach(player ->
                Game.players.stream()
                        .filter(other -> !player.equals(other))
                        .filter(other -> player.getFriendships().stream()
                                .noneMatch(f -> f.getFriend().equals(other)))
                        .forEach(other -> player.getFriendships().add(new Friendship(other)))
        );
    }


}

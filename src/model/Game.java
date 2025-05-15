package model;

import model.enums.Shops;

import java.util.ArrayList;

public class Game {
    private Map mainMap;
    private Player adminPlayer;
    private Player activePlayer;
    private static final TimeAndDate timeAndDate = new TimeAndDate();
    private static TimeAndDate time;

    public static TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Shops> shops = new ArrayList<>();

    public Game(ArrayList<Player> players) {
        this.players = players;
    }

    public Map getMainMap() {
        return mainMap;
    }

    public void setMainMap(Map mainMap) {
        this.mainMap = mainMap;
    }

    public Player getAdminPlayer() {
        return adminPlayer;
    }

    public void setAdminPlayer(Player adminPlayer) {
        this.adminPlayer = adminPlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Shops> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shops> shops) {
        this.shops = shops;
    }
    public void changeTurn(){
        int playerIndex = this.players.indexOf(this.activePlayer) + 1;
        if(playerIndex >= this.players.size())
            playerIndex = 0;
        this.activePlayer = this.players.get(playerIndex);
    }

}

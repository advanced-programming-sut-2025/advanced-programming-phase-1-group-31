package model;

import model.enums.npc.Shops;
import model.materials.Shop;
import model.materials.ShoppingBin;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private Map mainMap;
    private Player adminPlayer;
    private Player activePlayer;
    private final TimeAndDate timeAndDate = new TimeAndDate();
    private final ShoppingBin shoppingBin = new ShoppingBin();
    private final ArrayList<Shop> shops = new ArrayList<>(
            Arrays.asList(
                    new Shop(Shops.BlackSmith),
                    new Shop(Shops.MarnieRanch),
                    new Shop(Shops.CarpenterShop),
                    new Shop(Shops.StardropSaloon),
                    new Shop(Shops.Jojamart),
                    new Shop(Shops.PierreGeneralStore),
                    new Shop(Shops.FishShop)
            )
    );

    public TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }


    public ShoppingBin getShoppingBin(){
        return shoppingBin;
    }

    private ArrayList<Player> players = new ArrayList<>();

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

    public void changeTurn(){
        int playerIndex = this.players.indexOf(this.activePlayer) + 1;
        if(playerIndex >= this.players.size())
            playerIndex = 0;
        this.activePlayer = this.players.get(playerIndex);
        timeAndDate.addHour(1);
        if (this.activePlayer.getEnergy().getEnergyAmount() <= 0){
            changeTurn();
        }
    }

    public Player findPlayerByUsername(String name){
        for (Player player : players){
            if (player.getUsername().equals(name)) return player;
        }
        return null;
    }


    public void addFriendShip() {
        players.forEach(player ->
                players.stream()
                        .filter(other -> !player.equals(other))
                        .filter(other -> player.getFriendships().stream()
                                .noneMatch(f -> f.getFriend().equals(other)))
                        .forEach(other -> player.getFriendships().add(new Friendship(other)))
        );
    }

    public void addFriendshipWithNPC(){
        for (Player player : )
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

}

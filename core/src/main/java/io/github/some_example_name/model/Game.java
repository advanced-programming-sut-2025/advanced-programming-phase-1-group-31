package io.github.some_example_name.model;



import io.github.some_example_name.control.FarmController;
import io.github.some_example_name.control.HouseController;
import io.github.some_example_name.control.MineController;
import io.github.some_example_name.control.StoreController;
import io.github.some_example_name.Main;
import io.github.some_example_name.view.*;
import io.github.some_example_name.model.materials.ShoppingBin;

import java.util.ArrayList;

public class Game {
    private FarmMap mainMap;
    private Player adminPlayer;
    private Player activePlayer;
    private final TimeAndDate timeAndDate = new TimeAndDate();
    private final ShoppingBin shoppingBin = new ShoppingBin();
    private GameView gameView;
    private MapManager mapManager;
    private MapInstanceManager instanceManager = new MapInstanceManager();
    public MapManager getMapManager() {
        return mapManager;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
    public MapInstanceManager getInstanceManager() {
        return instanceManager;
    }

    public void setInstanceManager(MapInstanceManager instanceManager) {
        this.instanceManager = instanceManager;
    }
//    private final ArrayList<Shop> shops = new ArrayList<>(
//            Arrays.asList(
//                    new Shop(Shops.BlackSmith),
//                    new Shop(Shops.MarnieRanch),
//                    new Shop(Shops.CarpenterShop),
//                    new Shop(Shops.StardropSaloon),
//                    new Shop(Shops.Jojamart),
//                    new Shop(Shops.PierreGeneralStore),
//                    new Shop(Shops.FishShop)
//            )
//    );
//
//    private final ArrayList<NPC> NPCs = new ArrayList<>(
//            Arrays.asList(
//                    new NPC(model.enums.npc.NPCs.SEBASTIAN),
//                    new NPC(model.enums.npc.NPCs.ABIGAIL),
//                    new NPC(model.enums.npc.NPCs.HARVEY),
//                    new NPC(model.enums.npc.NPCs.LEAH),
//                    new NPC(model.enums.npc.NPCs.ROBIN)
//            )
//    );
//
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

    public FarmMap getMainMap() {
        return mainMap;
    }
//
    public void setMainMap(FarmMap mainMap) {
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

    public void changeTurn() {
        int nextPlayerIndex = this.players.indexOf(this.activePlayer) + 1;
        if (nextPlayerIndex >= this.players.size()) {
            nextPlayerIndex = 0;
        }

        this.activePlayer = this.players.get(nextPlayerIndex);

        if (this.activePlayer.getEnergy().getEnergyAmount() <= 0) {
            changeTurn();
            return;
        }

        GameView newGameView;
        switch (this.activePlayer.getCurrentMapType()) {
            case FARM -> newGameView = new FarmView(new FarmController(), GameAssetManager.getGameAssetManager().getSkin() , MapType.FARM);
            case FARM1 -> newGameView = new FarmView(new FarmController(), GameAssetManager.getGameAssetManager().getSkin() , MapType.FARM1);
            case FARM2 -> newGameView = new FarmView(new FarmController(), GameAssetManager.getGameAssetManager().getSkin() , MapType.FARM2);
            case MINE -> newGameView = new MineView(new MineController(), GameAssetManager.getGameAssetManager().getSkin());
            case HOUSE -> newGameView = new HouseView(new HouseController() , GameAssetManager.getGameAssetManager().getSkin());
            case BLACKSMITH -> newGameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.BLACKSMITH);
            case STARDROPSALOON -> newGameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.STARDROPSALOON);
            case JOJAMART -> newGameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.JOJAMART);
            case CARPENTERSHOP -> newGameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.CARPENTERSHOP);
            case MARNIERANCH -> newGameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.MARNIERANCH);
            default -> throw new IllegalArgumentException("Unknown map type: " + this.activePlayer.getCurrentMapType());
        }

        // 👇 اول ست کن گیم ویو
        App.getCurrentGame().setGameView(newGameView);

        // 👇 حالا فید اجرا بشه از ویوی جدید
            Main.getMain().setScreen(newGameView);
        timeAndDate.addHour(1);

    }

    public Player findPlayerByUsername(String name){
        for (Player player : players){
            if (player.getUsername().equals(name)) return player;
        }
        return null;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
    public FarmMap getMapForPlayer(Player player, MapType type) {
        if (type.isShared()) {

            return App.getCurrentGame().getMapManager().getMap(type.getFilename());
        } else {

            return App.getCurrentGame().getInstanceManager().getPlayerMap(player.getId(), type);
        }
    }
//    public void addFriendShip() {
//        players.forEach(player ->
//                players.stream()
//                        .filter(other -> !player.equals(other))
//                        .filter(other -> player.getFriendships().stream()
//                                .noneMatch(f -> f.getFriend().equals(other)))
//                        .forEach(other -> player.getFriendships().add(new Friendship(other)))
//        );
//    }
//
//    public void addFriendshipWithNPC(){
//        for (Player player : players){
//            for (NPC npc : getNPCs()){
//                player.getFriendshipWithNPCS().add(new FriendshipWithNPC(npc));
//            }
//        }
//    }
//
//    public ArrayList<Shop> getShops() {
//        return shops;
//    }
//
//    public ArrayList<NPC> getNPCs() {
//        return NPCs;
//    }
}

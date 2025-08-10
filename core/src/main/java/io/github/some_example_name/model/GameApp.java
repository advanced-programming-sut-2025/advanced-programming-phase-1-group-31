package io.github.some_example_name.model;



import common.Lobby;
import common.Message;
import common.UserInfo;
import io.github.some_example_name.view.*;
import io.github.some_example_name.model.materials.ShoppingBin;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameApp {
    public static C2SConnectionThread c2sConnectionThread;
    public static Player player = new Player(new UserInfo(null, null, null, null));
    public static ArrayList<Point> othersPoint = new ArrayList<>();
    private static FarmMap mainMap;
    private static final TimeAndDate timeAndDate = new TimeAndDate();
    private static final ShoppingBin shoppingBin = new ShoppingBin();
    private static GameView gameView;
    private static MapManager mapManager;
    private static MapInstanceManager instanceManager = new MapInstanceManager();

    public static Player getPlayer() {
        return GameApp.player;
    }
    public static void leaveLobby(Lobby lobby) {
        lobby.removePlayer(player.getUsername());
        HashMap<String, Object> body = new HashMap<>();
        if (lobby.getAdmin().equals(player.getUsername()) && lobby.getNumberOfPlayers() != 0) {
            lobby.setAdmin(lobby.getPlayers().getFirst());
        } else if (lobby.getNumberOfPlayers() == 0) {
            body.put("delete-lobby", true);
        }
        body.put("lobby", lobby);
        GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Lobby));
        player.setLobby(null);
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static void setMapManager(MapManager mapManager) {
        GameApp.mapManager = mapManager;
    }
    public static MapInstanceManager getInstanceManager() {
        return instanceManager;
    }

    public static void setInstanceManager(MapInstanceManager instanceManager) {
        GameApp.instanceManager = instanceManager;
    }

    public static ShoppingBin getShoppingBin(){
        return GameApp.shoppingBin;
    }

    public static FarmMap getMainMap() {
        return mainMap;
    }

    public static void setMainMap(FarmMap mainMap) {
        GameApp.mainMap = mainMap;
    }
    public static GameView getGameView() {
        return gameView;
    }

    //                players.stream()
//        players.forEach(player ->
//    public void addFriendShip() {
//
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
    public static TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }

    public static void setGameView(GameView gameView) {
        GameApp.gameView = gameView;
    }

    public static FarmMap getMapForPlayer(Player player, MapType type) {
        if (type.isShared()) {

            return GameApp.getMapManager().getMap(type.getFilename());
        } else {

            return GameApp.getInstanceManager().getPlayerMap(player.getId(), type);
        }
    }

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

package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.view.FarmView;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.MineView;
import io.github.some_example_name.view.StoreView;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.List;

public class FarmController extends GameController{
    public void checkWarpsAndSpecialAreas(float delta) {
        if (getView().isJustTeleported())
            return;

        checkMapWarps();
        checkPlayerFarmWarps();
        // checkSpecialAreas();
    }
    public void checkPlayerFarmWarps() {
        if (getView().getMapType() != MapType.FARM){
            return;
        }
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < GameApp.getPlayers().size(); i++) {
            players.add(GameApp.getPlayers().get(i));
        }

        for (Player player : players) {
            if (player.getFarm() == null || player.getFarm().getAllObjects() == null)
                continue;
            List<MapObject> objects = new ArrayList<>();
            for (MapObject object : player.getFarm().getAllObjects()) {
                objects.add(object);
            }


            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject
                    && Intersector.overlaps(GameApp.getPlayer().getPlayerRectangle(),
                    ((RectangleMapObject) object).getRectangle())) {
                    handleFarmWarp(object, player);
                } else if (object instanceof PolygonMapObject) {
                    handlePolygonSpecialAreas((PolygonMapObject) object);
                }
            }
        }
    }
    public void startPoint(TiledMap map){
        for (Player player : GameApp.getPlayers()) {
            Farm farm = player.getFarm();
            if (farm == null)
                continue;



            if (getView().getMapType() == MapType.FARM) {
                if (farm.getAllObjects() != null) {
                    for (MapObject object : farm.getAllObjects()) {
                        if (object instanceof RectangleMapObject && object.getName().equals("start")) {
                            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                            player.setPlace(new Vector2(rectangle.x, rectangle.y));
                            player.setPlayerRectangle(new Rectangle(rectangle.x, rectangle.y, 20, 20));
                            player.setCharacterPlacer(new CharacterPlacer(map));
                        }
                    }
                }
            }

        }
    }
    public void handlePolygonSpecialAreas(PolygonMapObject area) {
        if (area.getName().equals("lake"))
            handleLakeArea(area.getPolygon());
        if (!area.getPolygon().contains(GameApp.getPlayer().getPlace().x,
            GameApp.getPlayer().getPlace().y))
            return;

        switch (area.getName()) {
            case "lake":
                handleLakeArea(area.getPolygon());
                break;
            case "mineDoor":
                handleMineDoor(area);
                break;
            case "house":
                handleHouseDoor(area);
                break;
        }
    }
    public void handleFarmWarp(MapObject warpObject, Player owner) {
        String newMap = (String) warpObject.getProperties().get("targetMap");
        if (newMap == null)
            return;
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        GameApp.getPlayer().setCurrentMapType(targetType);

        GameView gameView;
        switch (GameApp.getPlayer().getCurrentMapType()) {
            case FARM:
                gameView = new FarmView(new FarmController(),
                    GameAssetManager.getGameAssetManager().getSkin() , MapType.FARM);
                break;
            case MINE:
                gameView = new MineView(new MineController(),
                    GameAssetManager.getGameAssetManager().getSkin());
                break;
            case BLACKSMITH:
                gameView = new StoreView(new StoreController(),
                    GameAssetManager.getGameAssetManager().getSkin() , MapType.BLACKSMITH);
                break;
            case STARDROPSALOON:
                gameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.STARDROPSALOON);
                break;
            case JOJAMART:
                gameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.JOJAMART);
                break;
            case PIERREGENERALSTORE:
                gameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.PIERREGENERALSTORE);
                break;
            case CARPENTERSHOP:
                gameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.CARPENTERSHOP);
                break;
            case MARNIERANCH:
                gameView = new StoreView(new StoreController() , GameAssetManager.getGameAssetManager().getSkin(), MapType.MARNIERANCH);
                break;
            default:
                // Handle unexpected map types
                throw new IllegalArgumentException("Unknown map type: " +
                    GameApp.getPlayer().getCurrentMapType());
        }
        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);

            Main.getMain().setScreen(gameView);
            if (targetType == MapType.FARM1 || targetType == MapType.FARM2) {
                gameView.getGameController().setStartPoint(gameView.getMap(), "infarm");
            }else {
                gameView.getGameController().startPoint(gameView.getMap());
            }

        });
//        positionPlayerAtExit("out");
        startTeleportCooldown();
    }
    public void setStartPoint(TiledMap map, String targetObjectName) {
        Farm farm = GameApp.getPlayer().getFarm();
        if (farm == null || farm.getAllObjects() == null)
            return;

        for (MapObject object : farm.getAllObjects()) {
            if (object instanceof RectangleMapObject && object.getName().equals(targetObjectName)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                teleportPlayer(rectangle.x, rectangle.y);
                break; // پس از یافتن اولین شیء مورد نظر متوقف می‌شود
            }
        }
        for (MapObject object : map.getLayers().get("object1").getObjects()) {
            if (object instanceof RectangleMapObject && object.getName().equals(targetObjectName)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                teleportPlayer(rectangle.x, rectangle.y);
                break;
            }

        }
    }
}

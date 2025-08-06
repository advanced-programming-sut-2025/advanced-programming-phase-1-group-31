package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.Main;
import io.github.some_example_name.view.FarmView;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreController extends GameController {
    @Override
    public void startPoint(TiledMap map) {
        String exitName = "outStore";
        MapObject exit = getView().getMap().getLayers().get("Object").getObjects().get(exitName);
        if (exit instanceof RectangleMapObject) {
            Rectangle exitRect = ((RectangleMapObject) exit).getRectangle();
            teleportPlayer(exitRect.x, exitRect.y);

        }
    }

    public void openDoorAt(TiledMap map, int tileX, int tileY, int[] animationFrameIds, int finalTileId) {
        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("door");
        TiledMapTileSet tileSet = map.getTileSets().getTileSet("Cursors"); // فرض: یه tileSet داری

        Array<StaticTiledMapTile> frameTiles = new Array<>();
        for (int id : animationFrameIds) {
            frameTiles.add(new StaticTiledMapTile(tileSet.getTile(id).getTextureRegion()));
        }

        AnimatedTiledMapTile animTile = new AnimatedTiledMapTile(0.15f, frameTiles);
        TiledMapTileLayer.Cell animCell = new TiledMapTileLayer.Cell();
        animCell.setTile(animTile);
        doorLayer.setCell(tileX, tileY, animCell);

        // بعد از اتمام انیمیشن، تایل نهایی رو ست کن
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                TiledMapTileLayer.Cell finalCell = new TiledMapTileLayer.Cell();
                finalCell.setTile(tileSet.getTile(finalTileId)); // در باز
                doorLayer.setCell(tileX, tileY, finalCell);
            }
        }, 0.15f * animationFrameIds.length); // مدت زمان انیمیشن
    }
    @Override
    public void checkWarpsAndSpecialAreas(float delta) {
        if (getView().isJustTeleported())
            return;

        checkMapWarps();
        checkPlayerFarmWarps();
    }

    @Override
    public void checkPlayerFarmWarps() {
        Player player = GameApp.getPlayer();
        if (player.getFarm() == null || player.getFarm().getAllObjects() == null)
            return;
        List<MapObject> objects = new ArrayList<>();
        for (MapObject object : getView().getMap().getLayers().get("Object").getObjects()) {
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
    public boolean isBlocked(float x, float y) {
        // تبدیل مختصات جهانی به مختصات تایل
        int tileX = (int) (x / getView().getTILE_SIZE());
        int tileY = (int) (y / getView().getTILE_SIZE());

        // 1. بررسی لایه‌های بلوک در مزارع بازیکنان
//        if (checkPlayerFarmBlocks(x, y, tileX, tileY)) {
//            return true;
//        }

        // 2. بررسی لایه‌های خاص در نقشه اصلی
        if (checkMapBlockingLayers(tileX, tileY)) {
            return true;
        }

        return false;
    }
    public boolean checkMapBlockingLayers(int tileX, int tileY) {
        String[] blockingLayers = { "Buildings", "Front", "Front2" };
        if (getView().getMapType() == MapType.JOJAMART || getView().getMapType() == MapType.PIERREGENERALSTORE) {
            blockingLayers = Arrays.stream(blockingLayers)
                .filter(layer -> !layer.equals("Front"))
                .toArray(String[]::new);
        }

        for (String layerName : blockingLayers) {
            TiledMapTileLayer layer = (TiledMapTileLayer) getView().getMap().getLayers().get(layerName);
            if (layer != null && layer.getCell(tileX, tileY) != null) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void handleFarmWarp(MapObject warpObject, Player owner) {
        if (warpObject.getName().equals("door")) {
            TiledMapTileLayer doorLayer = (TiledMapTileLayer) getView().getMap().getLayers().get("door");
            for (int x = 0; x < doorLayer.getWidth(); x++) {
                for (int y = 0; y < doorLayer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = doorLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null) {
                        int tileId = cell.getTile().getId();
                        if (tileId == 3981) { // فرض: tileId 11 بخش چپ در
                            openDoorAt(getView().getMap(), x, y, new int[]{3981, 3982, 3983, 3984}, 3984);
                            openDoorAt(getView().getMap(), x, y - 1, new int[]{4025, 4026, 4027, 4028}, 4028);
                            openDoorAt(getView().getMap(), x, y - 2, new int[]{4069, 4070, 4071, 4072}, 4072);
                        }
                    }
                }
            }
        }


        if (!warpObject.getName().equals("out")) {
            return;
        }

        String newMap = (String) warpObject.getProperties().get("targetMap");
        if (newMap == null) {
            return;
        }

        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        MapType Type = GameApp.getPlayer().getCurrentMapType();
        GameApp.getPlayer().setCurrentMapType(targetType);

        GameView gameView = new FarmView(new FarmController(),
            GameAssetManager.getGameAssetManager().getSkin() , targetType);

        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);
            Main.getMain().setScreen(gameView);

            String targetMap;
            switch (Type ) {
                case BLACKSMITH:
                    targetMap = "outblacksmith";
                    break;
                case STARDROPSALOON:
                    targetMap = "outstardropsaloon";
                    break;
                case JOJAMART:
                    targetMap = "outjojamart";
                    break;
                case PIERREGENERALSTORE:
                    targetMap = "outpierregeneralstore";
                    break;
                case CARPENTERSHOP:
                    targetMap = "outcarpentershop";
                    break;
                case MARNIERANCH:
                    targetMap = "outmarnieranch";
                    break;
                default:
                    // Handle unexpected map types (optional: log an error or use a default)
                    targetMap = "outblacksmith"; // Fallback
                    break;
            }

            gameView.getGameController().setStartPoint(gameView.getMap(), targetMap);
        });

        startTeleportCooldown();
    }
    @Override
    public void handlePolygonSpecialAreas(PolygonMapObject area) {

    }
}

package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.some_example_name.Main;
import io.github.some_example_name.view.*;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class GameController {
    private GameView view;
    private Label messageLabel;

    public void setView(GameView view) {
        this.view = view;
        messageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public abstract void startPoint(TiledMap map);

    public void handleInput(float delta) {
        int oldTileX = (int) (GameApp.getPlayer().getPlace().x / view.getTILE_SIZE());
        int oldTileY = (int) (GameApp.getPlayer().getPlace().y / view.getTILE_SIZE());

        // مدیریت تلهپورت
        if (view.getTeleportCooldown() > 0) {
            view.setTeleportCooldown(view.getTeleportCooldown() - delta);
            view.setJustTeleported(true);
        } else {
            view.setJustTeleported(false);
        }

        boolean moved = false;
        CharacterPlacer.Direction direction = null;

        float nextX = GameApp.getPlayer().getPlace().x;
        float nextY = GameApp.getPlayer().getPlace().y;

        // پردازش ورودی کاربر
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nextX -= view.getSpeed() * delta;
            direction = CharacterPlacer.Direction.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nextX += view.getSpeed() * delta;
            direction = CharacterPlacer.Direction.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nextY += view.getSpeed() * delta;
            direction = CharacterPlacer.Direction.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nextY -= view.getSpeed() * delta;
            direction = CharacterPlacer.Direction.DOWN;
        }

        // بررسی حرکت و موانع
        if (direction != null && !isBlocked(nextX, nextY)) {
            GameApp.getPlayer().setPlace(new Vector2(nextX, nextY));
            moved = true;
            GameApp.getPlayer().getEnergy().changeEnergy(-0.005);
        }

        int newTileX = (int) (GameApp.getPlayer().getPlace().x / view.getTILE_SIZE());
        int newTileY = (int) (GameApp.getPlayer().getPlace().y / view.getTILE_SIZE());

        // مدیریت نمایش کاراکتر
        if (moved && (newTileX != oldTileX || newTileY != oldTileY)) {
            GameApp.getPlayer().getCharacterPlacer().clearCharacter(oldTileX, oldTileY);
            GameApp.getPlayer().getCharacterPlacer().placeCharacter(newTileX, newTileY, direction);
        } else if (!moved) {
            GameApp.getPlayer().getCharacterPlacer().clearCharacter(newTileX, newTileY);
            GameApp.getPlayer().getCharacterPlacer().dontMove(newTileX, newTileY);
        }
        if (GameApp.getPlayer().getEnergy().getEnergyAmount()<0){
            GameApp.getPlayer().getCharacterPlacer().faint(newTileX , newTileY+2);
        } else {
            GameApp.getPlayer().getCharacterPlacer().clearCharacter(newTileX-1 , newTileY+2);
            GameApp.getPlayer().getCharacterPlacer().clearCharacter(newTileX , newTileY+2);
        }
//        GameApp.getPlayers().stream().filter(player -> player != GameApp.getPlayer()).forEach(player -> {player.getCharacterPlacer().dontMove((int) (player.getPlace().x / view.getTILE_SIZE()), (int) (player.getPlace().y / view.getTILE_SIZE()));});

        GameApp.getPlayer().getPlayerRectangle().setPosition(nextX, nextY);
        view.setShowFullMap(Gdx.input.isKeyPressed(Input.Keys.M));
        int mapWidth = view.getMap().getProperties().get("width", Integer.class);
        int mapHeight = view.getMap().getProperties().get("height", Integer.class);
        int tileWidth = view.getMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = view.getMap().getProperties().get("tileheight", Integer.class);
        if (view.isShowFullMap()) {


            float worldWidth = mapWidth * tileWidth;
            float worldHeight = mapHeight * tileHeight;

            view.getCamera().setToOrtho(false, worldWidth, worldHeight);
            view.getCamera().position.set(worldWidth / 2f, worldHeight / 2f, 0);
        } else {
            view.getCamera().setToOrtho(false, (float) (mapWidth * tileWidth) /2, (float) (mapHeight * tileHeight) /2);
            view.getCamera().position.set(
                GameApp.getPlayer().getPlace().x + view.getTILE_SIZE() / 2f,
                GameApp.getPlayer().getPlace().y + view.getTILE_SIZE() / 2f, 0);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
//            GameApp.changeTurn();
//        }
    }

    public boolean isBlocked(float x, float y) {
        // تبدیل مختصات جهانی به مختصات تایل
        int tileX = (int) (x / view.getTILE_SIZE());
        int tileY = (int) (y / view.getTILE_SIZE());

        // 1. بررسی لایه‌های بلوک در مزارع بازیکنان
        if (checkPlayerFarmBlocks(x, y, tileX, tileY)) {
            return true;
        }

        // 2. بررسی لایه‌های خاص در نقشه اصلی
        if (checkMapBlockingLayers(tileX, tileY)) {
            return true;
        }

        return false;
    }

    public boolean checkPlayerFarmBlocks(float x, float y, int tileX, int tileY) {
        if (getView().getMapType() != MapType.FARM) {
            return false;
        }
        for (Player player : GameApp.getPlayers()) {
            Farm farm = player.getFarm();
            if (farm == null)
                continue;

            // بررسی لایه بلوک
            TiledMapTileLayer blockLayer = (TiledMapTileLayer) farm.getBlockLayer();
            if (blockLayer != null && blockLayer.getCell(tileX, tileY) != null) {
                return true;
            }

            // بررسی آبجکت‌های پلیگونی (فقط وقتی تلهپورت نکرده‌ایم)
            if (!view.isJustTeleported() && farm.getAllObjects() != null) {
                for (MapObject object : farm.getAllObjects()) {
                    if (isBlockingPolygonObject(object, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlockingPolygonObject(MapObject object, float x, float y) {
        if (!(object instanceof PolygonMapObject))
            return false;

        String objectName = object.getName();
        if ("plantable".equals(objectName) || "mineDoor".equals(objectName) || "house".equals(objectName)) {
            return false;
        }

        Polygon poly = ((PolygonMapObject) object).getPolygon();
        return poly.contains(x, y);
    }

    private boolean checkMapBlockingLayers(int tileX, int tileY) {
        String[] blockingLayers = { "craft", "Buildings5", "block" , "mine" , "Buildings8" , "Buildings10" };

        for (String layerName : blockingLayers) {
            TiledMapTileLayer layer = (TiledMapTileLayer) view.getMap().getLayers().get(layerName);
            if (layer != null && layer.getCell(tileX, tileY) != null) {
                return true;
            }
        }
        return false;
    }

    // در کلاس View یا GameScreen
    public abstract void checkWarpsAndSpecialAreas(float delta) ;

    public void checkMapWarps() {
        checkLayerWarps("object");
        checkLayerWarps("object1");
    }

    private void checkLayerWarps(String layerName) {
        if (view.getMap().getLayers().get(layerName) == null)
            return;

        for (MapObject object : view.getMap().getLayers().get(layerName).getObjects()) {
            if (object instanceof RectangleMapObject
                    && Intersector.overlaps(GameApp.getPlayer().getPlayerRectangle(),
                            ((RectangleMapObject) object).getRectangle()) && (object.getName().equals("blacksmith") || object.getName().equals("stardropsaloon") || object.getName().equals("jojamart") || object.getName().equals("pierregeneralstore") || object.getName().equals("carpentershop") || object.getName().equals("marnieranch") )) {
                handleStoreDoor(object);
            }
            else if (object instanceof RectangleMapObject
                && Intersector.overlaps(GameApp.getPlayer().getPlayerRectangle(),
                ((RectangleMapObject) object).getRectangle()) && ((object.getName().equals("Farm1")) || object.getName().equals("Farm") || (object.getName().equals("Farm2"))) ) {
                handleFarmDoor(object);
            }

        }
    }

    public abstract void checkPlayerFarmWarps() ;

    private void handleWarp(MapObject warpObject, String exitPointName) {
        String newMap = warpObject.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;

        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        FarmMap farmMap = GameApp.getMapForPlayer(GameApp.getPlayer() , targetType);
        changeMap(farmMap);
//        positionPlayerAtExit(exitPointName);
        startTeleportCooldown();
    }

    public abstract void handleFarmWarp(MapObject warpObject, Player owner) ;

    public abstract void handlePolygonSpecialAreas(PolygonMapObject area) ;

    protected void handleLakeArea(Polygon poly) {
        // placeScaledImageAsTile(map, "craft", (int)(playerX/TILE_SIZE),
        // (int)(playerY/TILE_SIZE), "soil.png");
        if (isNearPolygonEdge(poly, GameApp.getPlayer().getPlace().x,
            GameApp.getPlayer().getPlace().y, 16)) {

            // view.getPlacer().clearCharacter((int)
            // App.getCurrentGame().getActivePlayer().getPlace().x, (int)
            // App.getCurrentGame().getActivePlayer().getPlace().y);
            //// placeScaledImageAsTile(map, "craft", newTileX, newTileY, "soil.png");
            // teleportPlayer(10*16, 10*16);
        }
        // موقعیت پیش‌فرض بعد از افتادن در آب
    }
    protected void handleStoreDoor(MapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        GameApp.getPlayer().setCurrentMapType(targetType);

        GameView gameView = new StoreView(new StoreController(),GameAssetManager.getGameAssetManager().getSkin() , targetType );
        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);

            Main.getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleMineDoor(PolygonMapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;
        GameApp.getPlayer().setCurrentMapType(MapType.MINE);

        GameView gameView = new MineView(new MineController(),GameAssetManager.getGameAssetManager().getSkin() );
        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);

        Main.getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleHouseDoor(PolygonMapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;
        GameApp.getPlayer().setCurrentMapType(MapType.HOUSE);

        GameView gameView = new HouseView(new HouseController(),GameAssetManager.getGameAssetManager().getSkin() );
        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);

            Main.getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleFarmDoor(MapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);

        if (newMap == null)
            return;
        MapType oldType = GameApp.getPlayer().getCurrentMapType();
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        GameApp.getPlayer().setCurrentMapType(targetType);

        GameView gameView = new FarmView(new FarmController(),GameAssetManager.getGameAssetManager().getSkin() , targetType );
        Gdx.app.postRunnable(() -> {
            GameApp.setGameView(gameView);

            Main.getMain().setScreen(gameView);
            if(targetType == MapType.FARM) {
                if (oldType == MapType.FARM1) {
                    gameView.getGameController().setStartPoint(gameView.getMap() , "outfarm1");
                }else {
                    gameView.getGameController().setStartPoint(gameView.getMap() , "outfarm2");
                }
            } else {
                gameView.getGameController().setStartPoint(gameView.getMap() , "infarm");

            }

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }

    private void changeMap(FarmMap newMap) {
        view.setMap(newMap);
        view.getMapRenderer().setMap(newMap.getTmxMap());
        GameApp.getPlayer().setCharacterPlacer(new CharacterPlacer(newMap.getTmxMap()));
    }

//    public void positionPlayerAtExit(String exitName) {
//        MapObject exit = view.getMap().getLayers().get("Object").getObjects().get(exitName);
//        if (exit instanceof RectangleMapObject) {
//            Rectangle exitRect = ((RectangleMapObject) exit).getRectangle();
//            teleportPlayer(exitRect.x, exitRect.y);
//        }
//    }

    public void teleportPlayer(float x, float y) {
        GameApp.getPlayer().getCharacterPlacer().clearCharacter(
                (int) (GameApp.getPlayer().getPlace().x / view.getTILE_SIZE()),
                (int) (GameApp.getPlayer().getPlace().y / view.getTILE_SIZE()));
        GameApp.getPlayer().setPlace(new Vector2(x, y));

        GameApp.getPlayer().getPlayerRectangle().setPosition(x, y);
        GameApp.getPlayer().setCharacterPlacer(new CharacterPlacer(getView().getMap()));


    }

    protected void startTeleportCooldown() {
        view.setTeleportCooldown(1.0f);
    }

    public void handleStartGameWithMaps(List<String> selectedMaps, List<String> playerUsernames) {
        List<String> notSelectedYet = new ArrayList<>();

        for (int i = 0; i < playerUsernames.size(); i++) {
            if (i >= selectedMaps.size() || selectedMaps.get(i) == null || selectedMaps.get(i).isEmpty()) {
                notSelectedYet.add(playerUsernames.get(i));
            }
        }
        if (!notSelectedYet.isEmpty()) {
            showErrorMessage("Players who haven't selected their map yet: " + notSelectedYet);
        }
    }

    public static boolean isNearPolygonEdge(Polygon polygon, float px, float py, float maxDistance) {
        float[] vertices = polygon.getTransformedVertices();
        Vector2 player = new Vector2(px, py);

        for (int i = 0; i < vertices.length - 2; i += 2) {
            Vector2 p1 = new Vector2(vertices[i], vertices[i + 1]);
            Vector2 p2 = new Vector2(vertices[i + 2], vertices[i + 3]);

            if (Intersector.distanceSegmentPoint(p1, p2, player) <= maxDistance)
                return true;
        }

        Vector2 pLast = new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]);
        Vector2 pFirst = new Vector2(vertices[0], vertices[1]);

        return Intersector.distanceSegmentPoint(pLast, pFirst, player) <= maxDistance;
    }

    public void showSuccessMessage(String text) {
        messageLabel.clearActions();
        messageLabel.setText(text);
        messageLabel.setColor(0, 1, 0, 0);

        float scaleUp = 1.2f;
        float scaleDuration = 0.3f;

        SequenceAction shakeSequence = Actions.sequence();
        for (int i = 0; i < 2; i++) {
            shakeSequence.addAction(Actions.moveBy(3f, 0, 0.05f));
            shakeSequence.addAction(Actions.moveBy(-6f, 0, 0.05f));
            shakeSequence.addAction(Actions.moveBy(3f, 0, 0.05f));
        }

        SequenceAction successSequence = Actions.sequence(
                Actions.parallel(
                        Actions.fadeIn(0.5f),
                        Actions.scaleTo(scaleUp, scaleUp, scaleDuration),
                        Actions.rotateBy(15f, scaleDuration)),
                Actions.parallel(
                        Actions.scaleTo(1f, 1f, 0.3f),
                        Actions.rotateTo(0f, 0.3f)),
                shakeSequence,
                Actions.delay(3f),
                Actions.fadeOut(1f));

        messageLabel.addAction(successSequence);
    }

    public void showErrorMessage(String text) {
        messageLabel.clearActions();
        messageLabel.setText(text);
        messageLabel.setColor(1, 0, 0, 1);

        float shakeAmount = 5f;
        float shakeDuration = 0.05f;

        SequenceAction shakeSequence = Actions.sequence();
        for (int i = 0; i < 3; i++) {
            shakeSequence.addAction(Actions.moveBy(shakeAmount, 0, shakeDuration));
            shakeSequence.addAction(Actions.moveBy(-2 * shakeAmount, 0, shakeDuration));
            shakeSequence.addAction(Actions.moveBy(shakeAmount, 0, shakeDuration));
        }
        shakeSequence.addAction(Actions.moveTo(messageLabel.getX(), messageLabel.getY(), shakeDuration));

        RepeatAction blinkRepeat = Actions.repeat(3, Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.fadeIn(0.5f)));

        SequenceAction fullSequence = Actions.sequence(
                shakeSequence,
                blinkRepeat,
                Actions.delay(3f),
                Actions.fadeOut(1f));

        messageLabel.addAction(fullSequence);
    }

    public GameView getView() {
        return view;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    protected void setStartPoint(TiledMap map , String targetObjectName) {
        return;
    }
}

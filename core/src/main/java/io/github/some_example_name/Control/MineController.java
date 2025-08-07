package io.github.some_example_name.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.FarmView;
import io.github.some_example_name.View.GameView;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.List;

public class MineController extends GameController {
    @Override
    public void startPoint(TiledMap map) {
        String exitName = "outMine";
        MapObject exit = getView().getMap().getLayers().get("Object").getObjects().get(exitName);
        if (exit instanceof RectangleMapObject) {
            Rectangle exitRect = ((RectangleMapObject) exit).getRectangle();
            teleportPlayer(exitRect.x, exitRect.y);

        }
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
        Player player = App.getCurrentGame().getActivePlayer();
        if (player.getFarm() == null || player.getFarm().getAllObjects() == null)
            return;
        List<MapObject> objects = new ArrayList<>();
        for (MapObject object : getView().getMap().getLayers().get("Object").getObjects()) {
            objects.add(object);
        }


        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject
                && Intersector.overlaps(App.getCurrentGame().getActivePlayer().getPlayerRectangle(),
                ((RectangleMapObject) object).getRectangle())) {
                handleFarmWarp(object, player);
            } else if (object instanceof PolygonMapObject) {
                handlePolygonSpecialAreas((PolygonMapObject) object);
            }
        }
    }

    @Override
    public void handleFarmWarp(MapObject warpObject, Player owner) {
        if (!warpObject.getName().equals("out")) {
            return;
        }
        String newMap = (String) warpObject.getProperties().get("targetMap");
        Boolean isPrivate = (Boolean) warpObject.getProperties().get("private");
        if (newMap == null)
            return;
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        App.getCurrentGame().getActivePlayer().setCurrentMapType(targetType);

        GameView gameView = new FarmView(new FarmController(),
            GameAssetManager.getInstance().getSkin() , MapType.FARM);

        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

            Main.getMain().setScreen(gameView);
            gameView.getGameController().setStartPoint(gameView.getMap() , "outMine");

        });
//        positionPlayerAtExit("out");
        startTeleportCooldown();
    }

    @Override
    public void handlePolygonSpecialAreas(PolygonMapObject area) {
        return;

    }
}

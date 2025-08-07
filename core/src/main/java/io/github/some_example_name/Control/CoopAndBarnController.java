package io.github.some_example_name.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.CoopAndBarnView;
import io.github.some_example_name.View.FarmView;
import io.github.some_example_name.View.GameView;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.MapType;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.materials.Animal;
import io.github.some_example_name.model.materials.Barn;
import io.github.some_example_name.model.materials.Coop;
import io.github.some_example_name.model.materials.Material;

import java.util.ArrayList;
import java.util.List;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class CoopAndBarnController extends GameController{

    @Override
    public void startPoint(TiledMap map) {
        String exitName = "outCAB";
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
        if (getView().getMap().getLayers().get("Object")==null){
            return;
        }
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
    public void setCameraBuild(){
        Iterable<MapObject> objects = getView().getMap().getLayers().get("Object").getObjects();

        // همه نواحی plantable رو ذخیره کن
        for (MapObject obj : objects) {
            if (obj instanceof RectangleMapObject && "bac".equals(obj.getName())) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;

                float x = rectObj.getRectangle().x;
                float y = rectObj.getRectangle().y;
                float width = rectObj.getRectangle().width;
                float height = rectObj.getRectangle().height;

                float centerX = x + width / 2f;
                float centerY = y + height / 2f;

                getView().getCamera().setToOrtho(false ,width , height);
                getView().getCamera().position.set(centerX, centerY, 0);
                break;
            }
        }
    }
    @Override
    public void handleFarmWarp(MapObject warpObject, Player owner) {
        if (warpObject.getName() == null) {
            return;
        }
        if (!warpObject.getName().equals("out")) {
            return;
        }

        String newMap = (String) warpObject.getProperties().get("targetMap");
        if (newMap == null) {
            return;
        }
        CoopAndBarnView coopAndBarnView = (CoopAndBarnView) getView();
        Material material = coopAndBarnView.getBarnOrCoop();
        RectangleMapObject rectangleMapObject ;
        if (material instanceof Coop) {
            Coop coop = (Coop) coopAndBarnView.getBarnOrCoop();
            rectangleMapObject = coop.getOut();
        } else {
            Barn barn = (Barn) coopAndBarnView.getBarnOrCoop();
            rectangleMapObject = barn.getOut();
        }

        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        MapType Type = App.getCurrentGame().getActivePlayer().getCurrentMapType();
        App.getCurrentGame().getActivePlayer().setCurrentMapType(targetType);

        GameView gameView = new FarmView(new FarmController(),
            GameAssetManager.getInstance().getSkin() , targetType);

        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);
            Main.getMain().setScreen(gameView);

            String targetMap;
//            switch (Type ) {
//                case BLACKSMITH:
//                    targetMap = "outblacksmith";
//                    break;
//                case STARDROPSALOON:
//                    targetMap = "outstardropsaloon";
//                    break;
//                case JOJAMART:
//                    targetMap = "outjojamart";
//                    break;
//                case PIERREGENERALSTORE:
//                    targetMap = "outpierregeneralstore";
//                    break;
//                case CARPENTERSHOP:
//                    targetMap = "outcarpentershop";
//                    break;
//                case MARNIERANCH:
//                    targetMap = "outmarnieranch";
//                    break;
//                default:
//                    // Handle unexpected map types (optional: log an error or use a default)
//                    targetMap = "outblacksmith"; // Fallback
//                    break;
//            }


            gameView.getGameController().setStartPointFromBAC(gameView.getMap(), rectangleMapObject);
        });

        startTeleportCooldown();
    }
    public void setAnimal() {
        MapLayer objectLayer = getView().getMap().getLayers().get("Object");
        if (objectLayer == null) {
            return;
        }

        CoopAndBarnView coopAndBarnView = (CoopAndBarnView) getView();
        ArrayList<Animal> animals = new ArrayList<>();

        if (coopAndBarnView.getBarnOrCoop() instanceof Barn barn) {
            animals = barn.getAnimals();
        } else if (coopAndBarnView.getBarnOrCoop() instanceof Coop coop) {
            animals = coop.getAnimals();
        }

        List<MapObject> objects = new ArrayList<>();
        for (MapObject object : objectLayer.getObjects()) {
            objects.add(object);
        }
        // ابتدا تایل‌های حیوانات را قرار دهید
        for (int i = 0; i < animals.size() && i < objects.size(); i++) {
            Animal animal = animals.get(i);
            if (animal.getAnimalFriendship().isStayedOutsideTonight()) {
                continue;
            }
            MapObject object = objects.get(i);

            float x = 0, y = 0;
            if (object instanceof RectangleMapObject rectObj) {
                x = rectObj.getRectangle().x;
                y = rectObj.getRectangle().y;
            } else if (object instanceof EllipseMapObject ellipseObj) {
                x = ellipseObj.getEllipse().x;
                y = ellipseObj.getEllipse().y;
            }

            int tileX = (int) (x / getView().getTILE_SIZE());
            int tileY = (int) (y / getView().getTILE_SIZE());

            placeScaledImageAsTile(
                coopAndBarnView.getMap(),
                "animal",
                tileX,
                tileY,
                animal.getTexturePath()
            );
            animal.setLocation(new Vector2(tileX, tileY));
        }

        // سپس سیستم تشخیص کلیک راست را راه‌اندازی کنید
        TiledMapTileLayer animalLayer = (TiledMapTileLayer)getView().getMap().getLayers().get("animal");
//        AnimalContextMenu.setupTileRightClick(getView().getStage(), getView().getSkin(), animalLayer, animals);
    }
    @Override
    public void handlePolygonSpecialAreas(PolygonMapObject area) {

    }
}

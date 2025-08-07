package io.github.some_example_name.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.*;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.npc.Shops;
import io.github.some_example_name.model.materials.Material;

import java.util.ArrayList;
import java.util.List;

public class FarmController extends GameController {
    public void checkWarpsAndSpecialAreas(float delta) {
        if (getView().isJustTeleported())
            return;

        checkMapWarps();
        checkPlayerFarmWarps();
        // checkSpecialAreas();
    }

    public void checkPlayerFarmWarps() {
        if (getView().getMapType() != MapType.FARM) {
            return;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < App.getCurrentGame().getPlayers().size(); i++) {
            players.add(App.getCurrentGame().getPlayers().get(i));
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
                    && Intersector.overlaps(App.getCurrentGame().getActivePlayer().getPlayerRectangle(),
                    ((RectangleMapObject) object).getRectangle())) {
                    handleFarmWarp(object, player);
                } else if (object instanceof PolygonMapObject) {
                    handlePolygonSpecialAreas((PolygonMapObject) object);
                }
            }
        }
    }

    public void startPoint(TiledMap map) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            Farm farm = player.getFarm();
            if (farm == null)
                continue;


            if (getView().getMapType() == MapType.FARM) {
                if (farm.getAllObjects() != null) {
                    for (MapObject object : farm.getAllObjects()) {
                        System.out.println(object.getName() + " " + object.getClass());
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
        if (!area.getPolygon().contains(App.getCurrentGame().getActivePlayer().getPlace().x,
            App.getCurrentGame().getActivePlayer().getPlace().y))
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
            case "sale":
                if (getInputAdapter().getKeysHeld().contains(Input.Keys.X)){
                    showSellMenu();
                }

break;
            case "greenhousedoor":
                boolean isMade = area.getProperties().get("ismade", Boolean.class);
                if (!isMade && getInputAdapter().getKeysHeld().contains(Input.Keys.X)) {
                    handleGreenHouseDoor(area);
                }else if (isMade) {
                    proceedToGreenHouse(area);
                }

                break;
        }
    }
    public void showSellMenu() {
        // ساخت یک Stage جدید برای منوی فروش


        // ساخت Table اصلی
        getMainTable().clear();
        getMainTable().setFillParent(true);
        if (!getMainTable().hasParent()) {
            getView().getStage().addActor(getMainTable());
        }
        // عنوان منو
        Label titleLabel = new Label("Sell Items", getView().getSkin());
        getMainTable().add(titleLabel).colspan(2).padBottom(20).row();

        // لیست آیتم‌ها در ScrollPane
        Table itemsTable = new Table();
        ScrollPane scrollPane = new ScrollPane(itemsTable, getView().getSkin());
        scrollPane.setFadeScrollBars(false);
        getMainTable().add(scrollPane).colspan(2).expand().fill().pad(10).row();

        // نمایش آیتم‌های قابل فروش
        Backpack playerInventory = App.getCurrentGame().getActivePlayer().getInventory();
        for (Material item : playerInventory.getElements().keySet()) {
            int amount = playerInventory.getElements().get(item);

            Table itemRow = new Table();
            itemRow.add(new Label(item.getName(), getView().getSkin())).width(150).padRight(100).left();
            itemRow.add(new Label(amount + "x", getView().getSkin())).width(50).padRight(100).center();
            itemRow.add(new Label(item.baseSellPrice() + "g", getView().getSkin())).width(80).right();

            TextButton sellButton = new TextButton("Sell", getView().getSkin());
            sellButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showQuantityInput(item, amount);
                }
            });
            itemRow.add(sellButton).padLeft(10);

            itemsTable.add(itemRow).fillX().padBottom(5).row();
        }

        // دکمه بستن
        TextButton closeButton = new TextButton("Close", getView().getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMainTable().remove();
            }
        });
        getMainTable().add(closeButton).colspan(2).padTop(10);

        // نمایش Stage
        getView().getStage().act();
        getView().getStage().draw();
    }

    private void showQuantityInput(Material item, int maxAmount) {
        getMainTable().clear();
        getMainTable().setFillParent(true);
        if (!getMainTable().hasParent()) {
            getView().getStage().addActor(getMainTable());
        }


        Table inputTable = new Table();
        inputTable.setSize(300, 200);
        inputTable.setPosition(
            (Gdx.graphics.getWidth() - inputTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - inputTable.getHeight()) / 2
        );
        inputTable.setBackground(new TextureRegionDrawable(createLightTexture()));
        getView().getStage().addActor(inputTable);

        // عنوان
        inputTable.add(new Label("Sell " + item.getName(), getView().getSkin())).colspan(2).padBottom(15).row();

        // فیلد ورودی
        TextField quantityField = new TextField("1", getView().getSkin());
        quantityField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        inputTable.add(new Label("Quantity (Max: " + maxAmount + "):", getView().getSkin())).padRight(10);
        inputTable.add(quantityField).width(100).row();

        // دکمه‌ها
        Table buttonsTable = new Table();
        inputTable.add(buttonsTable).colspan(2).padTop(20);

        TextButton confirmButton = new TextButton("Confirm", getView().getSkin());
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    Result result = App.getCurrentGame().getShoppingBin().work(item.getName(), quantity);
                    if (result.Success()){
                        showSuccessMessage(result.Message());
                    } else {
                        showErrorMessage(result.Message());
                    }
//                        sellItem(item, quantity);
                        getMainTable().remove();

                } catch (NumberFormatException e) {
                    // ورودی نامعتبر
                }
            }
        });
        buttonsTable.add(confirmButton).padRight(10);

        TextButton cancelButton = new TextButton("Cancel", getView().getSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMainTable().remove();
            }
        });
        buttonsTable.add(cancelButton);
        getMainTable().add(inputTable).center();

    }
    private TextureRegion createLightTexture() {

        Texture texture = new Texture("Particle-Park-master/graphics/output/1x/rect.png");
        return new TextureRegion(texture);
    }
    private void sellItem(Material item, int quantity) {
        int totalPrice = item.baseSellPrice() * quantity;
        App.getCurrentGame().getActivePlayer().getInventory().removeElementFromBackpack(item, quantity);
        App.getCurrentGame().getActivePlayer().addMoney(totalPrice);

        // نمایش پیام موفقیت (اختیاری)
        Label successLabel = new Label("Sold " + quantity + " " + item.getName() +
            " for " + totalPrice + "g!", getView().getSkin());
        successLabel.setPosition(
            (Gdx.graphics.getWidth() - successLabel.getWidth()) / 2,
            Gdx.graphics.getHeight() - 50
        );
        getView().getStage().addActor(successLabel);

        // پنهان کردن پیام پس از 2 ثانیه
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                successLabel.remove();
            }
        }, 2);
    }
    public void handleFarmWarp(MapObject warpObject, Player owner) {
        CoopAndBarnView cb;
        String newMap = (String) warpObject.getProperties().get("targetMap");
        if (newMap == null)
            return;
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        App.getCurrentGame().getActivePlayer().setCurrentMapType(targetType);

        GameView gameView;
        switch (App.getCurrentGame().getActivePlayer().getCurrentMapType()) {
            case FARM:
                gameView = new FarmView(new FarmController(),
                    GameAssetManager.getInstance().getSkin(), MapType.FARM);
                break;
            case MINE:
                gameView = new MineView(new MineController(),
                    GameAssetManager.getInstance().getSkin());
                break;
            case BLACKSMITH:
                gameView = new StoreView(new StoreController(),
                    GameAssetManager.getInstance().getSkin(), MapType.BLACKSMITH , Shops.BLACKSMITH);
                break;
            case STARDROPSALOON:
                gameView = new StoreView(new StoreController(), GameAssetManager.getInstance().getSkin(), MapType.STARDROPSALOON , Shops.STARDROPSALOON);
                break;
            case JOJAMART:
                gameView = new StoreView(new StoreController(), GameAssetManager.getInstance().getSkin(), MapType.JOJAMART , Shops.JOJAMART);
                break;
            case PIERREGENERALSTORE:
                gameView = new StoreView(new StoreController(), GameAssetManager.getInstance().getSkin(), MapType.PIERREGENERALSTORE , Shops.PIERREGENERALSTORE);
                break;
            case CARPENTERSHOP:
                gameView = new StoreView(new StoreController(), GameAssetManager.getInstance().getSkin(), MapType.CARPENTERSHOP , Shops.CARPENTERSHOP);
                break;
            case MARNIERANCH:
                gameView = new StoreView(new StoreController(), GameAssetManager.getInstance().getSkin(), MapType.MARNIERANCH , Shops.MARNIERANCH);
                break;
            case COOP:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.COOP, App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn() );
                break;
            case LARGE_COOP:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.LARGE_COOP , App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn());
                break;
            case DELUXE_COOP:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.DELUXE_COOP, App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn() );
                break;
            case BARN:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.BARN , App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn());
                break;
            case LARGE_BARN:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.LARGE_BARN, App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn() );
                break;
            case DELUXE_BARN:
                App.getCurrentGame().getActivePlayer().setCurrentCoopOrBarn((Material) warpObject.getProperties().get("coopandbarn"));
                gameView = new CoopAndBarnView(new CoopAndBarnController(), GameAssetManager.getInstance().getSkin(), MapType.DELUXE_BARN, App.getCurrentGame().getActivePlayer().getCurrentCoopOrBarn() );
                break;
            default:
                // Handle unexpected map types
                throw new IllegalArgumentException("Unknown map type: " +
                    App.getCurrentGame().getActivePlayer().getCurrentMapType());
        }
        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

            Main.getMain().setScreen(gameView);
            if (targetType == MapType.FARM1 || targetType == MapType.FARM2) {
                gameView.getGameController().setStartPoint(gameView.getMap(), "infarm");
            } else {
                gameView.getGameController().startPoint(gameView.getMap());
            }

        });
//        positionPlayerAtExit("out");
        startTeleportCooldown();
    }

    public void setStartPoint(TiledMap map, String targetObjectName) {
        Farm farm = App.getCurrentGame().getActivePlayer().getFarm();
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

    public void setStartPointFromBAC(TiledMap map, RectangleMapObject rectangleMapObject) {
        Rectangle rectangle = rectangleMapObject.getRectangle();

        teleportPlayer(rectangle.x, rectangle.y);
        if (rectangleMapObject.getName().equals("out")) {
            System.out.println("fgfggfg");
            System.out.println(rectangle.x + " " + rectangle.y);
            System.out.println(App.getCurrentGame().getActivePlayer().getPlace().x + " " + App.getCurrentGame().getActivePlayer().getPlace().y);
        }
    }
    public void setCameraBuild(){
        Iterable<MapObject> objects = App.getCurrentGame().getActivePlayer().getFarm().getAllObjects();

        // همه نواحی plantable رو ذخیره کن
        for (MapObject obj : objects) {
            if (obj instanceof RectangleMapObject && "farm".equals(obj.getName())) {
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
}

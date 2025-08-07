package io.github.some_example_name.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.*;
import io.github.some_example_name.View.ui.GameMenuInputAdapter;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.npc.Shops;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;

import java.util.ArrayList;
import java.util.List;

import static io.github.some_example_name.Main.getMain;

public abstract class GameController {
    private GameView view;

    public Table getMainTable() {
        return mainTable;
    }

    public void setMainTable(Table mainTable) {
        this.mainTable = mainTable;
    }

    private Table mainTable = new Table();
    private Label messageLabel;
    private ToolController toolController;

    private GameMenuInputAdapter inputAdapter;

    public void setView(GameView view) {
        this.view = view;
        this.messageLabel = new Label("", GameAssetManager.getInstance().getSkin());
        this.inputAdapter = new GameMenuInputAdapter(this);

        // Set up UI listeners
        view.getInventoryBtn().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getBackpackUI().refreshBackpackItems();
                view.getBackpackUI().setVisible(!view.getBackpackUI().isVisible());
            }
        });

        view.getSkillBtn().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getSkillUI().setVisible(!view.getSkillUI().isVisible());
            }
        });
        view.getSettingsBtn().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getSettingsUI().setVisible(!view.getSettingsUI().isVisible());
            }
        });
        view.getMapBtn().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.setShowFullMap(!view.isShowFullMap());
            }
        });

        // Set up input processor
        Gdx.input.setInputProcessor(new InputMultiplexer(view.getStage(), inputAdapter));
    }

    public abstract void startPoint(TiledMap map);

    public void handleInput(float delta) {
        inputAdapter.update(delta);

        // Update camera based on full map view
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
            view.getCamera().setToOrtho(false, (float) (mapWidth * tileWidth) / 5, (float) (mapHeight * tileHeight) / 5);
            view.getCamera().position.set(
                App.getCurrentGame().getActivePlayer().getPlace().x + view.getTILE_SIZE() / 2f,
                App.getCurrentGame().getActivePlayer().getPlace().y + view.getTILE_SIZE() / 2f, 0);
        }
        if (view.getBuildModeHandler().isBuildMode()){

          setCameraBuild();
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
        for (Player player : App.getCurrentGame().getPlayers()) {
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
        if ("plantable".equals(objectName) || "mineDoor".equals(objectName) || "house".equals(objectName) || "greenhousedoor".equals(objectName) || "sale".equals(objectName)) {
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
                    && Intersector.overlaps(App.getCurrentGame().getActivePlayer().getPlayerRectangle(),
                            ((RectangleMapObject) object).getRectangle()) && (object.getName().equals("blacksmith") || object.getName().equals("stardropsaloon") || object.getName().equals("jojamart") || object.getName().equals("pierregeneralstore") || object.getName().equals("carpentershop") || object.getName().equals("marnieranch") || object.getName().equals("fishshop") )) {
                handleStoreDoor(object);
            }
            else if (object instanceof RectangleMapObject
                && Intersector.overlaps(App.getCurrentGame().getActivePlayer().getPlayerRectangle(),
                ((RectangleMapObject) object).getRectangle()) && ((object.getName().equals("Farm1")) || object.getName().equals("Farm") || object.getName().equals("Farm2") || object.getName().equals("Beach")) ) {
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
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , targetType);
        changeMap(farmMap);
//        positionPlayerAtExit(exitPointName);
        startTeleportCooldown();
    }

    public abstract void handleFarmWarp(MapObject warpObject, Player owner) ;

    public abstract void handlePolygonSpecialAreas(PolygonMapObject area) ;

    protected void handleLakeArea(Polygon poly) {
        // placeScaledImageAsTile(map, "craft", (int)(playerX/TILE_SIZE),
        // (int)(playerY/TILE_SIZE), "soil.png");
        if (isNearPolygonEdge(poly, App.getCurrentGame().getActivePlayer().getPlace().x,
                App.getCurrentGame().getActivePlayer().getPlace().y, 16)) {

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
        Shops targetShop = Shops.valueOf(newMap.toUpperCase());
        App.getCurrentGame().getActivePlayer().setCurrentMapType(targetType);

        GameView gameView = new StoreView(new StoreController(),GameAssetManager.getInstance().getSkin() , targetType , targetShop);
        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

            getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleMineDoor(PolygonMapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;
        App.getCurrentGame().getActivePlayer().setCurrentMapType(MapType.MINE);

        GameView gameView = new MineView(new MineController(),GameAssetManager.getInstance().getSkin() );
        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

        getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleHouseDoor(PolygonMapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);
        if (newMap == null)
            return;
        App.getCurrentGame().getActivePlayer().setCurrentMapType(MapType.HOUSE);

        GameView gameView = new HouseView(new HouseController(),GameAssetManager.getInstance().getSkin() );
        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

            getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());

        });

//        changeMap(farmMap);
        startTeleportCooldown();
    }
    protected void handleGreenHouseDoor(PolygonMapObject door) {
        // ایجاد یک جدول اصلی برای محتوا
        mainTable.clear();
        mainTable.setFillParent(true);
        if (!mainTable.hasParent()) {
            view.getStage().addActor(mainTable);
        }



        // ایجاد جدول برای محتوای دیالوگ
        Table dialogTable = new Table();
        dialogTable.setBackground(new TextureRegionDrawable(createLightTexture()));
        dialogTable.pad(20);

        // عنوان
        Label titleLabel = new Label("Build Greenhouse?", view.getSkin());
        dialogTable.add(titleLabel).colspan(2).padBottom(20).row();

        // متن محتوا
        Label contentLabel = new Label(
            "The greenhouse hasn't been built yet.\nIt requires 1000 wood to build.\nDo you want to build it now?",
            view.getSkin()
        );
        dialogTable.add(contentLabel).colspan(2).padBottom(20).row();

        // دکمه‌ها
        TextButton buildButton = new TextButton("Build", view.getSkin());
        TextButton cancelButton = new TextButton("Cancel", view.getSkin());

        // لیسنرهای دکمه‌ها
        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = App.getCurrentGame().getActivePlayer().getInventory()
                    .removeElementFromBackpack(new ForagingMineral(ForagingMinerals.WOOD), 1000);

                if (result.Success()) {
                    door.getProperties().put("ismade", true);
                    MapLayer mapLayer = App.getCurrentGame().getActivePlayer().getFarm().getGreenHouseLayer().stream().filter(layer -> layer.getName().equals("greenhouse1")).findFirst().get();
                    mapLayer.setVisible(false);
                    MapLayer mapLayer1 = App.getCurrentGame().getActivePlayer().getFarm().getGreenHouseLayer().stream().filter(layer -> layer.getName().equals("greenhouse2")).findFirst().get();
                    mapLayer1.setVisible(true);
                    showSuccessDialog("Greenhouse built successfully!");
                } else {
                    showErrorDialog("Not enough wood! You need 1000 wood.");
                }
                mainTable.remove();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.remove();
            }
        });

        // اضافه کردن دکمه‌ها به جدول
        dialogTable.add(buildButton).padRight(10).width(120).height(50);
        dialogTable.add(cancelButton).width(120).height(50);

        // اضافه کردن جدول دیالوگ به جدول اصلی
        mainTable.add(dialogTable).center();

        // برای بستن با کلیک خارج از دیالوگ
        mainTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // اگر کلیک خارج از محدوده دیالوگ بود

                    mainTable.remove();
            }
        });
    }
    private TextureRegion createLightTexture() {

        Texture texture = new Texture("Particle-Park-master/graphics/output/1x/rect.png");
        return new TextureRegion(texture);
    }
    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", getView().getSkin());
        errorDialog.getTitleLabel().setColor(Color.RED);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(getView().getStage());
    }

    private void showSuccessDialog(String message) {
        Dialog successDialog = new Dialog("Success", getView().getSkin());
        successDialog.getTitleLabel().setColor(Color.GREEN);
        successDialog.text(message);
        successDialog.button("OK");
        successDialog.show(getView().getStage());
    }
    // Helper method for greenhouse transition
    public void proceedToGreenHouse(PolygonMapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);

        if (newMap == null) {
            return;
        }
        App.getCurrentGame().getActivePlayer().setCurrentMapType(MapType.GREENHOUSE);
        GameView gameView = new GreenHouseView(new GreenHouseController(), GameAssetManager.getInstance().getSkin());

        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);
            getMain().setScreen(gameView);
            gameView.getGameController().startPoint(gameView.getMap());
        });

        startTeleportCooldown();
    }
    protected void handleFarmDoor(MapObject door) {
        String newMap = door.getProperties().get("targetMap", String.class);

        if (newMap == null)
            return;
        MapType oldType = App.getCurrentGame().getActivePlayer().getCurrentMapType();
        MapType targetType = MapType.valueOf(newMap.toUpperCase());
        App.getCurrentGame().getActivePlayer().setCurrentMapType(targetType);

        GameView gameView = new FarmView(new FarmController(),GameAssetManager.getInstance().getSkin() , targetType );
        Gdx.app.postRunnable(() -> {
            App.getCurrentGame().setGameView(gameView);

            getMain().setScreen(gameView);
            if(targetType == MapType.FARM) {
                if (oldType == MapType.FARM1) {
                    gameView.getGameController().setStartPoint(gameView.getMap() , "outfarm1");
                }else if (oldType == MapType.FARM2) {
                    gameView.getGameController().setStartPoint(gameView.getMap() , "outfarm2");
                } else {
                    gameView.getGameController().setStartPoint(gameView.getMap() , "outbeach");
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
        App.getCurrentGame().getActivePlayer().setCharacterPlacer(new CharacterPlacer(newMap.getTmxMap()));
    }

//    public void positionPlayerAtExit(String exitName) {
//        MapObject exit = view.getMap().getLayers().get("Object").getObjects().get(exitName);
//        if (exit instanceof RectangleMapObject) {
//            Rectangle exitRect = ((RectangleMapObject) exit).getRectangle();
//            teleportPlayer(exitRect.x, exitRect.y);
//        }
//    }

    public void teleportPlayer(float x, float y) {
        App.getCurrentGame().getActivePlayer().getCharacterPlacer().clearCharacter(
                (int) (App.getCurrentGame().getActivePlayer().getPlace().x / view.getTILE_SIZE()),
                (int) (App.getCurrentGame().getActivePlayer().getPlace().y / view.getTILE_SIZE()));
        App.getCurrentGame().getActivePlayer().setPlace(new Vector2(x, y));

        App.getCurrentGame().getActivePlayer().getPlayerRectangle().setPosition(x, y);
        App.getCurrentGame().getActivePlayer().setCharacterPlacer(new CharacterPlacer(getView().getMap()));


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
    private void addSkillRow(Table table, String name, String iconPath, int level, Skin skin) {
        table.pad(10);

        // آیکون مهارت
        Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
        table.add(icon).size(32).padRight(10);

        // لیبل نام مهارت
        Label skillLabel = new Label(name, skin);
        table.add(skillLabel).width(100).left().padRight(60);

        // مربع‌های سطح (10 مربع)
        for (int i = 0; i < 4; i++) {
            String imgPath = i < level ? "StardewGUI_v1.3.1/assets/minecraft/textures/gui/sprites/advancements/goal_frame_obtained.png" : "StardewGUI_v1.3.1/assets/minecraft/textures/gui/sprites/advancements/goal_frame_unobtained.png";
            Image levelBox = new Image(new Texture(Gdx.files.internal(imgPath)));
            table.add(levelBox).size(24).pad(2);
        }
        String desc = getSkillDescription(name.toLowerCase(),level );

        addTooltipToActor(icon, desc, skin);

        // شماره سطح
        Label levelLabel = new Label(String.valueOf(level), skin);
        table.add(levelLabel).padLeft(20);

        table.row(); // ردیف بعدی
    }
    private String getSkillDescription(String skillName, int level) {
        return switch (skillName.toLowerCase()) {
            case "farming" -> """
            +5 Farming XP when harvesting crops or animal products.
            Higher levels increase crop quality chance.

            Current Level: %d
            🔧 Bonus: %s
            """.formatted(level, switch (level) {
                case 1 -> "+1 Hoe Efficiency";
                case 2 -> "+1 Watering Can Efficiency";
                case 3 -> "+10% Crop Growth Speed";
                case 4 -> "+15% Quality Chance";
                default -> "No bonus yet";
            });

            case "mining" -> """
            +10 Mining XP for breaking stones or ores.
            At level 2, extra resource drops from nodes.

            Current Level: %d
            🔨 Bonus: %s
            """.formatted(level, level >= 2 ? "+1 Bonus Ore Drop" : "No bonus yet");

            case "foraging" -> """
            +10 Foraging XP for collecting natural resources.
            Higher levels increase rare item spawn rate.

            Current Level: %d
            🌿 Bonus: %s
            """.formatted(level, level >= 2 ? "+1 Rare Drop Chance" : "No bonus yet");

            case "fishing" -> """
            +5 Fishing XP per caught fish.
            Higher levels increase bite speed and rare fish chance.

            Current Level: %d
            🎣 Bonus: %s
            """.formatted(level, level >= 2 ? "+1 Cast Range / Rare Chance" : "No bonus yet");

            case "combat" -> """
            +10 Combat XP per enemy hit.
            Higher levels improve sword damage and defense.

            Current Level: %d
            ⚔️ Bonus: %s
            """.formatted(level, level >= 2 ? "+1 Attack Power" : "No bonus yet");

            default -> "Unknown skill";
        };
    }

    public Window createSkillTable(Skill skill, Skin skin) {
        Table table = new Table();
        Window window = new Window("Skill", skin);

        addSkillRow(table, "Farming", "skill/farming.png", skill.getFarmingLevel(), skin);
        addSkillRow(table, "Mining", "skill/Fishing_Skill_Icon.png", skill.getMiningLevel(), skin);
        addSkillRow(table, "Foraging", "skill/Foraging_Skill_Icon.png", skill.getForagingLevel(), skin);
        addSkillRow(table, "Fishing", "skill/Mining_Skill_Icon.png", skill.getFishingLevel(), skin);

        window.setSize(1800, 900);
        window.setPosition(30, 30);
        window.add(table);
//        addSkillRow(table, "Combat", "icons/combat.png", 0, skin); // فعلاً صفر یا دستی

        return window;
    }

        public Window createSettingsWindow(Skin skin) {
            // Create main window
            Window window = new Window("Settings", skin);

            // Create table for layout
            Table table = new Table();
            table.defaults().pad(10);

            // Add buttons to the table
            addButtonRow(table, "Exit Game", skin, () -> showExitDialog(window , skin));
            addButtonRow(table, "Remove Players", skin, () -> showRemovePlayersDialog(window, skin));

            // Window settings
            window.setSize(400, 300);
            window.setPosition(
                (Gdx.graphics.getWidth() - window.getWidth()) / 2,
                (Gdx.graphics.getHeight() - window.getHeight()) / 2
            );
            window.add(table);

            return window;
        }

        private static void addButtonRow(Table table, String text, Skin skin, Runnable action) {
            TextButton button = new TextButton(text, skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    action.run();
                }
            });
            table.add(button).width(200).height(50).row();
        }
    private static void showExitDialog(Window parent, Skin skin) {
        Player currentPlayer = App.getCurrentGame().getActivePlayer();

        Dialog exitDialog = new Dialog("Exit Game", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    // Player confirmed exit - remove them
                    App.getCurrentGame().getPlayers().remove(currentPlayer);

                    // If this was the last player, close the game
                    if (App.getCurrentGame().getPlayers().isEmpty()) {
                        getMain().setScreen(new PreGameMenuView(new PreGameMenuController(),GameAssetManager.getInstance().getSkin()));
                    } else {
                        App.getCurrentGame().changeTurn();
                    }
                }
            }
        };

        exitDialog.text("Are you sure you want to exit the game?\nYou will be removed from the player list.");
        exitDialog.button("Yes", true);
        exitDialog.button("No", false);
        exitDialog.show(parent.getStage());
    }
    private static void showRemovePlayersDialog(Window parent, Skin skin) {
        Player activePlayer = App.getCurrentGame().getActivePlayer();
        Player adminPlayer = App.getCurrentGame().getAdminPlayer();

        // First check if active player is admin
        if (!activePlayer.equals(adminPlayer)) {
            Dialog errorDialog = new Dialog("Permission Denied", skin);
            errorDialog.text("You are not the admin player!");
            errorDialog.button("OK");
            errorDialog.show(parent.getStage());
            return;
        }

        // If we get here, the player is admin
        List<Player> players = App.getCurrentGame().getPlayers();

        // Create the remove players dialog
        Dialog dialog = new Dialog("Remove Players", skin);
        dialog.text("Which player do you want to remove?");

        // Add a scroll pane if there are many players
        Table playersTable = new Table();
        ScrollPane scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);

        // Add buttons for each player (except admin)
        for (Player player : players) {
            if (!player.equals(adminPlayer)) { // Don't allow removing admin
                TextButton playerButton = new TextButton(player.getUsername(), skin);
                playerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // Confirm removal of this specific player
                        showConfirmPlayerRemovalDialog(parent, skin, player);
                        dialog.hide();
                    }
                });
                playersTable.add(playerButton).width(200).height(40).row();
            }
        }

        dialog.getContentTable().add(scrollPane).width(300).height(200);
        dialog.button("Cancel", false);
        dialog.show(parent.getStage());
    }

    private static void showConfirmPlayerRemovalDialog(Window parent, Skin skin, Player player) {
        Dialog confirmDialog = new Dialog("Confirm Removal", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    // Actually remove the player
                    App.getCurrentGame().getPlayers().remove(player);
                    Gdx.app.log("Settings", "Player removed: " + player.getUsername());
                }
            }
        };

        confirmDialog.text("Are you sure you want to remove " + player.getUsername() + "?");
        confirmDialog.button("Yes", true);
        confirmDialog.button("No", false);
        confirmDialog.show(parent.getStage());
    }
    public GameView getView() {
        return view;
    }
    private void addTooltipToActor(Actor actor, String text, Skin skin) {
        Tooltip<Label> tooltip = new Tooltip<>(new Label(text, skin));
        actor.addListener(tooltip);
    }
    public Label getMessageLabel() {
        return messageLabel;
    }

    public ToolController getToolController() {
        return toolController;
    }

    public GameMenuInputAdapter getInputAdapter() {
        return inputAdapter;
    }

    public void setInputAdapter(GameMenuInputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;
    }

    public void setToolController(ToolController toolController) {
        this.toolController = toolController;
    }

    protected void setStartPoint(TiledMap map , String targetObjectName) {
        return;
    }
    public void setStartPointFromBAC(TiledMap map, RectangleMapObject rectangleMapObject) {
        return;
    }
}

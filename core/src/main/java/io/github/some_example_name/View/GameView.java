package io.github.some_example_name.View;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Control.GameController;
//import io.github.some_example_name.View.ui.InventoryUI;
import io.github.some_example_name.Control.ToolController;
import io.github.some_example_name.View.ui.BackpackUI;
import io.github.some_example_name.View.ui.BuildModeHandler;
import io.github.some_example_name.View.ui.GameMenuInputAdapter;
import io.github.some_example_name.View.ui.OverlayImageButton;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.Seed;
import io.github.some_example_name.model.materials.Tools.Tool;
import io.github.some_example_name.model.materials.Tools.WateringCan;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class GameView implements Screen {

    private FarmMap map;
    MapType mapType;
    private Table rootTable = new Table();

    private BackpackUI backpackUI;
    private Window skillUI;
    private Window settingsUI;

    private OrthogonalTiledMapRenderer mapRenderer;
    private BuildModeHandler buildModeHandler = new BuildModeHandler();



    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private SpriteBatch batch;
    private float speed = 500;
    private boolean justTeleported = false;
    private float teleportCooldown = 1.0f;
    private GameController gameController;
    private Stage stage;
    private Skin skin;
    private ImageButton mapBtn;
    private ImageButton settingsBtn;
    private ImageButton inventoryBtn;
    private ImageButton skillsBtn;
    private ScrollPane toolBar;
//    private InventoryUI inventoryUI;

    private final int TILE_SIZE = 16;
    protected RayHandler rayHandler;
            Table topButtonsTable = new Table();



    // محتوای اصلی متغیر (وسط صفحه)
        Stack contentStack = new Stack();



    public GameView(GameController gameController, Skin skin , MapType mapType ) {
        mapBtn = new ImageButton(skin,"Map");
        settingsBtn = new ImageButton(skin, "Setting");
        inventoryBtn = new ImageButton( skin, "Inventory");
        skillsBtn = new ImageButton( skin, "Skills");
        this.gameController = gameController;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.mapType = mapType;
        gameController.setView(this);
        create();
//        InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
//        Gdx.input.setInputProcessor(multiplexer);
    }

    public void create() {
        map = App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , getMapType());
        mapRenderer = new OrthogonalTiledMapRenderer(map.getTmxMap());
        backpackUI = new BackpackUI(App.getCurrentGame().getActivePlayer().getInventory() , skin);
        toolBar = createItemTable(skin);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        int mapWidth = map.getTmxMap().getProperties().get("width", Integer.class);
        int mapHeight = map.getTmxMap().getProperties().get("height", Integer.class);
        int tileWidth = map.getTmxMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getTmxMap().getProperties().get("tileheight", Integer.class);
        camera.setToOrtho(false, mapWidth*tileWidth, mapHeight*tileHeight);

//        inventoryUI = new InventoryUI(skin);
//        stage.addActor(inventoryUI);
        // inventoryUI.pack(); // حتماً ابتدا اندازه‌اش رو فیکس کن

//        inventoryUI.setPosition(
//                stage.getWidth() / 2f - inventoryUI.getWidth() / 2f,
//                stage.getHeight() / 2f - inventoryUI.getHeight() / 2f);

        // rainEffect.setPosition(100, 200);


        rayHandler = new RayHandler(null); // اگه Box2D داری
// rayHandler = new RayHandler(null); // اگه بدون Box2D فقط نور می‌خوای
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(1f); // روز
        updateAmbientLight();
        loadEnergyTextures();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        topButtonsTable.add(mapBtn).width(50).height(50).padRight(10);
        topButtonsTable.add(settingsBtn).width(50).height(50).padRight(10);
        topButtonsTable.add(inventoryBtn).width(50).height(50).padRight(10);
        topButtonsTable.add(skillsBtn).width(50).height(50).padRight(10);
        skillUI = gameController.createSkillTable(App.getCurrentGame().getActivePlayer().getSkills(), skin);
        stage.addActor(skillUI);
        skillUI.setVisible(false);
        settingsUI = gameController.createSettingsWindow(skin);
        stage.addActor(settingsUI);
        settingsUI.setVisible(false);
        TooltipManager tooltipManager = TooltipManager.getInstance();
        tooltipManager.initialTime = 0.2f; // تأخیر قبل نمایش
        tooltipManager.subsequentTime = 0.1f;
        tooltipManager.hideAll();





//        inventoryContent.setVisible(false);
//        settingsContent.setVisible(false);
//        inventoryBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                inventoryContent.setVisible(true);
//                settingsContent.setVisible(false);
//            }
//        });
//
//        settingsBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                inventoryContent.setVisible(false);
//                settingsContent.setVisible(true);
//            }
//        });
//        چینش نهایی در root
        rootTable.top();
        rootTable.add(topButtonsTable).expandX().fillX().row();
        rootTable.add(contentStack).expand().fill();
        stage.addActor(backpackUI);
        gameController.getMessageLabel().setPosition(
            Gdx.graphics.getWidth() / 2f - 200, // adjust X
            40                                // Y: پایین صفحه
        );
        stage.addActor(gameController.getMessageLabel());

        rootTable.setVisible(false);
        if (App.getCurrentGame().getActivePlayer().getInHand() != null) {
            Tool tool = App.getCurrentGame().getActivePlayer().getInHand();
            gameController.setToolController(new ToolController((Material) tool));
        }
//        rayHandler.updateAndRender();
    }
    protected void updateAmbientLight() {
        int hour = App.getCurrentGame().getTimeAndDate().getHour();

        float lightLevel;

        if (hour >= 18 && hour <= 22) {
            lightLevel = 0.7f - (hour - 18) / 4f * 0.6f; // تا 0.4 کم می‌کنه
        }
//        else if (hour < 9) {
//            lightLevel = 0.4f; // صبح تاریک
//        }
        else {
            lightLevel = 1f; // روز روشن
        }

        rayHandler.setAmbientLight(lightLevel);
    }
    private boolean showFullMap = false;

    @Override
    public void render(float delta) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        gameController.handleInput(delta);
        gameController.checkWarpsAndSpecialAreas(delta);
        refreshItemTable();


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(mapRenderer.getBatch().getProjectionMatrix());
        batch.begin();
        if (gameController.getToolController() != null) {
            gameController.getToolController().update(batch);
        }
        buildModeHandler.render(batch , camera);
        batch.end();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        App.getCurrentGame().getTimeAndDate().render(getBatch());
        renderEnergyBar(batch, App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount(), App.getCurrentGame().getActivePlayer().getEnergy().getMaxEnergy());


        batch.end();

        if (!showFullMap) {
//            inventoryUI.update();
            stage.act(delta);
            stage.draw();
        }
        rayHandler.setCombinedMatrix(camera);
        updateAmbientLight();
        rayHandler.updateAndRender();
    }



    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    // public float getPlayerY() {
    // return playerY;
    // }
    //
    // public void setPlayerY(float playerY) {
    // this.playerY = playerY;
    // }
    //
    // public float getPlayerX() {
    // return playerX;
    // }
    //
    // public void setPlayerX(float playerX) {
    // this.playerX = playerX;
    // }
    public void refreshItemTable() {
        if (toolBar != null) {
            // موقعیت و وضعیت فعلی را ذخیره کنید
            boolean wasVisible = toolBar.isVisible();
            float scrollY = toolBar.getScrollY();

            // اسکرول پین قدیمی را حذف کنید
            toolBar.remove();

            // اسکرول پین جدید ایجاد کنید
            toolBar = createItemTable(getSkin());
            stage.addActor(toolBar);

            // وضعیت قبلی را بازگردانید
            toolBar.setScrollY(scrollY);
            toolBar.setVisible(wasVisible);
        }
    }
    public float getTeleportCooldown() {
        return teleportCooldown;
    }

    public boolean isJustTeleported() {
        return justTeleported;
    }

    // public Rectangle getPlayerRectangle() {
    // return playerRectangle;
    // }
    //
    // public void setPlayerRectangle(Rectangle playerRectangle) {
    // this.playerRectangle = playerRectangle;
    // }

    // public CharacterPlacer getPlacer() {
    // return placer;
    // }
    //
    // public void setPlacer(CharacterPlacer placer) {
    // this.placer = placer;
    // }
    private Texture energyBarEmpty;
    private Texture energyBarFull;
    public void renderEnergyBar(SpriteBatch batch, float energy, float maxEnergy) {
        float x = 20;
        float y = 20;
        float height = 167.5f;
        float width = 20;
        batch.draw(energyBarEmpty, Gdx.graphics.getWidth()-100-10, Gdx.graphics.getHeight()-1000-10); // تنظیم محل نمایش HUD

//        batch.draw(energyBarEmpty, x, y, width, height);

        float percent = energy / maxEnergy;
        batch.draw(energyBarFull, Gdx.graphics.getWidth()-90-12, Gdx.graphics.getHeight()-1000-4, width, height * percent );
    }
    public void loadEnergyTextures() {
        energyBarEmpty = new Texture("energyBarEmpty.jpg");
        energyBarFull = new Texture("energy_fill.png");
    }
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setJustTeleported(boolean justTeleported) {
        this.justTeleported = justTeleported;
    }

    public void setTeleportCooldown(float teleportCooldown) {
        this.teleportCooldown = teleportCooldown;
    }



    public TiledMap getMap() {
        return map.getTmxMap();
    }

    public void setMap(FarmMap map) {
        this.map = map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(OrthogonalTiledMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public boolean isShowFullMap() {
        return showFullMap;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void setShowFullMap(boolean showFullMap) {
        this.showFullMap = showFullMap;
    }

    public abstract MapType getMapType();


    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void fadeToNextDay(Runnable onFinish) {
        Image fadeOverlay = new Image(new Texture("bg.png")); // 1x1 پیکسل مشکی بساز و بزار assets
        fadeOverlay.setSize(stage.getWidth(), stage.getHeight());
        fadeOverlay.getColor().a = 0;
        fadeOverlay.addAction(
            Actions.sequence(
                Actions.fadeIn(1f),
                Actions.run(onFinish),  // عملیات روز جدید
                Actions.fadeOut(1f),
                Actions.removeActor()
            )
        );
        stage.addActor(fadeOverlay);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
    public Table getRootTable() {
        return rootTable;
    }

    public void setRootTable(Table rootTable) {
        this.rootTable = rootTable;
    }

    public ImageButton getMapBtn() {
        return mapBtn;
    }

    public ImageButton getSettingsBtn() {
        return settingsBtn;
    }

    public ImageButton getInventoryBtn() {
        return inventoryBtn;
    }
    public ImageButton getSkillBtn() {
        return skillsBtn;
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        hudCamera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        App.getCurrentGame().getMapManager().disposeAll();
        mapRenderer.dispose();
        batch.dispose();
        if (App.getCurrentGame().getTimeAndDate().getRainEffect() != null)
            App.getCurrentGame().getTimeAndDate().getRainEffect().forEach(ParticleEffect::dispose);
    }



    public Window getSkillUI() {
        return skillUI;
    }

    public Window getSettingsUI() {
        return settingsUI;
    }

    public void setSkillUI(Window skillUI) {
        this.skillUI = skillUI;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollPane getToolBar() {
        return toolBar;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setHudCamera(OrthographicCamera hudCamera) {
        this.hudCamera = hudCamera;
    }

    public OrthographicCamera getHudCamera() {
        return hudCamera;
    }

    public void setToolBar(ScrollPane toolBar) {
        this.toolBar = toolBar;
    }

    public BuildModeHandler getBuildModeHandler() {
        return buildModeHandler;
    }

    public void setBuildModeHandler(BuildModeHandler buildModeHandler) {
        this.buildModeHandler = buildModeHandler;
    }

    public BackpackUI getBackpackUI() {
        return backpackUI;
    }

    public ScrollPane createItemTable(Skin skin) {
        Table toolTable = new Table();
        toolTable.top().left().pad(10);
        toolTable.defaults().pad(4);

        ArrayList<Tool> tools = App.getCurrentGame().getActivePlayer().getInventory().getTools();
        ArrayList<OverlayImageButton> allToolButtons = new ArrayList<>();
        for (Tool tool : tools) {
            Material material = (Material) tool;
            Texture texture = new Texture(Gdx.files.internal(material.getTexturePath()));
            OverlayImageButton toolButton = new OverlayImageButton(getSkin().get("Inventory", TextButton.TextButtonStyle.class), texture);
            allToolButtons.add(toolButton);


            toolButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (OverlayImageButton b : allToolButtons) {
                        b.setSelected(false);
                    }
                    App.getCurrentGame().getActivePlayer().setInHand(tool);
                    App.getCurrentGame().getActivePlayer().setSeedInHand(null);
                    gameController.setToolController(new ToolController((Material) tool));

                    toolButton.setSelected(true);
                }
            });

            if (tool == App.getCurrentGame().getActivePlayer().getInHand()) {
                toolButton.setSelected(true);
            }

            toolTable.add(toolButton).size(64, 64).row(); // عمودی
            if (material instanceof WateringCan wateringCan){
                toolTable.add(OverlayImageButton.createWaterProgressBar(wateringCan,skin)).size(16, 16).row();
            }


        }
        HashMap<Material , Integer> materials = App.getCurrentGame().getActivePlayer().getInventory().getElements();
        materials.keySet().stream()
            .filter(Seed.class::isInstance)
            .map(Seed.class::cast)
            .forEach(seed -> {
                Texture texture = new Texture(Gdx.files.internal(seed.getTexturePath()));
                OverlayImageButton toolButton = new OverlayImageButton(getSkin().get("Inventory", TextButton.TextButtonStyle.class), texture);
                allToolButtons.add(toolButton);


                toolButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (OverlayImageButton b : allToolButtons) {
                            b.setSelected(false);
                        }
                        App.getCurrentGame().getActivePlayer().setInHand(null);
                        App.getCurrentGame().getActivePlayer().setSeedInHand(seed);
                        gameController.setToolController(new ToolController(seed));

                        toolButton.setSelected(true);
                    }
                });

                if (seed == App.getCurrentGame().getActivePlayer().getSeedInHand()) {
                    toolButton.setSelected(true);
                }

                toolTable.add(toolButton).size(64, 64).row(); // عمودی

            });




        ScrollPane scrollPane = new ScrollPane(toolTable, skin);
        scrollPane.setScrollingDisabled(true, false); // فقط اسکرول عمودی فعال
        scrollPane.setFadeScrollBars(false);
        scrollPane.setSize(150, 1000); // محدود کردن ارتفاع برای اسکرول شدن
        scrollPane.setPosition(10, Gdx.graphics.getHeight() - scrollPane.getHeight() - 10);
        scrollPane.setVisible(false);

        stage.addActor(scrollPane);
        return scrollPane;
    }

    public Direction getMouseDirection(int mouseX, int mouseY) {
        // تبدیل مختصات صفحه به مختصات دنیای بازی
        Vector3 worldMouse = camera.unproject(new Vector3(mouseX, mouseY, 0));

        // مختصات مرکز بازیکن (نه گوشه آن)
        float px = App.getCurrentGame().getActivePlayer().getPlace().x + (TILE_SIZE / 2f);
        float py = App.getCurrentGame().getActivePlayer().getPlace().y + (TILE_SIZE / 2f);

        float dx = worldMouse.x - px;
        float dy = worldMouse.y - py;

        // آستانه حساسیت برای تشخیص جهت
        float threshold = 0.1f * TILE_SIZE;

        if (Math.abs(dx) - Math.abs(dy) > threshold) {
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (Math.abs(dy) - Math.abs(dx) > threshold) {
            return dy > 0 ? Direction.UP : Direction.DOWN;
        }

        // اگر تفاوت محسوسی نبود، جهت قبلی را حفظ کنید یا جهت پیش‌فرض برگردانید
        return Direction.RIGHT; // یا جهت پیش‌فرض دیگر
    }
}

package io.github.some_example_name.View;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.View.ui.InventoryUI;
import io.github.some_example_name.model.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class GameView implements Screen, InputProcessor {

    private FarmMap map;
    MapType mapType;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private SpriteBatch batch;
    private float speed = 500;
    private boolean justTeleported = false;
    private float teleportCooldown = 1.0f;
    private GameController gameController;
    private Stage stage;
    private Skin skin;
    private InventoryUI inventoryUI;

    private final int TILE_SIZE = 16;
    private RayHandler rayHandler;

    public GameView(GameController gameController, Skin skin , MapType mapType ) {
        this.gameController = gameController;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.mapType = mapType;
        gameController.setView(this);
        create();
    }

    public void create() {
        map = App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , getMapType());
        mapRenderer = new OrthogonalTiledMapRenderer(map.getTmxMap());
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        int mapWidth = map.getTmxMap().getProperties().get("width", Integer.class);
        int mapHeight = map.getTmxMap().getProperties().get("height", Integer.class);
        int tileWidth = map.getTmxMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getTmxMap().getProperties().get("tileheight", Integer.class);
        camera.setToOrtho(false, mapWidth*tileWidth, mapHeight*tileHeight);

        inventoryUI = new InventoryUI(skin);
        stage.addActor(inventoryUI);
        // inventoryUI.pack(); // حتماً ابتدا اندازه‌اش رو فیکس کن

        inventoryUI.setPosition(
                stage.getWidth() / 2f - inventoryUI.getWidth() / 2f,
                stage.getHeight() / 2f - inventoryUI.getHeight() / 2f);
        App.getCurrentGame().getTimeAndDate().setRainEffect(App.getCurrentGame().getTimeAndDate().loadEffectsForFullMap(map.getTmxMap(), App.getCurrentGame().getTimeAndDate().getWeather().getEffectName(), 0, 0));

        // rainEffect.setPosition(100, 200);

        InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(multiplexer);
        rayHandler = new RayHandler(null); // اگه Box2D داری
// rayHandler = new RayHandler(null); // اگه بدون Box2D فقط نور می‌خوای
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(1f); // روز
        updateAmbientLight();
        loadEnergyTextures();
//        rayHandler.updateAndRender();
    }
    private void updateAmbientLight() {
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


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(mapRenderer.getBatch().getProjectionMatrix());
        batch.begin();
        App.getCurrentGame().getTimeAndDate().renderEffect(batch ,delta);
        batch.end();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        App.getCurrentGame().getTimeAndDate().render(batch);
        renderEnergyBar(batch, App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount(), App.getCurrentGame().getActivePlayer().getEnergy().getMaxEnergy());

        batch.end();

        if (!showFullMap) {
            inventoryUI.update();
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            inventoryUI.toggle();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}

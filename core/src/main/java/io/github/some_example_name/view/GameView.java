package io.github.some_example_name.view;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import common.Others;
import common.PlayerBoard;
import io.github.some_example_name.control.GameController;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.view.ui.InventoryUI;
import io.github.some_example_name.model.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public abstract class GameView implements Screen, InputProcessor {

    private final int  count = GameApp.player.getLobby().getPlayers().size();
    private long time = System.currentTimeMillis();
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
        map = GameApp.getMapForPlayer(GameApp.getPlayer() , getMapType());
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
        inventoryUI.setPosition(
            stage.getWidth() / 2f - inventoryUI.getWidth() / 2f,
            stage.getHeight() / 2f - inventoryUI.getHeight() / 2f);
        GameApp.getTimeAndDate().setRainEffect(GameApp.getTimeAndDate().loadEffectsForFullMap(map.getTmxMap(), GameApp.getTimeAndDate().getWeather().getEffectName(), 0, 0));

        // rainEffect.setPosition(100, 200);

        InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(multiplexer);
        rayHandler = new RayHandler(null);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(1f);
        updateAmbientLight();
        loadEnergyTextures();
//        rayHandler.updateAndRender();
    }
    private void updateAmbientLight() {
        int hour = GameApp.getTimeAndDate().getHour();

        float lightLevel;

        if (hour >= 18 && hour <= 22) {
            lightLevel = 0.7f - (hour - 18) / 4f * 0.6f;
        }

        else {
            lightLevel = 1f;
        }

        rayHandler.setAmbientLight(lightLevel);
    }
    private boolean showFullMap = false;

    Texture otherPlayers1;
    Label label;

    private final HashMap<Reactions, Actor> reactionActors = new HashMap<>();
    private final HashMap<String, Texture> emojiTextureCache = new HashMap<>();
    private static final float EMOJI_SIZE = 32f; // اندازه معقول برای ایموجی
    private static final float EMOJI_Y_OFFSET = 8f; // فاصله بالای سر بازیکن
    private static final long REACTION_TTL_MS = 10_000L; // زمان نمایش واکنش

    @Override
    public void show() {
        otherPlayers1 = new Texture("OtherPlayer.png");
    }

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
        if (System.currentTimeMillis() - time > 1000) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("x", GameApp.player.getPlace().x);
            body.put("y", GameApp.player.getPlace().y);
            GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Place));
            body.clear();
            body.put("player-board", new PlayerBoard(GameApp.player.getUsername(), (int) GameApp.player.getMoney(), GameApp.player.getSkills().getFarmingLevel(), GameApp.player.getSkills().getFishingLevel(), GameApp.player.getSkills().getForagingLevel(), GameApp.player.getSkills().getMiningLevel()) );
            GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Player_Board));
            time = System.currentTimeMillis();
        }
        switch (count) {
            case 2:
                Point point0 = GameApp.others.getFirst().getPoint();
                batch.draw(otherPlayers1, point0.x, point0.y, 25, 45);
                break;
            case 3:
                Point point1 = GameApp.others.get(0).getPoint();
                Point point2 = GameApp.others.get(1).getPoint();
                batch.draw(otherPlayers1, point1.x, point1.y, 25, 45);
                batch.draw(otherPlayers1, point2.x, point2.y, 25, 45);
                break;
            case 4:
                Point point3 = GameApp.others.get(0).getPoint();
                Point point4 = GameApp.others.get(1).getPoint();
                Point point5 = GameApp.others.get(2).getPoint();
                batch.draw(otherPlayers1, point3.x, point3.y, 25, 45);
                batch.draw(otherPlayers1, point4.x, point4.y, 25, 45);
                batch.draw(otherPlayers1, point5.x, point5.y, 25, 45);
                break;
        }
        batch.end();

        batch.begin();
        Iterator<Reactions> iterator = GameApp.reactions.iterator();
        while (iterator.hasNext()) {
            Reactions reactions = iterator.next();
            long age = System.currentTimeMillis() - reactions.getTime();

            if (age < REACTION_TTL_MS) {
                Others others = GameApp.others.stream()
                    .filter(o -> o.getNumber() == reactions.getNumber())
                    .findFirst()
                    .orElse(null);
                if (others == null) continue; // اگر بازیکن پیدا نشد، نادیده بگیر

                // موقعیت روی دنیا: بالای سر بازیکن
                float worldX = others.getPoint().x + 25f / 2f; // 25 همان عرضی است که شما رسم می‌کنید
                float worldY = others.getPoint().y + 45f + EMOJI_Y_OFFSET; // 45 همان قدی است که شما رسم می‌کنید
                Vector3 screenPos = new Vector3(worldX, worldY, 0f);
                camera.project(screenPos); // تبدیل به مختصات صفحه (پیکسل)

                float actorX = screenPos.x - EMOJI_SIZE / 2f;
                float actorY = screenPos.y; // stage از گوشهٔ پایین-چپ استفاده می‌کند

                Actor actor = reactionActors.get(reactions);
                if (actor == null) {
                    // ساخت Actor جدید (Image یا Label)
                    if (reactions.getPath() != null && reactions.getPath().contains(".png")) {
                        Texture tex = emojiTextureCache.get(reactions.getPath());
                        if (tex == null) {
                            try {
                                tex = new Texture(reactions.getPath());
                                emojiTextureCache.put(reactions.getPath(), tex);
                            } catch (Exception e) {
                                // اگر بارگذاری با خطا مواجه شد، به متن برمی‌گردیم
                                Label fallback = new Label(reactions.getPath(), skin);
                                fallback.setFontScale(0.9f);
                                fallback.setTouchable(Touchable.disabled);
                                fallback.setPosition(actorX, actorY);
                                stage.addActor(fallback);
                                reactionActors.put(reactions, fallback);
                                continue;
                            }
                        }
                        Image img = new Image(tex);
                        img.setSize(EMOJI_SIZE, EMOJI_SIZE);
                        img.setTouchable(Touchable.disabled);
                        img.setPosition(actorX, actorY);
                        stage.addActor(img);
                        reactionActors.put(reactions, img);
                    } else {
                        Label lbl = new Label(reactions.getPath(), skin);
                        lbl.setFontScale(0.9f);
                        lbl.setTouchable(Touchable.disabled);
                        lbl.setPosition(actorX, actorY);
                        stage.addActor(lbl);
                        reactionActors.put(reactions, lbl);
                    }
                } else {
                    // اگر قبلاً actor ساخته شده، فقط موقعیت را آپدیت کن
                    actor.setPosition(actorX, actorY);
                }
            } else {
                // منقضی شده — حذف از لیست و پاک کردن actor مربوطه
                iterator.remove();
                Actor removed = reactionActors.remove(reactions);
                if (removed != null) removed.remove();
            }
        }
        batch.end();

        batch.begin();
        GameApp.getTimeAndDate().renderEffect(batch, delta);
        batch.end();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        GameApp.getTimeAndDate().render(batch);
        renderEnergyBar(batch, GameApp.getPlayer().getEnergy().getEnergyAmount(), GameApp.getPlayer().getEnergy().getMaxEnergy());

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
        GameApp.getMapManager().disposeAll();
        mapRenderer.dispose();
        batch.dispose();
        if (GameApp.getTimeAndDate().getRainEffect() != null)
            GameApp.getTimeAndDate().getRainEffect().forEach(ParticleEffect::dispose);
        // dispose cached emoji textures
        for (Texture t : emojiTextureCache.values()) {
            try { t.dispose(); } catch (Exception ignored) {}
        }
        emojiTextureCache.clear();
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

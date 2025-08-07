package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.control.PreGameMenuController;
import io.github.some_example_name.model.C2SConnectionThread;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.PreGameMenuView;
import io.github.some_example_name.view.SignUpMenuView;

import java.io.IOException;
import java.net.Socket;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        // Starting Connecting to Server
        Socket socket;
        try {
            socket = new Socket("localhost", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            GameApp.c2sConnectionThread = new C2SConnectionThread(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameApp.c2sConnectionThread.start();

//        getMain().setScreen(new PreGameMenuView(new PreGameMenuController(GameAssetManager.getGameAssetManager().getSkin()),GameAssetManager.getGameAssetManager().getSkin(), null));
        getMain().setScreen(new SignUpMenuView(GameAssetManager.getGameAssetManager().getSkin()));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (GameApp.c2sConnectionThread != null) GameApp.c2sConnectionThread.end();
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}
//
//package io.github.some_example_name;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.math.Intersector;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
//public class Main extends ApplicationAdapter {
//    private MapManager mapManager;
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer mapRenderer;
//    private OrthographicCamera camera;
//    private SpriteBatch batch;
//    private Texture overlayTexture;
//    private Sprite overlaySprite;
//    private Texture playerTexture;
//    private float playerX = 200, playerY = 160;
//    private Rectangle playerRectangle = new Rectangle(200, 160, 20, 20);
//    private float speed = 160;
//
//    private boolean justTeleported = false;
//    private float teleportCooldown = 1.0f;
//
//    @Override
//    public void create() {
//        mapManager = new MapManager();
//        map = mapManager.getMap("bigmap.tmx");
//        mapRenderer = new OrthogonalTiledMapRenderer(map);
//
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 880, 560);
//
//        batch = new SpriteBatch();
//        playerTexture = new Texture("libgdx.png");
//
//        TiledMapTileLayer layert = null;
//
//        for (int i = map.getLayers().getCount() - 1; i >= 0; i--) {
//            MapLayer l = map.getLayers().get(i);
//            if (l instanceof TiledMapTileLayer) {
//                layert = (TiledMapTileLayer) l;
//                break;
//            }
//        }
//
//        if (layert == null) {
//            System.out.println("No tile layer found!");
//            return;
//        }
//
//        // اندازه تایل رو بگیر
//        int tileWidth = (int) layert.getTileWidth();
//        int tileHeight = (int) layert.getTileHeight();
//
//        // بارگذاری تصویر و بریدن به اندازه تایل
//        Texture texture = new Texture(Gdx.files.internal("libgdx.png")); // بهتره مسیر رو نسبی بذاری
//        TextureRegion region = new TextureRegion(texture, 0, 0, tileWidth, tileHeight);
//        StaticTiledMapTile tile = new StaticTiledMapTile(region);
//
//        TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
//        newCell.setTile(tile);
//
//        int x = 0;
//        int y = 0;
//        layert.setCell(x, y, newCell);
//    }
//
//    @Override
//    public void render() {
//        float delta = Gdx.graphics.getDeltaTime();
//
//        if (teleportCooldown > 0) {
//            teleportCooldown -= delta;
//            justTeleported = true;
//        } else {
//            justTeleported = false;
//        }
//
//        // کنترل حرکت بازیکن
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerX -= speed * delta;
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += speed * delta;
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerY += speed * delta;
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerY -= speed * delta;
//
//        playerRectangle.setPosition(playerX, playerY);
//
//        // بررسی برخورد با آبجکت‌ها
//        if (!justTeleported && map.getLayers().get("object") != null) {
//            for (MapObject object : map.getLayers().get("object").getObjects()) {
//                if (object instanceof RectangleMapObject) {
//                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
//                    if (Intersector.overlaps(playerRectangle, rect)) {
//                        String newMap = (String) object.getProperties().get("targetMap");
//                        if (newMap != null) {
//                            map = mapManager.getMap(newMap);
//                            mapRenderer.setMap(map);
//
//                            // تنظیم موقعیت بازیکن
//                            MapObject objectPoint = map.getLayers().get("object").getObjects().get("out");
//                            if (objectPoint instanceof RectangleMapObject) {
//                                float x = ((RectangleMapObject) objectPoint).getRectangle().x;
//                                float y = ((RectangleMapObject) objectPoint).getRectangle().y;
//                                playerX = x;
//                                playerY = y;
//                                playerRectangle.setPosition(x, y);
//                            }
//
//                            teleportCooldown = 1.0f;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        if (!justTeleported && map.getLayers().get("object1") != null) {
//            for (MapObject object : map.getLayers().get("object1").getObjects()) {
//                if (object instanceof RectangleMapObject) {
//                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
//                    if (Intersector.overlaps(playerRectangle, rect)) {
//                        String newMap = (String) object.getProperties().get("targetMap");
//                        if (newMap != null) {
//                            map = mapManager.getMap(newMap);
//                            mapRenderer.setMap(map);
//
//                            // تنظیم موقعیت بازیکن
//                            MapObject objectPoint = map.getLayers().get("object1").getObjects().get("outstore1");
//                            if (objectPoint instanceof RectangleMapObject) {
//                                float x = ((RectangleMapObject) objectPoint).getRectangle().x;
//                                float y = ((RectangleMapObject) objectPoint).getRectangle().y;
//                                playerX = x;
//                                playerY = y;
//                                playerRectangle.setPosition(x, y);
//                            }
//
//                            teleportCooldown = 1.0f;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//        // رندر نقشه و بازیکن
//        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        camera.position.set(playerX + 16, playerY + 16, 0);
//        camera.update();
//
//        mapRenderer.setView(camera);
//        mapRenderer.render();
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        batch.draw(playerTexture, playerX, playerY, 16, 16);
//        batch.end();
//    }
//
//    @Override
//    public void dispose() {
//        mapManager.disposeAll();
//        mapRenderer.dispose();
//        batch.dispose();
//        playerTexture.dispose();
//    }
//}

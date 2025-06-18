package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Main extends ApplicationAdapter {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture playerTexture;
    private float playerX = 200, playerY = 160; // نقطه وسط نقشه (تقریبی)
    Rectangle playerRectangle = new Rectangle(200 , 160 , 20 , 20);
    private float speed = 100; // پیکسل در ثانیه

    @Override
    public void create() {
        TmxMapLoader loader = new TmxMapLoader();
         map = loader.load("untitled.tmx"); // should be in assets
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 880, 560);

        batch = new SpriteBatch();
        playerTexture = new Texture("libgdx.png"); // عکس بازیکن
    }

    boolean justTeleported = false;
    float teleportCooldown = 1.0f; // 1 ثانیه

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (teleportCooldown > 0) {
            teleportCooldown -= delta;
            justTeleported = true;
        } else {
            justTeleported = false;
        }

        // حرکت بازیکن
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerY -= speed * delta;

        playerRectangle.setPosition(playerX, playerY);

        if (!justTeleported) {
            for (MapObject object : map.getLayers().get("object").getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();

                    if (Intersector.overlaps(playerRectangle, rect)) {
                        String newMap = (String) object.getProperties().get("targetMap");
                        if (newMap != null) {
                            map.dispose();
                            map = new TmxMapLoader().load(newMap);
                            mapRenderer.setMap(map);

                            // موقعیت بازیکن و زمان تاخیر
                            playerX = 200;
                            playerY = 160;
                            playerRectangle.setPosition(playerX, playerY);
                            teleportCooldown = 1.0f;
                            break;
                        }

                    }
                }
            }
        }


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // دوربین رو دنبال بازیکن بفرست
        camera.position.set(playerX + 16, playerY + 16, 0); // مرکز بازیکن
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        // کشیدن بازیکن روی مپ
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playerTexture, playerX, playerY , 20 , 20);

        batch.end();
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        playerTexture.dispose();
    }
}

package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.model.enums.creature.Animals;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin;
    private final Map<String, AnimatedTiledMapTile> animalAnimations;
    private final float ANIMATION_SPEED = 0.2f;
    private final int FRAME_COUNT = 4;
    private final int FRAME_WIDTH32 = 32;
    private final int FRAME_HEIGHT32 = 32;
    private final int FRAME_WIDTH16 = 16;
    private final int FRAME_HEIGHT16 = 16;

    private GameAssetManager() {
        skin = new Skin(Gdx.files.internal("skin/NzSkin.json"));
        animalAnimations = new HashMap<>();

        // Initialize all animal animations
        loadAnimalAnimationEating32("cow", "eating");
        loadAnimalAnimationEating32("goat", "eating");
        loadAnimalAnimationEating16("chicken", "eating");
        loadAnimalAnimationEating16("dinosaur", "eating");
        loadAnimalAnimationEating16("duck", "eating");
        loadAnimalAnimationEating32("pig", "eating");
        loadAnimalAnimationEating16("rabbit", "eating");
        loadAnimalAnimationEating32("sheep", "eating");
        loadAnimalAnimationPet32("pig", "pet");
        loadAnimalAnimationPet32("cow", "pet");
        loadAnimalAnimationPet32("sheep", "pet");
        loadAnimalAnimationPet32("goat", "pet");
        loadAnimalAnimationPet16("chicken", "pet");
        loadAnimalAnimationPet16("duck", "pet");
        loadAnimalAnimationPet32("rabbit", "pet");
        loadAnimalAnimationPet32("dinosaur", "pet");

        // You can add more animations for other states (idle, walking, etc.) as needed
    }


    private void loadAnimalAnimationEating32(String animal, String state) {
        String path = String.format("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Animal/%s/%s.png",
            animal, state);
        Texture texture = new Texture(Gdx.files.internal(path));

        TextureRegion[] frames = new TextureRegion[FRAME_COUNT];
        StaticTiledMapTile[] staticTiles = new StaticTiledMapTile[FRAME_COUNT];

        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = new TextureRegion(texture, i * FRAME_WIDTH32, 0, FRAME_WIDTH32, FRAME_HEIGHT32);
            staticTiles[i] = new StaticTiledMapTile(frames[i]);
        }

        Array<StaticTiledMapTile> tilesArray = new Array<>(staticTiles);
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(ANIMATION_SPEED, tilesArray);

        String key = getAnimationKey(animal, state);
        animalAnimations.put(key, animatedTile);
    }

    private void loadAnimalAnimationPet32(String animal, String state) {
        String path = String.format("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Animal/%s/%s.png",
            animal, state);

        TextureRegion[] frames = new TextureRegion[2];
        StaticTiledMapTile[] staticTiles = new StaticTiledMapTile[2];

        // texture0 -> resize with Pixmap
        Texture texture0 = new Texture(Gdx.files.internal(path));
        TextureData data0 = texture0.getTextureData();
        if (!data0.isPrepared()) {
            data0.prepare();
        }
        Pixmap pixmap0 = data0.consumePixmap();
        Pixmap region0 = new Pixmap(FRAME_WIDTH32, FRAME_HEIGHT32, pixmap0.getFormat());
        region0.drawPixmap(pixmap0,
            2 * FRAME_WIDTH32, 0, FRAME_WIDTH32, FRAME_HEIGHT32,
            0, 0, FRAME_WIDTH32, FRAME_HEIGHT32
        );

        Pixmap resized0 = new Pixmap(16, 16, region0.getFormat());
        resized0.drawPixmap(region0,
            0, 0, FRAME_WIDTH32, FRAME_HEIGHT32,
            0, 0, 16, 16
        );

        Texture textureResized0 = new Texture(resized0);
        frames[0] = new TextureRegion(textureResized0);
        staticTiles[0] = new StaticTiledMapTile(frames[0]);

        // texture1 -> resize with Pixmap
        Pixmap pixmap1 = new Pixmap(Gdx.files.internal(Animals.valueOf(animal.toUpperCase()).getImagePath()));
        Pixmap resized1 = new Pixmap(16, 16, pixmap1.getFormat());
        resized1.drawPixmap(pixmap1,
            0, 0, pixmap1.getWidth(), pixmap1.getHeight(),
            0, 0, 16, 16
        );

        Texture textureResized1 = new Texture(resized1);
        frames[1] = new TextureRegion(textureResized1);
        staticTiles[1] = new StaticTiledMapTile(frames[1]);

        // Clean up
        pixmap0.dispose();
        region0.dispose();
        resized0.dispose();
        pixmap1.dispose();
        resized1.dispose();

        Array<StaticTiledMapTile> tilesArray = new Array<>(staticTiles);
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(ANIMATION_SPEED, tilesArray);

        String key = getAnimationKey(animal, state);
        animalAnimations.put(key, animatedTile);
    }
    private void loadAnimalAnimationPet16(String animal, String state) {
        String path = String.format("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Animal/%s/%s.png",
            animal, state);

        TextureRegion[] frames = new TextureRegion[2];
        StaticTiledMapTile[] staticTiles = new StaticTiledMapTile[2];

        // texture0 -> resize with Pixmap
        Texture texture0 = new Texture(Gdx.files.internal(path));
        TextureData data0 = texture0.getTextureData();
        if (!data0.isPrepared()) {
            data0.prepare();
        }
        Pixmap pixmap0 = data0.consumePixmap();
        Pixmap region0 = new Pixmap(FRAME_WIDTH16, FRAME_HEIGHT16, pixmap0.getFormat());
        region0.drawPixmap(pixmap0,
            2 * FRAME_WIDTH16, 0, FRAME_WIDTH16, FRAME_HEIGHT16,
            0, 0, FRAME_WIDTH16, FRAME_HEIGHT16
        );

        Pixmap resized0 = new Pixmap(16, 16, region0.getFormat());
        resized0.drawPixmap(region0,
            0, 0, FRAME_WIDTH16, FRAME_HEIGHT16,
            0, 0, 16, 16
        );

        Texture textureResized0 = new Texture(resized0);
        frames[0] = new TextureRegion(textureResized0);
        staticTiles[0] = new StaticTiledMapTile(frames[0]);

        // texture1 -> resize with Pixmap
        Pixmap pixmap1 = new Pixmap(Gdx.files.internal(Animals.valueOf(animal.toUpperCase()).getImagePath()));
        Pixmap resized1 = new Pixmap(16, 16, pixmap1.getFormat());
        resized1.drawPixmap(pixmap1,
            0, 0, pixmap1.getWidth(), pixmap1.getHeight(),
            0, 0, 16, 16
        );

        Texture textureResized1 = new Texture(resized1);
        frames[1] = new TextureRegion(textureResized1);
        staticTiles[1] = new StaticTiledMapTile(frames[1]);

        // Clean up
        pixmap0.dispose();
        region0.dispose();
        resized0.dispose();
        pixmap1.dispose();
        resized1.dispose();

        Array<StaticTiledMapTile> tilesArray = new Array<>(staticTiles);
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(ANIMATION_SPEED, tilesArray);

        String key = getAnimationKey(animal, state);
        animalAnimations.put(key, animatedTile);
    }
    private void loadAnimalAnimationEating16(String animal, String state) {
        String path = String.format("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Animal/%s/%s.png",
            animal, state);
        Texture texture = new Texture(Gdx.files.internal(path));

        TextureRegion[] frames = new TextureRegion[FRAME_COUNT];
        StaticTiledMapTile[] staticTiles = new StaticTiledMapTile[FRAME_COUNT];

        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = new TextureRegion(texture, i * FRAME_WIDTH16, 0, FRAME_WIDTH16, FRAME_HEIGHT16);
            staticTiles[i] = new StaticTiledMapTile(frames[i]);
        }

        Array<StaticTiledMapTile> tilesArray = new Array<>(staticTiles);
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(ANIMATION_SPEED, tilesArray);

        String key = getAnimationKey(animal, state);
        animalAnimations.put(key, animatedTile);
    }
    private String getAnimationKey(String animal, String state) {
        return animal.toLowerCase() + "_" + state.toLowerCase();
    }

    public static GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public Skin getSkin() {
        return skin;
    }

    public AnimatedTiledMapTile getAnimalAnimation(String animal, String state) {
        String key = getAnimationKey(animal, state);
        return animalAnimations.get(key);
    }

    public void dispose() {
        skin.dispose();
        // Dispose of all textures (you might want to keep track of them separately)
        for (AnimatedTiledMapTile tile : animalAnimations.values()) {
            for (StaticTiledMapTile staticTile : tile.getFrameTiles()) {
                staticTile.getTextureRegion().getTexture().dispose();
            }
        }
        animalAnimations.clear();
    }
}

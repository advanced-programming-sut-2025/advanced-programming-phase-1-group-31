package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Lobby;
import io.github.some_example_name.control.PreGameMenuController;

import java.util.ArrayList;
import java.util.List;

/**
 * A screen that allows players to select a map before starting the game.
 * Properly handles resource management and selection state.
 */
public class PreGameMenuView implements Screen {
    private final Stage stage;
    private final PreGameMenuController controller;
    private final Lobby lobby;
    private final Skin skin;
    private final Table table;

    private final List<String> mapFiles;
    private final List<Texture> mapTextures;
    private final List<ImageButton> mapButtons;
    private String selectedMap;

    public PreGameMenuView(PreGameMenuController controller, Skin skin, Lobby lobby) {
        this.controller = controller;
        this.skin = skin;
        this.lobby = lobby;
        this.mapFiles = List.of("farm1.png", "farm2.png", "farm2.png");
        this.mapTextures = new ArrayList<>();
        this.mapButtons = new ArrayList<>();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table(skin);
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        loadMapTextures();
        buildUI();
    }

    private void loadMapTextures() {
        for (String path : mapFiles) {
            mapTextures.add(new Texture(Gdx.files.internal(path)));
        }
    }

    private void buildUI() {
        table.clear();

        // Header
        Label title = new Label("Select Your Map:", skin);
        title.setFontScale(1.7f);
        table.add(title).colspan(mapFiles.size()).padBottom(20);
        table.row();
        table.row();

        // Map buttons
        for (int i = 0; i < mapFiles.size(); i++) {
            final int index = i;
            TextureRegionDrawable drawable = new TextureRegionDrawable(mapTextures.get(i));
            ImageButton button = new ImageButton(drawable);
            button.getImage().setScaling(Scaling.fit);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    highlightSelected(index);
                }
            });
            mapButtons.add(button);
            table.add(button).width(250).height(90).pad(10);
        }
        table.row();

        // Start button
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedMap != null) {
//                    controller.handleStartGameWithMaps(selectedMap);
                } else {
//                    controller.showMessage("Please select a map before starting.");
                }
            }
        });
        table.add(startButton).colspan(mapFiles.size()).padTop(20);

        // Message label (for feedback)
        Label messageLabel = new Label("Salam",  skin);
        messageLabel.setFontScale(1.2f);
        messageLabel.setAlignment(Align.center);
        table.row();
        table.add(messageLabel).colspan(mapFiles.size()).padTop(10);
    }

    private void highlightSelected(int index) {
        // Clear previous highlights
        for (ImageButton btn : mapButtons) {
            btn.getImage().setColor(Color.WHITE);
        }
        // Highlight the chosen button
        ImageButton selectedButton = mapButtons.get(index);
        selectedButton.getImage().setColor(Color.GREEN);
        selectedMap = mapFiles.get(index);
//        controller.showMessage("Selected map: " + selectedMap);
    }

    @Override
    public void show() {
        // No-op
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear( Color.valueOf("#FAA25A"));
        stage.act(Math.min(delta, 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        mapTextures.forEach(Texture::dispose);
    }
}

package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Lobby;
import common.Message;
import io.github.some_example_name.model.GameApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PreGameMenuView implements Screen {
    private final Stage stage;
    private final Lobby lobby;
    private final Skin skin;
    private final Table table;


    private final List<String> mapFiles;
    private final List<Texture> mapTextures;
    private final List<ImageButton> mapButtons;
    private String selectedMap;

    public PreGameMenuView(Skin skin, Lobby lobby) {
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
            table.add(button).width(300).height(300).pad(10);
        }
        table.row();

        // Start button
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedMap != null) {
                    HashMap<String,Object> body = new HashMap<>();
                    body.put("selected-map", selectedMap);
                    body.put("lobby", lobby);
                    body.put("username", GameApp.player.getUserInfo().getUsername());
                    GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Players_Map));
                    startWaiting();
//                    controller.handleStartGameWithMaps(selectedMap, lobby, number);
                } else {
                    showErrorDialog("Please select a map.", "Error", Color.RED);
                }
            }
        });
        table.add(startButton).colspan(mapFiles.size()).padTop(20);

    }

    private void highlightSelected(int index) {
        for (ImageButton btn : mapButtons) {
            btn.getImage().setColor(Color.WHITE);
        }
        ImageButton selectedButton = mapButtons.get(index);
        selectedButton.getImage().setColor(Color.GREEN);
        selectedMap = mapFiles.get(index);
    }

    public void startWaiting() {
        table.clear();

        Table waitingTable = new Table(skin);
        waitingTable.setFillParent(true);
        waitingTable.center();

        Texture spinnerTexture = new Texture(Gdx.files.internal("spinner.png")); // یک عکس spinner باید داشته باشید
        Image loadingSpinner = new Image(spinnerTexture);
        loadingSpinner.setSize(100, 100);
        waitingTable.add(loadingSpinner).padBottom(30).row();

        Label waitingLabel = new Label("Please wait for other players...", skin);
        waitingLabel.setFontScale(2.0f);
        waitingLabel.setColor(Color.WHITE);
        waitingTable.add(waitingLabel);

        loadingSpinner.addAction(Actions.forever
            (Actions.sequence
                (Actions.moveBy(30f, 0, 1.5f),
                    Actions.moveBy(-30f, 0, 1.5f))));

        stage.clear();
        stage.addActor(waitingTable);

    }

    public void showErrorDialog(String message, String title, Color color) {
        Dialog dialog;
        dialog = new Dialog(title, table.getSkin()) {
            protected void result(Object object) {
                this.hide();
            }
        };
        dialog.getTitleLabel().setFontScale(1f);
        dialog.getTitleLabel().setColor(color);
        dialog.getTitleLabel().setAlignment(Align.center);
        dialog.pad(50);
        dialog.setWidth(1000);
        dialog.setHeight(1000);
        Label textLabel = new Label(message, table.getSkin());
        textLabel.setAlignment(Align.center);
        dialog.text(textLabel);
        dialog.button("OK");
        dialog.getContentTable().pad(20);
        dialog.show(stage);
    }

    @Override
    public void show() {
        // No-op
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#FAA25A"));
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        mapTextures.forEach(Texture::dispose);
    }
}

package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.reflect.TypeToken;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.control.FarmController;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.MapManager;
import io.github.some_example_name.model.MapType;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class PrePreGameMenuView implements Screen {

    private final Stage stage;
    private GameView gameView;

    public PrePreGameMenuView(Message message) {
        stage = new Stage(new ScreenViewport());
        GameApp.othersPoint.add(new Point(0, 0));
        GameApp.othersPoint.add(new Point(0, 0));
        GameApp.othersPoint.add(new Point(0, 0));
        int number = message.getFromBody("number", Integer.class);
        Type type = new TypeToken<HashMap<Integer, String>>() {}.getType();
        HashMap<Integer, String> mapNumber = message.getFromBodyType("all-maps", type);
        System.out.println("mapNumber: " + mapNumber);
        handleStartGameWithMaps(mapNumber, number);
    }

    public void handleStartGameWithMaps(HashMap<Integer, String> mapNumber, int number) {
        MapManager mapManager = new MapManager();
        mapManager.createMap(mapNumber, number);
        GameApp.setMapManager(mapManager);
        gameView = new FarmView(new FarmController(), GameAssetManager.getGameAssetManager().getSkin(), MapType.FARM);
        GameApp.setGameView(gameView);
        gameView.getGameController().startPoint(gameView.getMap());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.getLabel().setColor(Color.WHITE);
        startButton.getLabel().setFontScale(2f);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameView);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(startButton).width(500).height(300);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);
        stage.act(delta);
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
    }
}

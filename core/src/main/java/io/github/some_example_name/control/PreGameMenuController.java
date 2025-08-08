package io.github.some_example_name.control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import common.Lobby;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.view.FarmView;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.PreGameMenuView;

import java.util.ArrayList;

/**
 * Controller for the pre-game map selection screen.
 * Manages user interactions, message feedback, and game startup logic.
 */
public class PreGameMenuController {
    private final Skin skin;
    private final Label messageLabel;
    private PreGameMenuView view;

    public PreGameMenuController(Skin skin) {
        this.skin = skin;
        this.messageLabel = new Label("", skin);
    }

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handleStartGameWithMaps(String selectedMapPng, Lobby lobby, int number) {
        String selectedMapTmx;

        MapManager mapManager = new MapManager();
        selectedMapTmx = selectedMapPng.replace(".png", ".tmx");

        mapManager.createMap(selectedMapTmx, GameApp.player, number);
        GameApp.setMapManager(mapManager);

        GameView gameView = new FarmView(new FarmController(), GameAssetManager.getGameAssetManager().getSkin(), MapType.FARM);
        GameApp.setGameView(gameView);
        gameView.getGameController().startPoint(gameView.getMap());

        Main.getMain().setScreen(gameView);
    }

}

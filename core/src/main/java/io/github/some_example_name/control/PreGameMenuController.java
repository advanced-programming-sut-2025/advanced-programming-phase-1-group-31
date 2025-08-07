package io.github.some_example_name.control;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import common.Lobby;
import io.github.some_example_name.view.PreGameMenuView;

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

    public void handleStartGameWithMaps(String selectedMap, Lobby lobby) {
    }
}

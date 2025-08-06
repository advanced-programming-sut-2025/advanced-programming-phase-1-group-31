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
    private final Lobby lobby;
    private final Skin skin;
    private final Label messageLabel;
    private PreGameMenuView view;

    /**
     * @param skin  UI skin for creating labels
     * @param lobby Game lobby for managing session state
     */
    public PreGameMenuController(Skin skin, Lobby lobby) {
        this.skin = skin;
        this.lobby = lobby;
        this.messageLabel = new Label("", skin);
    }

    /**
     * Called by view to register itself with the controller.
     */
    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    /**
     * Returns the Label used to display messages to the player.
     */
    public Label getMessageLabel() {
        return messageLabel;
    }

    /**
     * Shows a transient message on the pre-game screen.
     * @param message Text to display
     */
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Handles the "Start Game" action after map selection.
     * @param selectedMap The filename of the chosen map
     */
//    public void handleStartGameWithMap(String selectedMap) {
//        if (selectedMap == null || selectedMap.isEmpty()) {
//            showMessage("Error: No map selected.");
//            return;
//        }
//        try {
//            // Configure lobby with chosen map
//            lobby.setSelectedMap(selectedMap);
//
//            // Attempt to start the game session
//            lobby.startGame();
//
//            // Provide feedback
//            showMessage("Starting game on " + selectedMap + "...");
//
//            // Transition to the gameplay screen
//            // e.g., view.goToGameplayScreen();
//        } catch (Exception e) {
//            showMessage("Failed to start game: " + e.getMessage());
//        }
//    }
}

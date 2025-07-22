package io.github.some_example_name.Control;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.FarmView;
import io.github.some_example_name.View.GameView;
import io.github.some_example_name.View.PreGameMenuView;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.List;

public class PreGameMenuController {
    private PreGameMenuView view;
    private Label messageLabel;


    public void setView(PreGameMenuView view) {
        this.view = view;
        messageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void onConfirmPressed(List<String> usernames) {
        // هر کاری باید در فشردن کانفیرم انجام شود
        if (usernames.size() < 1){
            showErrorMessage( "At least 1 username is required."); return;}



//        if (Arrays.asList(usernames).contains(App.getPlayerLoggedIn().getUsername())) {
//            return new Result(false, "you can not chose own");
//        }
//
        if (usernames.size() > 3){
            showErrorMessage( "A maximum of 3 usernames is allowed."); return;}
//        if (isPlayerAlreadyInGame(App.getPlayerLoggedIn()))
//            return new Result(false, "User already in game: " + App.getPlayerLoggedIn().getUsername());
//        for (String username : usernames) {
//            Player player = App.getRegisteredPlayers().stream().filter(b -> b.getUsername().equals(username))
//                .findFirst().orElse(null);
//            if (player == null)
//                return new Result(false, "Invalid username: " + username);
//            if (isPlayerAlreadyInGame(player))
//                return new Result(false, "User already in game: " + username);
//        }
//        ArrayList<Player> matchedPlayers = App.getRegisteredPlayers().stream()
//            .filter(player -> Arrays.asList(usernames).contains(player.getUsername()))
//            .collect(Collectors.toCollection(ArrayList::new));
//        matchedPlayers.add(0, App.getPlayerLoggedIn());
//        App.setCurrentMenu(Menus.GameMenu);
//        Game game = new Game(matchedPlayers);
//        game.setAdminPlayer(App.getPlayerLoggedIn());
//        game.setActivePlayer(App.getPlayerLoggedIn());
//        App.addGames(game);
//        App.setCurrentGame(game);
//        gameMap(matchedPlayers);
//        game.addFriendShip();
//        game.addFriendshipWithNPC();
//        return new Result(true, "Game started with " + matchedPlayers.size()
//            + " new player(s)." + "\n" + "It's " + App.getCurrentGame().getActivePlayer().getUsername() + " turn.");        // مثال: تغییر منو، ذخیره داده، شروع بازی، ...
        view.setupMapSelection(usernames);
    }
    public void handleStartGameWithMaps(List<String> selectedMapsPng , List<String> playerUsernames) {
        List<String> notSelectedYet = new ArrayList<>();
        List<String> selectedMapsTmx = new ArrayList<>();

        for (int i = 0; i < playerUsernames.size(); i++) {
            if (i >= selectedMapsPng.size() || selectedMapsPng.get(i) == null || selectedMapsPng.get(i).isEmpty()) {
                notSelectedYet.add(playerUsernames.get(i));
            }
        }
        if (!notSelectedYet.isEmpty()) {
            showErrorMessage("Players who haven't selected their map yet: " + notSelectedYet);
            return;
        }
        MapManager mapManager = new MapManager();

        for (String selectedMap : selectedMapsPng) {
           selectedMapsTmx.add(selectedMap.replace(".png" , ".tmx"));
        }

        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(playerUsernames.get(0));
        Player player2 = new Player(playerUsernames.get(1));
        Player player3 = new Player(playerUsernames.get(2));
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Game game = new Game(players);
        App.setCurrentGame(game);

        mapManager.createMap(selectedMapsTmx , players);
        game.setActivePlayer(player1);
        game.setMapManager(mapManager);

        GameView gameView = new FarmView(new FarmController(),GameAssetManager.getGameAssetManager().getSkin() , MapType.FARM );
        App.getCurrentGame().setGameView(gameView);
        gameView.getGameController().startPoint(gameView.getMap());

        Main.getMain().setScreen(gameView);
    }
    public void showSuccessMessage(String text) {
        messageLabel.clearActions();
        messageLabel.setText(text);
        messageLabel.setColor(0, 1, 0, 0);

        float scaleUp = 1.2f;
        float scaleDuration = 0.3f;


        SequenceAction shakeSequence = Actions.sequence();
        for (int i = 0; i < 2; i++) {
            shakeSequence.addAction(Actions.moveBy(3f, 0, 0.05f));
            shakeSequence.addAction(Actions.moveBy(-6f, 0, 0.05f));
            shakeSequence.addAction(Actions.moveBy(3f, 0, 0.05f));
        }

        SequenceAction successSequence = Actions.sequence(
            Actions.parallel(
                Actions.fadeIn(0.5f),
                Actions.scaleTo(scaleUp, scaleUp, scaleDuration),
                Actions.rotateBy(15f, scaleDuration)
            ),
            Actions.parallel(
                Actions.scaleTo(1f, 1f, 0.3f),
                Actions.rotateTo(0f, 0.3f)
            ),
            shakeSequence,
            Actions.delay(3f),
            Actions.fadeOut(1f)
        );

        messageLabel.addAction(successSequence);
    }


    public void showErrorMessage(String text) {
        messageLabel.clearActions();
        messageLabel.setText(text);
        messageLabel.setColor(1, 0, 0, 1);

        float shakeAmount = 5f;
        float shakeDuration = 0.05f;

        SequenceAction shakeSequence = Actions.sequence();
        for (int i = 0; i < 3; i++) {
            shakeSequence.addAction(Actions.moveBy(shakeAmount, 0, shakeDuration));
            shakeSequence.addAction(Actions.moveBy(-2 * shakeAmount, 0, shakeDuration));
            shakeSequence.addAction(Actions.moveBy(shakeAmount, 0, shakeDuration));
        }
        shakeSequence.addAction(Actions.moveTo(messageLabel.getX(), messageLabel.getY(), shakeDuration));

        RepeatAction blinkRepeat = Actions.repeat(3, Actions.sequence(
            Actions.fadeOut(0.5f),
            Actions.fadeIn(0.5f)
        ));

        SequenceAction fullSequence = Actions.sequence(
            shakeSequence,
            blinkRepeat,
            Actions.delay(3f),
            Actions.fadeOut(1f)
        );


        messageLabel.addAction(fullSequence);
    }
    public Label getMessageLabel() {
        return messageLabel;
    }
}

package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import io.github.some_example_name.Main;
import common.Lobby;
import common.UserInfo;
import io.github.some_example_name.control.PreGameMenuController;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.util.HashMap;


public class MyLobbyMenuView implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final Skin skin;
    private final Lobby lobby;

    public MyLobbyMenuView(Skin skin) {
        this.skin = skin;
        HashMap<String, Object> body = new HashMap<>();
        body.put("player", GameApp.player.getUserInfo());
        Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.Which_Lobby));
        this.lobby = message.getFromBody("lobby", Lobby.class);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        rootTable = new Table(skin);
        rootTable.setFillParent(true);
        rootTable.center();
        rootTable.pad(30);
    }

    @Override
    public void show() {
        rootTable.clear();

        // Lobby Info Card
        Table infoCard = new Table(skin);
        infoCard.pad(20);
        infoCard.defaults().pad(8);

        Label title = new Label(lobby.getLobbyName(), skin);
        Label line = new Label("---------------------------", skin);
        title.setFontScale(2f);
        title.setColor(Color.GREEN);
        infoCard.add(title).colspan(2).center();
        infoCard.row();
        infoCard.add(line).colspan(2).center();
        infoCard.row();

        infoCard.add(new Label("Lobby ID:", skin)).left();
        infoCard.add(new Label(String.valueOf(lobby.getLobbyID()), skin)).right();
        infoCard.row();

        infoCard.add(new Label("Players:", skin)).left();
        infoCard.add(new Label(lobby.getNumberOfPlayers() + " -> 4", skin)).right();
        infoCard.row();

        infoCard.add(new Label("Private:", skin)).left();
        infoCard.add(new Label(lobby.isPrivate() ? "Yes" : "No", skin)).right();
        infoCard.row();

        rootTable.add(infoCard).width(400).row();

        // Player List
        Table playerTable = new Table(skin);
        playerTable.defaults().pad(6).left();
        for (String user : lobby.getPlayers()) {
            playerTable.add(new Label("• " + user, skin));
            playerTable.row();
        }
        ScrollPane playerScroll = new ScrollPane(playerTable, skin);
        playerScroll.setFadeScrollBars(false);
        playerScroll.setScrollingDisabled(true, false);

        rootTable.add(new Label("Players in Lobby", skin)).padTop(20).colspan(2).center();
        rootTable.row();
        rootTable.add(playerScroll).width(400).height(180).colspan(2).padTop(10).row();

        // Buttons
        Table buttonRow = new Table(skin);
        buttonRow.defaults().pad(10).expandX().fillX();

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        TextButton leaveBtn = new TextButton("Leave", skin);
        leaveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameApp.leaveLobby(lobby);
                Dialog dialog;
                dialog = new Dialog("Left", skin) {
                    protected void result(Object object) {
                        Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
                    }
                };
                dialog.getTitleLabel().setFontScale(1f);
                dialog.getTitleLabel().setColor(Color.YELLOW);
                dialog.getTitleLabel().setAlignment(Align.center);
                dialog.pad(50);
                dialog.setWidth(1000);
                dialog.setHeight(1000);
                Label textLabel = new Label("You successfully left the lobby", skin);
                textLabel.setAlignment(Align.center);
                dialog.text(textLabel);
                dialog.button("OK");
                dialog.getContentTable().pad(20);
                dialog.show(stage);
            }
        });

        buttonRow.add(backBtn);
        buttonRow.add(leaveBtn);

        // Start Game (admin only)
        UserInfo me = GameApp.player.getUserInfo();
        if (me != null && me.getUsername().equals(lobby.getAdmin())) {
            TextButton startBtn = new TextButton("Start Game", skin);
            startBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (lobby.getNumberOfPlayers() < 2) {
                        showErrorDialog("Not enough players to start the game.", "Error", Color.RED);
                    } else {
                        HashMap<String, Object> body = new HashMap<>();
                        body.put("lobby", lobby);
                        Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.Start_Button_Pressed));
                        String error_message = message.getFromBody("error-message", String.class);
                        if (error_message != null) {
                            showErrorDialog(error_message, "Error", Color.RED);
                        } else {
                            lobby.setStarted(true);
                            body.replace("lobby", lobby);
                            GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Lobby));
                            Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(skin), GameAssetManager.getGameAssetManager().getSkin(), lobby, 0));
                        }
                    }
                }
            });
            buttonRow.add(startBtn);
        }

        rootTable.add(buttonRow).padTop(30).colspan(2);

        stage.addActor(rootTable);
    }

    public void showErrorDialog(String message, String title, Color color) {
        Dialog dialog;
        dialog = new Dialog(title, skin) {
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
        Label textLabel = new Label(message, skin);
        textLabel.setAlignment(Align.center);
        dialog.text(textLabel);
        dialog.button("OK");
        dialog.getContentTable().pad(20);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#faa25a"));
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
    }
}

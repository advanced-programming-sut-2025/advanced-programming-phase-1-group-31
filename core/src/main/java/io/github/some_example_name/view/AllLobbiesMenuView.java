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
import com.google.gson.reflect.TypeToken;
import common.Lobby;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class AllLobbiesMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Table root;
    private Table lobbyTable;
    private long lastRefreshTime;
    private ArrayList<Lobby> lobbies;
    private Dialog dialog;

    // Callback invoked when a lobby button is clicked
    private Consumer<Lobby> onLobbyClick;

    public AllLobbiesMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        root = new Table();
        root.setFillParent(true);
        lastRefreshTime = System.currentTimeMillis();
    }

    public void setOnLobbyClickListener(Consumer<Lobby> listener) {
        this.onLobbyClick = listener;
    }

    @Override
    public void show() {
        stage.clear();
        stage.addActor(root);

        // Header (Back button + title + search field)
        Table header = new Table();
        header.setFillParent(false);
        header.pad(10);
        header.defaults().pad(10);

        TextButton back = new TextButton("Back", skin);
        TextButton refresh = new TextButton("Refresh", skin);
        TextButton search = new TextButton("search", skin);
        Label title = new Label("All Lobbies", skin);
        title.setFontScale(1.8f);

        TextField searchField = new TextField("", skin);
        searchField.setMessageText("search lobbies (ID)...");
        searchField.setMaxLength(20);

        header.add(title).expandX().center().colspan(3);
        header.row();
        header.add(refresh).width(200).height(80).colspan(1).padLeft(20).row();
        header.add(back).width(200).height(80).colspan(1).padLeft(20);
        header.add(searchField).center().height(100).width(800).colspan(1);
        header.add(search).padRight(20).colspan(1);

        root.add(header).fillX().padBottom(20).row();

        lobbyTable = new Table();
        lobbyTable.top().pad(5);
        lobbyTable.defaults().pad(8).fillX();

        ScrollPane scroll = new ScrollPane(lobbyTable, skin);
        scroll.setFadeScrollBars(false);
        scroll.setSmoothScrolling(true);
        scroll.setScrollingDisabled(true, false);
        root.add(scroll).expand().fill().pad(10).row();

        // Back button action
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        // Search button action
        search.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!searchField.getText().isEmpty()) {
                    filterLobby(searchField.getText());
                }
            }
        });


        // Refresh button action
        refresh.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadLobbies();
            }
        });


        setOnLobbyClickListener(lobby -> {
            if (GameApp.player.getLobby() != null) {
                showErrorDialog("You are already in a lobby", "Error", Color.RED);
            }
            else if (lobby.isPrivate()) {
                showPasswordDialog(lobby, passwordIsCorrect -> {
                    if (passwordIsCorrect) {
                        showDialog(lobby);
                    } else {
                        showErrorDialog("Password is incorrect", "Error", Color.RED);
                    }
                });
            } else {
                showDialog(lobby);
            }
        });


        loadLobbies();
    }

    private void loadLobbies() {
        Message msg = GameApp.c2sConnectionThread
            .sendAndWaitForResponse(new Message(new HashMap<>(), Message.Type.All_Lobbies));
        Type listType = new TypeToken<ArrayList<Lobby>>() {
        }.getType();
        lobbies = msg.getFromBodyType("lobbies", listType);

        lobbyTable.clearChildren();

        for (Lobby lobby : lobbies) {
            if (!lobby.isVisible()) continue;
            Table row = createLobbyRow(lobby);
            lobbyTable.add(row).row();
        }
    }

    private void filterLobby(String text) {
        lobbyTable.clearChildren();
        try {
            int id = Integer.parseInt(text.trim());
            for (Lobby lobby : lobbies) {
                if (id == lobby.getLobbyID()) {
                    Table row = createLobbyRow(lobby);
                    lobbyTable.add(row).row();
                    break;
                }
            }
        } catch (NumberFormatException nfe) {
            for (Lobby lobby : lobbies) {
                if (lobby.getLobbyName().toLowerCase().contains(text.toLowerCase())) {
                    Table row = createLobbyRow(lobby);
                    lobbyTable.add(row).row();
                }
            }
        }
    }

    private Table createLobbyRow(Lobby lobby) {
        Table row = new Table(skin);
        row.pad(8);

        // Lobby info label
        String info = String.format("ID: %d   %s  ( %d -> 4)",
            lobby.getLobbyID(), lobby.getLobbyName(), lobby.getNumberOfPlayers());
        Label infoLabel = new Label(info, skin);
        infoLabel.setFontScale(1.2f);
        infoLabel.setColor(lobby.isPlayerInThisLobby(GameApp.player.getUserInfo()) ? Color.GREEN : Color.WHITE);

        // Action button
        TextButton actionBtn = new TextButton("Select", skin);
        actionBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onLobbyClick != null) onLobbyClick.accept(lobby);
            }
        });

        // Player list
        Table playersTbl = new Table(skin);
        playersTbl.top();
        playersTbl.add(new Label("Players:", skin)).colspan(1).left().padRight(20);
        playersTbl.row();
        for (String user : lobby.getPlayers()) {
            Label p = new Label(user, skin);
            p.setAlignment(Align.left);
            playersTbl.add(p).left().row();
        }
        playersTbl.add(new Label("-----------------------------------", skin)).colspan(3).center().row();

        // Assemble row
        row.add(infoLabel).expandX().left().colspan(2);
        row.add(actionBtn).right().padLeft(40);
        row.row();
        row.add(playersTbl).colspan(3).left().padTop(4);

        return row;
    }

    public void showDialog(Lobby lobby) {
        Dialog dialog = new Dialog("Connection", skin) {
            @Override
            protected void result(Object object) {
                boolean result = Boolean.TRUE.equals(object);
                if (result) {
                    try {
                        lobby.addPlayer(GameApp.player.getUserInfo());
                        HashMap<String, Object> body = new HashMap<>();
                        body.put("lobby", lobby);
                        GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Lobby));
                        GameApp.player.setLobby(lobby);
                        showErrorDialog("You added to lobby.", "Successful", Color.GREEN);
                    } catch (IllegalArgumentException e) {
                        showErrorDialog(e.getMessage(), "Error", Color.RED);
                    }
                }
            }
        };
        Label label = new Label("connect to the\n" + lobby.getLobbyName() + "\n?", skin);
        label.setAlignment(Align.center);
        dialog.text(label);
        dialog.button("Yes", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    public void showPasswordDialog(Lobby lobby, Consumer<Boolean> onResult) {
        TextField pass = new TextField("", skin);
        pass.setAlignment(Align.center);
        pass.setMessageText("Enter password");

        Dialog dialog = new Dialog("Password", skin) {
            @Override
            protected void result(Object object) {
                boolean success = pass.getText().equals(lobby.getLobbyPassword());
                onResult.accept(success);
            }
        };

        Label label = new Label(lobby.getLobbyName() + "\nis Private.\nPlease enter pass.\n", skin);
        label.setAlignment(Align.center);
        dialog.getTitleLabel().setAlignment(Align.center);
        dialog.text(label);
        dialog.row();
        dialog.button("Join").pad(15).row();
        dialog.button("Cancel", false).row();
        dialog.add(pass).width(300).height(80).pad(15);
        dialog.show(stage);
    }

    public void showErrorDialog(String message, String title, Color color) {
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
        ScreenUtils.clear(Color.valueOf("2b2b2b"));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        if (System.currentTimeMillis() - lastRefreshTime > 6000) {
//            loadLobbies();
//            lastRefreshTime = System.currentTimeMillis();
//        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

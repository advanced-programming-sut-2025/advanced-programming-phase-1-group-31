package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import common.Lobby;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.util.HashMap;

public class AddLobbyMenuView implements Screen {
    private Stage stage;
    private final Skin skin;

    private Dialog dialog;
    // UI elements
    private final Label titleLabel;
    private final TextField lobbyNameField;
    private final TextField lobbyPasswordField;
    private final CheckBox isPrivateCheck;
    private final CheckBox isVisibleCheck;
    private final TextButton createButton;
    private final TextButton backButton;

    // Container for password field
    private Table passwordContainer;

    public AddLobbyMenuView(Skin skin) {
        this.skin = skin;

        // Initialize UI elements
        titleLabel = new Label("Create New Lobby", skin);
        titleLabel.setFontScale(1.5f);
        titleLabel.setColor(Color.WHITE);
        titleLabel.setAlignment(Align.center);

        lobbyNameField = new TextField("", skin);
        lobbyNameField.setMessageText("name");
        lobbyNameField.setAlignment(Align.left);
        lobbyNameField.setFocusTraversal(false);

        lobbyPasswordField = new TextField("", skin);
        lobbyPasswordField.setMessageText("password");

        isPrivateCheck = new CheckBox(" Private", skin);
        isVisibleCheck = new CheckBox(" Visible", skin);

        createButton = new TextButton("Create", skin);
        createButton.getLabel().setFontScale(1.1f);

        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(1.1f);
    }

    @Override
    public void show() {
        // Setup stage and viewport
        stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);

        // Root table
        Table root = new Table(skin);
        root.setFillParent(true);
        root.center();

        // Add title
        root.add(titleLabel).colspan(2).padBottom(20).expandX().fillX();
        root.row();

        // Lobby name
        root.add(new Label("Lobby Name:", skin)).align(Align.right).pad(10);
        root.add(lobbyNameField).width(300).pad(10);
        root.row();

        // Checkboxes (moved ABOVE password)
        Table optionsTable = new Table(skin);
        optionsTable.add(isPrivateCheck).padRight(30);
        optionsTable.add(isVisibleCheck);
        isPrivateCheck.setChecked(false);
        isVisibleCheck.setChecked(true);
        root.add(new Label("Options:", skin)).align(Align.right).pad(10);
        root.add(optionsTable).pad(10);
        root.row();

        // Password container (initially hidden)
        passwordContainer = new Table();
        passwordContainer.add(new Label("Password:", skin)).align(Align.right).pad(10).padRight(130);
        passwordContainer.add(lobbyPasswordField).width(300).pad(10);
        root.add(passwordContainer).colspan(2);
        root.row();

        // Buttons
        Table buttons = new Table(skin);
        buttons.add(createButton).width(250).height(100).padRight(30);
        buttons.add(backButton).width(250).height(100);
        root.add(buttons).colspan(2).padTop(30);

        // Initially disable and hide password field
        lobbyPasswordField.setVisible(false);
        lobbyPasswordField.setDisabled(true);
        passwordContainer.setVisible(false);

        // Listener for private checkbox
        isPrivateCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Boolean isPrivate = isPrivateCheck.isChecked();
                passwordContainer.setVisible(isPrivate);
                lobbyPasswordField.setVisible(isPrivate);
                lobbyPasswordField.setDisabled(!isPrivate);


                if (!isPrivate) {
                    lobbyPasswordField.setText(""); // Clear password when disabled
                }
            }
        });

        // Listeners for buttons
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = lobbyNameField.getText().trim();
                if (name.isEmpty()) {
                    showErrorDialog("Lobby name cannot be empty.", "Error", Color.RED);
                } else if (isPrivateCheck.isChecked() && lobbyPasswordField.getText().isEmpty()) {
                    showErrorDialog("Lobby pass cannot be empty.", "Error", Color.RED);
                } else if (GameApp.player.getLobby() != null) {
                    showErrorDialog("You are now in another lobby", "Error", Color.RED);
                } else {
                    Lobby lobby;
                    Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(new HashMap<>(), Message.Type.Get_Lobby_ID));
                    int id = message.getIntFromBody("id");
                    if (isPrivateCheck.isChecked()) {
                        lobby = new Lobby(name, lobbyPasswordField.getText(), id, GameApp.player.getUserInfo(), isVisibleCheck.isChecked());
                    } else {
                        lobby = new Lobby(name, id, GameApp.player.getUserInfo(), isVisibleCheck.isChecked());
                    }
                    GameApp.player.setLobby(lobby);
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("lobby", lobby);
                    GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Lobby));
                    // TODO: send create-lobby request to server
                    showErrorDialog("Lobby\n\nName:  " + name + "\n\nID:  " + id + "\n\n created successfully!", "Success", Color.GREEN);
                    dialog.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
                        }
                    });
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        stage.addActor(root);
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
        Color bg = Color.valueOf("#faa25a");
        ScreenUtils.clear(bg);
        stage.act(delta);
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

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
import common.Player;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.LobbyMenuController;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.util.HashMap;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final Table table;

    // Elements
    private Dialog dialog;
    private final TextField username;
    private final TextButton loginButton;
    private final TextButton back;


    public LoginMenuView(Skin skin) {
        table = new Table(skin);

        loginButton = new TextButton("login", skin);
        back = new TextButton("back", skin);
        username = new TextField("Username", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(username).pad(30, 20, 30, 20).height(150).width(400);
        table.row();
        table.add(loginButton).pad(30, 20, 30, 20);
        table.row();
        table.add(back).pad(30, 20, 30, 20);


        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (username.getText().isEmpty() || username.getText().equals("Username")) {
                    showErrorDialog("Username is empty", "Error", Color.RED);
                } else {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("command", "login");
                    body.put("username", username.getText());
                    Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.Menu));
                    String error = message.getFromBody("error-message", String.class);
                    if (error != null) {
                        showErrorDialog(message.getFromBody("error-message", String.class), "Error", Color.RED);
                    } else {
                        GameApp.player = message.getFromBody("player", Player.class);
                        showErrorDialog("Login\nSuccessfully", "Success", Color.GREEN);
                        dialog.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Main.getMain().setScreen(new LobbyMenuView(new LobbyMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                            }
                        });
                    }
                }
            }
        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new SignUpMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        stage.addActor(table);
    }


    public void showErrorDialog(String message, String title, Color color) {
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
        dialog.text(message);
        dialog.button("OK");
        dialog.getContentTable().pad(20);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.2f, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    }

}

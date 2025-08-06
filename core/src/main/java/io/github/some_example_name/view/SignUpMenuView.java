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
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.util.HashMap;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Table table;

    // Elements
    private Dialog dialog;
    private final TextField username;
    private final TextField password;
    private final TextField nickName;
    private final TextButton signUpButton;
    private final TextButton loginButton;


    public SignUpMenuView(Skin skin) {
        table = new Table(skin);

        signUpButton = new TextButton("SignUp", skin);
        loginButton = new TextButton("login", skin);
        username = new TextField("", skin);
        username.setMessageText("Username");
        password = new TextField("", skin);
        password.setMessageText("Password");
        nickName = new TextField("", skin);
        nickName.setMessageText("Nickname");
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(username).pad(30, 20, 30, 20).height(150).width(400);
        table.row();
        table.add(password).pad(30, 20, 30, 20).height(150).width(400);
        table.row();
        table.add(nickName).pad(30, 20, 30, 20).height(150).width(400);
        table.row();
        table.add(signUpButton).pad(30, 20, 30, 20);
        table.row();
        table.add(loginButton).pad(30, 20, 30, 20);


        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (username.getText().equals("Username") || password.getText().equals("Password") || nickName.getText().equals("NickName") || username.getText().isEmpty() || password.getText().isEmpty() || nickName.getText().isEmpty()) {
                    showErrorDialog("Please fill all the fields", "Error", Color.RED);
                } else {
                    Player player = new Player(username.getText(), password.getText(), nickName.getText(), null);
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("command", "signup");
                    body.put("player", player);
                    Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.Menu));
                    if (message != null) {
                        String error = message.getFromBody("error-message", String.class);
                        if (error != null) {
                            showErrorDialog(message.getFromBody("error-message", String.class), "Error", Color.RED);
                        }
                    } else {
                        GameApp.player = player;
                        showErrorDialog("Sign Up\nSuccessfully", "Success", Color.GREEN);

                        dialog.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
                            }
                        });
                    }
                }
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new LoginMenuView(GameAssetManager.getGameAssetManager().getSkin()));
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
        Label textLabel = new Label(message, table.getSkin());
        textLabel.setAlignment(Align.center);
        dialog.text(textLabel);
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

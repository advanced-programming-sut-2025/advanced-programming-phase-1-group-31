package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.LobbyMenuController;
import io.github.some_example_name.model.C2SConnectionThread;
import io.github.some_example_name.model.GameApp;

import java.io.IOException;
import java.net.Socket;

public class LobbyMenuView implements Screen {
    private Stage stage;
    private final Table table;
    private final LobbyMenuController controller;

    // Elements
    private final TextButton connection;
    private final TextButton save;
    private final TextButton send;
    private final TextField username;
    private final TextField sentMessage;
    private final TextField receiverUsername;
    private final Label receivedMessage;



    public LobbyMenuView(LobbyMenuController controller, Skin skin) {
        table = new Table(skin);
        this.controller = controller;
        controller.setView(this);

        connection = new TextButton("Connection", skin);
        save = new TextButton("save", skin);
        username = new TextField("username", skin);
        sentMessage = new TextField("Enter Your common.Message.", skin);
        receivedMessage = new Label("The common.Message Will be Shown here.", skin);
        send = new TextButton("Send", skin);
        receiverUsername = new TextField("Enter Receiver Username.", skin);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(save).pad(30, 20, 30, 20);
        table.row();
        table.add(username).pad(30, 20, 30, 20).height(150).width(300);
        table.row();
        table.add(connection).pad(30, 20, 30, 20);
        table.row();
        table.add(receivedMessage).pad(30, 20, 30, 20);
        table.row();
        table.add(receiverUsername).pad(30, 20, 30, 20);
        table.row();
        table.add(send).pad(30, 20, 30, 20);
        table.row();
        table.add(sentMessage).pad(30, 20, 30, 20).height(150).width(600);



        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameApp.username =  username.getText();
            }
        });

        connection.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (GameApp.c2sConnectionThread != null) {
                        receivedMessage.setText("You Can't have more than one connection.");
                    } else {
                        Socket socket = new Socket("localhost", 5000);
                        GameApp.c2sConnectionThread = new C2SConnectionThread(socket);
                        GameApp.c2sConnectionThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        sentMessage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (GameApp.c2sConnectionThread != null) {

                    }
                } catch (Exception ignored) {}
            }
        });

        stage.addActor(table);
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

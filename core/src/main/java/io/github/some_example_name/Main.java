package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.model.C2SConnectionThread;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.AddLobbyMenuView;
import io.github.some_example_name.view.OnlinePlayersView;
import io.github.some_example_name.view.SignUpMenuView;

import java.io.IOException;
import java.net.Socket;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        // Starting Connecting to Server
        Socket socket;
        try {
            socket = new Socket("localhost", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            GameApp.c2sConnectionThread = new C2SConnectionThread(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameApp.c2sConnectionThread.start();


        getMain().setScreen(new SignUpMenuView(GameAssetManager.getGameAssetManager().getSkin()));
//        getMain().setScreen(new AddLobbyMenuView(GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (GameApp.c2sConnectionThread != null) GameApp.c2sConnectionThread.end();
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}

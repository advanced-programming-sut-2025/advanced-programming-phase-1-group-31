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
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

public class LobbyMenuView implements Screen {
    private final Stage stage;
    private final Table table;

    // Elements
    private final Label title;
    private final TextButton allPlayers;
    private final TextButton allLobbies;
    private final TextButton createLobby;
    private final TextButton logout;


    public LobbyMenuView(Skin skin) {
        table = new Table(skin);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();
        title = new Label("Lobby Menu", skin);
        allPlayers = new TextButton("All Players", skin);
        allLobbies = new TextButton("All Lobbies", skin);
        createLobby = new TextButton("Create Lobby", skin);
        logout = new TextButton("Logout", skin);

    }

    @Override
    public void show() {

        table.add(title).pad(10);
        table.row();
        table.add(allPlayers).pad(10);
        table.row();
        table.add(allLobbies).pad(10);
        table.row();
        table.add(createLobby).pad(10);
        table.row();
        table.add(logout).pad(10);

        stage.addActor(table);

        logout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameApp.player = null;
                Main.getMain().setScreen(new SignUpMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
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

package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;

import java.util.HashMap;
import java.util.Map;

public class OnlinePlayersView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Table root;
    private Table playersTable;
    private long Time = System.currentTimeMillis();

    public OnlinePlayersView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Root table
        root = new Table();
        root.setFillParent(true);
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
        Label title = new Label("All Players", skin);
        title.setFontScale(1.8f);

        header.add(title).expandX().center();
        header.row();
        header.add(back).width(200).height(80).center();

        root.add(header).fillX().padBottom(20).row();

        // Players table inside scroll pane
        playersTable = new Table();
        playersTable.top().pad(5);
        playersTable.defaults().pad(8).fillX();

        ScrollPane scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scroll only

        root.add(scrollPane).expand().fill().pad(10).row();

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        loadPlayers();
    }

    private void loadPlayers() {
        Message message = GameApp.c2sConnectionThread
            .sendAndWaitForResponse(new Message(new HashMap<>(), Message.Type.All_Players));
        Map<?, ?> rawMap = message.getFromBody("players", Map.class);

        playersTable.clearChildren();
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (entry.getKey() instanceof String && entry.getValue() instanceof Boolean) {
                playersTable.add(createPlayerCard((String) entry.getKey(), (Boolean) entry.getValue()));
                playersTable.row();
            }
        }
    }

    private Table createPlayerCard(String username, boolean isOnline) {
        Table card = new Table(skin);
        card.pad(12);

        boolean isThisUser = GameApp.player.getUsername().equals(username);
        // Status dot
        Label statusDot = new Label("o", skin);
        statusDot.setFontScale(2f);
        statusDot.setColor(isOnline ? Color.GREEN : Color.RED);

        // Name label
        String thisUser = isThisUser ? " *" : "";
        Label nameLabel = new Label(username + thisUser, skin);
        nameLabel.setColor(isOnline ? Color.GREEN : Color.RED);
        if (isThisUser) {
            nameLabel.setColor(Color.FOREST);
            statusDot.setColor(Color.FOREST);
        }
        nameLabel.setFontScale(1.5f);

        card.add(statusDot).center().padRight(40);
        card.add(nameLabel).center().expandX();

        return card;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("2b2b2b"));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (System.currentTimeMillis() - Time > 3000) {
            loadPlayers();
            Time = System.currentTimeMillis();
        }
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

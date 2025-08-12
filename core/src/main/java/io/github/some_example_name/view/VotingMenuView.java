package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;

import java.util.HashMap;

public class VotingMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Table table;

    public VotingMenuView(Skin skin, Boolean isTerminated, String username) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table(skin);
        table.setFillParent(true);
        table.center();
        table.getColor().a = 0f;
        stage.addActor(table);

        if (isTerminated) {
            terminateVote();
        } else {
            sendOutVote(username);
        }
    }

    public void terminateVote() {
        table.clear();

        Label title = new Label("Terminate Game", skin);
        title.setFontScale(1.5f);
        title.setColor(Color.RED);
        Label msg = new Label("Are you sure you want to\nterminate the game for everyone?", skin);
        msg.setFontScale(1.5f);

        TextButton yes = new TextButton("Yes", skin);
        TextButton no = new TextButton("No", skin);

        yes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("vote", true);
                GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Terminate_Game_Request));
                Main.getMain().setScreen(new WaitMenuView(skin));
            }
        });

        no.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("vote", false);
                GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Terminate_Game_Request));
                Main.getMain().setScreen(new WaitMenuView(skin));
            }
        });

        table.pad(18f);
        table.add(title).colspan(2).center().pad(15).row();
        table.add(msg).width(420f).colspan(2).pad(15).row();
        table.add(yes).width(160f).pad(15);
        table.add(no).width(160f).pad(15);

        table.addAction(Actions.fadeIn(0.18f));
    }


    public void sendOutVote(String username) {
        table.clear();

        String safeName = username == null ? "player" : username;
        Label title = new Label("Vote to Kick", skin);
        title.setColor(Color.RED);
        title.setFontScale(1.5f);
        Label msg = new Label("Do you want to remove\n \"" + safeName + "\" from the game?", skin);
        msg.setFontScale(1.5f);

        TextButton yes = new TextButton("Yes", skin);
        TextButton no = new TextButton("No", skin);

        yes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("vote", true);
                body.put("username", username);
                GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Send_Somebody_Out));
                Main.getMain().setScreen(new WaitMenuView(skin));
            }
        });

        no.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("vote", false);
                body.put("username", username);
                GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Send_Somebody_Out));
                Main.getMain().setScreen(new WaitMenuView(skin));
            }
        });

        table.pad(18f);
        table.add(title).colspan(2).center().pad(15).row();
        table.add(msg).colspan(2).pad(15).row();
        table.add(yes).width(160f).pad(15);
        table.add(no).width(160f).pad(15);

        table.addAction(Actions.fadeIn(0.18f));
    }

    @Override
    public void show() {
        // No-op
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#FAA25A"));
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

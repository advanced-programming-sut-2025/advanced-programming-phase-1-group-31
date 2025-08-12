package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class VoteMenuView implements Screen {
    private final Stage stage;
    private final Table table;
    private final Skin skin;

    // Elements
    private final TextButton gameTerminate;
    private final TextButton sendSomebodyOut;
    private final TextButton back;

    private final Runnable onTerminateConfirmed = () -> {
        GameApp.c2sConnectionThread.sendMessage(new Message(new HashMap<>(), Message.Type.Terminate_Game_Request));
    };
    private final Consumer<String> onSendOutSelected = (s) -> {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", s);
        GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Send_Somebody_Out));

    };

    public VoteMenuView(Skin skin) {
        this.skin = skin;
        table = new Table(skin);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        back = new TextButton("Back", skin);
        gameTerminate = new TextButton("Terminate Game", skin);
        sendSomebodyOut = new TextButton("Send Somebody Out", skin);

        addListeners();
    }

    private void addListeners() {
        gameTerminate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog confirm = new Dialog("Confirm Termination", skin) {
                    @Override
                    public void result(Object object) {
                        if (Boolean.TRUE.equals(object)) {
                            onTerminateConfirmed.run();
                        }
                    }
                };
                confirm.text("Are you sure you want to terminate the game?");
                confirm.button("Yes", true);
                confirm.button("No", false);
                confirm.key(Input.Keys.ENTER, true);
                confirm.key(Input.Keys.ESCAPE, false);
                confirm.setWidth(1000);
                confirm.getTitleLabel().setAlignment(Align.center);
                confirm.setHeight(1000);
                confirm.show(stage);
            }
        });

        sendSomebodyOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                List<String> otherPlayers = getOtherPlayers();
                if (otherPlayers.isEmpty()) {
                    Dialog info = new Dialog("Info", skin);
                    info.text("No other players in the lobby.");
                    info.button("OK", true);
                    info.setWidth(1000);
                    info.getTitleLabel().setAlignment(Align.center);
                    info.setHeight(1000);
                    info.show(stage);
                    return;
                }

                final SelectBox<String> selectBox = new SelectBox<>(skin);
                selectBox.setItems(otherPlayers.toArray(new String[0]));

                Dialog chooseDialog = new Dialog("Choose player to send out", skin) {
                    @Override
                    public void result(Object object) {
                        if (Boolean.TRUE.equals(object)) {
                            String player = selectBox.getSelected();
                            if (player != null && !player.isEmpty()) {
                                onSendOutSelected.accept(player);
                            }
                        }
                    }
                };

                chooseDialog.getContentTable().pad(12);
                chooseDialog.getContentTable().add(new Label("Select player:", skin)).left().row();
                chooseDialog.getContentTable().add(selectBox).width(400).padTop(8).row();
                chooseDialog.button("OK", true).pad(10);
                chooseDialog.button("Cancel", false).pad(10);
                chooseDialog.key(Input.Keys.ENTER, true);
                chooseDialog.key(Input.Keys.ESCAPE, false);
                chooseDialog.getTitleLabel().setAlignment(Align.center);
                chooseDialog.setWidth(1000);
                chooseDialog.setHeight(1000);
                chooseDialog.show(stage);
            }
        });

        // Back
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(GameApp.getGameView());
            }
        });
    }

    private List<String> getOtherPlayers() {
        List<String> all = new ArrayList<>();
        try {
            all.addAll(GameApp.player.getLobby().getPlayers());
        } catch (Exception ignored) {
        }
        String me = "";
        try {
            me = GameApp.player.getUsername();
        } catch (Exception ignored) {
        }

        List<String> others = new ArrayList<>();
        for (String p : all) {
            if (!p.equals(me)) others.add(p);
        }
        return others;
    }

    @Override
    public void show() {
        table.clear();
        table.add(gameTerminate).width(400).height(120).pad(10);
        table.row();
        table.add(sendSomebodyOut).width(400).height(120).pad(10);
        table.row();
        table.add(back).width(400).height(120).pad(10);

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

package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WaitMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Table table;

    public WaitMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table(skin);
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
        startWaiting();
    }

    public void startWaiting() {
        table.clear();

        Table waitingTable = new Table(skin);
        waitingTable.setFillParent(true);
        waitingTable.center();

        Texture spinnerTexture = new Texture(Gdx.files.internal("spinner.png"));
        Image loadingSpinner = new Image(spinnerTexture);
        loadingSpinner.setSize(100, 100);
        waitingTable.add(loadingSpinner).padBottom(30).row();

        Label waitingLabel = new Label("Other Players Are Voting...", skin);
        waitingLabel.setFontScale(2.0f);
        waitingLabel.setColor(Color.WHITE);
        waitingTable.add(waitingLabel);

        loadingSpinner.addAction(Actions.forever
            (Actions.sequence
                (Actions.moveBy(30f, 0, 1.5f),
                    Actions.moveBy(-30f, 0, 1.5f))));

        stage.clear();
        stage.addActor(waitingTable);

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

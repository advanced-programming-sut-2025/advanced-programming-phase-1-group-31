package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.MapManager;
import io.github.some_example_name.model.MapType;

public class FarmView extends GameView{
    public FarmView(GameController gameController, Skin skin , MapType mapType ) {
        super(gameController, skin , mapType );
        App.getCurrentGame().getTimeAndDate().setRainEffect(App.getCurrentGame().getTimeAndDate().loadEffectsForFullMap(getMap(), App.getCurrentGame().getTimeAndDate().getWeather().getEffectName(), 0, 0));
        App.getCurrentGame().getTimeAndDate().updateWeatherEffect();
    }
    @Override
    public void render(float delta) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        getGameController().handleInput(delta);
        getGameController().checkWarpsAndSpecialAreas(delta);

        refreshItemTable();

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getCamera().update();

        getMapRenderer().setView(getCamera());
        getMapRenderer().render();

        getBatch().setProjectionMatrix(getMapRenderer().getBatch().getProjectionMatrix());
        getBatch().begin();
        App.getCurrentGame().getTimeAndDate().renderEffect(getBatch() ,delta);
        if (getGameController().getToolController() != null) {
            getGameController().getToolController().update(getBatch());
        }
        getBuildModeHandler().render(getBatch() , getCamera());
        getBatch().end();
        getBatch().setProjectionMatrix(getHudCamera().combined);
        getBatch().begin();
        App.getCurrentGame().getTimeAndDate().render(getBatch());
        renderEnergyBar(getBatch(), App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount(), App.getCurrentGame().getActivePlayer().getEnergy().getMaxEnergy());


        getBatch().end();

        if (!isShowFullMap()) {
//            inventoryUI.update();
            getStage().act(delta);
            getStage().draw();
        }
        rayHandler.setCombinedMatrix(getCamera());
        updateAmbientLight();
        rayHandler.updateAndRender();
    }


    @Override
    public MapType getMapType() {
        return mapType;
    }
}

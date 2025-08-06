package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.control.GameController;
import io.github.some_example_name.model.MapType;

public class MineView extends GameView {

    public MineView(GameController gameController, Skin skin) {
        super(gameController, skin , MapType.MINE);
        int mapWidth = getMap().getProperties().get("width", Integer.class);
        int mapHeight = getMap().getProperties().get("height", Integer.class);
        int tileWidth = getMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = getMap().getProperties().get("tileheight", Integer.class);
        getCamera().setToOrtho(false, mapWidth*tileWidth, mapHeight*tileHeight);
    }

    @Override
    public MapType getMapType() {
        return MapType.MINE;
    }
}

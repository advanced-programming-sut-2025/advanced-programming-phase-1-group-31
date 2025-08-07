package io.github.some_example_name.View;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.MapType;

public class MineView extends GameView {

    public MineView(GameController gameController, Skin skin) {
        super(gameController, skin , MapType.MINE);
    }

    @Override
    public MapType getMapType() {
        return MapType.MINE;
    }
}

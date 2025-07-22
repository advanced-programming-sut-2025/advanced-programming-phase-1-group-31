package io.github.some_example_name.View;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.MapType;

public class HouseView extends GameView {
    public HouseView(GameController gameController, Skin skin) {
        super(gameController, skin , MapType.HOUSE);
    }

    @Override
    public MapType getMapType() {
        return MapType.HOUSE;
    }
}

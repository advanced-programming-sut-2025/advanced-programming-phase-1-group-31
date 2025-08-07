package io.github.some_example_name.View;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.MapType;

public class GreenHouseView extends GameView {

    public GreenHouseView(GameController gameController, Skin skin) {
        super(gameController, skin, MapType.GREENHOUSE);
    }

    @Override
    public MapType getMapType() {
        return MapType.GREENHOUSE;
    }
}

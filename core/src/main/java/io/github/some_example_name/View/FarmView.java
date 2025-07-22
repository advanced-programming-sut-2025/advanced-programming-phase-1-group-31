package io.github.some_example_name.View;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.MapManager;
import io.github.some_example_name.model.MapType;

public class FarmView extends GameView{
    public FarmView(GameController gameController, Skin skin , MapType mapType ) {
        super(gameController, skin , mapType );
    }

    @Override
    public MapType getMapType() {
        return mapType;
    }
}

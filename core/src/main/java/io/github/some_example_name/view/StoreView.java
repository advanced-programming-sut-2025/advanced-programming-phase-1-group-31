package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.control.GameController;
import io.github.some_example_name.model.MapType;

public class StoreView extends GameView{

    public StoreView(GameController gameController, Skin skin , MapType mapType) {
        super(gameController, skin ,mapType);
    }

    @Override
    public MapType getMapType() {
        return mapType ;
    }

}

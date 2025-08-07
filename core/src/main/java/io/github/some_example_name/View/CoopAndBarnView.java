package io.github.some_example_name.View;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.Control.CoopAndBarnController;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.MapType;
import io.github.some_example_name.model.materials.Coop;
import io.github.some_example_name.model.materials.Material;

import javax.swing.text.View;

public class CoopAndBarnView extends GameView {

    private Material barnOrCoop;

    public Material getBarnOrCoop() {
        return barnOrCoop;
    }

    public void setBarnOrCoop(Material barnOrCoop) {
        this.barnOrCoop = barnOrCoop;
    }

    public CoopAndBarnView(GameController gameController, Skin skin, MapType mapType , Material barnOrCoop) {
        super(gameController, skin, mapType);
        setBarnOrCoop(barnOrCoop);
        CoopAndBarnController coopAndBarnController = (CoopAndBarnController) gameController;
        coopAndBarnController.setAnimal();
    }


    @Override
    public MapType getMapType() {
        return mapType;
    }
}

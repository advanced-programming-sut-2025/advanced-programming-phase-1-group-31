package io.github.some_example_name.Control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.materials.Material;

public class ToolController {
    private Material tool;
    Sprite sprite;


    public ToolController(Material tool) {
        this.tool = tool;
        Texture texture = new Texture(tool.getTexturePath());
        sprite = new Sprite(texture);
    }

    public void update(Batch batch) {
        sprite.setSize(16 , 16);

        sprite.setPosition((App.getCurrentGame().getActivePlayer().getPlace().x ), (App.getCurrentGame().getActivePlayer().getPlace().y ));

        sprite.draw(batch);

    }

    public void handleToolRotation(float x, float y) {
        sprite.setOriginCenter();

        float weaponCenterX = App.getCurrentGame().getActivePlayer().getPlace().x + sprite.getWidth() / 2;
        float weaponCenterY = App.getCurrentGame().getActivePlayer().getPlace().y + sprite.getHeight() / 2;

        float angleRad = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);
        float angleDeg = angleRad * MathUtils.radiansToDegrees;

        sprite.setRotation(angleDeg);
    }


}

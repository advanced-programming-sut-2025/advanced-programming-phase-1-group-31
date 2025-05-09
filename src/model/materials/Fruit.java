package model.materials;

import model.enums.plantable.Fruits;
import model.materials.Tools.MaterialType;

public class Fruit implements Material {
    private Fruits fruit;
    private int quantity;

    // quantity is the number of the item in the backpack

    @Override
    public MaterialType getType() {
        return fruit;
    }

}

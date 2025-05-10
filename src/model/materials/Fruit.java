package model.materials;

import model.enums.plantable.Fruits;

public class Fruit implements Material {
    private Fruits fruit;
    private int quantity;

    public Fruits getFruit() {
        return fruit;
    }

    public void setFruit(Fruits fruit) {
        this.fruit = fruit;
    }

    // quantity is the number of the item in the backpack

    @Override
    public MaterialType getType() {
        return fruit;
    }

}

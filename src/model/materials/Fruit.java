package model.materials;

import model.enums.plantable.Fruits;

public class Fruit implements Material {
    private Fruits fruit;

    public Fruit(Fruits fruit) {
        this.fruit = fruit;
    }

    public void setFruit(Fruits fruit) {
        this.fruit = fruit;
    }


    @Override
    public MaterialType getType() {
        return null;
    }
}

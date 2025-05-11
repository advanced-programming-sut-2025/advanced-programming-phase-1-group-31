package model.materials;

import model.enums.plantable.Fruits;

public class Fruit implements Material {
    private Fruits fruit;

    public Fruit(Fruits fruit) {
        this.fruit = fruit;
    }

    public Fruits getFruit() {
        return fruit;
    }

    public void setFruit(Fruits fruit) {
        this.fruit = fruit;
    }

    // quantity is the number of the item in the backpack



}

package io.github.some_example_name.model.materials;


import io.github.some_example_name.model.enums.plantable.Fruits;

import java.util.Objects;

public class Fruit implements Material {
    private Fruits fruit;
    private int quantity;

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

    @Override
    public MaterialType getType() {
        return fruit;
    }

    @Override
    public String getName() {
        return fruit.getName();
    }

    @Override
    public int baseSellPrice() {
        return fruit.getBaseSellPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}

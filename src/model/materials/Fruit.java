package model.materials;

import model.enums.plantable.Fruits;

import java.util.Objects;

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
        return fruit;
    }

    @Override
    public String getName() {
        return fruit.getName();
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

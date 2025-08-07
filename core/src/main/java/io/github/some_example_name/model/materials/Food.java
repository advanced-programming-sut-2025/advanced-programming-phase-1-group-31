package io.github.some_example_name.model.materials;


import io.github.some_example_name.model.enums.crafting.Foods;

import java.util.Objects;

public class Food implements Material {
    private final Foods foodType;

    public Food(Foods foodType) {
        this.foodType = foodType;
    }

    @Override
    public MaterialType getType() {
        return foodType;
    }

    @Override
    public String getTexturePath() {
        return "";
    }

    @Override
    public String getName() {
        return foodType.getName();
    }

    @Override
    public int baseSellPrice() {
        return foodType.getPrice();
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

    public int getEnergy() {
        return foodType.getEnergy();
    }
}


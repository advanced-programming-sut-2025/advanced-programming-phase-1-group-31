package model.materials;

import model.enums.crafting.Foods;
import model.materials.Material;

import java.util.Objects;

public class Food implements Material {
    private Foods foodType;


    @Override
    public MaterialType getType() {
        return foodType;
    }

    @Override
    public String getName() {
        return foodType.name();
    }

    @Override
    public int baseSellPrice() {
        return foodType.getBasePrice();
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

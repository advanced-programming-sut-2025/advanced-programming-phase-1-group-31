package model.materials.Products;

import model.enums.creature.FishTypes;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class FishProduct implements Material {
    private FishTypes fishTypes;

    public void setFishTypes(FishTypes fishTypes) {
        this.fishTypes = fishTypes;
    }

    @Override
    public MaterialType getType() {
        return fishTypes;
    }

    @Override
    public String getName() {
        return fishTypes.name();
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

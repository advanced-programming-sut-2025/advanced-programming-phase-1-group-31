package io.github.some_example_name.model.materials.Foraging;



import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Objects;

public class ForagingCrop implements Material {
    private final ForagingCrops foragingCrop;

    public ForagingCrop(ForagingCrops foragingCrop) {
        this.foragingCrop = foragingCrop;
    }

    public ForagingCrops getForagingCrop() {
        return foragingCrop;
    }

    @Override
    public MaterialType getType() {
        return foragingCrop;
    }

    @Override
    public String getName() {
        return foragingCrop.getDisplayName();
    }

    @Override
    public int baseSellPrice() {
        return foragingCrop.getBaseSellPrice();
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
    @Override
    public String getTexturePath() {
        return foragingCrop.getImagePath();
    }
}

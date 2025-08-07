package io.github.some_example_name.model.materials.Foraging;

import io.github.some_example_name.model.enums.foragings.ForagingSeeds;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Objects;

public class ForagingSeed implements Material {
    private final ForagingSeeds foragingSeed;

    public ForagingSeed(ForagingSeeds foragingSeed) {
        this.foragingSeed = foragingSeed;
    }

    @Override
    public MaterialType getType() {
        return foragingSeed;
    }

    @Override
    public String getName() {
        return foragingSeed.getDisplayName();
    }

    public ForagingSeeds getForagingSeed() {
        return foragingSeed;
    }

    @Override
    public int baseSellPrice() {
        return 0;
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
        return "";
    }
}

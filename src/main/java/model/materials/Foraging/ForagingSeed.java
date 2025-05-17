package model.materials.Foraging;

import model.enums.foragings.ForagingSeeds;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class ForagingSeed implements Material {
    private ForagingSeeds foragingSeed;

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

    public void setForagingSeed(ForagingSeeds foragingSeed) {
        this.foragingSeed = foragingSeed;
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
}

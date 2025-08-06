package io.github.some_example_name.model.materials.Foraging;



import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Objects;

public class ForagingMineral implements Material {
    private final ForagingMinerals foragingMineral;

    public ForagingMineral(ForagingMinerals foragingMineral) {
        this.foragingMineral = foragingMineral;
    }

    public ForagingMinerals getForagingMineral() {
        return foragingMineral;
    }

    @Override
    public MaterialType getType() {
        return foragingMineral;
    }

    @Override
    public String getName() {
        return foragingMineral.getDisplayName();
    }

    @Override
    public int baseSellPrice() {
        return foragingMineral.getSellPrice();
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

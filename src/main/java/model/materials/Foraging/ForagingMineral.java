package model.materials.Foraging;

import model.enums.foragings.ForagingMinerals;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class ForagingMineral implements Material {
    private ForagingMinerals foragingMineral;

    public ForagingMineral(ForagingMinerals foragingMineral) {
        this.foragingMineral = foragingMineral;
    }

    public ForagingMinerals getForagingMineral() {
        return foragingMineral;
    }

    public void setForagingMineral(ForagingMinerals foragingMineral) {
        this.foragingMineral = foragingMineral;
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
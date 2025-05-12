package model.materials.Foraging;

import model.enums.foragings.ForagingCrops;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class ForagingCrop implements Material {
    private ForagingCrops foragingCrop;

    public ForagingCrops getForagingCrop() {
        return foragingCrop;
    }

    public void setForagingCrop(ForagingCrops foragingCrop) {
        this.foragingCrop = foragingCrop;
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

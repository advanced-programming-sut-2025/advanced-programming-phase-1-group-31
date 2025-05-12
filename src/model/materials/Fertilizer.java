package model.materials;

import model.enums.plantable.Fertilizers;

import java.util.Objects;

public class Fertilizer implements Material {
    private Fertilizers fertilizerType;

    @Override
    public MaterialType getType() {
        return fertilizerType;
    }

    @Override
    public String getName() {
        return fertilizerType.name();
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

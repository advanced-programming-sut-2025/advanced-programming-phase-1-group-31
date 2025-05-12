package model.materials;

import model.enums.creature.CoopsAndBarnsTypes;

import java.util.Objects;

public class Coop implements Material {
    private CoopsAndBarnsTypes coopType;
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
    public MaterialType getType() {
        return coopType;
    }

    @Override
    public String getName() {
        return coopType.getName();
    }
}

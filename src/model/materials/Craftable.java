package model.materials;

import model.enums.crafting.Craftables;

import java.util.Objects;

public class Craftable implements Material {
    private Craftables craftableType;
    private final int energyConsumption = 2;


    @Override
    public Craftables getType() {
        return craftableType;
    }

    @Override
    public String getName() {
        return craftableType.name();
    }

    @Override
    public int baseSellPrice() {
        return craftableType.getBasePrice();
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

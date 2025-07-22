package io.github.some_example_name.model.materials;


import io.github.some_example_name.model.enums.crafting.Craftables;

import java.util.Objects;

public class Craftable implements Material {
    private final Craftables craftableType;

    public Craftable(Craftables craftableType) {
        this.craftableType = craftableType;
    }


    @Override
    public Craftables getType() {
        return craftableType;
    }

    @Override
    public String getName() {
        return craftableType.getName();
    }

    @Override
    public int baseSellPrice() {
        return craftableType.getPrice();
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

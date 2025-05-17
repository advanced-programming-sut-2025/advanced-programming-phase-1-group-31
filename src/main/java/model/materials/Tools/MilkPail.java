package model.materials.Tools;

import model.Result;
import model.enums.general.Direction;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class MilkPail implements Tool, Material {
    private int energyConsumption = 4;

    @Override
    public Result work(Direction direction) {
        return null;

    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public String getName() {
        return "Milk Pail";
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool tool)) return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
package model.materials.Tools;

import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.WateringCanType;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class WateringCan implements Tool, Material {
    private WateringCanType wateringCanType;

    public WateringCan(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }


    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }

    @Override
    public Result work(Direction direction) {
        return null;

    }

    @Override
    public MaterialType getType() {
        return wateringCanType;
    }

    @Override
    public String getName() {
        return "Watering Can " + wateringCanType.name();
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
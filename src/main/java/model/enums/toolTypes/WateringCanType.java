package model.enums.toolTypes;

import model.materials.MaterialType;

public enum

WateringCanType implements MaterialType {
    Initial(40, 5),
    Copper(55, 4),
    Iron(70, 3),
    Gold(85, 2),
    Iridium(100, 1);

    private final int capacity;
    private final int energyConsumption;

    WateringCanType(int capacity, int energyConsumption) {
        this.capacity = capacity;
        this.energyConsumption = energyConsumption;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }
}
package model.enums.toolTypes;

import model.materials.Tools.MaterialType;

public enum TrashCanType implements MaterialType {
    Initial(0),
    Copper(15),
    Iron(30),
    Gold(45),
    Iridium(60);

    private final int moneyReturnedPercentage;

    TrashCanType(int moneyReturnedPercentage) {
        this.moneyReturnedPercentage = moneyReturnedPercentage;
    }

    public int getEnergyConsumption() {
        return moneyReturnedPercentage;
    }
}

package io.github.some_example_name.model.enums.toolTypes;


import io.github.some_example_name.model.materials.MaterialType;

public enum FishingPoleType implements MaterialType {
    Training(8, false),
    Bamboo(8, true),
    Fiberglass(6, true),
    Iridium(4, true);

    private final int energyConsumption;
    private final Boolean canCatchAllFishes;

    FishingPoleType(int energyConsumption, Boolean canCatchAllFishes) {
        this.energyConsumption = energyConsumption;
        this.canCatchAllFishes = canCatchAllFishes;
    }

    public Boolean isCanCatchAllFishes() {
        return canCatchAllFishes;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }
}

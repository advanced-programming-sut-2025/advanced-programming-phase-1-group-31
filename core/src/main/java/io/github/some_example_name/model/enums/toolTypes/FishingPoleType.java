package io.github.some_example_name.model.enums.toolTypes;


import io.github.some_example_name.model.materials.MaterialType;

public enum FishingPoleType implements MaterialType {
    Training(8, false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fishing_Pole/Training_Rod.png"),
    Bamboo(8, true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fishing_Pole/Bamboo_Pole.png"),
    Fiberglass(6, true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fishing_Pole/Fiberglass_Rod.png"),
    Iridium(4, true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fishing_Pole/Iridium_Rod.png");

    private final int energyConsumption;
    private final boolean canCatchAllFishes;
    private final String fishingPoleName;

    FishingPoleType(int energyConsumption, boolean canCatchAllFishes, String fishingPoleName) {
        this.energyConsumption = energyConsumption;
        this.canCatchAllFishes = canCatchAllFishes;
        this.fishingPoleName = fishingPoleName;
    }

    public boolean isCanCatchAllFishes() {
        return canCatchAllFishes;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public String getFishingPoleName() {
        return fishingPoleName;
    }
}

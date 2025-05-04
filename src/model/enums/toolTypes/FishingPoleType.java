package model.enums.toolTypes;

public enum FishingPoleType {
    Training(8, false),
    Bamboo(8, true),
    Fiberglass(6, true),
    Iridium(4, true);

    private final int energyConsumption;
    private final boolean canCatchAllFishes;

    FishingPoleType(int energyConsumption, boolean canCatchAllFishes) {
        this.energyConsumption = energyConsumption;
        this.canCatchAllFishes = canCatchAllFishes;
    }

    public boolean isCanCatchAllFishes() {
        return canCatchAllFishes;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }
}

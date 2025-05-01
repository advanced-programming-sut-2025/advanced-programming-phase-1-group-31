package model.enums;

public enum ToolType {
    Initial(),
    Initial(),
    Initial(),
    Initial();




    private final int backpackCapacity;
    private final int waterCanCapacity;
    private final int fishingEnergyConsumption;
    private final int axeHoePickEnergyConsumption;
    private final int moneyReturnedPercentage;
    private final boolean canCatchAllFishes;




    ToolType(int fishingEnergyConsumption,
             int axeHoePickEnergyConsumption,
             int waterCanCapacity,
             int backpackCapacity,
             int moneyReturnedPercentage,
             boolean canCatchAllFishes) {
        this.moneyReturnedPercentage = moneyReturnedPercentage;
        this.axeHoePickEnergyConsumption = axeHoePickEnergyConsumption;
        this.fishingEnergyConsumption = fishingEnergyConsumption;
        this.waterCanCapacity = waterCanCapacity;
        this.backpackCapacity = backpackCapacity;
        this.canCatchAllFishes = canCatchAllFishes;
    }



    public int getMoneyReturnedPercentage() {
        return moneyReturnedPercentage;
    }

    public int getFishingEnergyConsumption() {
        return fishingEnergyConsumption;
    }

    public int getAxeHoePickEnergyConsumption() {
        return axeHoePickEnergyConsumption;
    }

    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    public int getWaterCanCapacity() {
        return waterCanCapacity;
    }

    public boolean isCanCatchAllFishes() {
        return canCatchAllFishes;
    }

    abstract void work();
}

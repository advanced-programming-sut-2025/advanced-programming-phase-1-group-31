package model.enums.toolTypes;

public enum AxePickHoeType {
    Initial(5),
    Copper(4),
    Iron(3),
    Gold(2),
    Iridium(1);

    private final int energyConsumption;

    AxePickHoeType(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }
}

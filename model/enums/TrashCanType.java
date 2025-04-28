package model.enums;

public enum TrashCanType {
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

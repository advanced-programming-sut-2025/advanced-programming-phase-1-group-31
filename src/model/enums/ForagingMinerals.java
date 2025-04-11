package model.enums;

public enum ForagingMinerals {
    //wood alongside all the other minerals
    ;

    private final String name;
    private final String description;
    private final int sellPrice;

    ForagingMinerals(String name, String description, int sellPrice) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
    }
}

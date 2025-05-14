package model.enums.plantable;

import model.materials.MaterialType;

public enum Fruits implements MaterialType {
    Apricot("Apricot", 50, true, 38),
    Cherry("Cherry", 80, true, 38),
    Banana("Banana", 150, true, 75),
    Mango("Mango", 130, true, 100),
    Orange("Orange", 100, true, 38),
    Peach("Peach", 140, true, 38),
    Apple("Apple", 100, true, 38),
    Pomegranate("Pomegranate", 140, true, 38),
    OakResin("Oak Resin", 150, false, null),
    MapleSyrup("Maple Syrup", 200, false, null),
    PineTar("Pine Tar", 100, false, null),
    Sap("Sap", 2, true, null),
    CommonMushroom("Common Mushroom", 40, true, 38),
    MysticSyrup("Mystic Syrup", 1000, true, 500);

    private final String name;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final Integer energy;

    Fruits(String name, int baseSellPrice, boolean isEdible, Integer energy) {
        this.name = name;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public Integer getEnergy() {
        return energy;
    }
}

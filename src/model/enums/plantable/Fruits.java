package model.enums.plantable;

import model.materials.MaterialType;

public enum Fruits implements MaterialType {
    Apricot("Apricot"),
    Cherry("Cherry"),
    Banana("Banana"),
    Mango("Mango"),
    Orange("Orange"),
    Peach("Peach"),
    Apple("Apple"),
    Pomegranate("Pomegranate"),
    OakResin("Oak Resin"),
    MapleSyrup("Maple Syrup"),
    PineTar("Pine Tar"),
    Sap("Sap"),
    CommonMushroom("Common Mushroom"),
    MysticSyrup("Mystic Syrup");

    private final String name;

    Fruits(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

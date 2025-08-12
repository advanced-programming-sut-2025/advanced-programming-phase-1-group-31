package io.github.some_example_name.model.enums.plantable;



import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.List;

public enum Fruits implements MaterialType {
    Apricot("Apricot", 50, true, 38, List.of(Seasons.Spring)),
    Cherry("Cherry", 80, true, 38, List.of(Seasons.Spring)),
    Banana("Banana", 150, true, 75, List.of(Seasons.Summer)),
    Mango("Mango", 130, true, 100, List.of(Seasons.Summer)),
    Orange("Orange", 100, true, 38, List.of(Seasons.Summer)),
    Peach("Peach", 140, true, 38, List.of(Seasons.Summer)),
    Apple("Apple", 100, true, 38, List.of(Seasons.Fall)),
    Pomegranate("Pomegranate", 140, true, 38, List.of(Seasons.Fall)),
    OakResin("Oak Resin", 150, false, null, List.of(Seasons.values())),
    MapleSyrup("Maple Syrup", 200, false, null, List.of(Seasons.values())),
    PineTar("Pine Tar", 100, false, null, List.of(Seasons.values())),
    Sap("Sap", 2, true, null, List.of(Seasons.values())),
    CommonMushroom("Common Mushroom", 40, true, 38, List.of(Seasons.values())),
    MysticSyrup("Mystic Syrup", 1000, true, 500, List.of(Seasons.values()));

    private final String name;
    private final int baseSellPrice;
    private final Boolean isEdible;
    private final Integer energy;
    private final List<Seasons> harvestSeasons;

    Fruits(String name, int baseSellPrice, Boolean isEdible, Integer energy, List<Seasons> harvestSeasons) {
        this.name = name;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.harvestSeasons = harvestSeasons;
    }

    public String getName() {
        return name;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public Boolean isEdible() {
        return isEdible;
    }

    public Integer getEnergy() {
        return energy;
    }

    public List<Seasons> getHarvestSeasons() {
        return harvestSeasons;
    }

    public static Fruits findByName(String name) {
        for (Fruits fruit : Fruits.values()) {
            if (fruit.getName().equalsIgnoreCase(name)) {
                return fruit;
            }
        }
        return null;
    }

}

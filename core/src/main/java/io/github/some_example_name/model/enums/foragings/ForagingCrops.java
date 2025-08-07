package io.github.some_example_name.model.enums.foragings;



import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public enum ForagingCrops implements MaterialType {
    CommonMushroom("Common Mushroom", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter),
            40, 38, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Common_Mushroom.png"),
    Daffodil("Daffodil", List.of(Seasons.Spring), 30, 0, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Daffodil.png"),
    Dandelion("Dandelion", List.of(Seasons.Spring), 40, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Dandelion.png"),
    Leek("Leek", List.of(Seasons.Spring), 60, 40, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Leek.png"),
    Morel("Morel", List.of(Seasons.Spring), 150, 20, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Morel.png"),
    Salmonberry("Salmonberry", List.of(Seasons.Spring), 5, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Salmonberry.png"),
    SpringOnion("Spring Onion", List.of(Seasons.Spring), 8, 13, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Spring_Onion.png"),
    WildHorseradish("Wild Horseradish", List.of(Seasons.Spring), 50, 13, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Wild_Horseradish.png"),
    FiddleheadFern("Fiddlehead Fern", List.of(Seasons.Summer), 90, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Fiddlehead_Fern.png"),
    Grape("Grape", List.of(Seasons.Summer), 80, 38, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Grape.png"),
    RedMushroom("Red Mushroom", List.of(Seasons.Summer), 75, -50, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Red_Mushroom.png"),
    SpiceBerry("Spice Berry", List.of(Seasons.Summer), 80, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Spice_Berry.png"),
    SweetPea("Sweet Pea", List.of(Seasons.Summer), 50, 0, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Sweet_Pea.png"),
    Blackberry("Blackberry", List.of(Seasons.Fall), 25, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Blackberry.png"),
    Chanterelle("Chanterelle", List.of(Seasons.Fall), 160, 75, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Chanterelle.png"),
    Hazelnut("Hazelnut", List.of(Seasons.Fall), 40, 38, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Hazelnut.png"),
    PurpleMushroom("Purple Mushroom", List.of(Seasons.Fall), 90, 30, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Purple_Mushroom.png"),
    WildPlum("Wild Plum", List.of(Seasons.Fall), 80, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Wild_Plum.png"),
    Crocus("Crocus", List.of(Seasons.Winter), 60, 0, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Crocus.png"),
    CrystalFruit("Crystal Fruit", List.of(Seasons.Winter), 150, 63, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Crystal_Fruit.png"),
    Holly("Holly", List.of(Seasons.Winter), 80, -37, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Holly.png"),
    SnowYam("Snow Yam", List.of(Seasons.Winter), 100, 30, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Snow_Yam.png"),
    Hey("Hey (For Animals)", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), 10, 1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/5x_14.png"),
    WinterRoot("Winter Root", List.of(Seasons.Winter), 70, 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Foraging/Winter_Root.png");

    private final String displayName;
    private final List<Seasons> seasons;
    private final int baseSellPrice;
    private final int energy;
    private final String imagePath;

    ForagingCrops(String displayName, List<Seasons> seasons, int baseSellPrice, int energy, String imagePath) {
        this.displayName = displayName;
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
        this.imagePath = imagePath;
    }

    public static ForagingCrops getRandomBySeason(Seasons season) {
        List<ForagingCrops> filtered = new ArrayList<>();
        for (ForagingCrops crop : values()) {
            if (crop.getSeasons().contains(season)) {
                filtered.add(crop);
            }
        }
        if (filtered.isEmpty()) return null;
        return filtered.get(ThreadLocalRandom.current().nextInt(filtered.size()));
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return displayName;
    }

    private static final Map<String, ForagingCrops> nameCrop = new HashMap<>();

    static {
        for (ForagingCrops crop : values()) {
            nameCrop.put(crop.getDisplayName().toLowerCase(), crop);
        }
    }

    public static ForagingCrops findByName(String name) {
        return nameCrop.get(name.toLowerCase());
    }
}

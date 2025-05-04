package model.enums;

import java.util.List;

public enum ForagingCrops {
    CommonMushroom("Common Mushroom", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter),
            40, 38),
    Daffodil("Daffodil", List.of(Seasons.Spring), 30, 0),
    Dandelion("Dandelion", List.of(Seasons.Spring), 40, 25),
    Leek("Leek", List.of(Seasons.Spring), 60, 40),
    Morel("Morel", List.of(Seasons.Spring), 150, 20),
    Salmonberry("Salmonberry", List.of(Seasons.Spring), 5, 25),
    SpringOnion("Spring Onion", List.of(Seasons.Spring), 8, 13),
    WildHorseradish("Wild Horseradish", List.of(Seasons.Spring), 50, 13),
    FiddleheadFern("Fiddlehead Fern", List.of(Seasons.Summer), 90, 25),
    Grape("Grape", List.of(Seasons.Summer), 80, 38),
    RedMushroom("Red Mushroom", List.of(Seasons.Summer), 75, -50),
    SpiceBerry("Spice Berry", List.of(Seasons.Summer), 80, 25),
    SweetPea("Sweet Pea", List.of(Seasons.Summer), 50, 0),
    Blackberry("Blackberry", List.of(Seasons.Fall), 25, 25),
    Chanterelle("Chanterelle", List.of(Seasons.Fall), 160, 75),
    Hazelnut("Hazelnut", List.of(Seasons.Fall), 40, 38),
    PurpleMushroom("Purple Mushroom", List.of(Seasons.Fall), 90, 30),
    WildPlum("Wild Plum", List.of(Seasons.Fall), 80, 25),
    Crocus("Crocus", List.of(Seasons.Winter), 60, 0),
    CrystalFruit("Crystal Fruit", List.of(Seasons.Winter), 150, 63),
    Holly("Holly", List.of(Seasons.Winter), 80, -37),
    SnowYam("Snow Yam", List.of(Seasons.Winter), 100, 30),
    WinterRoot("Winter Root", List.of(Seasons.Winter), 70, 25);

    private final String displayName;
    private final List<Seasons> seasons;
    private final int baseSellPrice;
    private final int energy;

    ForagingCrops(String displayName, List<Seasons> seasons, int baseSellPrice, int energy) {
        this.displayName = displayName;
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
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

    @Override
    public String toString() {
        return displayName;
    }
}

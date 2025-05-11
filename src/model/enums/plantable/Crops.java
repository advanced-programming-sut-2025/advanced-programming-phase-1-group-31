package model.enums.plantable;

import model.enums.general.Seasons;

import java.util.List;

public enum Crops {
    Blue_Jazz("Blue Jazz", Seeds.JazzSeeds, List.of(1, 2, 2, 2), 7, true, null, 50, true, 45, List.of(Seasons.Spring), false),
    Carrot("Carrot", Seeds.CarrotSeeds, List.of(1, 1, 1), 3, true, null, 35, true, 75, List.of(Seasons.Spring), false),
    Cauliflower("Cauliflower", Seeds.CauliflowerSeeds, List.of(1, 2, 4, 4, 1), 12, true, null, 175, true, 75, List.of(Seasons.Spring), true),
    Coffee_Bean("Coffee Bean", Seeds.CoffeeBean, List.of(1, 2, 2, 3, 2), 10, false, 2, 15, false, 0, List.of(Seasons.Spring, Seasons.Summer), false),
    Garlic("Garlic", Seeds.GarlicSeeds, List.of(1, 1, 1, 1), 4, true, null, 60, true, 20, List.of(Seasons.Spring), false),
    Green_Bean("Green Bean", Seeds.BeanStarter, List.of(1, 1, 1, 3, 4), 10, false, 3, 40, true, 25, List.of(Seasons.Spring), false),
    Kale("Kale", Seeds.KaleSeeds, List.of(1, 2, 2, 1), 6, true, null, 110, true, 50, List.of(Seasons.Spring), false),
    Parsnip("Parsnip", Seeds.ParsnipSeeds, List.of(1, 1, 1, 1), 4, true, null, 35, true, 25, List.of(Seasons.Spring), false),
    Potato("Potato", Seeds.PotatoSeeds, List.of(1, 1, 1, 2, 1), 6, true, null, 80, true, 25, List.of(Seasons.Spring), false),
    Rhubarb("Rhubarb", Seeds.RhubarbSeeds, List.of(2, 2, 2, 3, 4), 13, true, null, 220, false, 0, List.of(Seasons.Spring), false),
    Strawberry("Strawberry", Seeds.StrawberrySeeds, List.of(1, 1, 2, 2, 2), 8, false, 4, 120, true, 50, List.of(Seasons.Spring), false),
    Tulip("Tulip", Seeds.TulipBulb, List.of(1, 1, 2, 2), 6, true, null, 30, true, 45, List.of(Seasons.Spring), false),
    UnmilledRice("Unmilled Rice", Seeds.RiceShoot, List.of(1, 2, 2, 3), 8, true, null, 30, true, 3, List.of(Seasons.Spring), false),
    Blueberry("Blueberry", Seeds.BlueberrySeeds, List.of(1, 3, 3, 4, 2), 13, false, 4, 50, true, 25, List.of(Seasons.Summer), false),
    Corn("Corn", Seeds.CornSeeds, List.of(2, 3, 3, 3, 3), 14, false, 4, 50, true, 25, List.of(Seasons.Summer, Seasons.Fall), false),
    Hops("Hops", Seeds.HopsStarter, List.of(1, 1, 2, 3, 4), 11, false, 1, 25, true, 45, List.of(Seasons.Summer), false),
    Hot_Pepper("Hot Pepper", Seeds.PepperSeeds, List.of(1, 1, 1, 1, 1), 5, false, 3, 40, true, 13, List.of(Seasons.Summer), false),
    Melon("Melon", Seeds.MelonSeeds, List.of(1, 2, 3, 3, 3), 12, true, null, 250, true, 113, List.of(Seasons.Summer), true),
    Poppy("Poppy", Seeds.PoppySeeds, List.of(1, 2, 2, 2), 7, true, null, 140, true, 45, List.of(Seasons.Summer), false),
    Radish("Radish", Seeds.RadishSeeds, List.of(2, 1, 2, 1), 6, true, null, 90, true, 45, List.of(Seasons.Summer), false),
    Red_Cabbage("Red Cabbage", Seeds.RedCabbageSeeds, List.of(2, 1, 2, 2, 2), 9, true, null, 260, true, 75, List.of(Seasons.Summer), false),
    Starfruit("Starfruit", Seeds.StarfruitSeeds, List.of(2, 3, 2, 3, 3), 13, true, null, 750, true, 125, List.of(Seasons.Summer), false),
    Summer_Spangle("Summer Spangle", Seeds.SpangleSeeds, List.of(1, 2, 3, 1), 8, true, null, 90, true, 45, List.of(Seasons.Summer), false),
    Summer_Squash("Summer Squash", Seeds.SummerSquashSeeds, List.of(1, 1, 1, 2, 1), 6, false, 3, 45, true, 63, List.of(Seasons.Summer), false),
    Sunflower("Sunflower", Seeds.SunflowerSeeds, List.of(1, 2, 3, 2), 8, true, null, 80, true, 45, List.of(Seasons.Summer, Seasons.Fall), false),
    Tomato("Tomato", Seeds.TomatoSeeds, List.of(2, 2, 2, 2, 3), 11, false, 4, 60, true, 20, List.of(Seasons.Summer), false),
    Wheat("Wheat", Seeds.WheatSeeds, List.of(1, 1, 1, 1), 4, true, null, 25, false, 0, List.of(Seasons.Summer, Seasons.Fall), false),
    Amaranth("Amaranth", Seeds.AmaranthSeeds, List.of(1, 2, 2, 2), 7, true, null, 150, true, 50, List.of(Seasons.Fall), false),
    Artichoke("Artichoke", Seeds.ArtichokeSeeds, List.of(2, 2, 1, 2, 1), 8, true, null, 160, true, 30, List.of(Seasons.Fall), false),
    Beet("Beet", Seeds.BeetSeeds, List.of(1, 1, 2, 2), 6, true, null, 100, true, 30, List.of(Seasons.Fall), false),
    BokChoy("Bok Choy", Seeds.BokChoySeeds, List.of(1, 1, 1, 1), 4, true, null, 80, true, 25, List.of(Seasons.Fall), false),
    Broccoli("Broccoli", Seeds.BroccoliSeeds, List.of(2, 2, 2, 2), 8, false, 4, 70, true, 63, List.of(Seasons.Fall), false),
    Cranberries("Cranberries", Seeds.CranberrySeeds, List.of(1, 2, 1, 1, 2), 7, false, 5, 75, true, 38, List.of(Seasons.Fall), false),
    Eggplant("Eggplant", Seeds.EggplantSeeds, List.of(1, 1, 1, 1), 5, false, 5, 60, true, 20, List.of(Seasons.Fall), false),
    Fairy_Rose("Fairy Rose", Seeds.FairySeeds, List.of(1, 4, 4, 3), 12, true, null, 290, true, 45, List.of(Seasons.Fall), false),
    Grape("Grape", Seeds.GrapeStarter, List.of(1, 1, 2, 3, 3), 10, false, 3, 80, true, 38, List.of(Seasons.Fall), false),
    Pumpkin("Pumpkin", Seeds.PumpkinSeeds, List.of(1, 2, 3, 4, 3), 13, true, null, 320, false, 0, List.of(Seasons.Fall), true),
    Yam("Yam", Seeds.YamSeeds, List.of(1, 3, 3, 3), 10, true, null, 160, true, 45, List.of(Seasons.Fall), false),
    Sweet_Gem_Berry("Sweet Gem Berry", Seeds.RareSeed, List.of(2, 4, 6, 6, 6), 24, true, null, 3000, false, 0, List.of(Seasons.Fall), false),
    PowderMelon("Powder Melon", Seeds.PowderMelonSeeds, List.of(1, 2, 1, 2, 1), 7, true, null, 60, true, 63, List.of(Seasons.Winter), true),
    Ancient_Fruit("Ancient Fruit", Seeds.AncientSeeds, List.of(2, 7, 7, 7, 5), 28, false, 7, 550, false, 0, List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall), false);

    private final String displayName;
    private final Seeds source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final List<Seasons> seasons;
    private final boolean canBecomeGiant;

    Crops(String displayName, Seeds source, List<Integer> stages, int totalHarvestTime, boolean oneTime, Integer regrowthTime,
          int baseSellPrice, boolean isEdible, int energy, List<Seasons> seasons, boolean canBecomeGiant) {
        this.displayName = displayName;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Seeds getSource() {
        return source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public Integer getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

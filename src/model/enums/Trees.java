package model.enums;

import java.util.List;

public enum Trees {
    Apricot("Apricot Tree", Seeds.ApricotSapling, List.of(7, 7, 7, 7), 28, Fruits.Apricot,
            1,
            59, true, 38, List.of(Seasons.Spring)),

    Cherry("Cherry Tree", Seeds.CherrySapling, List.of(7, 7, 7, 7), 28, Fruits.Cherry,
            1,
            80, true, 38, List.of(Seasons.Spring)),

    Banana("Banana Tree", Seeds.BananaSapling, List.of(7, 7, 7, 7), 28, Fruits.Banana,
            1,
            150, true, 75, List.of(Seasons.Summer)),

    Mango("Mango Tree", Seeds.MangoSapling, List.of(7, 7, 7, 7), 28, Fruits.Mango,
            1,
            130, true, 100, List.of(Seasons.Summer)),

    Orange("Orange Tree", Seeds.OrangeSapling, List.of(7, 7, 7, 7), 28, Fruits.Orange,
            1,
            100, true, 38, List.of(Seasons.Summer)),

    Peach("Peach Tree", Seeds.PeachSapling, List.of(7, 7, 7, 7), 28, Fruits.Peach,
            1,
            140, true, 38, List.of(Seasons.Summer)),

    Apple("Apple Tree", Seeds.AppleSapling, List.of(7, 7, 7, 7), 28, Fruits.Apple,
            1,
            100, true, 38, List.of(Seasons.Fall)),

    Pomegranate("Pomegranate Tree", Seeds.PomegranateSapling, List.of(7, 7, 7, 7), 28,
            Fruits.Pomegranate, 1,
            140, true, 38, List.of(Seasons.Fall)),

    OakTree("Oak Tree", Seeds.Acorns, List.of(7, 7, 7, 7), 28, Fruits.OakResin, 7,
            150, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MapleTree("Maple Tree", Seeds.MapleSeeds, List.of(7, 7, 7, 7), 28, Fruits.MapleSyrup,
            9, 200, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    PineTree("Pine Tree", Seeds.PineCones, List.of(7, 7, 7, 7), 28, Fruits.PineTar, 5,
            100, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MahoganyTree("Mahogany Tree", Seeds.MahoganySeeds, List.of(7, 7, 7, 7), 28,
            Fruits.Sap, 1, 2, true, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MushroomTree("Mushroom Tree", Seeds.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28,
            Fruits.CommonMushroom, 1, 40, true, 38,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MysticTree("Mystic Tree", Seeds.MysticTreeSeeds, List.of(7, 7, 7, 7), 28,
            Fruits.MysticSyrup, 7, 1000, true, 500,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));

    private final String name;
    private final Seeds source;
    private final List<Integer> stages;
    private final Integer totalHarvestTime;
    private final Fruits fruit;
    private final Integer fruitHarvestCycle;
    private final Integer fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final Integer fruitEnergy;
    private final List<Seasons> seasons;

    Trees(String name, Seeds source, List<Integer> stages, Integer totalHarvestTime, Fruits fruit,
          Integer fruitHarvestCycle, Integer fruitBaseSellPrice, boolean isFruitEdible,
          Integer fruitEnergy, List<Seasons> seasons) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.seasons = seasons;
    }

    public String getName() {
        return name;
    }

    public Seeds getSource() {
        return source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public Integer getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Fruits getFruit() {
        return fruit;
    }

    public Integer getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public Integer getFruitBaseSellPrice() {
        return fruitBaseSellPrice;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public Integer getFruitEnergy() {
        return fruitEnergy;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }
}

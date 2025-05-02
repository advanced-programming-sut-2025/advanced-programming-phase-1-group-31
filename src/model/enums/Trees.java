package model.enums;

import java.util.List;

public enum Trees {
    Apricot(Seeds.ApricotSapling, List.of(7, 7, 7, 7), 28, Fruits.Apricot, 1,
            59, true, 38, List.of(Seasons.Spring)),

    Cherry(Seeds.CherrySapling, List.of(7, 7, 7, 7), 28, Fruits.Cherry, 1,
            80, true, 38, List.of(Seasons.Spring)),

    Banana(Seeds.BananaSapling, List.of(7, 7, 7, 7), 28, Fruits.Banana, 1,
            150, true, 75, List.of(Seasons.Summer)),

    Mango(Seeds.MangoSapling, List.of(7, 7, 7, 7), 28, Fruits.Mango, 1,
            130, true, 100, List.of(Seasons.Summer)),

    Orange(Seeds.OrangeSapling, List.of(7, 7, 7, 7), 28, Fruits.Orange, 1,
            100, true, 38, List.of(Seasons.Summer)),

    Peach(Seeds.PeachSapling, List.of(7, 7, 7, 7), 28, Fruits.Peach, 1,
            140, true, 38, List.of(Seasons.Summer)),

    Apple(Seeds.AppleSapling, List.of(7, 7, 7, 7), 28, Fruits.Apple, 1,
            100, true, 38, List.of(Seasons.Fall)),

    Pomegranate(Seeds.PomegranateSapling, List.of(7, 7, 7, 7), 28, Fruits.Pomegranate, 1,
            140, true, 38, List.of(Seasons.Fall)),

    OakTree(Seeds.Acorns, List.of(7, 7, 7, 7), 28, Fruits.OakResin, 7,
            150, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MapleTree(Seeds.MapleSeeds, List.of(7, 7, 7, 7), 28, Fruits.MapleSyrup, 9,
            200, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    PineTree(Seeds.PineCones, List.of(7, 7, 7, 7), 28, Fruits.PineTar, 5,
            100, false, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MahoganyTree(Seeds.MahoganySeeds, List.of(7, 7, 7, 7), 28,
            Fruits.Sap, 1,2, true, null,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MushroomTree(Seeds.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28,
            Fruits.CommonMushroom, 1,40, true, 38,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),

    MysticTree(Seeds.MysticTreeSeeds, List.of(7, 7, 7, 7), 28,
            Fruits.MysticSyrup, 7,1000, true, 500,
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));

    private final Seeds source;
    private final List<Integer> stages;
    private final Integer totalHarvestTime;
    private final Fruits fruit;
    private final Integer fruitHarvestCycle;
    private final Integer fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final Integer fruitEnergy;
    private final List<Seasons> seasons;

    Trees(Seeds source, List<Integer> stages, Integer totalHarvestTime, Fruits fruit, Integer fruitHarvestCycle,
          Integer fruitBaseSellPrice, boolean isFruitEdible, Integer fruitEnergy, List<Seasons> seasons) {
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

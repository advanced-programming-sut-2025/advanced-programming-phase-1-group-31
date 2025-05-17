package model.enums.plantable;

import model.materials.MaterialType;

import java.util.List;

public enum Trees implements MaterialType {
    Apricot("Apricot Tree", Seeds.ApricotSapling, List.of(7, 7, 7, 7), 28, Fruits.Apricot,
            1),
    Cherry("Cherry Tree", Seeds.CherrySapling, List.of(7, 7, 7, 7), 28, Fruits.Cherry,
            1),
    Banana("Banana Tree", Seeds.BananaSapling, List.of(7, 7, 7, 7), 28, Fruits.Banana,
            1),
    Mango("Mango Tree", Seeds.MangoSapling, List.of(7, 7, 7, 7), 28, Fruits.Mango,
            1),
    Orange("Orange Tree", Seeds.OrangeSapling, List.of(7, 7, 7, 7), 28, Fruits.Orange,
            1),
    Peach("Peach Tree", Seeds.PeachSapling, List.of(7, 7, 7, 7), 28, Fruits.Peach,
            1),
    Apple("Apple Tree", Seeds.AppleSapling, List.of(7, 7, 7, 7), 28, Fruits.Apple,
            1),
    Pomegranate("Pomegranate Tree", Seeds.PomegranateSapling, List.of(7, 7, 7, 7), 28,
            Fruits.Pomegranate, 1),
    OakTree("Oak Tree", Seeds.Acorns, List.of(7, 7, 7, 7), 28, Fruits.OakResin,
            7),
    MapleTree("Maple Tree", Seeds.MapleSeeds, List.of(7, 7, 7, 7), 28, Fruits.MapleSyrup,
            9),
    PineTree("Pine Tree", Seeds.PineCones, List.of(7, 7, 7, 7), 28, Fruits.PineTar,
            5),
    MahoganyTree("Mahogany Tree", Seeds.MahoganySeeds, List.of(7, 7, 7, 7), 28, Fruits.Sap,
            1),
    MushroomTree("Mushroom Tree", Seeds.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28,
            Fruits.CommonMushroom, 1),
    MysticTree("Mystic Tree", Seeds.MysticTreeSeeds, List.of(7, 7, 7, 7), 28, Fruits.MysticSyrup,
            7);

    private final String name;
    private final Seeds source;
    private final List<Integer> stages;
    private final Integer totalHarvestTime;
    private final Fruits fruit;
    private final Integer harvestCycle;

    Trees(String name, Seeds source, List<Integer> stages, Integer totalHarvestTime, Fruits fruit,
          Integer harvestCycle) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.harvestCycle = harvestCycle;
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

    public Integer getHarvestCycle() {
        return harvestCycle;
    }

    public static Trees findByName(String name) {
        for (Trees tree : Trees.values()) {
            if (tree.getName().equalsIgnoreCase(name)) {
                return tree;
            }
        }
        return null;
    }

}

package model.enums.plantable;

import model.materials.MaterialType;

public enum Seeds implements MaterialType {
    JazzSeeds("Jazz Seeds", 37),
    CarrotSeeds("Carrot Seeds", 5),
    CauliflowerSeeds("Cauliflower Seeds", 100),
    CoffeeBean("Coffee Bean", 200),
    GarlicSeeds("Garlic Seeds", 0),            // price not specified
    BeanStarter("Bean Starter", 75),
    KaleSeeds("Kale Seeds", 87),
    ParsnipSeeds("Parsnip Seeds", 25),
    PotatoSeeds("Potato Seeds", 62),
    RhubarbSeeds("Rhubarb Seeds", 100),
    StrawberrySeeds("Strawberry Seeds", 100),
    TulipBulb("Tulip Bulb", 25),
    RiceShoot("Rice Shoot", 250),
    BlueberrySeeds("Blueberry Seeds", 0),     // price not specified
    CornSeeds("Corn Seeds", 187),
    HopsStarter("Hops Starter", 75),
    PepperSeeds("Pepper Seeds", 50),
    MelonSeeds("Melon Seeds", 100),
    PoppySeeds("Poppy Seeds", 125),
    RadishSeeds("Radish Seeds", 50),
    RedCabbageSeeds("Red Cabbage Seeds", 0),
    StarfruitSeeds("Starfruit Seeds", 400),
    SpangleSeeds("Spangle Seeds", 62),
    SummerSquashSeeds("Summer Squash Seeds", 10),
    SunflowerSeeds("Sunflower Seeds", 125),
    TomatoSeeds("Tomato Seeds", 62),
    WheatSeeds("Wheat Seeds", 12),
    AmaranthSeeds("Amaranth Seeds", 0),
    ArtichokeSeeds("Artichoke Seeds", 0),
    BeetSeeds("Beet Seeds", 20),
    BokChoySeeds("Bok Choy Seeds", 62),
    BroccoliSeeds("Broccoli Seeds", 15),
    CranberrySeeds("Cranberry Seeds", 300),
    EggplantSeeds("Eggplant Seeds", 25),
    FairySeeds("Fairy Seeds", 250),
    GrapeStarter("Grape Starter", 75),
    PumpkinSeeds("Pumpkin Seeds", 125),
    YamSeeds("Yam Seeds", 75),
    RareSeed("Rare Seed", 1000),
    PowderMelonSeeds("Powder Melon Seeds", 20),
    AncientSeeds("Ancient Seeds", 500),
    ApricotSapling("Apricot Sapling", 0),
    CherrySapling("Cherry Sapling", 0),
    BananaSapling("Banana Sapling", 0),
    MangoSapling("Mango Sapling", 0),
    OrangeSapling("Orange Sapling", 0),
    PeachSapling("Peach Sapling", 0),
    AppleSapling("Apple Sapling", 0),
    PomegranateSapling("Pomegranate Sapling", 0),
    Acorns("Acorns", 0),
    MapleSeeds("Maple Seeds", 0),
    PineCones("Pine Cones", 0),
    MahoganySeeds("Mahogany Seeds", 0),
    MushroomTreeSeeds("Mushroom Tree Seeds", 0),
    MysticTreeSeeds("Mystic Tree Seeds", 0);

    private final String name;
    private final int price;

    Seeds(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static Seeds getByName(String inputName) {
        for (Seeds seed : values()) {
            if (seed.getName().equals(inputName)) {
                return seed;
            }
        }
        return null;
    }
}

package model.enums.plantable;

import model.materials.MaterialType;

public enum Seeds implements MaterialType {
    JazzSeeds("Jazz Seeds", 30),
    CarrotSeeds("Carrot Seeds", 5),
    CauliflowerSeeds("Cauliflower Seeds", 80),
    CoffeeBean("Coffee Bean", 200),
    GarlicSeeds("Garlic Seeds", 40),
    BeanStarter("Bean Starter", 60),
    KaleSeeds("Kale Seeds", 70),
    ParsnipSeeds("Parsnip Seeds", 20),
    PotatoSeeds("Potato Seeds", 50),
    RhubarbSeeds("Rhubarb Seeds", 100),
    StrawberrySeeds("Strawberry Seeds", 100),
    TulipBulb("Tulip Bulb", 20),
    RiceShoot("Rice Shoot", 40),
    BlueberrySeeds("Blueberry Seeds", 80),
    CornSeeds("Corn Seeds", 150),
    HopsStarter("Hops Starter", 60),
    PepperSeeds("Pepper Seeds", 40),
    MelonSeeds("Melon Seeds", 80),
    PoppySeeds("Poppy Seeds", 100),
    RadishSeeds("Radish Seeds", 40),
    RedCabbageSeeds("Red Cabbage Seeds", 100),
    StarfruitSeeds("Starfruit Seeds", 400),
    SpangleSeeds("Spangle Seeds", 50),
    SummerSquashSeeds("Summer Squash Seeds", 10),
    SunflowerSeeds("Sunflower Seeds", 200),
    TomatoSeeds("Tomato Seeds", 50),
    WheatSeeds("Wheat Seeds", 10),
    AmaranthSeeds("Amaranth Seeds", 0),
    ArtichokeSeeds("Artichoke Seeds", 30),
    BeetSeeds("Beet Seeds", 20),
    BokChoySeeds("Bok Choy Seeds", 50),
    BroccoliSeeds("Broccoli Seeds", 15),
    CranberrySeeds("Cranberry Seeds", 240),
    EggplantSeeds("Eggplant Seeds", 20),
    FairySeeds("Fairy Seeds", 200),
    GrapeStarter("Grape Starter", 60),
    PumpkinSeeds("Pumpkin Seeds", 100),
    YamSeeds("Yam Seeds", 60),
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

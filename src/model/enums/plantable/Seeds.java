package model.enums.plantable;

public enum Seeds {
    JazzSeeds("Jazz Seeds"),
    CarrotSeeds("Carrot Seeds"),
    CauliflowerSeeds("Cauliflower Seeds"),
    CoffeeBean("Coffee Bean"),
    GarlicSeeds("Garlic Seeds"),
    BeanStarter("Bean Starter"),
    KaleSeeds("Kale Seeds"),
    ParsnipSeeds("Parsnip Seeds"),
    PotatoSeeds("Potato Seeds"),
    RhubarbSeeds("Rhubarb Seeds"),
    StrawberrySeeds("Strawberry Seeds"),
    TulipBulb("Tulip Bulb"),
    RiceShoot("Rice Shoot"),
    BlueberrySeeds("Blueberry Seeds"),
    CornSeeds("Corn Seeds"),
    HopsStarter("Hops Starter"),
    PepperSeeds("Pepper Seeds"),
    MelonSeeds("Melon Seeds"),
    PoppySeeds("Poppy Seeds"),
    RadishSeeds("Radish Seeds"),
    RedCabbageSeeds("Red Cabbage Seeds"),
    StarfruitSeeds("Starfruit Seeds"),
    SpangleSeeds("Spangle Seeds"),
    SummerSquashSeeds("Summer Squash Seeds"),
    SunflowerSeeds("Sunflower Seeds"),
    TomatoSeeds("Tomato Seeds"),
    WheatSeeds("Wheat Seeds"),
    AmaranthSeeds("Amaranth Seeds"),
    ArtichokeSeeds("Artichoke Seeds"),
    BeetSeeds("Beet Seeds"),
    BokChoySeeds("Bok Choy Seeds"),
    BroccoliSeeds("Broccoli Seeds"),
    CranberrySeeds("Cranberry Seeds"),
    EggplantSeeds("Eggplant Seeds"),
    FairySeeds("Fairy Seeds"),
    GrapeStarter("Grape Starter"),
    PumpkinSeeds("Pumpkin Seeds"),
    YamSeeds("Yam Seeds"),
    RareSeed("Rare Seed"),
    PowderMelonSeeds("Powder Melon Seeds"),
    AncientSeeds("Ancient Seeds"),
    ApricotSapling("Apricot Sapling"),
    CherrySapling("Cherry Sapling"),
    BananaSapling("Banana Sapling"),
    MangoSapling("Mango Sapling"),
    OrangeSapling("Orange Sapling"),
    PeachSapling("Peach Sapling"),
    AppleSapling("Apple Sapling"),
    PomegranateSapling("Pomegranate Sapling"),
    Acorns("Acorns"),
    MapleSeeds("Maple Seeds"),
    PineCones("Pine Cones"),
    MahoganySeeds("Mahogany Seeds"),
    MushroomTreeSeeds("Mushroom Tree Seeds"),
    MysticTreeSeeds("Mystic Tree Seeds");

    private final String name;

    Seeds(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

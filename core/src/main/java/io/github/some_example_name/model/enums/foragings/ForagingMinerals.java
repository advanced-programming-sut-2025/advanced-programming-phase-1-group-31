package io.github.some_example_name.model.enums.foragings;


import io.github.some_example_name.model.materials.MaterialType;

import java.util.Random;

public enum ForagingMinerals implements MaterialType {
    Quartz("Quartz", "A clear crystal commonly found in caves and mines.", 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Mineral/Quartz.png"),
    EarthCrystal("Earth Crystal", "A resinous substance found near the surface.", 50, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Mineral/Earth_Crystal.png"),
    FrozenTear("Frozen Tear", "A crystal fabled to be the frozen tears of a yeti.", 75, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Mineral/Frozen_Tear.png"),
    FireQuartz("Fire Quartz", "A glowing red crystal commonly found near hot lava.", 100, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Mineral/Fire_Quartz.png"),
    Emerald("Emerald", "A precious stone with a brilliant green color.", 250, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Emerald.png"),
    Aquamarine("Aquamarine", "A shimmery blue-green gem.", 180, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Aquamarine.png"),
    Ruby("Ruby", "A precious stone that is sought after for its rich color and beautiful luster.",
            250, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Ruby.png"),
    Amethyst("Amethyst", "A purple variant of quartz.", 100, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Amethyst.png"),
    Topaz("Topaz", "Fairly common but still prized for its beauty.", 80, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Topaz.png"),
    Jade("Jade", "A pale green ornamental stone.", 200, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Jade.png"),
    Diamond("Diamond", "A rare and valuable gem.", 750, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Diamond.png"),
    PrismaticShard("Prismatic Shard", "A very rare and powerful substance with unknown origins.",
            2000, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Gem/Prismatic_Shard.png"),
    WOOD("Wood", "It is taken from a tree.", 4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Wood.png"),
    STONE("Stone", "Common Stone", 2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Stone.png"),
    COPPER_ORE("Copper Ore", "A common ore that can be smelted into bars.", 5, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Copper_Ore.png"),
    IRON_ORE("Iron Ore", "A fairly common ore that can be smelted into bars.", 10, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Iron_Ore.png"),
    GOLD_ORE("Gold Ore", "A precious ore that can be smelted into bars.", 25, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Gold_Ore.png"),
    IRIDIUM_ORE("Iridium Ore", "An exotic ore with many curious properties. Can be smelted into bars.",
            100, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Iridium_Ore.png"),
    FIBER("Fiber", "just fiber", 10, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Fiber.png");


    private final String displayName;
    private final String description;
    private final int sellPrice;
    private final String imagePath;

    ForagingMinerals(String displayName, String description, int sellPrice, String imagePath) {
        this.displayName = displayName;
        this.description = description;
        this.sellPrice = sellPrice;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    private static final Random RANDOM = new Random();
    @Override
    public String toString() {
        return displayName;
    }

    public static ForagingMinerals getRandom() {
        ForagingMinerals[] values = ForagingMinerals.values();
        return values[RANDOM.nextInt(values.length)];
    }

    public String getImagePath() {
        return imagePath;
    }
}

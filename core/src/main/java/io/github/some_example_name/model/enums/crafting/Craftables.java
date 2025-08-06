package io.github.some_example_name.model.enums.crafting;


import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.foragings.ForagingSeeds;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Foraging.ForagingSeed;
import io.github.some_example_name.model.materials.Industrial;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Map;

public enum Craftables implements MaterialType {
    CHERRY_BOMB("Cherry bomb", "Destroys everything in a three tile radius", Map.of(new ForagingMineral(ForagingMinerals.COPPER_ORE), 4, new Industrial(Industrials.COAL), 1) ,50),
    BOMB("Bomb", "Destroys everything in a five tile radius", Map.of(new ForagingMineral(ForagingMinerals.IRON_ORE), 4, new Industrial(Industrials.COAL), 1) ,100),
    MEGA_BOMB("Mega bomb", "Destroys everything in a seven tile radius", Map.of(new ForagingMineral(ForagingMinerals.GOLD_ORE), 4, new Industrial(Industrials.COAL), 1) ,150),
    SPRINKLER("Sprinkler", "Waters everything in a four tile radius", Map.of(new Industrial(Industrials.COPPER_BAR), 1, new Industrial(Industrials.COAL), 1),50),
    QUALITY_SPRINKLER("Quality sprinkler", "Waters everything in a eight tile radius", Map.of(new Industrial(Industrials.IRON_BAR), 1, new Industrial(Industrials.COAL), 1),100),
    IRIDIUM_SPRINKLER("Iridium sprinkler", "Waters everything in a twenty-four tile radius", Map.of(new Industrial(Industrials.GOLD_BAR), 1, new Industrial(Industrials.COAL), 1),150),
    FURNACE("Furnace", "Smelts ores and coal into bars", Map.of(new ForagingMineral(ForagingMinerals.COPPER_ORE), 20, new ForagingMineral(ForagingMinerals.STONE), 25),  100),
    SCARECROW("Scarecrow", "Prevents crow attacks in a eight tile radius", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 50, new Industrial(Industrials.COAL),  1, new ForagingMineral(ForagingMinerals.FIBER), 20), 100),
    DELUXE_SCARECROW("Deluxe scarecrow", "Prevents crow attacks in a twelve tile radius", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 50, new Industrial(Industrials.COAL),  1, new ForagingMineral(ForagingMinerals.IRIDIUM_ORE), 1, new ForagingMineral(ForagingMinerals.FIBER), 20),150),
    BEE_HOUSE("Bee house", "Used to produce honey", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 40, new Industrial(Industrials.COAL), 8, new Industrial(Industrials.IRON_BAR), 1),150),
    CHARCOAL_KILN("Charcoal kiln", "Turns ten wood into one coal", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 20, new Industrial(Industrials.COPPER_BAR), 2),150),
    CHEESE_PRESS("Cheese press", "Used to turn milk into cheese", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 45, new ForagingMineral(ForagingMinerals.STONE), 45, new Industrial(Industrials.COPPER_BAR), 1),150),
    KEG("Keg", "Used to turn fruits and vegetables into drinks", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 30, new Industrial(Industrials.COPPER_BAR), 1, new Industrial(Industrials.IRON_BAR), 1),150),
    DEHYDRATOR("Dehydrator","Used to dry fruits or shrooms", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 30, new ForagingMineral(ForagingMinerals.STONE), 20, new ForagingMineral(ForagingMinerals.FIBER), 30), 150),
    LOOM("Loom", "Used to turn wool into fabric", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 60, new ForagingMineral(ForagingMinerals.FIBER), 30),150),
    MAYONNAISE_MACHINE("Mayonnaise machine", "Used to turn eggs into mayo", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 15, new ForagingMineral(ForagingMinerals.STONE), 15, new Industrial(Industrials.COPPER_BAR), 1),150),
    OIL_MAKER("Oil maker", "Used to turn truffle into oil", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 100, new Industrial(Industrials.IRON_BAR), 1, new Industrial(Industrials.GOLD_BAR), 1),150),
    PRESERVER_JAR("Preserver jar", "Used for turning fruits into jam and pickling vegetables", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 80, new ForagingMineral(ForagingMinerals.STONE), 40, new Industrial(Industrials.COAL), 8),150),
    GRASS_STARTER("Grass starter", "Will make grass grow where it's placed", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 1, new ForagingMineral(ForagingMinerals.FIBER), 1),100),
    FISH_SMOKER("Fish smoker", "Used to smoke fish, preserving its quality", Map.of(new ForagingMineral(ForagingMinerals.WOOD), 50, new Industrial(Industrials.COAL), 10, new Industrial(Industrials.IRON_BAR), 3),150),
    MYSTIC_TREE_SEED("Mystic tree seed", "Will grow into a mystic tree", Map.of(new ForagingSeed(ForagingSeeds.Acorns) ,5, new ForagingSeed(ForagingSeeds.PineCones),5, new ForagingSeed(ForagingSeeds.MahoganySeeds), 5, new ForagingSeed(ForagingSeeds.MapleSeeds), 5),200);


    private final String name;
    private final String description;
    private final Map<Material, Integer> ingredients;
    private final int price;

    Craftables(String name, String description, Map<Material, Integer> ingredients,  int price) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Material, Integer> getIngredients() {
        return ingredients;
    }

    public int getPrice() {
        return price;
    }
}

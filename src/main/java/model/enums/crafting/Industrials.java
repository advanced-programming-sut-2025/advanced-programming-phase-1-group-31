package model.enums.crafting;

import model.enums.creature.AnimalProducts;
import model.enums.foragings.ForagingCrops;
import model.enums.foragings.ForagingMinerals;
import model.enums.plantable.Crops;
import model.materials.*;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Products.AnimalProduct;

import java.util.function.*;


import java.util.Map;

public enum Industrials implements MaterialType {
    HONEY("Honey", "It's a sweet syrup produced by bees", 75, 52, null, 350, Craftables.BEE_HOUSE),
    CHEESE("Cheese", "It's your basic cheese", 100, 3, Map.of(new AnimalProduct(AnimalProducts.MILK), 1), 230, Craftables.CHEESE_PRESS),
    LARGE_CHEESE("Cheese", "It's your basic cheese", 100, 3, Map.of(new AnimalProduct(AnimalProducts.LARGE_MILK), 1), 345, Craftables.CHEESE_PRESS),
    GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk", 100, 3 ,Map.of(new AnimalProduct(AnimalProducts.GOAT_MILK), 1), 400, Craftables.CHEESE_PRESS),
    LARGE_GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk", 100, 3 , Map.of(new AnimalProduct(AnimalProducts.LARGE_GOAT_MILK), 1), 600, Craftables.CHEESE_PRESS),
    BEER("Beer", "Drink in moderation", 50, 13, Map.of(new ForagingCrop(ForagingCrops.Hazelnut), 1), 200, Craftables.KEG),
    VINEGAR("Vinegar", "An aged fermented liquid used in many recipes", 13, 10, Map.of(new ForagingCrop(ForagingCrops.Grape), 1), 100, Craftables.KEG),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost", 75, 2, Map.of(new Crop(Crops.Coffee_Bean), 1), 150, Craftables.KEG),
    JUICE("Juice", "A sweet, nutritious beverage", null, null, 52, 200, Craftables.KEG, material -> material instanceof Fruit, material -> ((Fruit)material).getEnergy() * 2, material -> (int)(((Fruit)material).baseSellPrice()*2.25)),
    MEAD("Mead", "Drink in moderation", 100, 10, Map.of(new Industrial(Industrials.HONEY), 1),300, Craftables.KEG),
    PALE_ALE("Pale ale", "Drink in moderation", 50, 39, Map.of(new Crop(Crops.Wheat), 1), 300, Craftables.KEG),
    WINE("Wine", "Drink in moderation", null, null, 91, 300, Craftables.KEG, material -> material instanceof Fruit, material -> (int)(((Fruit)material).getEnergy() * 1.75), material -> (((Fruit)material).baseSellPrice()*3)),
    DRIED_MUSHROOMS("Dried mushrooms", "A package of gourmet mushrooms", null, null, -1, null, Craftables.DEHYDRATOR, material -> material instanceof ForagingCrop, material -> 50,material -> (int)(((ForagingCrop)material).baseSellPrice()*7.5) + 25),
    DRIED_FRUIT("Dried fruit", "Chewy pieces of dried fruit", null, null, -1, null, Craftables.DEHYDRATOR, material -> material instanceof Fruit, material -> 75, material -> (int)(((Fruit)material).baseSellPrice()*7.5) + 25),
    RAISINS("Raisins", "It's said to be the Junimos' favorite food", 125, 600, Map.of(new ForagingCrop(ForagingCrops.Grape), 5), -1, Craftables.DEHYDRATOR),
    COAL("Coal", "Coal", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.WOOD), 10), 2, Craftables.CHARCOAL_KILN),
    CLOTH("Cloth", "A bolt of fine wool cloth", -1, 470, Map.of(new AnimalProduct(AnimalProducts.WOOL), 1), 4, Craftables.LOOM),
    MAYONNAISE("Mayonnaise", "It looks spreadable", 50, 3, Map.of(new AnimalProduct(AnimalProducts.EGG), 1), 190, Craftables.MAYONNAISE_MACHINE),
    DUCK_MAYONNAISE("Duck mayonnaise", "It looks spreadable", 75, 3, Map.of(new AnimalProduct(AnimalProducts.DUCK_EGG), 1), 370, Craftables.MAYONNAISE_MACHINE),
    DINOSAUR_MAYONNAISE("Dinosaur mayonnaise", "It looks spreadable", 75, 3, Map.of(new AnimalProduct(AnimalProducts.DINOSAUR_EGG), 1), 800, Craftables.MAYONNAISE_MACHINE),
    TRUFFLE_OIL("Truffle oil", "A gourmet cooking ingredient", 37, 8, Map.of(new AnimalProduct(AnimalProducts.TRUFFLE), 1), 1065, Craftables.OIL_MAKER),
    OIL("Oil", "All purpose cooking oil", 13, 6, Map.of(new Crop(Crops.Corn), 1), 100, Craftables.OIL_MAKER),
    COPPER_BAR("Copper bar", "Copper ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.COPPER_ORE), 5), 10, Craftables.FURNACE),
    IRON_BAR("Copper bar", "Copper ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.IRON_ORE), 5), 10, Craftables.FURNACE),
    GOLD_BAR("Copper bar", "Copper ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.GOLD_ORE), 5), 10, Craftables.FURNACE),
    IRIDIUM_BAR("Copper bar", "Copper ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.IRIDIUM_ORE), 5), 10, Craftables.FURNACE);





    private final String name;
    private final String description;
    private final int processingTime;
    private final Map<Material, Integer> ingredients;
    private final Craftables craftables;
    private final Integer fixedEnergy;
    private final Integer fixedPrice;
    private final Predicate<Material> ingredientFilter;
    private final Function<Material, Integer> energyCalculator;
    private final Function<Material, Integer> priceCalculator;

    Industrials(String name, String description, Integer fixedEnergy, Integer fixedPrice,
                Map<Material, Integer> ingredients, int processingTime, Craftables craftables) {
        this(name, description, fixedEnergy, ingredients, processingTime, fixedPrice, craftables, null, null, null);
    }

    Industrials(String name, String description, Integer fixedEnergy,
                Map<Material, Integer> ingredients, int processingTime, Integer fixedPrice, Craftables craftables,
                Predicate<Material> ingredientFilter,
                Function<Material, Integer> energyCalculator,
                Function<Material, Integer> priceCalculator) {
        this.name = name;
        this.description = description;
        this.fixedEnergy = fixedEnergy;
        this.fixedPrice = fixedPrice;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.craftables = craftables;
        this.ingredientFilter = ingredientFilter;
        this.energyCalculator = energyCalculator;
        this.priceCalculator = priceCalculator;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Map<Material, Integer> getIngredients() {
        return ingredients;
    }

    public Craftables getCraftables() {
        return craftables;
    }

    public boolean isDynamic() {
        return ingredientFilter != null && energyCalculator != null;
    }

    public boolean accepts(Material material) {
        return isDynamic() && ingredientFilter.test(material);
    }

    public int calculateEnergy(Material material) {
        if (!isDynamic()) {
            return fixedEnergy;
        }
        return energyCalculator.apply(material);
    }

    public int calculatePrice(Material material) {
        if (!isDynamic()) {
            return fixedPrice;
        }
        return priceCalculator.apply(material);
    }
}
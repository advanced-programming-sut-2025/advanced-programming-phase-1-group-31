package io.github.some_example_name.model.enums.crafting;

import io.github.some_example_name.model.enums.creature.AnimalProducts;
import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.plantable.Crops;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Products.AnimalProduct;


import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum Industrials implements MaterialType {
    HONEY("Honey", "It's a sweet syrup produced by bees", 75, 52, null, 350, () -> new Craftable(Craftables.BEE_HOUSE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Honey.png"),
    CHEESE("Cheese", "It's your basic cheese", 100, 3, Map.of(new AnimalProduct(AnimalProducts.MILK), 1), 230, () -> new Craftable(Craftables.CHEESE_PRESS), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Cheese.png"),
    LARGE_CHEESE("Cheese", "It's your basic cheese", 100, 3, Map.of(new AnimalProduct(AnimalProducts.LARGE_MILK), 1), 345, () -> new Craftable(Craftables.CHEESE_PRESS), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Cheese.png"),
    GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk", 100, 3, Map.of(new AnimalProduct(AnimalProducts.GOAT_MILK), 1), 400, () -> new Craftable(Craftables.CHEESE_PRESS), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Goat_Cheese.png"),
    LARGE_GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk", 100, 3, Map.of(new AnimalProduct(AnimalProducts.LARGE_GOAT_MILK), 1), 600, () -> new Craftable(Craftables.CHEESE_PRESS), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Goat_Cheese.png"),
    BEER("Beer", "Drink in moderation", 50, 13, Map.of(new ForagingCrop(ForagingCrops.Hazelnut), 1), 200, () -> new Craftable(Craftables.KEG), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Beer.png"),
    VINEGAR("Vinegar", "An aged fermented liquid used in many recipes", 13, 10, Map.of(new ForagingCrop(ForagingCrops.Grape), 1), 100, () -> new Craftable(Craftables.KEG), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Ingredient/Vinegar.png"),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost", 75, 2, Map.of(new Crop(Crops.Coffee_Bean), 1), 150, () -> new Craftable(Craftables.KEG), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Coffee.png"),
    MEAD("Mead", "Drink in moderation", 100, 10, Map.of(new Industrial(Industrials.HONEY), 1), 300, () -> new Craftable(Craftables.KEG), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Mead.png"),
    PALE_ALE("Pale ale", "Drink in moderation", 50, 39, Map.of(new Crop(Crops.Wheat), 1), 300, () -> new Craftable(Craftables.KEG), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Pale_Ale.png"),
    RAISINS("Raisins", "It's said to be the Junimos' favorite food", 125, 600, Map.of(new ForagingCrop(ForagingCrops.Grape), 5), -1, () -> new Craftable(Craftables.DEHYDRATOR), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Raisins.png"),
    COAL("Coal", "Coal", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.WOOD), 10), 2, () -> new Craftable(Craftables.CHARCOAL_KILN), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Resource/Coal.png"),
    CLOTH("Cloth", "A bolt of fine wool cloth", -1, 470, Map.of(new AnimalProduct(AnimalProducts.WOOL), 1), 4, () -> new Craftable(Craftables.LOOM), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Cloth.png"),
    MAYONNAISE("Mayonnaise", "It looks spreadable", 50, 3, Map.of(new AnimalProduct(AnimalProducts.EGG), 1), 190, () -> new Craftable(Craftables.MAYONNAISE_MACHINE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Mayonnaise.png"),
    DUCK_MAYONNAISE("Duck mayonnaise", "It looks spreadable", 75, 3, Map.of(new AnimalProduct(AnimalProducts.DUCK_EGG), 1), 370, () -> new Craftable(Craftables.MAYONNAISE_MACHINE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Duck_Mayonnaise.png"),
    DINOSAUR_MAYONNAISE("Dinosaur mayonnaise", "It looks spreadable", 75, 3, Map.of(new AnimalProduct(AnimalProducts.DINOSAUR_EGG), 1), 800, () -> new Craftable(Craftables.MAYONNAISE_MACHINE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Dinosaur_Mayonnaise.png"),
    TRUFFLE_OIL("Truffle oil", "A gourmet cooking ingredient", 37, 8, Map.of(new AnimalProduct(AnimalProducts.TRUFFLE), 1), 1065, () -> new Craftable(Craftables.OIL_MAKER), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Artisan_good/Truffle_Oil.png"),
    OIL("Oil", "All purpose cooking oil", 13, 6, Map.of(new Crop(Crops.Corn), 1), 100, () -> new Craftable(Craftables.OIL_MAKER), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Ingredient/Oil.png"),
    COPPER_BAR("Copper bar", "Copper ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.COPPER_ORE), 5), 10, () -> new Craftable(Craftables.FURNACE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Resource/Copper_Bar.png"),
    IRON_BAR("Iron bar", "Iron ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.IRON_ORE), 5), 10, () -> new Craftable(Craftables.FURNACE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Iron_Bar.png"),
    GOLD_BAR("Gold bar", "Gold ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.GOLD_ORE), 5), 10, () -> new Craftable(Craftables.FURNACE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Gold_Bar.png"),
    IRIDIUM_BAR("Iridium bar", "Iridium ores smelt into a bar", -1, 10, Map.of(new ForagingMineral(ForagingMinerals.IRIDIUM_ORE), 5), 10, () -> new Craftable(Craftables.FURNACE), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crafting/Iridium_Bar.png");

    private final String name;
    private final String description;
    private final int processingTime;
    private final Map<Material, Integer> ingredients;
    private final Supplier<Craftable> craftableSupplier;
    private final Integer fixedEnergy;
    private final Integer fixedPrice;
    private final Predicate<Material> ingredientFilter;
    private final Function<Material, Integer> energyCalculator;
    private final Function<Material, Integer> priceCalculator;
    private final String imagePath;

    Industrials(String name, String description, Integer fixedEnergy, Integer fixedPrice,
                Map<Material, Integer> ingredients, int processingTime, Supplier<Craftable> craftableSupplier, String imagePath) {
        this(name, description, fixedEnergy, fixedPrice, ingredients, processingTime, craftableSupplier,
                null, null, null, imagePath);
    }

    Industrials(String name, String description, Integer fixedEnergy, Integer fixedPrice,
                int processingTime, Supplier<Craftable> craftableSupplier,
                Predicate<Material> ingredientFilter,
                Function<Material, Integer> energyCalculator,
                Function<Material, Integer> priceCalculator, String imagePath) {
        this(name, description, fixedEnergy, fixedPrice, null, processingTime, craftableSupplier,
                ingredientFilter, energyCalculator, priceCalculator, imagePath);
    }

    Industrials(String name, String description, Integer fixedEnergy, Integer fixedPrice,
                Map<Material, Integer> ingredients, int processingTime, Supplier<Craftable> craftableSupplier,
                Predicate<Material> ingredientFilter,
                Function<Material, Integer> energyCalculator,
                Function<Material, Integer> priceCalculator, String imagePath) {
        this.name = name;
        this.description = description;
        this.fixedEnergy = fixedEnergy;
        this.fixedPrice = fixedPrice;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.craftableSupplier = craftableSupplier;
        this.ingredientFilter = ingredientFilter;
        this.energyCalculator = energyCalculator;
        this.priceCalculator = priceCalculator;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
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

    public Craftable getCraftable() {
        return craftableSupplier.get();
    }

    public boolean isDynamic() {
        return ingredientFilter != null && energyCalculator != null && priceCalculator != null;
    }

    public boolean accepts(Material material) {
        return isDynamic() && ingredientFilter.test(material);
    }

    public int calculateEnergy(Material material) {
        return isDynamic() ? energyCalculator.apply(material) : fixedEnergy;
    }

    public int calculatePrice(Material material) {
        return isDynamic() ? priceCalculator.apply(material) : fixedPrice;
    }
}

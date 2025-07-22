package io.github.some_example_name.model.enums.crafting;



import io.github.some_example_name.model.enums.creature.AnimalProducts;
import io.github.some_example_name.model.enums.creature.FishTypes;
import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.enums.plantable.Crops;
import io.github.some_example_name.model.enums.plantable.Fruits;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Products.AnimalProduct;
import io.github.some_example_name.model.materials.Products.FishProducts;

import java.util.Map;

public enum Foods implements MaterialType {
    FRIED_EGG("Fried egg", Map.of(new AnimalProduct(AnimalProducts.EGG), 1), 50, 35),
    BAKED_FISH("Baked fish", Map.of(new FishProducts(FishTypes.SARDINE), 1, new FishProducts(FishTypes.SALMON), 1, new Crop(Crops.Wheat), 1), 75, 100),
    SALAD("Salad", Map.of(new ForagingCrop(ForagingCrops.Leek), 1, new ForagingCrop(ForagingCrops.Dandelion), 1), 113, 100),
    OMELET("Omelet", Map.of(new AnimalProduct(AnimalProducts.EGG), 1, new AnimalProduct(AnimalProducts.MILK), 1), 100, 125),
    PUMPKIN_PIE("Pumpkin pie", Map.of(new Crop(Crops.Pumpkin), 1, new AnimalProduct(AnimalProducts.MILK), 1, new Crop(Crops.Wheat), 1), 225, 385),
    SPAGHETTI("Spaghetti", Map.of(new Crop(Crops.Tomato), 1, new Crop(Crops.Wheat), 1), 75, 120),
    PIZZA("Pizza", Map.of(new Crop(Crops.Tomato), 1, new Industrial(Industrials.CHEESE), 1, new Crop(Crops.Wheat), 1  ), 150, 300),
    TORTILLA("Tortilla", Map.of(new Crop(Crops.Corn), 1), 50, 50),
    TRIPLE_SHOT_ESPRESSO("Triple shot espresso", Map.of(new Industrial(Industrials.COFFEE), 3), 200, 450),
    COOKIE("Cookie", Map.of(new AnimalProduct(AnimalProducts.EGG), 1, new Crop(Crops.Wheat), 1), 90, 140),
    HASH_BROWN("Hash brown", Map.of(new Crop(Crops.Potato), 1, new Industrial(Industrials.OIL), 1), 90, 120),
    PANCAKE("Pancake", Map.of(new AnimalProduct(AnimalProducts.EGG), 1), 90, 80),
    FRUIT_SALAD("Fruit salad", Map.of(new Fruit(Fruits.Apricot), 1, new Fruit(Fruits.Apple), 1, new Fruit(Fruits.Banana), 1), 263, 450),
    RED_PLATE("Red plate", Map.of(new Crop(Crops.Red_Cabbage), 1, new Crop(Crops.Radish), 1), 240, 400),
    BREAD("Bread", Map.of(), 50, 60),
    SALMON_DINNER("Salmon dinner", Map.of(new FishProducts(FishTypes.SALMON), 1, new Crop(Crops.Amaranth), 1, new Crop(Crops.Kale), 1), 125, 300),
    VEGETABLE_MEDLEY("Vegetable medley", Map.of(new Crop(Crops.Tomato), 1, new Crop(Crops.Beet), 1), 165, 120),
    FARMERS_LUNCH("Farmer's lunch", Map.of(new Food(Foods.OMELET), 1, new Crop(Crops.Parsnip),1 ), 200, 150),
    SURVIVAL_BURGER("Survival burger", Map.of(new Crop(Crops.Eggplant), 1, new Crop(Crops.Carrot), 1, new Food(Foods.BREAD), 1), 125, 180),
    DISH_O_THE_SEA("Dish O' the sea", Map.of(new FishProducts(FishTypes.SARDINE), 2, new Food(Foods.HASH_BROWN), 1), 150, 220),
    SEAFORM_PUDDING("Seaform pudding", Map.of(new FishProducts(FishTypes.FLOUNDER), 1, new FishProducts(FishTypes.MIDNIGHT_CARP), 1), 175, 300),
    MINERS_TREAT("Miner's treat", Map.of(new Crop(Crops.Carrot), 2, new AnimalProduct(AnimalProducts.MILK), 1, new Crop(Crops.Wheat), 1 ), 125, 200),
    ;

    private final String name;
    private final Map<Material, Integer> ingredients;
    private final int energy;
    private final int price;


    Foods(String name, Map<Material, Integer> ingredients, int Energy, int Price) {
        this.name = name;
        this.ingredients = ingredients;
        this.energy = Energy;
        this.price = Price;
    }


    public String getName() {
        return name;
    }

    public Map<Material, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPrice() {
        return price;
    }
}

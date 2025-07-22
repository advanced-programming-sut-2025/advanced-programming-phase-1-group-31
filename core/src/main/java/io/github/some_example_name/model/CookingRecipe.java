package io.github.some_example_name.model;



import io.github.some_example_name.model.enums.crafting.Foods;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingSeed;
import io.github.some_example_name.model.materials.Foraging.ForagingTree;
import io.github.some_example_name.model.materials.Products.AnimalProduct;
import io.github.some_example_name.model.materials.Products.FishProducts;

import java.util.Map;


public class CookingRecipe {
    private final Foods food;

    public CookingRecipe(Foods food){
        this.food = food;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<Material, Integer> entry : food.getIngredients().entrySet()){
            String name;

            Object key = entry.getKey();
            name = switch (key) {
                case AnimalProduct animalProduct -> animalProduct.getName();
                case FishProducts fishProducts -> fishProducts.getName();
                case ForagingCrop foragingCrop -> foragingCrop.getName();
                case ForagingSeed foragingSeed -> foragingSeed.getName();
                case ForagingTree foragingTree -> foragingTree.getName();
                case Food food1 -> food1.getName();
                case Industrial industrial -> industrial.getName();
                case Crop crop -> crop.getName();
                case Fruit fruit -> fruit.getName();
                case null, default -> throw new IllegalStateException("Unexpected value: " + key);
            };

            stringBuilder.append(entry.getValue())
                    .append(" ")
                    .append(name)
                    .append("\n");
        }

        return (this.food.getName() + "\nRequires: " + stringBuilder + "Price: " + this.food.getPrice());
    }

    public Foods getFood(){
        return this.food;
    }

}

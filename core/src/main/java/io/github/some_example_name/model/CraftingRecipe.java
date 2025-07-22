package io.github.some_example_name.model;



import io.github.some_example_name.model.enums.crafting.Craftables;
import io.github.some_example_name.model.materials.Crop;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Foraging.ForagingSeed;
import io.github.some_example_name.model.materials.Foraging.ForagingTree;
import io.github.some_example_name.model.materials.Fruit;
import io.github.some_example_name.model.materials.Industrial;
import io.github.some_example_name.model.materials.Material;

import java.util.Map;

public class CraftingRecipe {
    private final Craftables craftables;

    public CraftingRecipe(Craftables craftables){
        this.craftables = craftables;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<Material, Integer> entry : craftables.getIngredients().entrySet()){
            String name;

            Object key = entry.getKey();
            name = switch (key) {
                case ForagingMineral foragingMineral -> foragingMineral.getName();
                case ForagingTree foragingTree -> foragingTree.getName();
                case ForagingSeed foragingSeed -> foragingSeed.getName();
                case ForagingCrop foragingCrop -> foragingCrop.getName();
                case Fruit fruit -> fruit.getName();
                case Crop crop -> crop.getName();
                case Industrial industrial -> industrial.getName();
                case null, default -> throw new IllegalStateException("Unexpected value: " + key);
            };

            stringBuilder.append(entry.getValue())
                    .append(" ")
                    .append(name)
                    .append("\n");
        }
        return (this.craftables.getName() + ": " + this.craftables.getDescription() + "\nRequires: " + stringBuilder + "Price: " + this.craftables.getPrice());
    }

    public Craftables getCraftables() {
        return this.craftables;
    }
}

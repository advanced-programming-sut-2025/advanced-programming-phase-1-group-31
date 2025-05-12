package model;

import model.Tools.Axe;
import model.Tools.Pickaxe;
import model.Tools.Tool;
import model.enums.toolTypes.AxePickHoeType;
import model.enums.toolTypes.BackpackType;
import model.materials.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Backpack {
    private BackpackType backpackType = BackpackType.Initial;

    private final HashMap<Material, Integer> elements = new HashMap<>();


    private final ArrayList<Tool> tools = new ArrayList<>(
            Arrays.asList(
                    new Axe(AxePickHoeType.Initial),
                    new Pickaxe(AxePickHoeType.Initial)
            )
    );

    public BackpackType getBackpackType() {
        return backpackType;
    }

    public void setBackpackType(BackpackType backpackType) {
        this.backpackType = backpackType;
    }


    public Result addElementToBackpack(Material material, int amount) {
        if (material == null || material.getType() == null || amount <= 0) {
            return new Result(false, "Something went wrong! Please try again.");
        }

        for (Map.Entry<Material, Integer> entry : elements.entrySet()) {
            if (entry.getKey().getType().equals(material.getType())) {
                elements.put(entry.getKey(), amount + entry.getValue());
                return new Result(true, "It successfully added to backpack.");

            }
        }

        if (elements.size() < backpackType.getCapacity()) {
            elements.put(material, amount);
            return new Result(true, "It successfully added to backpack.");
        }
        return new Result(false, "You don't have enough capacity to carry more items.");
    }

    public Result removeElementFromBackpack(Material material, int amount) {
        if (material == null || material.getType() == null || amount <= 0) {
            return new Result(false, "Something went wrong! Please try again.");
        }

        for (Map.Entry<Material, Integer> entry : elements.entrySet()) {
            if (entry.getKey().getType().equals(material.getType())) {
                int currentAmount = entry.getValue();
                if (amount < currentAmount) {
                    elements.put(entry.getKey(), currentAmount - amount);
                    return new Result(true,
                            "Picked up " + amount + " of this item from the backpack.");
                } else if (amount == currentAmount) {
                    elements.remove(entry.getKey());
                    return new Result(true, "All of this item was picked up from the backpack.");
                } else {
                    return new Result(false, "You don't have enough of this item.");
                }
            }
        }
        return new Result(false, "You don't have this item in your backpack.");
    }

    public boolean isExistInBackpack(Material material) {
        return elements.keySet().stream()
                .anyMatch(m -> m.getType().equals(material.getType()));
    }

    public Result showBackPack(){
        StringBuilder result = new StringBuilder();
        result.append("Your Backpack:");
        for (Map.Entry<Material, Integer> entry : elements.entrySet()){
            result.append("\n").append(entry.getValue()).append(" of ").append(entry.getKey().getName());
        }
        return new Result(true, result.toString());
    }
}

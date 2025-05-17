package model;

import model.enums.toolTypes.AxePickHoeType;
import model.enums.toolTypes.BackpackType;
import model.enums.toolTypes.TrashCanType;
import model.enums.toolTypes.WateringCanType;
import model.materials.Material;
import model.materials.Tools.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Backpack {
    private BackpackType backpackType = BackpackType.Initial;

    private final HashMap<Material, Integer> elements = new HashMap<>();

    private final ArrayList<Tool> tools = new ArrayList<>(
            Arrays.asList(
                    new Hoe(AxePickHoeType.Initial),
                    new Pickaxe(AxePickHoeType.Initial),
                    new Axe(AxePickHoeType.Initial),
                    new WateringCan(WateringCanType.Initial),
                    new TrashCan(TrashCanType.Initial)
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

        if (elements.containsKey(material)) {
            int updatedAmount = elements.get(material) + amount;
            elements.put(material, updatedAmount);
            return new Result(true, "It successfully added to backpack.");
        }

        if (elements.size() < backpackType.getCapacity()) {
            elements.put(material, amount);
            return new Result(true, "It successfully added to backpack.");
        }

        return new Result(false, "You don't have enough capacity to carry more items.");
    }


    public Result removeElementFromBackpack(Material material, int amount) {
        if (material == null || material.getType() == null || amount < -1) {
            return new Result(false, "Something went wrong! Please try again.");
        }

        if (!elements.containsKey(material)) {
            return new Result(false, "You don't have this item in your backpack.");
        }

        int currentAmount = elements.get(material);

        if (amount == -1 || amount == currentAmount) {
            elements.remove(material);
            return new Result(true, "All of this item was picked up from the backpack.");
        } else if (amount < currentAmount) {
            elements.put(material, currentAmount - amount);
            return new Result(true, "Picked up " + amount + " of this item from the backpack.");
        } else {
            return new Result(false, "You don't have enough of this item.");
        }
    }

    public Material isExistInBackpackOrNull(Material material) {
        for (Material material1 : elements.keySet()) {
            if (material1.equals(material)) return material1;
        }
        return null;
    }

    public Material isExistInBackpackOrNull(String name) {
        for (Material material1 : elements.keySet()) {
            if (material1.getName().equalsIgnoreCase(name)) return material1;
        }
        return null;
    }

    public int howManyInBackpack(Material material){
        return elements.get(material);
    }

    public Tool isExistToolOrNull(Tool tool) {
        for (Tool tool1 : tools) {
            if (tool1.equals(tool)) return tool1;
        }
        return null;
    }

    public Result showBackPack() {
        StringBuilder result = new StringBuilder("Your Backpack:");
        for (Map.Entry<Material, Integer> entry : elements.entrySet()) {
            result.append("\n").append(entry.getValue()).append(" of ").append(entry.getKey().getName());
        }
        return new Result(true, result.toString());
    }

    public Result showTools() {
        StringBuilder result = new StringBuilder("Your Tools:");
        for (Tool tool : tools) {
            result.append("\n").append(tool.getName());
        }
        return new Result(true, result.toString());
    }

    public HashMap<Material, Integer> getElements() {
        return elements;
    }

    public ArrayList<Tool> getTools() {
        return tools;
    }
}
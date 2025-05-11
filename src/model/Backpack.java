package model;

import model.Tools.Tool;
import model.enums.toolTypes.BackpackType;
import model.materials.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Backpack {
    private BackpackType backpackType = BackpackType.Initial;

    private HashMap<Material, Integer> elements = new HashMap<>();
    private ArrayList<Tool> tools = new ArrayList<>();


    public BackpackType getBackpackType() {
        return backpackType;
    }

    public void setBackpackType(BackpackType backpackType) {
        this.backpackType = backpackType;
    }

    public boolean addElementToBackpack(Material material, int amount) {
        if (material == null || material.getType() == null || amount <= 0) {
            return false;
        }

        for (Map.Entry<Material, Integer> entry : elements.entrySet()) {
            if (entry.getKey().getType().equals(material.getType())) {
                elements.put(entry.getKey(), amount + entry.getValue());
                return true;
            }
        }

        if (elements.size() < backpackType.getCapacity()) {
            elements.put(material, amount);
            return true;
        } else return false;
    }

}

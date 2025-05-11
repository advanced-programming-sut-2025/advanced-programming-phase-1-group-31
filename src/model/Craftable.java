package model;

import model.enums.crafting.Craftables;
import model.materials.Material;
import model.materials.MaterialType;

public class Craftable implements Material {
    private Craftables craftableType;
    private final int energyConsumption = 2;

    @Override
    public MaterialType getType() {
        return craftableType;
    }
}

package model.materials.Tools;

import model.materials.Material;
import model.materials.MaterialType;

public class Shear implements Tool, Material {
    private int energyConsumption = 4;

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}

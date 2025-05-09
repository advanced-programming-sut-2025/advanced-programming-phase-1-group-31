package model.materials.Tools;

import model.materials.Material;

public class Shear implements Tool, Material {
    private int energyConsumption = 4;
    public void work() {

    }

    @Override
    public MaterialType getType() {
        return null;
    }
}

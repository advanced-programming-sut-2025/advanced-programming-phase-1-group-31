package model.materials.Tools;

import model.materials.Material;
import model.materials.MaterialType;

public class Scythe implements Tool, Material {
    private int energyConsumption = 2;

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}

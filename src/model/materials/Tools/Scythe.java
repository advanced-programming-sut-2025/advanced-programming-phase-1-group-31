package model.materials.Tools;

import model.materials.Material;

public class Scythe implements Tool, Material {
    private int energyConsumption = 2;
    public void work() {

    }

    @Override
    public MaterialType getType() {
        return null;
    }
}

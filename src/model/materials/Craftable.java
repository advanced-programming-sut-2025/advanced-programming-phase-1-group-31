package model.materials;

import model.enums.crafting.Craftables;

public class Craftable implements Material {
    private Craftables craftableType;
    private final int energyConsumption = 2;


    @Override
    public Craftables getType() {
        return craftableType;
    }

    @Override
    public String getName() {
        return craftableType.name();
    }
}

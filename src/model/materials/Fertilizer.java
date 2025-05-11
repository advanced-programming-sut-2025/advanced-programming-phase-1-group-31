package model.materials;

import model.enums.plantable.Fertilizers;

public class Fertilizer implements Material {
    private Fertilizers fertilizerType;

    @Override
    public MaterialType getType() {
        return fertilizerType;
    }
}

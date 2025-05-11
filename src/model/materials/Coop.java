package model.materials;

import model.enums.creature.CoopsAndBarnsTypes;

public class Coop implements Material {
    private CoopsAndBarnsTypes coopType;

    @Override
    public MaterialType getType() {
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

}

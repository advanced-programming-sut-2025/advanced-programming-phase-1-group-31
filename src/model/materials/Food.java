package model.materials;

import model.enums.crafting.Foods;
import model.materials.Material;

public class Food implements Material {
    private Foods foodType;


    @Override
    public MaterialType getType() {
        return foodType;
    }
}

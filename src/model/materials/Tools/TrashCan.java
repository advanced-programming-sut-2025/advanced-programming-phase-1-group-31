package model.materials.Tools;

import model.enums.toolTypes.TrashCanType;
import model.materials.Material;
import model.materials.MaterialType;

public class TrashCan implements Tool, Material {
    private TrashCanType trashCanType = TrashCanType.Initial;


    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public void work() {

    }
}

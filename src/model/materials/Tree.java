package model.materials;


import model.enums.plantable.Trees;
import model.materials.Tools.MaterialType;

public class Tree implements Material{
    private Trees treeType;



    @Override
    public MaterialType getType() {
        return treeType;
    }
}

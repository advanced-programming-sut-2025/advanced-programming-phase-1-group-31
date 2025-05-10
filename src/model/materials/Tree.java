package model.materials;


import model.enums.plantable.Trees;

public class Tree implements Material{
    private Trees treeType;

    public Trees getTreeType() {
        return treeType;
    }

    public void setTreeType(Trees treeType) {
        this.treeType = treeType;
    }

    @Override
    public MaterialType getType() {
        return treeType;
    }
}

package model.materials;


import model.enums.plantable.Trees;

import java.util.Objects;

public class Tree implements Material{
    private Trees treeType;

    public Tree(Trees treeType) {
        this.treeType = treeType;
    }

    public void setTreeType(Trees treeType) {
        this.treeType = treeType;
    }

    @Override
    public MaterialType getType() {
        return treeType;
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}

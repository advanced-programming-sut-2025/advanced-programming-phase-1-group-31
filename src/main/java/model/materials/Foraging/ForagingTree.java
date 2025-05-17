package model.materials.Foraging;

import model.enums.foragings.ForagingTrees;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class ForagingTree implements Material {
    private ForagingTrees foragingTree;

    public ForagingTree(ForagingTrees foragingTree) {
        this.foragingTree = foragingTree;
    }

    public ForagingTrees getForagingTree() {
        return foragingTree;
    }

    public void setForagingTree(ForagingTrees foragingTree) {
        this.foragingTree = foragingTree;
    }

    @Override
    public MaterialType getType() {
        return foragingTree;
    }

    @Override
    public String getName() {
        return foragingTree.getName();
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

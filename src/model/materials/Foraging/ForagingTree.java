package model.materials.Foraging;

import model.enums.foragings.ForagingTrees;
import model.materials.Material;
import model.materials.MaterialType;

public class ForagingTree implements Material {
    private ForagingTrees foragingTree;


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
}

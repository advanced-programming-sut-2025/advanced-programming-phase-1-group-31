package model.materials.Foraging;

import model.enums.foragings.ForagingTrees;
import model.materials.Material;
import model.materials.Tools.MaterialType;

public class ForagingTree implements Material {
    private ForagingTrees foragingTree;

    @Override
    public MaterialType getType() {
        return foragingTree;
    }
}

package io.github.some_example_name.model.materials.Foraging;




import io.github.some_example_name.model.enums.foragings.ForagingTrees;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Objects;

public class ForagingTree implements Material {
    private final ForagingTrees foragingTree;

    public ForagingTree(ForagingTrees foragingTree) {
        this.foragingTree = foragingTree;
    }

    public ForagingTrees getForagingTree() {
        return foragingTree;
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
    @Override
    public String getTexturePath() {
        return "";
    }
}

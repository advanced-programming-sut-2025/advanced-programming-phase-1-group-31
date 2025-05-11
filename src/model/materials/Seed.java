package model.materials;

import model.enums.plantable.Seeds;
import model.enums.plantable.Trees;

import java.util.List;

import model.enums.plantable.Crops;

public class Seed implements Material {
    private Seeds sourceName;
    private int daysInCurrentStage = 0;
    
    public Seed(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    public void grow() {
        daysInCurrentStage++;
//        Crops crop = getCorrespondingCrop();
//
//        if (crop != null) {
//            List<Integer> stages = crop.getStages();
//            if (daysInCurrentStage < stages.size() - 1) {
//                if (daysInCurrentStage >= stages.get(growthStage)) {
//                    growthStage++;
//                    daysInCurrentStage = 0;
//                }
//            }
//        }
    }
    
    public boolean isReadyToTreeOrCrop() {
        Crops crop = getCorrespondingCrop();
        Trees tree = getCorrespondingTrees();
        if (crop != null) return daysInCurrentStage >= crop.getStages().get(0) ;
        if (tree != null) return daysInCurrentStage >= tree.getStages().get(0) ;
        return false;
    }
    
    public Crops getCorrespondingCrop() {
        for (Crops crop : Crops.values()) {
            if (crop.getSource() == this.sourceName) {
                return crop;
            }
        }
        return null;
    }
    public Trees getCorrespondingTrees() {
        for (Trees tree : Trees.values()) {
            if (tree.getSource() == this.sourceName) {
                return tree;
            }
        }
        return null;
    }
    

    @Override
    public MaterialType getType() {
        return sourceName;
    }

    // Other existing methods...
}
package model.materials;

import model.enums.plantable.Seeds;
import model.enums.plantable.Trees;

import java.util.List;

import model.enums.plantable.Crops;

public class Seed implements Material {
    private Seeds sourceName;
    private int daysInStage = 0;
    private int currentStage = 0;
    private int daysWithoutWater = 0;

    public int getDaysInStage() {
        return daysInStage;
    }

    public void setDaysInStage(int daysInStage) {
        this.daysInStage = daysInStage;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

//    public void nextDay() {
//        if (isFullyGrown()) return;
//        Crops crop = getCorrespondingCrop();
//        if (crop != null)
//        {
//            if (daysInStage >= crop.getStages().get(currentStage)) {
//                currentStage++;
//                daysInStage = 0;
//            }
//        }
//        Trees trees = getCorrespondingTrees();
//        if (trees != null) {
//            if (daysWithoutWater >= trees.getStages().get(currentStage)) {
//                currentStage++;
//                daysInStage = 0;
//            }
//        }
//
//        daysInStage++;
//
//
//    }
    public boolean isFullyGrown() {
        Crops crop = getCorrespondingCrop();
        if (crop != null)
        {
            return currentStage >= crop.getStages().size();
        }
        Trees trees = getCorrespondingTrees();
        if (trees != null) {
            return currentStage >= trees.getStages().size();
        }
        return false;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }



    public Seed(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    public void grow() {
        daysInStage++;
        daysWithoutWater++;
        if (daysWithoutWater >= 2) {
            System.out.println("The plant has died due to lack of water for two consecutive days.");
            this.sourceName = null;
            return;
        }
        if (isFullyGrown()) return;
        Crops crop = getCorrespondingCrop();
        if (crop != null)
        {
            if (daysInStage >= crop.getStages().get(currentStage)) {
                currentStage++;
                daysInStage = 0;
            }
        }
        Trees trees = getCorrespondingTrees();
        if (trees != null) {
            if (daysWithoutWater >= trees.getStages().get(currentStage)) {
                currentStage++;
                daysInStage = 0;
            }
        }

        // Crops crop = getCorrespondingCrop();
        //
        // if (crop != null) {
        // List<Integer> stages = crop.getStages();
        // if (daysInCurrentStage < stages.size() - 1) {
        // if (daysInCurrentStage >= stages.get(growthStage)) {
        // growthStage++;
        // daysInCurrentStage = 0;
        // }
        // }
        // }
    }

//    public boolean isReadyToTreeOrCrop() {
//        Crops crop = getCorrespondingCrop();
//        Trees tree = getCorrespondingTrees();
//        if (crop != null)
//            return currentStage >= crop.getStages().get(0);
//        if (tree != null)
//            return currentStage >= tree.getStages().get(0);
//        return false;
//    }

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

    public String getPlantInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Plant name: ").append(sourceName.getName()).append("\n");
        info.append("Current growth stage: ").append(currentStage + 1).append(" out of ").append(
                (getCorrespondingCrop() != null ? getCorrespondingCrop().getStages().size() :
                        getCorrespondingTrees() != null ? getCorrespondingTrees().getStages().size() : 0)).append("\n");
        info.append("Days in current stage: ").append(daysInStage).append("\n");

        int remainingDays = 0;
        List<Integer> stages = getCorrespondingCrop() != null ?
                getCorrespondingCrop().getStages() :
                getCorrespondingTrees() != null ?
                        getCorrespondingTrees().getStages() : null;

        if (stages != null) {
            for (int i = currentStage; i < stages.size(); i++) {
                remainingDays += stages.get(i);
            }
            info.append("Days remaining until harvest: ").append(remainingDays - daysInStage).append("\n");
        }

        info.append("Watered today? ").append(daysWithoutWater==0 ? "Yes" : "No");
        return info.toString();
    }


    // Other existing methods...
}
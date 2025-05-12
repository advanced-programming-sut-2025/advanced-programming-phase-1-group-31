package model.materials;

import model.enums.plantable.Crops;


public class Crop implements Material{
    private Crops crop;
    private int currentStage = 0;
    private int daysInStage = 0;

    public void PlantedCrop(Crops crop) {
        this.crop = crop;
    }

    public Crop(Crops crop) {
        this.crop = crop;
    }

    public void nextDay() {
        if (isFullyGrown()) return;

        daysInStage++;

        if (daysInStage >= crop.getStages().get(currentStage)) {
            currentStage++;
            daysInStage = 0;
        }
    }

    public boolean isFullyGrown() {
        return currentStage >= crop.getStages().size();
    }

    public Crops getCrop() {
        return crop;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public int getDaysInStage() {
        return daysInStage;
    }

    public boolean isHarvestable() {
        return isFullyGrown();
    }

    public void resetForRegrow() {
        if (!crop.isOneTime() && crop.getRegrowthTime() != null) {
            currentStage = crop.getStages().size() - 1;
            daysInStage = 0;
        }
    }

    @Override
    public MaterialType getType() {
        return crop;
    }

    @Override
    public String getName() {
        return crop.getDisplayName();
    }
}

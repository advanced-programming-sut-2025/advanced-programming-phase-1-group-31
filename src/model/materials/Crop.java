package model.materials;

import model.enums.plantable.Crops;


public class Crop implements Material{
    private Crops crop;
    private int daysInStage = 0;
    private int amount = 1;

    public void PlantedCrop(Crops crop) {
        this.crop = crop;
    }

    public Crop(Crops crop) {
        this.crop = crop;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void nextDay() {
        if (crop.isOneTime()) {
            return;
        }
        daysInStage++;
        if (daysInStage >= crop.getRegrowthTime()) {
            amount++;
            daysInStage = 0;
        }
    }


    public Crops getCrop() {
        return crop;
    }



    public int getDaysInStage() {
        return daysInStage;
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

package io.github.some_example_name.model.materials;


import io.github.some_example_name.model.enums.plantable.Crops;

import java.util.Objects;


public class Crop implements Material{
    private Crops crop;
    private int daysInStage = 0;
    private int daysWithoutWater = 0;
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

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
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

    @Override
    public int baseSellPrice() {
        return crop.getBaseSellPrice();
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

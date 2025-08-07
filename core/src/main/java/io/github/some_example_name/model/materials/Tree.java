package io.github.some_example_name.model.materials;



import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.some_example_name.model.enums.plantable.Crops;
import io.github.some_example_name.model.enums.plantable.Trees;

import java.awt.*;
import java.util.Objects;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class Tree implements Material{
    private Trees treeType;
    private int daysWithoutWater = 0;
    private int daysInStage = 0;
    private int amount = 1;
    private int currentStage = 0;
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }

    public void nextDay() {
        daysInStage++;
        if (daysInStage >= treeType.getHarvestCycle()) {
            amount++;
            daysInStage = 0;
        }
    }
    public boolean isFullyGrown() {

            return currentStage >= treeType.getStages().size();

    }

    public void grow(TiledMap map , Point point) {
        daysInStage++;
        if (daysWithoutWater >= 2) {
            System.out.println("The plant has died due to lack of water for two consecutive days.");
            return;
        }

        if (daysInStage >= treeType.getStages().get(currentStage).days()) {
            currentStage++;
            //change image
            placeScaledImageAsTile(map , "craft", point.x, point.y, treeType.getStages().get(currentStage).imagePath());
            daysInStage = 0;
        }

    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public Tree(Trees treeType) {
        this.treeType = treeType;
    }

    public Trees getTreeType() {
        return treeType;
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
        return treeType.getFruit().getBaseSellPrice();
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

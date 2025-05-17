package model.materials;


import model.enums.plantable.Trees;

public class Tree implements Material{
    private Trees treeType;
    private int daysWithoutWater = 0;
    private int daysInStage = 0;
    private int amount = 1;
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
}

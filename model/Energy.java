package model;

public class Energy {
    private int energyAmount;
    private int maxEnergy = 200;

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    // For CheatCode
    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
        if (energyAmount > maxEnergy)
            energyAmount = maxEnergy;
        if (energyAmount <= 0) {
            faint();
        }
    }

    public void changeEnergy(int changeEnergyAmount) {
        energyAmount += changeEnergyAmount;
        if (energyAmount > maxEnergy)
            energyAmount = maxEnergy;
        if (energyAmount <= 0) {
            faint();
        }
    }

    private void faint() {
        //to be added
    }


}

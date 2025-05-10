package model;

public class Energy {
    private Double energyAmount;
    private Double maxEnergy = 200.0;

    public void setMaxEnergy(Double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getEnergyAmount() {
        return energyAmount;
    }

    public Double getMaxEnergy() {
        return maxEnergy;
    }

    // For CheatCode
    public void setEnergyAmount(Double energyAmount) {
        this.energyAmount = energyAmount;
        if (energyAmount > maxEnergy)
            energyAmount = maxEnergy;
        if (energyAmount <= 0) {
            faint();
        }
    }

    public void changeEnergy(Double changeEnergyAmount) {
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

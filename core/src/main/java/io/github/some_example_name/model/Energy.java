package io.github.some_example_name.model;

import com.badlogic.gdx.utils.Timer;

public class Energy {
    private float energyAmount;
    private float maxEnergy = 200.0f;

    public void setMaxEnergy(float maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public float getEnergyAmount() {
        return energyAmount;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }

    // For CheatCode
    public void setEnergyAmount(float energyAmount) {
        this.energyAmount = energyAmount;
        if (energyAmount > maxEnergy)
            energyAmount = maxEnergy;
        if (energyAmount <= 0) {
            faint();
        }
    }

    public void changeEnergy(double changeEnergyAmount) {
        energyAmount += changeEnergyAmount;
        if (energyAmount > maxEnergy)
            energyAmount = maxEnergy;
        if (energyAmount <= 0) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    faint();
                }
            }, 3f); // زمان بر حسب ثانیه
        }
    }
        private void faint() {
//         Player player = App.getCurrentGame().getActivePlayer();

//             player.setLocation(player.getLastPositionBeforeFaint());

//         player.setLastPositionBeforeFaint(player.getLocation());

//        GameApp.changeTurn();
    }

    public void onNewDay() {
        if (this.energyAmount<=0) {
            this.energyAmount = maxEnergy * 0.75f;
            // player.setLocation(player.getLastPositionBeforeFaint());
        } else {
            this.energyAmount = maxEnergy;
        }
    }

}

package model.enums.toolTypes;

import model.materials.MaterialType;

public enum AxePickHoeType implements MaterialType {
    Initial(5){
        @Override
        public void work() {

        }
    },
    Copper(4){
        @Override
        public void work() {

        }
    },
    Iron(3){
        @Override
        public void work() {

        }
    },
    Gold(2){
        @Override
        public void work() {

        }
    },
    Iridium(1){
        @Override
        public void work() {

        }
    };

    private final int energyConsumption;

    AxePickHoeType(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public abstract void work();
}

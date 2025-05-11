package model.enums.toolTypes;

import model.materials.MaterialType;

public enum TrashCanType implements MaterialType {
    Initial(0){
        @Override
        public void work() {

        }
    },
    Copper(15){
        @Override
        public void work() {

        }
    },
    Iron(30){
        @Override
        public void work() {

        }
    },
    Gold(45){
        @Override
        public void work() {

        }
    },
    Iridium(60){
        @Override
        public void work() {

        }
    };

    private final int moneyReturnedPercentage;

    TrashCanType(int moneyReturnedPercentage) {
        this.moneyReturnedPercentage = moneyReturnedPercentage;
    }

    public int getEnergyConsumption() {
        return moneyReturnedPercentage;
    }

    public abstract void work();

}

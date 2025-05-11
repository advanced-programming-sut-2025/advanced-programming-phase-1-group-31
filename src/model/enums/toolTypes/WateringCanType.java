package model.enums.toolTypes;

import model.materials.MaterialType;

public enum

WateringCanType implements MaterialType {
    Initial(40){
        @Override
        public void work() {

        }
    },
    Copper(55){
        @Override
        public void work() {

        }
    },
    Iron(70){
        @Override
        public void work() {

        }
    },
    Gold(85){
        @Override
        public void work() {

        }
    },
    Iridium(100){
        @Override
        public void work() {

        }
    };

    private int capacity;

    WateringCanType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public abstract void work();

}

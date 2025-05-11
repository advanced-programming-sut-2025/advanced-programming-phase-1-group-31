package model.enums.toolTypes;

import model.materials.MaterialType;

public enum FishingPoleType implements MaterialType {
    Training(8, false){
        @Override
        public void work() {

        }
    },
    Bamboo(8, true){
        @Override
        public void work() {

        }
    },
    Fiberglass(6, true){
        @Override
        public void work() {

        }
    },
    Iridium(4, true){
        @Override
        public void work() {

        }
    };

    private final int energyConsumption;
    private final boolean canCatchAllFishes;

    FishingPoleType(int energyConsumption, boolean canCatchAllFishes) {
        this.energyConsumption = energyConsumption;
        this.canCatchAllFishes = canCatchAllFishes;
    }

    public boolean isCanCatchAllFishes() {
        return canCatchAllFishes;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public abstract void work();
}

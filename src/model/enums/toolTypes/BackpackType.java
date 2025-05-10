package model.enums.toolTypes;

import model.materials.MaterialType;

public enum BackpackType implements MaterialType {
    Initial(12),
    Large(24),
    Deluxe(Integer.MAX_VALUE);

    private final int capacity;


    BackpackType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

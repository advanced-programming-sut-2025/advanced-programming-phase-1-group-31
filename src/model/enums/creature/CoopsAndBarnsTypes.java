package model.enums.creature;

import model.materials.MaterialType;

public enum CoopsAndBarnsTypes implements MaterialType {
    COOP(4, "Coop"),
    LARGE_COOP(8, "Large Coop"),
    DELUXE_COOP(12, "Deluxe Coop"),
    BARN(4, "Barn"),
    LARGE_BARN(8, "Large Barn"),
    DELUXE_BARN(12, "Deluxe Barn");

    private final int capacity;
    private final String displayName;

    CoopsAndBarnsTypes(int capacity, String displayName) {
        this.capacity = capacity;
        this.displayName = displayName;
    }

    public int getCapacity() {
        return capacity;
    }
    public boolean isBarn() {
        return this == BARN || this == LARGE_BARN || this == DELUXE_BARN;
    }
    public String getDisplayName() {
        return displayName;
    }

    // Add this method to get an enum value from a display name
    public static CoopsAndBarnsTypes fromName(String name) {
        for (CoopsAndBarnsTypes type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}
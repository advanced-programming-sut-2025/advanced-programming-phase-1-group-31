package io.github.some_example_name.model.enums.creature;


import io.github.some_example_name.model.materials.MaterialType;

public enum CoopsAndBarnsTypes implements MaterialType {
    COOP(4, "Coop", "Content (unpacked)/Buildings/Coop.png"),
    LARGE_COOP(8, "Large Coop", "Content (unpacked)/Buildings/Big Coop.png"),
    DELUXE_COOP(12, "Deluxe Coop", "Content (unpacked)/Buildings/Deluxe Coop.png"),
    BARN(4, "Barn", "Content (unpacked)/Buildings/Barn.png"),
    LARGE_BARN(8, "Large Barn", "Content (unpacked)/Buildings/Big Barn.png"),
    DELUXE_BARN(12, "Deluxe Barn", "Content (unpacked)/Buildings/Deluxe Barn.png");
    private final int capacity;
    private final String displayName;
    private final String imagePath;

    CoopsAndBarnsTypes(int capacity, String displayName, String imagePath) {
        this.capacity = capacity;
        this.displayName = displayName;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    // Add this method to get an enum value from a display name
    public static CoopsAndBarnsTypes fromName(String name) {
        for (CoopsAndBarnsTypes type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}

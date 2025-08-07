package io.github.some_example_name.model.enums.toolTypes;


import io.github.some_example_name.model.materials.MaterialType;

public enum

WateringCanType implements MaterialType {
    Initial("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Watering_Can/Watering_Can.png", 40, 5 ),
    Copper("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Watering_Can/Copper_Watering_Can.png", 55, 4),
    Iron("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Watering_Can/Steel_Watering_Can.png", 70, 3),
    Gold("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Watering_Can/Gold_Watering_Can.png", 85, 2),
    Iridium("project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Watering_Can/Iridium_Watering_Can.png", 100, 1);

    private final String wateringCanName;
    private final int capacity;
    private final int energyConsumption;

    WateringCanType(String wateringCanName, int capacity, int energyConsumption) {
        this.wateringCanName = wateringCanName;
        this.capacity = capacity;
        this.energyConsumption = energyConsumption;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public String getWateringCanName() {
        return wateringCanName;
    }
}

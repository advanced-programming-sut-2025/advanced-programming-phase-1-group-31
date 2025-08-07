package io.github.some_example_name.model.enums.toolTypes;

import io.github.some_example_name.model.materials.MaterialType;

public enum AxePickHoeType implements MaterialType {
    Initial(5, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Axe/Axe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Pickaxe/Pickaxe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Hoe/Hoe.png"),
    Copper(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Axe/Copper_Axe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Pickaxe/Copper_Pickaxe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Hoe/Copper_Hoe.png"),
    Iron(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Axe/Steel_Axe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Pickaxe/Steel_Pickaxe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Hoe/Steel_Hoe.png"),
    Gold(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Axe/Gold_Axe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Pickaxe/Gold_Pickaxe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Hoe/Gold_Hoe.png"),
    Iridium(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Axe/Iridium_Axe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Pickaxe/Iridium_Pickaxe.png", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Hoe/Iridium_Hoe.png");

    private final int energyConsumption;
    private final String axeName;
    private final String pickName;
    private final String hoeName;

    AxePickHoeType(int energyConsumption, String axeName, String pickName, String hoeName) {
        this.energyConsumption = energyConsumption;
        this.axeName = axeName;
        this.pickName = pickName;
        this.hoeName = hoeName;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public String getAxeName() {
        return axeName;
    }

    public String getPickName() {
        return pickName;
    }

    public String getHoeName() {
        return hoeName;
    }
}

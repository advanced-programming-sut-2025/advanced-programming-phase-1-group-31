package io.github.some_example_name.model.enums.toolTypes;


import io.github.some_example_name.model.materials.Tools.*;

public enum ToolTypes {
    AXE(new Axe(AxePickHoeType.Initial), "Axe"),
    FISHING_POLE(new FishingPole(FishingPoleType.Training), "FishingPole"),
    HOE(new Hoe(AxePickHoeType.Initial), "Hoe"),
    MILK_PAIL(new MilkPail(), "MilkPail"),
    PICKAXE(new Pickaxe(AxePickHoeType.Initial), "Pickaxe"),
    SCYTHE(new Scythe(), "Scythe"),
    SHEAR(new Shear(), "Shear"),
    WATERING_CAN(new WateringCan(WateringCanType.Initial), "WateringCan");

    private final Tool tool;
    private final String toolName;

    ToolTypes(Tool tool, String toolName) {
        this.tool = tool;
        this.toolName = toolName;
    }

    public Tool getToolInstance() {
        return tool;
    }

    public static Tool fromString(String text) {
        for (ToolTypes type : ToolTypes.values()) {
            if (type.toolName.equalsIgnoreCase(text)) {
                return type.getToolInstance();
            }
        }
        return null;
    }
}

package io.github.some_example_name.model.enums.toolTypes;


import io.github.some_example_name.model.materials.MaterialType;

public enum TrashCanType implements MaterialType {
    Initial(0, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Trash_Can_Steel.png"),
    Copper(15, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Trash_Can_Copper.png"),
    Iron(30, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Trash_Can_Steel.png"),
    Gold(45, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Trash_Can_Gold.png"),
    Iridium(60, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Trash_Can_Iridium.png");

    private final int moneyReturnedPercentage;
    private final String trashCanName;

    TrashCanType(int moneyReturnedPercentage, String trashCanName) {
        this.moneyReturnedPercentage = moneyReturnedPercentage;
        this.trashCanName = trashCanName;
    }

    public int getEnergyConsumption() {
        return moneyReturnedPercentage;
    }

    public String getTrashCanName() {
        return trashCanName;
    }
}

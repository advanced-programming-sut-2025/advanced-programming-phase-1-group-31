package io.github.some_example_name.model.materials.Tools;



import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Backpack;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.toolTypes.TrashCanType;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.Objects;

public class TrashCan implements Tool, Material {
    private TrashCanType trashCanType;

    public TrashCan(TrashCanType trashCanType) {
        this.trashCanType = trashCanType;
    }

    public TrashCanType getTrashCanType() {
        return trashCanType;
    }

    public void setTrashCanType(TrashCanType trashCanType) {
        this.trashCanType = trashCanType;
    }

    public Result work(String name, int amount) {
        Backpack backpack = App.getCurrentGame().getActivePlayer().getInventory();
        Material material = backpack.isExistInBackpackOrNull(name);
        if (material == null) return new Result(false, "You don't have " + name + " in your backpack");
        int totalAmount = backpack.howManyInBackpack(material);
        Result result = backpack.removeElementFromBackpack(material, amount);
        if (!result.Success()) return result;
        if (amount == -1) {
            App.getCurrentGame().getActivePlayer().addMoney(material.baseSellPrice() * 0.7 * totalAmount);
            return  new Result(true, "All of " + name + " have been deleted");
        }
        App.getCurrentGame().getActivePlayer().addMoney(material.baseSellPrice() * 0.7 * amount);
        return new Result(true, amount + " of " + name + " have been deleted");
    }


    @Override
    public MaterialType getType() {
        return trashCanType;
    }

    @Override
    public String getTexturePath() {
        return trashCanType.getTrashCanName();
    }

    @Override
    public String getName() {
        return "Trash Can " + trashCanType.name();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public Result work(Direction direction) {
        return new Result(true, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool tool)) return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}

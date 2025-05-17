package model.materials.Tools;

import model.App;
import model.Backpack;
import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.TrashCanType;
import model.materials.Material;
import model.materials.MaterialType;

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

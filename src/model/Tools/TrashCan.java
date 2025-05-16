package model.Tools;

import model.App;
import model.Backpack;
import model.Game;
import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.TrashCanType;
import model.materials.Material;

import java.util.Objects;

public class TrashCan implements Tool {
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
        Result result = backpack.removeElementFromBackpack(material, amount);
        if (!result.Success()) return result;
//        addingMoney();
        if (amount == -1) return  new Result(true, "All of " + name + " have been deleted");
        return new Result(true, amount + " of " + name + " have been deleted");
    }


    @Override
    public String getName() {
        return "Trash Can " + trashCanType.name();
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

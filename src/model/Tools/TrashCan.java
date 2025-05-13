package model.Tools;

import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.TrashCanType;

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

    @Override
    public Result work(Direction direction) {

    }

    @Override
    public String getName() {
        return "Trash Can " + trashCanType.name();
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

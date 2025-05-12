package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.TrashCanType;

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
    public void work(Direction direction) {

    }
}

package model.Tools;

import model.enums.toolTypes.TrashCanType;

public class TrashCan implements Tool {
    private TrashCanType trashCanType = TrashCanType.Initial;

    public TrashCanType getTrashCanType() {
        return trashCanType;
    }

    public void setTrashCanType(TrashCanType trashCanType) {
        this.trashCanType = trashCanType;
    }
    public void work() {

    }

}

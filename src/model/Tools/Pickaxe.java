package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

import java.util.Objects;

public class Pickaxe implements Tool {
    private AxePickHoeType pickaxeType;

    public Pickaxe(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public void setPickaxeType(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public AxePickHoeType getPickaxeType() {
        return pickaxeType;
    }

    @Override
    public void work(Direction direction) {

    }

    @Override
    public String getName() {
        return "Pick Axe " + pickaxeType.name();
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
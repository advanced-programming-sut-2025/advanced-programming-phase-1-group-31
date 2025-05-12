package model.Tools;

import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

import java.util.Objects;

public class Hoe implements Tool{
    private AxePickHoeType hoeType;

    public Hoe(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public AxePickHoeType getHoeType() {
        return hoeType;
    }

    @Override
    public void work(Direction direction) {

    }

    @Override
    public String getName() {
        return "Hoe " + hoeType.name();
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
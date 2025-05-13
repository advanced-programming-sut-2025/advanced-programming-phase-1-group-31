package model.Tools;

import model.Result;
import model.enums.general.Direction;
import model.enums.toolTypes.AxePickHoeType;

import java.util.Objects;

public class Axe implements Tool {
    private AxePickHoeType axeType;

    public Axe(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public void setAxeType(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public AxePickHoeType getAxeType() {
        return axeType;
    }

    @Override
    public Result work(Direction direction) {


    }

    @Override
    public String getName() {
        return "Axe " + axeType.name();
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

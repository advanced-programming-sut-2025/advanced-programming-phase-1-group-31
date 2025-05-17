package model.Tools;

import model.Result;
import model.enums.general.Direction;

import java.util.Objects;

public class MilkPail implements Tool {
    private int energyConsumption = 4;

    @Override
    public Result work(Direction direction) {
        return null;

    }

    @Override
    public String getName() {
        return "Milk Pail";
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
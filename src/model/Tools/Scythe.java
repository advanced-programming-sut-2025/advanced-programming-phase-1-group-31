package model.Tools;

import model.enums.general.Direction;

import java.util.Objects;

public class Scythe implements Tool{
    private int energyConsumption = 2;

    @Override
    public void work(Direction direction) {

    }

    @Override
    public String getName() {
        return "Scythe";
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

package model.materials;

import model.enums.plantable.Seeds;

import java.util.Objects;

public class Seed implements Material {
    private Seeds sourceName;

    public Seed(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    public void setSourceName(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public Seeds getType() {
        return sourceName;
    }

    @Override
    public String getName() {
        return sourceName.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}

package model.materials;

import model.enums.plantable.Seeds;

public class Seed implements Material {
    private Seeds sourceName;

    public Seed(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    public void setSourceName(Seeds sourceName) {
        this.sourceName = sourceName;
    }

    public Seeds getType() {
        return sourceName;
    }
}

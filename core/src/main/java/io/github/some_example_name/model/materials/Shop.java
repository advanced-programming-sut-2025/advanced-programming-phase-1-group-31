package io.github.some_example_name.model.materials;


import io.github.some_example_name.model.enums.npc.Shops;

import java.util.Objects;

public class Shop implements Material {
    private final Shops shopName;

    public Shop(Shops shopName) {
        this.shopName = shopName;
    }

    public Shops getShopName() {
        return shopName;
    }

    @Override
    public MaterialType getType() {
        return shopName;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int baseSellPrice() {
        return 0;
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
    @Override
    public String getTexturePath() {
        return "";
    }
}

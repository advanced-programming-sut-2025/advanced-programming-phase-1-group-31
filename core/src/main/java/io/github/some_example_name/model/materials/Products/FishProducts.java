package io.github.some_example_name.model.materials.Products;



import io.github.some_example_name.model.enums.creature.FishTypes;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.ProductQualityCalculator.ProductQuality;
import io.github.some_example_name.model.materials.MaterialType;


import java.util.Objects;

public class FishProducts implements Material {
    private final FishTypes fishType;
    private ProductQuality quality;
    private int quantity;

    public FishProducts(FishTypes fishType, ProductQuality quality, int quantity) {
        this.fishType = fishType;
        this.quality = quality;
        this.quantity = quantity;
    }

    public FishProducts(FishTypes fishType) {
        this.fishType = fishType;
    }

    public FishTypes getFishType() {
        return fishType;
    }

    public ProductQuality getQuality() {
        return quality;
    }


    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return fishType.getBasePrice() * quality.getPriceMultiplier() * quantity;
    }


    @Override
    public MaterialType getType() {
        return fishType;
    }

    @Override
    public String getName() {
        return fishType.name();
    }

    @Override
    public String toString() {
        return quantity + "x " + quality.name() + " " + fishType.name();
    }

    @Override
    public int baseSellPrice() {
        return (int) getTotalPrice();
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


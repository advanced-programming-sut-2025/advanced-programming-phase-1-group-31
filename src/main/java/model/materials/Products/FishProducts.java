package model.materials.Products;

import model.enums.creature.FishTypes;
import model.ProductQualityCalculator.ProductQuality;
import model.materials.Material;
import model.materials.MaterialType;

public class FishProducts implements Material {
    private FishTypes fishType;
    private ProductQuality quality;
    private int quantity;

    public FishProducts(FishTypes fishType, ProductQuality quality, int quantity) {
        this.fishType = fishType;
        this.quality = quality;
        this.quantity = quantity;
    }

    public FishTypes getFishType() {
        return fishType;
    }

    public void setFishType(FishTypes fishType) {
        this.fishType = fishType;
    }

    public ProductQuality getQuality() {
        return quality;
    }

    public void setQuality(ProductQuality quality) {
        this.quality = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}


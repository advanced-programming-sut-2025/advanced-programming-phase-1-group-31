// File: model/materials/Products/AnimalProduct.java
package model.materials.Products;

import model.enums.creature.AnimalProducts;
import model.materials.Material;
import model.materials.MaterialType;
import model.ProductQualityCalculator.ProductQuality;

import java.util.Objects;

public class AnimalProduct implements Material {
    private AnimalProducts animalProducts;
    private ProductQuality quality;
    private int quantity;

    public AnimalProduct(AnimalProducts animalProducts, ProductQuality quality, int quantity) {
        this.animalProducts = animalProducts;
        this.quality = quality;
        this.quantity = quantity;
    }

    public AnimalProducts getAnimalProducts() {
        return animalProducts;
    }

    public ProductQuality getQuality() {
        return quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuality(ProductQuality quality) {
        this.quality = quality;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAnimalProducts(AnimalProducts animalProducts) {
        this.animalProducts = animalProducts;
    }

    @Override
    public MaterialType getType() {
        return animalProducts;
    }

    @Override
    public String getName() {
        return animalProducts.getEnglishName();
    }

    public double getTotalPrice() {
        return animalProducts.getPrice() * quality.getPriceMultiplier() * quantity;
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

    @Override
    public String toString() {
        return quantity + "x " + quality.name() + " " + animalProducts.getEnglishName() + " (" + getTotalPrice() + "g)";
    }
}

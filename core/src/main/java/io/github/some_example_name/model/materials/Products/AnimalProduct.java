// File: model/materials/Products/AnimalProduct.java
package io.github.some_example_name.model.materials.Products;


import io.github.some_example_name.model.enums.creature.AnimalProducts;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;
import io.github.some_example_name.model.ProductQualityCalculator.ProductQuality;


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

    public AnimalProduct(AnimalProducts animalProducts) {
        this.animalProducts = animalProducts;
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

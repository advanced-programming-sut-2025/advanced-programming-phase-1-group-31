package model.materials.Products;

import model.enums.creature.AnimalProducts;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

public class AnimalProduct implements Material {
    private AnimalProducts animalProducts;

    public void setAnimalProducts(AnimalProducts animalProducts) {
        this.animalProducts = animalProducts;
    }

    @Override
    public MaterialType getType() {
        return animalProducts;
    }

    @Override
    public String getName() {
        return animalProducts.name();
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

package model.materials.Products;

import model.enums.creature.AnimalProducts;
import model.materials.Material;
import model.materials.MaterialType;

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

}

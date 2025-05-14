package model.materials.Products;

import model.enums.creature.AnimalProducts;
import model.materials.Material;
import model.materials.MaterialType;

public class AnimalProduct implements Material {
    AnimalProducts animalProducts;
    @Override
    public MaterialType getType() {
    return animalProducts;
    }

    @Override
    public String getName() {
        return "";
    }
    // علت جدا کردن محصولات حیوانی و ماهی این است که نوع هر محصول مختص خود ان است


}

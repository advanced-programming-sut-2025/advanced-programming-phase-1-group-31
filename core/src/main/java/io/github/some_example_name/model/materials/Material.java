package io.github.some_example_name.model.materials;

public interface Material {
    MaterialType getType();
    String getTexturePath();
    String getName();
    int baseSellPrice();
}

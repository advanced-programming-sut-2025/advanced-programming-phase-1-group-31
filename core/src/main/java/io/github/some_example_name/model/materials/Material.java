package io.github.some_example_name.model.materials;

public interface Material {
    MaterialType getType();
    String getName();
    int baseSellPrice();
}

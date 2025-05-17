package model.materials;

public interface Material {
    MaterialType getType();
    String getName();
    int baseSellPrice();
}

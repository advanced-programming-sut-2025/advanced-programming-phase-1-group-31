package model.enums.general;

import model.materials.Material;
import model.materials.Tree;

public enum TileType {
    EMPTY(".", "\u001B[40m", null),
    TREE("T", "\u001B[42m", new Tree()),
    FORAGING("F", "\u001B[102m"),
    STONE("S", "\u001B[47m"),
    LAKE("L", "\u001B[44m"),
    HOUSE("H", "\u001B[45m"),
    GREENHOUSE("G", "\u001B[46m"),
    MINE("M", "\u001B[41m"),
    WALL("w", "\u001B[48;5;94m"),
    QUARRY("Q", "\u001B[47m"),
    STORE("S", "\u001B[45m"),
    NPC("N", "\u001B[46m");


    private final String symbol;
    private final String color;
    private final Material material;

    TileType(String symbol, String color, Material material) {
        this.symbol = symbol;
        this.color = color;
        this.material = material;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
}

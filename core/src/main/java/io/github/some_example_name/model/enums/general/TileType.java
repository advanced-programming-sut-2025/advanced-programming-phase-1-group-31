package io.github.some_example_name.model.enums.general;

public enum TileType {

    PLANTING_SOIL("craft", "soil.com"),
    EMPTY(".", "\u001B[40m"),
    WALL("w", "\u001B[48;5;239m"),
    DOOR("D", "\u001B[48;5;178m"),

    LAKE("L", "\u001B[44m"),

    HOUSE("H", "\u001B[48;5;135m"),
    GREENHOUSE_BUILT("G", "\u001B[46m"),
    GREENHOUSE_BROKEN("g", "\u001B[48;5;7m"),
    SHOP("S", "\u001B[48;5;200m"),
    COOP("C", "\u001B[48;5;136m"),
    BARN("B", "\u001B[48;5;94m"),

    TREE("t", "\u001B[42m"),
    CROPS("c", "\u001B[102m"),
    SEED("s", "\u001B[48;5;22m"),

    FORAGING_TREE("f", "\u001B[48;5;28m"),
    FORAGING_CROPS("v", "\u001B[48;5;118m"),
    FORAGING_SEED("u", "\u001B[48;5;64m"),
    FORAGING_MINERAL("m", "\u001B[47m"),

    MINE("M", "\u001B[48;5;124m"),
    QUARRY("Q", "\u001B[48;5;250m"),

    Wood("W", "\u001B[48;5;130m"),
    Craftable("Y", "\u001B[48;5;33m"),
    TRASH_BIN("T", "\u001B[41m"),
    ANIMAL("A", "\u001B[48;5;229m"),

    PLAYER1("1", "\u001B[34m"),
    PLAYER2("2", "\u001B[31m"),
    PLAYER3("3", "\u001B[33m"),
    PLAYER4("4", "\u001B[32m"),
    NPC("N", "\u001B[48;5;51m");

    private final String layerName;

    private final String imagePath;

    TileType(String layerName, String imagePath) {
        this.layerName = layerName;
        this.imagePath = imagePath;
    }

    public String getLayerName() {
        return layerName;
    }

    public String getImagePath() {
        return imagePath;
    }
}

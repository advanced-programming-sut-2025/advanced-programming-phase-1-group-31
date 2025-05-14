
package model.enums.general;

public enum TileType {
    EMPTY(".", "\u001B[40m"),
    DOOR("D", "\u001B[43m"),
    Wood("W", "\u001B[48;5;130m"),
    Craftable("C", "\u001B[48;5;12m"),
    SEED("s", "\u001B[42m"),             // سبز تیره
    TREE("t", "\u001B[42m"),             // سبز تیره
    CROPS("c", "\u001B[102m"),           // سبز روشن
    FORAGING_TREE("f", "\u001B[48;5;22m"),    // f = foraging tree
    FORAGING_CROPS("g", "\u001B[102m"),  // g = foraging crops
    FORAGING_SEED("h", "\u001B[102m"),   // h = foraging seed
    FORAGING_MINERAL("m", "\u001B[47m"),// سفید خاکستری
    LAKE("L", "\u001B[44m"),
    HOUSE("H", "\u001B[45m"),
    GREENHOUSE_BUILT("G", "\u001B[46m"),
    GREENHOUSE_BROKEN("G", "\u001B[48;5;7m"),
    PLANTINGSOIL("P", "\u001B[48;5;94m"),
    MINE("M", "\u001B[41m"),
    WALL("w", "\u001B[48;5;94m"),
    QUARRY("Q", "\u001B[47m"),
    SHOP("S", "\u001B[45m"),
    NPC("N", "\u001B[46m"),
        TRASH_BIN("T", "\u001B[41m"),
    PLAYER1("P", "\u001B[34m"),
    PLAYER2("P", "\u001B[31m"),
    PLAYER3("P", "\u001B[33m"),
    PLAYER4("P", "\u001B[32m"),
    COOP("C", "\u001B[48;5;7m" ),
    BARN("B", "\u001B[48;5;7m" ),
    ANIMAL("A","\u001B[102m" );



    private final String symbol;
    private final String color;

    TileType(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }
}

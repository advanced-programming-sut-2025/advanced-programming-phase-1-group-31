// package model.enums;

// public enum TileType {
//     EMPTY(".", "\u001B[40m"),  
//     TREE("T", "\u001B[42m"),
//     FORAGING("F", "\u001B[102m"),
//     STONE("S", "\u001B[47m"),
//     LAKE("L", "\u001B[44m"),
//     HOUSE("H", "\u001B[45m"),
//     GREENHOUSE("G", "\u001B[46m"),
//     MINE("M", "\u001B[41m"),
//     WALL("w", "\u001B[48;5;94m"),
//     QUARRY("Q", "\u001B[47m"),
//     STORE("S", "\u001B[45m"),        
//     NPC("N", "\u001B[46m"),         
//     TRASH_BIN("T", "\u001B[41m"),
//     PLAYER1("P", "\u001B[34m"),
//     PLAYER2("P", "\u001B[31m"),
//     PLAYER3("P", "\u001B[33m"),
//     PLAYER4("P", "\u001B[32m");

//     private final String symbol;
//     private final String color;

//     TileType(String symbol, String color) {
//         this.symbol = symbol;
//         this.color = color;
//     }

//     public String getSymbol() {
//         return symbol;
//     }

//     public String getColor() {
//         return color;
//     }
// }
package model.enums.general;

import model.NPC;
import model.Shop;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingSeed;
import model.materials.Foraging.ForagingTree;
import model.materials.Material;

public enum TileType {
    EMPTY(".", "\u001B[40m", null),
    FORAGING_TREE("T", "\u001B[42m", new ForagingTree()),
    FORAGING_CROPS("F", "\u001B[102m", new ForagingCrop()),
    FORAGING_SEED("F", "\u001B[102m", new ForagingSeed()),
    FORAGING_MINERAL("S", "\u001B[47m", new ForagingMineral()),
    LAKE("L", "\u001B[44m", null),
    HOUSE("H", "\u001B[45m", null),
    GREENHOUSE("G", "\u001B[46m", null),
    MINE("M", "\u001B[41m", null),
    WALL("w", "\u001B[48;5;94m", null),
    QUARRY("Q", "\u001B[47m", null),
    SHOP("S", "\u001B[45m", new Shop()),  
    NPC("N", "\u001B[46m", new NPC()),
        TRASH_BIN("T", "\u001B[41m" , null),
    PLAYER1("P", "\u001B[34m",null),
    PLAYER2("P", "\u001B[31m",null),
    PLAYER3("P", "\u001B[33m",null),
    PLAYER4("P", "\u001B[32m",null);


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

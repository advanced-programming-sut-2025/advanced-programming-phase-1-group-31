package model;

import model.enums.general.Seasons;
import model.materials.Material;

public class MaterialInShop {
    private final Material material;
    private final int ordinaryPrice;
    private final int outOfSeasonPrice;
    private final int dailyLimit;
    private final Seasons seasons;

    public MaterialInShop(Material material,
                          int ordinaryPrice,
                          int outOfSeasonPrice,
                          int dailyLimit,
                          Seasons seasons) {
        this.material = material;
        this.ordinaryPrice = ordinaryPrice;
        this.outOfSeasonPrice = outOfSeasonPrice;
        this.dailyLimit = dailyLimit;
        this.seasons = seasons;
    }

    public Material getMaterial() {
        return material;
    }

    public int getOrdinaryPrice() {
        return ordinaryPrice;
    }

    public int getOutOfSeasonPrice() {
        return outOfSeasonPrice;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public Seasons getSeasons() {
        return seasons;
    }
}

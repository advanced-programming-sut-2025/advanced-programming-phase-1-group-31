package model;

import model.enums.npc.Shops;
import model.materials.Material;
import model.materials.MaterialType;

public class Shop implements Material {
    private Shops shopType;

    public Shop(Shops shopType) {
        this.shopType = shopType;
    }

    public Shops getShopName() {
        return shopType;
    }

    public void setShopName(Shops shopName) {
        this.shopType = shopName;
    }

    @Override
    public MaterialType getType() {
        return shopType;
    }

    @Override
    public String getName() {
        return shopType.getName();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

}

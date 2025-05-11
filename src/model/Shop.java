package model;

import model.enums.npc.Shops;
import model.materials.Material;

public class Shop implements Material {
    private Shops shopName;

    public Shops getShopName() {
        return shopName;
    }

    public void setShopName(Shops shopName) {
        this.shopName = shopName;
    }

}

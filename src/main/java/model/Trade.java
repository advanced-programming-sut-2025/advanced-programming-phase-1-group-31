package model;

import model.materials.Material;

import java.util.HashMap;

public class Trade {
    private int id;
    private Player tradePartner;
    private Material materialToSell;
    private int amountToSell;
    //1:offer 0:Request
    private boolean isOffer;
    //1:accept 0:reject
    private boolean isAccepted;
    //one of the variables below should be null
    private int price;
    private Material materialToReceive;
    private int amountToReceive;

    //trade money with material
    public Trade(int id,
                 Player tradePartner,
                 Material materialToSell,
                 int amountToSell,
                 boolean isOffer,
                 int price) {
        this.id = id;
        this.tradePartner = tradePartner;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.isOffer = isOffer;
        this.price = price;
    }

    //trade material with material

    public Trade(int id,
                 Player tradePartner,
                 Material materialToSell,
                 int amountToSell,
                 boolean isOffer,
                 Material materialToReceive,
                 int amountToReceive) {
        this.id = id;
        this.tradePartner = tradePartner;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.isOffer = isOffer;
        this.materialToReceive = materialToReceive;
        this.amountToReceive = amountToReceive;
    }

    //for completing NPC quests

    public Trade(Material materialToSell, int amountToSell) {
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
    }
}
//this is to store the details of every trade done
//each trade has to be added to both players


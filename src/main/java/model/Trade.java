package model;

import model.materials.Material;

public class Trade {
    private final int id;
    private final Player sender;
    private final Player receiver;
    private final Material materialToSell;
    private final int amountToSell;
    /** true = offer, false = request */
    private final boolean isOffer;
    /** true = accepted, false = rejected (initially false) */
    private boolean isAccepted = false;

    private Integer price;               // null if material-for-material

    private Material materialToReceive;  // null if money-for-material
    private Integer amountToReceive;     // null if money-for-material

    // ========== Constructors ==========

    public Trade(int id,
                 Player sender,
                 Player receiver,
                 Material materialToSell,
                 int amountToSell,
                 boolean isOffer,
                 int price) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.isOffer = isOffer;
        this.price = price;
    }

    public Trade(int id,
                 Player sender,
                 Player receiver,
                 Material materialToSell,
                 int amountToSell,
                 boolean isOffer,
                 Material materialToReceive,
                 int amountToReceive) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.isOffer = isOffer;
        this.materialToReceive = materialToReceive;
        this.amountToReceive = amountToReceive;
    }

    public Trade(Player sender,
                 Material materialToSell,
                 int amountToSell) {
        this.id = -1;
        this.sender = sender;
        this.receiver = null;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.isOffer = true;
    }

    public int getId() { return id; }
    public Player getSender() { return sender; }
    public Player getReceiver() { return receiver; }
    public Material getMaterialToSell() { return materialToSell; }
    public int getAmountToSell() { return amountToSell; }
    public boolean isOffer() { return isOffer; }
    public boolean isAccepted() { return isAccepted; }
    public void setAccepted(boolean accepted) { isAccepted = accepted; }
    public Integer getPrice() { return price; }
    public Material getMaterialToReceive() { return materialToReceive; }
    public Integer getAmountToReceive() { return amountToReceive; }
}

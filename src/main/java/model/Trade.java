package model;

import model.materials.Material;

public class Trade {
    private int id;
    private final Player sender;
    private final Player receiver;
    private final Material materialToSell;
    private final int amountToSell;
    /** true = accepted, false = rejected (initially false) */
    private boolean isAccepted = false;

    private Integer price;               // null if material-for-material

    private Material materialToReceive;  // null if money-for-material
    private Integer amountToReceive;     // null if money-for-material
    private boolean isRead = false;

    // ========== Constructors ==========

    public Trade(Player sender,
                 Player receiver,
                 Material materialToSell,
                 int amountToSell,
                 int price) {
        this.sender = sender;
        this.receiver = receiver;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.price = price;
    }

    public Trade(Player sender,
                 Player receiver,
                 Material materialToSell,
                 int amountToSell,
                 Material materialToReceive,
                 int amountToReceive) {
        this.sender = sender;
        this.receiver = receiver;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.materialToReceive = materialToReceive;
        this.amountToReceive = amountToReceive;
    }

    public Trade(Player sender,
                 Material materialToSell,
                 int amountToSell) {
        this.sender = sender;
        this.receiver = null;
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
    }//quest

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public Player getSender() { return sender; }
    public Player getReceiver() { return receiver; }
    public Material getMaterialToSell() { return materialToSell; }
    public int getAmountToSell() { return amountToSell; }
    public boolean isAccepted() { return isAccepted; }
    public void setAccepted(boolean accepted) { isAccepted = accepted; }
    public Integer getPrice() { return price; }
    public Material getMaterialToReceive() { return materialToReceive; }
    public Integer getAmountToReceive() { return amountToReceive; }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}

package io.github.some_example_name.model;


import io.github.some_example_name.model.materials.Material;

public class Trade {
    private int id;
    private Player sender;
    private Player receiver;
    private final Material materialToSell;
    private final int amountToSell;
    private Boolean isDone = false;
    /**
     * true = accepted, false = rejected (initially false)
     */
    private Boolean isAccepted = false;

    private Integer price;               // null if material-for-material

    private Material materialToReceive;  // null if money-for-material
    private Integer amountToReceive;     // null if money-for-material
    private Boolean isRead = false;

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

    public Trade(Material materialToSell,
                 int amountToSell,
                 int price) {
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.price = price;
    }

    public Trade(Material materialToSell,
                 Material materialToReceive,
                 int amountToSell,
                 int amountToReceive) {
        this.materialToSell = materialToSell;
        this.amountToSell = amountToSell;
        this.materialToReceive = materialToReceive;
        this.amountToReceive = amountToReceive;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Material getMaterialToSell() {
        return materialToSell;
    }

    public int getAmountToSell() {
        return amountToSell;
    }

    public Boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Integer getPrice() {
        return price;
    }

    public Material getMaterialToReceive() {
        return materialToReceive;
    }

    public Integer getAmountToReceive() {
        return amountToReceive;
    }

    public Boolean isRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}

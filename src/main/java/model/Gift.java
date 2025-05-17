package model;

import model.materials.Material;

public class Gift {
    private final String sender;
    private final String receiver;
    private final Material material;
    private final int amount;
    private int rate = 0;
    private int id = 0;

    public Gift(String sender, String receiver, Material material, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.material = material;
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public int getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

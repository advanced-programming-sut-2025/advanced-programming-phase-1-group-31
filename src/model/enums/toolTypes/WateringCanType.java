package model.enums.toolTypes;

public enum

WateringCanType {
    Initial(40),
    Copper(55),
    Iron(70),
    Gold(85),
    Iridium(100);

    private int capacity;

    WateringCanType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

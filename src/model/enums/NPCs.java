package model.enums;

import model.Trade;
import model.materials.Material;

import java.util.ArrayList;

public enum NPCs {
    Sebastian("Sebastian", {new Trade(ForagingMinerals.Iron, 50),
                            new Trade(Foods.PumpkinPie, 1),
                            new Trade(ForagingMinerals.Stone, 150)},
            {AnimalProducts.Wool, Foods.PumpkinPie, Foods.Pizza});

    private final String name;
    public final ArrayList<Trade> quests = new ArrayList<>();
    public final ArrayList<Material> favoriteGifts = new ArrayList<>();
    //don't add an arraylist of friendships, we only store those in the players

    public abstract void questOneCompletion();
    public abstract void questTwoCompletion();
    public abstract void questThreeCompletion();
}

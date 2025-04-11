package model;

import model.enums.Crops;
import model.enums.Shops;

import java.util.ArrayList;

public class Game {
    private static Map mainMap;
    private static Player adminPlayer;
    private static Player activePlayer;
    private static int playerIDTurn;
    private static TimeAndDate time;
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Shops> shops = new ArrayList<>();
}

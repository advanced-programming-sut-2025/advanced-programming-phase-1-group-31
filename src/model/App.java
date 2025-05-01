package model;

import view.Menu;

import java.util.ArrayList;

public class App {
    private static App instance;

    private App() {
    }

    public App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private Player playerLoggedIn;
    private Game currentGame;
    private ArrayList<Player> players;
    private ArrayList<Map> maps;
    private ArrayList<Game> games;
}

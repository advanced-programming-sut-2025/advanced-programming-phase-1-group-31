package model.enums;

import model.Rainy;
import model.Sunny;
import model.Snowy;
import model.Stormy;
import model.Weathers;

public enum Weather {
    Sunny(new Sunny()),
    Rainy(new Rainy()),
    Stormy(new Stormy()),
    Snowy(new Snowy());

    private final Weathers weather;

    Weather(Weathers weather) {
        this.weather = weather;
    }

    public void checkWork(){
        this.weather.work();
    }
}
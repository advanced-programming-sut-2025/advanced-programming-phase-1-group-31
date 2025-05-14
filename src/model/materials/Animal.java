package model.materials;


import model.AnimalFriendship;
import model.enums.creature.Animals;

import java.awt.*;

public class Animal implements Material {
    private String name;
    private Animals animalType;
    private Point location;
    private AnimalFriendship animalFriendship;

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setAnimalType(Animals animalType) {
        this.animalType = animalType;
    }

    public Animal(String name, Animals animalType) {
        this.name = name;
        this.animalType = animalType;
        animalFriendship = new AnimalFriendship();
    }

    public AnimalFriendship getAnimalFriendship() {
        return animalFriendship;
    }

    public void setAnimalFriendship(AnimalFriendship animalFriendship) {
        this.animalFriendship = animalFriendship;
    }

    @Override
    public MaterialType getType() {
        return animalType;
    }

    @Override
    public String getName() {
        return name;
    }
}

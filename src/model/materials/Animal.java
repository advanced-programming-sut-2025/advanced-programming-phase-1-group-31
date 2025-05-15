package model.materials;

import model.AnimalFriendship;
import model.enums.creature.AnimalProducts;
import model.enums.creature.Animals;
import model.materials.Products.AnimalProduct;
import model.ProductQualityCalculator;
import model.ProductQualityCalculator.ProductQuality;
import java.util.concurrent.ThreadLocalRandom;


import java.awt.*;
import java.util.Random;

public class Animal implements Material {
    private String name;
    private Animals animalType;
    private Point location;
    private AnimalFriendship animalFriendship;
    private AnimalProduct todayProduct;

    public Animal(String name, Animals animalType) {
        this.name = name;
        this.animalType = animalType;
        this.animalFriendship = new AnimalFriendship();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Animals getAnimalType() {
        return animalType;
    }

    public AnimalFriendship getAnimalFriendship() {
        return animalFriendship;
    }

    public AnimalProduct getTodayProduct() {
        return todayProduct;
    }

    

    public boolean hasProduct() {
        return todayProduct != null;
    }

   public void resetProduct() {
        todayProduct = null;
    }

    public void generateProduct() {
        if (!animalFriendship.isWasFedToday()) return;

        Random rand = new Random();
        double friendship = animalFriendship.getFriendshipPoints();
        double randomNum = ThreadLocalRandom.current().nextDouble(0.5, 1.5);
        double randomChance = (randomNum * 150) + ( friendship) / 1500.0;

        AnimalProducts productType = animalType.getFirstProduct();
        if (friendship >= 100 && rand.nextDouble() < randomChance) {
            productType = animalType.getSecondProduct();
        }
        double randomNumQuality = ThreadLocalRandom.current().nextDouble(0, 1);
        double randomQuality = ((randomNumQuality * 0.5) + 0.5) * (animalFriendship.getFriendshipPercentage()) ;

        ProductQuality quality = ProductQualityCalculator.calculateQuality(randomQuality);
        todayProduct = new AnimalProduct(productType, quality, 1);
    }

    public AnimalProduct collectProduct() {
        if (!hasProduct()) return null;
        AnimalProduct collected = todayProduct;
        resetProduct();
        return collected;
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

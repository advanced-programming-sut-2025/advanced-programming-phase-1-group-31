package io.github.some_example_name.model.materials;



import io.github.some_example_name.model.AnimalFriendship;
import io.github.some_example_name.model.ProductQualityCalculator;
import io.github.some_example_name.model.enums.creature.AnimalProducts;
import io.github.some_example_name.model.enums.creature.Animals;
import io.github.some_example_name.model.materials.Products.AnimalProduct;
import io.github.some_example_name.model.ProductQualityCalculator.ProductQuality;


import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements Material {
    private final String name;
    private final Animals animalType;
    private Point location;
    private final AnimalFriendship animalFriendship;
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

    @Override
    public int baseSellPrice() {
        return animalType.getPurchasePrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }

}

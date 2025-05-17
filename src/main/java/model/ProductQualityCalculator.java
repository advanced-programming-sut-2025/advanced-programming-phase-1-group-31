package model;

import model.enums.general.Weather;
import model.enums.toolTypes.FishingPoleType;
import model.materials.Tools.FishingPole;

import java.util.Random;

public class ProductQualityCalculator {
    private static final Random random = new Random();

    public static ProductQuality calculateQuality(double friendshipPercentage) {
        double qualityValue = (random.nextDouble() * 0.5) + (0.5 * friendshipPercentage);

        if (qualityValue > 0.9) return ProductQuality.IRIDIUM;
        if (qualityValue > 0.7) return ProductQuality.GOLD;
        if (qualityValue > 0.5) return ProductQuality.SILVER;
        return ProductQuality.NORMAL;
    }
        public static ProductQuality calculateQualityScore(double score) {
            if (score > 0.9) return ProductQuality.IRIDIUM;
            if (score > 0.7) return ProductQuality.GOLD;
            if (score > 0.5) return ProductQuality.SILVER;
            return ProductQuality.NORMAL;
        }

    public static double getSeasonalMultiplier(Weather weather) {
        return switch (weather) {
            case Sunny -> 1.5;
            case Rainy -> 1.2;
            case Stormy -> 0.5;
            default -> 1.0;
        };
    }
    public static double getPoleMultiplier(FishingPoleType fishingPoleType) {
        return switch (fishingPoleType) {
            case Training -> 0.1;
            case Bamboo -> 0.5;
            case Fiberglass -> 0.9;
            case Iridium -> 1.2;
        };
    }

    public enum ProductQuality {
        NORMAL(1.0),
        SILVER(1.25),
        GOLD(1.5),
        IRIDIUM(2.0);

        private final double priceMultiplier;

        ProductQuality(double priceMultiplier) {
            this.priceMultiplier = priceMultiplier;
        }

        public double getPriceMultiplier() {
            return priceMultiplier;
        }
    }

}
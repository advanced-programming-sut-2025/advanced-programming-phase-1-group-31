package model;

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
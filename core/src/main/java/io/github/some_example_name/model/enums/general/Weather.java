package io.github.some_example_name.model.enums.general;

public enum Weather {
    Sunny("Particle Park Smoke Trail"),
    Rainy("Particle Park Rain Cinematic"),
    Stormy("Particle Park Smoke Trail"),
    Snowy("Particle Park Snow Flakes");
    final String effectName;
    Weather(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectName() {
        return effectName;
    }
}

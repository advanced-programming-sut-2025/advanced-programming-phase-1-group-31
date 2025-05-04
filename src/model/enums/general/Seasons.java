package model.enums.general;

public enum Seasons {
    Spring{
        @Override
        public void seasonEffects() {

        }
    },
    Summer,
    Fall,
    Winter;
    //each season must have its own method that applies special effects

    abstract public void seasonEffects();
}

package model;

public class Skill {
    private int farmingLevel = 0;
    private int miningLevel = 0;
    private int foragingLevel = 0;
    private int fishingLevel = 0;

    private int farmingUnit = 0;
    private int miningUnit = 0;
    private int foragingUnit = 0;
    private int fishingUnit = 0;

    public void setFarmingLevel(int addSkillUnit) {
        if (farmingLevel == 4) {
            return;
        }
        farmingUnit += addSkillUnit;
        int levelDifference = ((100 * (farmingLevel + 1)) + 50);
        if (farmingUnit >= levelDifference) {
            farmingLevel++;
            farmingUnit -= levelDifference;
        }
    }

    public void setMiningLevel(int addSkillUnit) {
        if (miningLevel == 4) {
            return;
        }
        miningUnit += addSkillUnit;
        int levelDifference = ((100 * (miningLevel + 1)) + 50);
        if (miningUnit >= levelDifference) {
            miningLevel++;
            miningUnit -= levelDifference;
        }
    }

    public void setFishingLevel(int addSkillUnit) {
        if (fishingLevel == 4) {
            return;
        }
        fishingUnit += addSkillUnit;
        int levelDifference = ((100 * (fishingLevel + 1)) + 50);
        if (fishingUnit >= levelDifference) {
            fishingLevel++;
            fishingUnit -= levelDifference;
        }
    }

    public void setForagingLevel(int addSkillUnit) {
        if (foragingLevel == 4) {
            return;
        }
        foragingUnit += addSkillUnit;
        int levelDifference = ((100 * (foragingLevel + 1)) + 50);
        if (foragingUnit >= levelDifference) {
            foragingLevel++;
            foragingUnit -= levelDifference;
        }    }

    public int getFarmingLevel() {
        return farmingLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

}

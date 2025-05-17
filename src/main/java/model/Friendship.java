package model;

public class Friendship {
    private final Player friend;
    private int friendshipLevel = 0;
    private int friendshipUnit = 0;
    private boolean flower;

    public Friendship(Player friend) {
        this.friend = friend;
    }

    public void addFriendshipLevel(int addFriendUnit) {
        if (addFriendUnit == 0 || (friendshipLevel == 4 && addFriendUnit > 0)) {
            return;
        }

        friendshipUnit += addFriendUnit;

        while (friendshipLevel < 4) {
            int requiredUnits = 100 * (friendshipLevel + 1);

            if (friendshipLevel == 2 && !flower) {
                friendshipUnit = Math.min(friendshipUnit, requiredUnits);
                break;
            }

            if (friendshipUnit >= requiredUnits) {
                friendshipUnit -= requiredUnits;
                friendshipLevel++;
            } else {
                break;
            }
        }

        while (friendshipUnit < 0 && friendshipLevel > 0) {
            friendshipLevel--;
            int requiredUnits = 100 * (friendshipLevel + 1);
            friendshipUnit += requiredUnits;
        }

        if (friendshipLevel == 0 && friendshipUnit < 0) {
            friendshipUnit = 0;
        }
    }


    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public Player getFriend() {
        return friend;
    }

    public int getFriendshipUnit() {
        return friendshipUnit;
    }

    public void setFlower(boolean flower) {
        this.flower = flower;
    }

    public boolean hasFlower() {
        return flower;
    }
}

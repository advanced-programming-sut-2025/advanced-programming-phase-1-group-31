package io.github.some_example_name.model;

public class AnimalFriendship {
    private static final int MAX_FRIENDSHIP = 1000;
    private int friendshipPoints;
    private Boolean wasPettedToday;
    private Boolean wasFedToday;
    private Boolean stayedOutsideTonight;

    public AnimalFriendship() {
        this.friendshipPoints = 0;
    }

    public void pet() {
        if (!wasPettedToday) {
            friendshipPoints = Math.min(friendshipPoints + 15, MAX_FRIENDSHIP);
            wasPettedToday = true;
        }
    }

    public void feed(Boolean ateOutside) {
        wasFedToday = true;
        if (ateOutside) {
            friendshipPoints = Math.min(friendshipPoints + 8, MAX_FRIENDSHIP);
        }
    }

    public void milkOrShear() {
        friendshipPoints = Math.min(friendshipPoints + 5, MAX_FRIENDSHIP);
    }

    public void endDay() {
        if (!wasFedToday) {
            friendshipPoints = Math.max(friendshipPoints - 20, 0);
        }
        if (stayedOutsideTonight) {
            friendshipPoints = Math.max(friendshipPoints - 20, 0);
        }
        if (!wasPettedToday) {
            friendshipPoints = Math.max(friendshipPoints - (200 / (friendshipPoints/100 + 1)), 0);
        }

        wasPettedToday = false;
        wasFedToday = false;
        stayedOutsideTonight = false;
    }
    public void setFriendshipPoints(int points) {
    this.friendshipPoints = Math.max(0, Math.min(points, MAX_FRIENDSHIP));
}
    public void setStayedOutsideTonight(Boolean stayedOutsideTonight) {
        this.stayedOutsideTonight = stayedOutsideTonight;
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
    }

    public double getFriendshipPercentage() {
        return friendshipPoints / (double)MAX_FRIENDSHIP;
    }

    public Boolean isWasPettedToday() {
        return wasPettedToday;
    }

    public void setWasPettedToday(Boolean wasPettedToday) {
        this.wasPettedToday = wasPettedToday;
    }

    public Boolean isWasFedToday() {
        return wasFedToday;
    }

    public void setWasFedToday(Boolean wasFedToday) {
        this.wasFedToday = wasFedToday;
    }

    public Boolean isStayedOutsideTonight() {
        return stayedOutsideTonight;
    }
}

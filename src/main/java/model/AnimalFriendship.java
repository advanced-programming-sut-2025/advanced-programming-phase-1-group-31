package model;

public class AnimalFriendship {
    private static final int MAX_FRIENDSHIP = 1000;
    private int friendshipPoints;
    private boolean wasPettedToday;
    private boolean wasFedToday;
    private boolean stayedOutsideTonight;

    public AnimalFriendship() {
        this.friendshipPoints = 0;
    }

    public void pet() {
        if (!wasPettedToday) {
            friendshipPoints = Math.min(friendshipPoints + 15, MAX_FRIENDSHIP);
            wasPettedToday = true;
        }
    }

    public void feed(boolean ateOutside) {
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
    public void setStayedOutsideTonight(boolean stayedOutsideTonight) {
        this.stayedOutsideTonight = stayedOutsideTonight;
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
    }

    public double getFriendshipPercentage() {
        return friendshipPoints / (double)MAX_FRIENDSHIP;
    }

    public boolean isWasPettedToday() {
        return wasPettedToday;
    }

    public void setWasPettedToday(boolean wasPettedToday) {
        this.wasPettedToday = wasPettedToday;
    }

    public boolean isWasFedToday() {
        return wasFedToday;
    }

    public void setWasFedToday(boolean wasFedToday) {
        this.wasFedToday = wasFedToday;
    }

    public boolean isStayedOutsideTonight() {
        return stayedOutsideTonight;
    }
}
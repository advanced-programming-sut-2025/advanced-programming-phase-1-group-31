package model;

public class Friendship {
    private Player friend;
    private int friendshipLevel = 0;
    private int friendshipUnit = 0;

    private void setFriendshipLevel(int addFriendUnit) {
        if (friendshipLevel == 4) {
            return;
        }
        friendshipUnit += addFriendUnit;
        int levelDifference = (100 * (friendshipLevel + 1));
        if (friendshipUnit >= levelDifference) {
            friendshipLevel++;
            friendshipUnit -= levelDifference;
        }
    }

    private int getFriendshipLevel() {
        return friendshipLevel;
    }

    public void giveGift() {
        // give material to the friend
    }

    public void hug() {
        //hug the friend <3
    }


}

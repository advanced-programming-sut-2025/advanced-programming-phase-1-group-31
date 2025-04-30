package model;

public class FriendshipWithNPC {
    private NPC npc;
    private int friendshipLevel = 0;
    private int friendshipUnit = 0;

    private void setFriendshipLevel(int addFriendUnit) {
        if (friendshipUnit > 799) {
            friendshipUnit = 799;
        }
        friendshipLevel = friendshipUnit / 200;
    }

    private int getFriendshipLevel() {
        return friendshipLevel;
    }

}

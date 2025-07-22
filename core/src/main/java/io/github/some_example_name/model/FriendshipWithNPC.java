package io.github.some_example_name.model;

public class FriendshipWithNPC {
    private final NPC npc;
    private int friendshipLevel = 0;
    private int friendshipUnit = 0;

    public FriendshipWithNPC(NPC npc) {
        this.npc = npc;
    }

    public void addFriendshipUnit(int addFriendUnit) {
        friendshipUnit += addFriendUnit;
        if (friendshipUnit > 799) friendshipUnit = 799;
        if (friendshipUnit < 0) friendshipUnit = 0;
        friendshipLevel = friendshipUnit / 200;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public int getFriendshipUnit() {
        return friendshipUnit;
    }

    public NPC getNpc() {
        return npc;
    }
}

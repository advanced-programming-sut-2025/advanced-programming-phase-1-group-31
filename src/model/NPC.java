package model;

import model.enums.npc.NPCs;
import model.materials.Material;

public class NPC implements Material {
    private NPCs NPCType;


    public NPCs getNPCType() {
        return NPCType;
    }

    public void setNPCType(NPCs NPCType) {
        this.NPCType = NPCType;
    }

}

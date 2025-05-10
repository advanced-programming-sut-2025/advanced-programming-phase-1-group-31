package model;

import model.enums.npc.NPCs;
import model.materials.Material;
import model.materials.MaterialType;

public class NPC implements Material {
    private NPCs NPCType;


    public NPCs getNPCType() {
        return NPCType;
    }

    public void setNPCType(NPCs NPCType) {
        this.NPCType = NPCType;
    }


    @Override
    public MaterialType getType() {
        return NPCType;
    }
}

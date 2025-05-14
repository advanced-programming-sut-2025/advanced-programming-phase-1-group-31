package model;

import model.enums.npc.NPCs;
import model.materials.Material;
import model.materials.MaterialType;

import java.util.Objects;

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

    @Override
    public String getName() {
        return NPCType.getName();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

}

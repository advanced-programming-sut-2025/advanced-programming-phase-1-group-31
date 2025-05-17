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
        return "NPC";
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}

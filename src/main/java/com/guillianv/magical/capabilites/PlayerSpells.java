package com.guillianv.magical.capabilites;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerSpells {

    CompoundTag playerSpells = new CompoundTag();

    public void resetSpells() {
        playerSpells = new CompoundTag();
    }

    public int getSpellLevel(String spellKey){
        if (playerSpells.contains(spellKey)){
            return playerSpells.getInt(spellKey);
        }
        return 1;
    }

    public void updateSpellLevel(String spellKey, int level) {
        if (this.playerSpells.contains(spellKey)) {
            this.playerSpells.putInt(spellKey, level);
        } else {
            this.addSpellLevel(spellKey);
        }
    }

    private void addSpellLevel(String spellKey) {
        this.playerSpells.putInt(spellKey, 1);
    }


    public void copyFrom(PlayerSpells source) {
        this.playerSpells = source.playerSpells;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.put("spells_data", playerSpells);
    }

    public void loadNBTData(CompoundTag nbt) {
        playerSpells = nbt.getCompound("spells_data");
    }
}

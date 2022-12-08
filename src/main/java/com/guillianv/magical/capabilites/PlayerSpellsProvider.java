package com.guillianv.magical.capabilites;


import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@AutoRegisterCapability
public class PlayerSpellsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSpells> PLAYER_SPELLS = CapabilityManager.get(new CapabilityToken<PlayerSpells>() { });

    private PlayerSpells spells = null;
    private final LazyOptional<PlayerSpells> optional = LazyOptional.of(this::createPlayerSpells);

    private PlayerSpells createPlayerSpells() {
        if(this.spells == null) {
            this.spells = new PlayerSpells();
        }

        return this.spells;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SPELLS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSpells().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSpells().loadNBTData(nbt);
    }
}
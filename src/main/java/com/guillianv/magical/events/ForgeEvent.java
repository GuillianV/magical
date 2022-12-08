package com.guillianv.magical.events;


import com.guillianv.magical.Magical;
import com.guillianv.magical.capabilites.PlayerSpells;
import com.guillianv.magical.capabilites.PlayerSpellsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ForgeEvent{



@Mod.EventBusSubscriber(modid = Magical.MOD_ID)
public static class ForgeEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerSpellsProvider.PLAYER_SPELLS).isPresent()) {
                event.addCapability(new ResourceLocation(Magical.MOD_ID, "properties"), new PlayerSpellsProvider());
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerSpells.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(thirst -> {
                   // ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                });
            }
        }
    }

}


}


package com.guillianv.magical;

import com.guillianv.magical.blocks.ModBlocks;
import com.guillianv.magical.blocks.entity.ModBlockEntities;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.items.ModItems;
import com.guillianv.magical.loot.ModLootModifiers;
import com.guillianv.magical.screen.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Magical.MOD_ID)
public class Magical
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "magical";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Magical()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

}

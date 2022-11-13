package com.guillianv.magical.events;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.entity.ModBlockEntities;
import com.guillianv.magical.blocks.render.AltarBlockRenderer;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.bottle.render.BottleRenderer;
import com.guillianv.magical.entity.animation.fireball.render.FireballRenderer;
import com.guillianv.magical.screen.AltarScreen;
import com.guillianv.magical.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Magical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {


    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ALTAR.get(), AltarBlockRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        MenuScreens.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
        EntityRenderers.register(ModEntityTypes.BOTTLE.get(), BottleRenderer::new);
        EntityRenderers.register(ModEntityTypes.FIREBALL.get(), FireballRenderer::new);


    }
}
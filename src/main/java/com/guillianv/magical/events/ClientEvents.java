package com.guillianv.magical.events;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.entity.ModBlockEntities;
import com.guillianv.magical.blocks.entity.RecognizerBlockEntity;
import com.guillianv.magical.blocks.render.AltarBlockRenderer;
import com.guillianv.magical.blocks.render.RecognizerBlockRenderer;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.bottle.render.BottleRenderer;
import com.guillianv.magical.entity.spells.celestial_blessing.render.CelestialBlessingRenderer;
import com.guillianv.magical.entity.spells.earth_fist.render.EarthFistRenderer;
import com.guillianv.magical.entity.spells.fire_rain.render.FireRainRenderer;
import com.guillianv.magical.entity.spells.fire_sword.render.FireSwordRenderer;
import com.guillianv.magical.entity.spells.fireball.render.FireballRenderer;
import com.guillianv.magical.entity.spells.throwable_block.render.ThrowableBlockRenderer;
import com.guillianv.magical.entity.spells.thunder_strike.render.ThunderStrikeRenderer;
import com.guillianv.magical.entity.spells.tornado.render.TornadoRenderer;
import com.guillianv.magical.screen.AltarScreen;
import com.guillianv.magical.screen.MagicalScreen;
import com.guillianv.magical.screen.ModMenuTypes;
import com.guillianv.magical.screen.RecognizerScreen;
import com.guillianv.magical.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkHooks;


public class ClientEvents {


    @Mod.EventBusSubscriber(modid = Magical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.ALTAR.get(), AltarBlockRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.RECOGNIZER.get(), RecognizerBlockRenderer::new);
        }


        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
            MenuScreens.register(ModMenuTypes.RECOGNIZER_MENU.get(), RecognizerScreen::new);


            EntityRenderers.register(ModEntityTypes.BOTTLE.get(), BottleRenderer::new);
            EntityRenderers.register(ModEntityTypes.FIREBALL.get(), FireballRenderer::new);
            EntityRenderers.register(ModEntityTypes.THUNDER_STRIKE.get(), ThunderStrikeRenderer::new);
            EntityRenderers.register(ModEntityTypes.EARTH_FIST.get(), EarthFistRenderer::new);
            EntityRenderers.register(ModEntityTypes.THROWABLE_BLOCK.get(), ThrowableBlockRenderer::new);
            EntityRenderers.register(ModEntityTypes.TORNADO.get(), TornadoRenderer::new);
            EntityRenderers.register(ModEntityTypes.CELESTIAL_BLESSING.get(), CelestialBlessingRenderer::new);
            EntityRenderers.register(ModEntityTypes.FIRE_SWORD.get(), FireSwordRenderer::new);
            EntityRenderers.register(ModEntityTypes.FIRE_SWORD_RAIN.get(), FireRainRenderer::new);
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_MENU);
        }

    }


    @Mod.EventBusSubscriber(modid = Magical.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.OPEN_MENU.consumeClick()) {

                LocalPlayer player = Minecraft.getInstance().player;
                Minecraft minecraft = Minecraft.getInstance();

                minecraft.setScreen(new MagicalScreen(player));


            }
        }
    }

}


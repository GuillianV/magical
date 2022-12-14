package com.guillianv.magical.screen;

import com.guillianv.magical.Magical;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AltarScreen extends AbstractContainerScreen<AltarMenu> {


    private static final ResourceLocation TEXTURE = new ResourceLocation(Magical.MOD_ID,"textures/gui/altar_gui.png");

    public AltarScreen(AltarMenu altarMenu, Inventory inventory, Component component) {
        super(altarMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (this.menu.canCraft()){
            this.blit(pPoseStack, x + 87, y +35, 176, 0, 23, 13);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY,float delta){
        renderBackground(pPoseStack);

        super.render(pPoseStack, pMouseX, pMouseY, delta);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
    }
}

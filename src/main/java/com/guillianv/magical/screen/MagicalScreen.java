package com.guillianv.magical.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;

public class MagicalScreen extends EffectRenderingInventoryScreen<InventoryMenu> {


    public static final ResourceLocation MAGICAL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/magical.png");

    public MagicalScreen(Player player) {
        super(player.inventoryMenu, player.getInventory(), Component.translatable("container.magical"));
        this.passEvents = true;
        this.titleLabelX = 97;
    }


    @Override
    protected void containerTick() {
        super.containerTick();
    }


    @Override
    protected void init() {

    }

    @Override
    protected void renderLabels(PoseStack poseStack, int p_98890_, int p_98891_) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }

    @Override
    public void render(PoseStack poseStack, int p_98876_, int p_98877_, float p_98878_) {
        this.renderBackground(poseStack);

    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_98871_, int p_98872_, int p_98873_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MAGICAL_GUI_TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }



}

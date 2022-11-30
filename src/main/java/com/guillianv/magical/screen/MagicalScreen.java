package com.guillianv.magical.screen;

import com.guillianv.magical.Magical;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MagicalScreen extends AbstractContainerScreen<MagicalMenu> {


    public static final ResourceLocation MAGICAL_GUI_TEXTURE = new ResourceLocation(Magical.MOD_ID,"textures/gui/container/magical.png");



    public MagicalScreen(MagicalMenu magicalMenu,Inventory inventory, Component component ) {
        super(magicalMenu, inventory, component);
        this.passEvents = true;
        this.titleLabelX = 97;
    }


    public MagicalScreen(Player player) {
        super(ModMenuTypes.MAGICAL_MENU.get().create(0,player.getInventory()), player.getInventory(), Component.translatable("container.magical"));
        this.passEvents = true;
        this.titleLabelX = 97;
    }


    @Override
    protected void containerTick() {
        super.containerTick();
    }



    @Override
    protected void init() {

        imageHeight = 136;
        imageWidth = 195;
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int p_98890_, int p_98891_) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }

    @Override
    public void render(PoseStack poseStack, int p_98876_, int p_98877_, float p_98878_) {
        this.renderBackground(poseStack);

        super.render(poseStack,p_98876_,p_98877_,p_98878_);


    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_98871_, int p_98872_, int p_98873_) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MAGICAL_GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        this.renderTabButton(poseStack);
    }




    protected void renderTabButton(PoseStack poseStack) {
      /*
        RenderSystem.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        this.blit(poseStack, l, i1, j, k, 28, 32);
        this.itemRenderer.blitOffset = 100.0F;

        this.itemRenderer.renderAndDecorateItem(itemstack, l, i1);
        this.itemRenderer.renderGuiItemDecorations(this.font, itemstack, l, i1);
        this.itemRenderer.blitOffset = 0.0F;*/
    }



}

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

    private boolean scrolling;

    private int scrollerTopXOffset = 175;
    private int scrollerTopYOffset = 18;

    private int scrollerMaxHeight = 124;

    private int tabBarWidth = 12;
    private int tabBarHeight = 15;

    private int getTabBarYOffset = 0;

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

        this.imageHeight = 136;
        this.imageWidth = 195;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
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


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int id) {

        if (id == 0) {
            double posXInsideContainer = mouseX - (double)this.leftPos;
            double posYInsideContainer = mouseY - (double)this.topPos;


            if (this.insideScrollbar(posXInsideContainer, posYInsideContainer)) {
                this.scrolling = true;
                return true;
            }

        }

        return super.mouseClicked(mouseX, mouseY, id);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int p_97754_, double p_97755_, double p_97756_) {
        if (this.scrolling){
            double posXInsideContainer = mouseX - (double)this.leftPos;
            double posYInsideContainer = mouseY - (double)this.topPos;


            if (posYInsideContainer <= scrollerTopYOffset + tabBarHeight /2){
                this.getTabBarYOffset = 0;
            }else if (posYInsideContainer + tabBarHeight - tabBarHeight /2 >  scrollerMaxHeight  ){
                this.getTabBarYOffset = scrollerMaxHeight - tabBarHeight - scrollerTopYOffset ;
            }else {
                this.getTabBarYOffset = (int) posYInsideContainer - scrollerTopYOffset - tabBarHeight /2 ;
            }




        } else {
            return super.mouseDragged(mouseX, mouseY, p_97754_, p_97755_, p_97756_);
        }
        return super.mouseDragged(mouseX, mouseY, p_97754_, p_97755_, p_97756_);
    }

    @Override
    public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
        this.scrolling = false;
        return super.mouseReleased(p_97812_, p_97813_, p_97814_);
    }

    protected boolean insideScrollbar(double mouseX, double mouseY) {

        int tabX0 = scrollerTopXOffset;
        int tabY0 = scrollerTopYOffset;
        int tabX1 = tabX0 + tabBarWidth ;
        int tabY1 = tabY0 + tabBarHeight + getTabBarYOffset ;

        return mouseX >= (double)tabX0 && mouseY >= (double)tabY0 && mouseX < (double)tabX1 && mouseY < (double)tabY1;
    }

    protected void renderTabButton(PoseStack poseStack) {


        RenderSystem.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        this.blit(poseStack, this.leftPos+this.scrollerTopXOffset, this.topPos+this.scrollerTopYOffset+this.getTabBarYOffset, 0, 136, tabBarWidth, tabBarHeight);
/*
        this.itemRenderer.blitOffset = 100.0F;

        this.itemRenderer.renderAndDecorateItem(itemstack, l, i1);
        this.itemRenderer.renderGuiItemDecorations(this.font, itemstack, l, i1);
        this.itemRenderer.blitOffset = 0.0F;*/
    }



}

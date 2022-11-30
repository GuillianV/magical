package com.guillianv.magical.screen;


import com.guillianv.magical.blocks.ModBlocks;
import com.guillianv.magical.blocks.entity.AltarBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;


public class MagicalMenu extends AbstractContainerMenu {
    public Player player;



    public MagicalMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv);

    }



    public MagicalMenu(int id, Inventory inv) {
        super(ModMenuTypes.MAGICAL_MENU.get(), id);
        player = inv.player;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }
}
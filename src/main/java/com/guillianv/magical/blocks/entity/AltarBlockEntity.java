package com.guillianv.magical.blocks.entity;

import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.items.ModItems;
import com.guillianv.magical.items.Scroll;
import com.guillianv.magical.items.Wand;
import com.guillianv.magical.screen.AltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AltarBlockEntity extends BlockEntity implements MenuProvider, IAnimatable {

    protected final ContainerData data;

    protected boolean craftable = false;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public boolean isCraftable(){
        return this.craftable;
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(3){

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {

            switch (slot){
                case 0:
                    if (stack.getItem() instanceof Wand)
                        return true;
                    break;
                case 1 :
                    if (stack.getItem() instanceof Scroll)
                        return true;
                case 2:
                    return false;

            }

                return false;


        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };


    public AltarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ALTAR.get(), blockPos, blockState);

        data = new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0: return ( AltarBlockEntity.this.isCraftable()) ? 1 : 0;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0: AltarBlockEntity.this.craftable = value == 1   ; break;
                }

            }

            @Override
            public int getCount() {
                return 1;
            }
        };


    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AltarMenu(id,inventory,this,this.data);
    }

    //Called when opening entity
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return  lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    //On loading world, put items in inventory
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    //When destroyed or unloaded
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    //When entity is created
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }

    //Each time and item enter in slot of block
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory",itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }



    //Drop when block is destroyed
    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    //On each tick
    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity pEntity) {

        Wand wand = null;
        if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof Wand)
            wand = (Wand) pEntity.itemHandler.getStackInSlot(0).getItem();

        Scroll scroll = null;
        if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof Scroll)
            scroll = (Scroll) pEntity.itemHandler.getStackInSlot(1).getItem();


        if (scroll == null || wand == null){
            pEntity.data.set(0,0);
        }else {
            pEntity.data.set(0,1);

            if (pEntity.itemHandler.getStackInSlot(2).getCount() == 0){
                pEntity.itemHandler.extractItem(0, 1, false);
                pEntity.itemHandler.extractItem(1, 1, false);
                Wand newWand = (Wand) ModItems.WAND_NORMAL.get();

                newWand.setEntityType(scroll.entityType);
                pEntity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.WAND_NORMAL.get(),
                        pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            }


        }

    }

    //region Animation

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<AltarBlockEntity>
                (this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder animationBuilder =  new AnimationBuilder().addAnimation("animation.altar.play", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
        event.getController().setAnimation(animationBuilder);

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}

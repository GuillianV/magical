package com.guillianv.magical.blocks.entity;

import com.guillianv.magical.blocks.RecognizerBlock;
import com.guillianv.magical.blocks.model.RecognizerBlockModel;
import com.guillianv.magical.blocks.render.RecognizerBlockRenderer;
import com.guillianv.magical.items.Scroll;
import com.guillianv.magical.items.Wand;
import com.guillianv.magical.screen.RecognizerMenu;
import net.minecraft.client.gui.screens.Screen;
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

public class RecognizerBlockEntity extends BlockEntity implements MenuProvider, IAnimatable {

    protected final ContainerData data;

    protected boolean craftable = false;
    protected int progress = 0;
    protected int maxProgress =600;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public boolean isCraftable(){
        return this.craftable;
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(2){

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {

            switch (slot){
                case 0:
                    if (stack.getItem() instanceof Scroll){
                        if (stack.hasTag() && stack.getShareTag().contains(Scroll.nbt_initialized) && stack.getShareTag().contains(Scroll.nbt_revealed)){
                            if (!stack.getShareTag().getBoolean(Scroll.nbt_revealed))
                            {
                                return true;
                            }

                        }
                    }
                    break;
                case 1 :
                    return false;

            }

                return false;


        }

        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }
    };


    public RecognizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.RECOGNIZER.get(), blockPos, blockState);

        data = new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0: return ( RecognizerBlockEntity.this.isCraftable()) ? 1 : 0;
                    case 1: return   RecognizerBlockEntity.this.progress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0: RecognizerBlockEntity.this.craftable = value == 1   ; break;
                    case 1: RecognizerBlockEntity.this.progress = value ; break;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };


    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Recognizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RecognizerMenu(id,inventory,this,this.data);
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
    public static void tick(Level level, BlockPos pos, BlockState state, RecognizerBlockEntity pEntity) {

        ItemStack scrollStack = pEntity.itemHandler.getStackInSlot(0);
        ItemStack revealedScrollStack = pEntity.itemHandler.getStackInSlot(1);

        Scroll scroll = null;

        if (scrollStack.getItem() instanceof Scroll)
            scroll = (Scroll) scrollStack.getItem();


        if (scroll != null && revealedScrollStack.getCount() == 0){

                Scroll newScroll = (Scroll) scrollStack.copy().getItem();
                ItemStack newItemStack = new ItemStack(newScroll,revealedScrollStack.getCount() + 1);
                newItemStack.readShareTag(scrollStack.getShareTag());
                newScroll.reveal(newItemStack,true);
                pEntity.itemHandler.extractItem(0, 1, false);
                pEntity.itemHandler.setStackInSlot(1,newItemStack );




        }

    }

    //region Animation

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<RecognizerBlockEntity>
                (this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder animationBuilder =  new AnimationBuilder().addAnimation(RecognizerBlockModel.animationName, ILoopType.EDefaultLoopTypes.LOOP);
        event.getController().setAnimation(animationBuilder);

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}
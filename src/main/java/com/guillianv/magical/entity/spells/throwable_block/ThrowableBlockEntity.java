package com.guillianv.magical.entity.spells.throwable_block;

import com.guillianv.magical.entity.spells.throwable_block.model.ThrowableBlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Collections;

public class ThrowableBlockEntity extends LivingEntity implements IAnimatable {



    public  AnimationFactory  factory = GeckoLibUtil.createFactory(this);
    public AnimationBuilder animationBuilder = new AnimationBuilder().addAnimation(ThrowableBlockModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE); ;

    private static final EntityDataAccessor<String> DATA_TEXTURE_ID = SynchedEntityData.defineId(ThrowableBlockEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> DATA_BLOCK_ID = SynchedEntityData.defineId(ThrowableBlockEntity.class, EntityDataSerializers.INT);



    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(animationBuilder);

        return PlayState.CONTINUE;
    }

    public ThrowableBlockEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }



    @Override
    public AttributeMap getAttributes() {
        return  new AttributeMap(LivingEntity.createLivingAttributes().build());
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_) {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
       return   Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getAllSlots() {
        return   Collections.emptyList();
    }

    @Override
    public boolean hasItemInSlot(EquipmentSlot p_21034_) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }



    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_TEXTURE_ID, "minecraft:textures/block/stone.png");
        this.getEntityData().define(DATA_BLOCK_ID, 1);
    }

    public String getTextureId(){
        return this.getEntityData().get(DATA_TEXTURE_ID);
    }

    public void setTextureId(String value){
        this.getEntityData().set(DATA_TEXTURE_ID,value);
    }

    public void setBlockId(int id){
        this.getEntityData().set(DATA_BLOCK_ID,id);
    }

    public int getBlockId(){
        return this.getEntityData().get(DATA_BLOCK_ID);
    }


    @Override
    public void setInvulnerable(boolean invulnerable) {
        super.setInvulnerable(true);
    }

    @Override
    public void tick() {
        if (this.isOnGround() || this.tickCount > 100  ){


            if (!this.level.isClientSide()){
                level.setBlockAndUpdate(new BlockPos(new BlockPos(position().x,position().y-1,position().z)).above(),Block.stateById(getBlockId()));
                this.remove(RemovalReason.DISCARDED);
            }

        }

        super.tick();



    }


    //endregion
}

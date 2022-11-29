package com.guillianv.magical.entity.spells.fire_sword;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.fire_sword.model.FireSwordModel;
import com.guillianv.magical.entity.spells.throwable_block.ThrowableBlockEntity;
import com.guillianv.magical.entity.spells.throwable_block.model.ThrowableBlockModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FireSwordEntity extends LivingEntity implements IAnimatable {



    private float damages = 7;


    private int groundCheck =3;
    private int actualGroundCheck =0;

    public FireSwordEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    //region Animation

    public  AnimationFactory  factory = GeckoLibUtil.createFactory(this);
    public AnimationBuilder animationBuilder = new AnimationBuilder().addAnimation(FireSwordModel.animationName, ILoopType.EDefaultLoopTypes.LOOP); ;


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(animationBuilder);

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }


    //endregion

    //region Override Methods

    public void setDamages(float damages) {
        this.damages = damages;
    }

    public float getDamages() {
        return damages;
    }

    public void setRotation(float roty,float rotx){
        this.setRot(roty,rotx);
    }


    @Override
    public void tick() {

        if (this.isOnGround() || this.tickCount > 100  ){


            if (groundCheck > actualGroundCheck){
                actualGroundCheck++;
            }else {

                if (!this.level.isClientSide()){

                    List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - 1.5D, this.getY() - 0.0D, this.getZ() - 1.5D, this.getX() + 1.5D, this.getY() +  2.0D, this.getZ() +1.5D), Entity::isAlive);
                    for(Entity entity : list1) {

                        if (entity instanceof LivingEntity){
                            LivingEntity sp = (LivingEntity)entity;
                            sp.hurt(DamageSource.mobAttack(sp),damages);
                        }

                    }

                    level.setBlockAndUpdate(new BlockPos(position().x,position().y-1,position().z).above(),Blocks.FIRE.defaultBlockState());

                    this.remove(RemovalReason.DISCARDED);
                }
            }



        }

        super.tick();

     super.tick();
    }


    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
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
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

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
    public boolean isInvulnerable() {
        return true;
    }


    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_) {
        return true;
    }


    //endregion
}

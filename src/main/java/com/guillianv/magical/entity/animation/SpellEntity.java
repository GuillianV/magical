package com.guillianv.magical.entity.animation;

import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public abstract class SpellEntity extends LivingEntity implements IAnimatable  {

    public  AnimationFactory  factory = GeckoLibUtil.createFactory(this);
    public abstract AnimationBuilder builder() ;
    public abstract  Animation animation();

    protected static final EntityDataAccessor<Integer> DATA_SENDER_ID = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.INT);


    protected static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_X = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_Y = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_Z = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);




    protected float scale = 1f;


    public void onSpellAnimationEnd(){

        this.remove(RemovalReason.DISCARDED);
    }


    public void setLookAngle(Vec3 lookAngle) {
        this.getEntityData().set(DATA_LOOK_ANGLE_X, (float) lookAngle.x);
        this.getEntityData().set(DATA_LOOK_ANGLE_Y, (float) lookAngle.y);
        this.getEntityData().set(DATA_LOOK_ANGLE_Z, (float) lookAngle.z);

    }

    public Vec3 getLookAngle(){
        return new Vec3(this.getEntityData().get(DATA_LOOK_ANGLE_X),this.getEntityData().get(DATA_LOOK_ANGLE_Y),this.getEntityData().get(DATA_LOOK_ANGLE_Z));
    }

    @Override
    public float getScale() {
        return scale;
    }

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

        event.getController().setAnimation(builder());

        return PlayState.CONTINUE;
    }

    public SpellEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AttributeInstance getAttribute(@NotNull Attribute attribute) {
        return super.getAttribute(attribute);
    }


    @Override
    public AttributeMap getAttributes() {
        return  new AttributeMap(LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH).build());
    }

    @Override
    public double getAttributeBaseValue(Attribute attribute) {

        return super.getAttributeBaseValue(attribute);
    }

    @Override
    public double getAttributeValue(Attribute attribute) {
        return super.getAttributeValue(attribute);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    public void setSenderId(int playerId){
        this.getEntityData().set(DATA_SENDER_ID, playerId);
    }

    public int getSender(){
       return this.getEntityData().get(DATA_SENDER_ID);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_SENDER_ID, 0);
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
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void tick() {

        if (this.tickCount >= animation().animationLength){
            this.onSpellAnimationEnd();
        }
    }
}

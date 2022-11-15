package com.guillianv.magical.entity.animation;

import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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


    //region properties and get/set

    public  AnimationFactory  factory = GeckoLibUtil.createFactory(this);
    public abstract AnimationBuilder builder() ;
    public abstract  Animation animation();

    private static final EntityDataAccessor<Integer> DATA_SENDER_ID = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_INITIAL_POS_X = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_INITIAL_POS_Y = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_INITIAL_POS_Z = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);


    protected float scale = 1f;







    public void setInitialPos(Vec3 pos){

        this.getEntityData().set(DATA_INITIAL_POS_X, (float) pos.x);
        this.getEntityData().set(DATA_INITIAL_POS_Y, (float) pos.y);
        this.getEntityData().set(DATA_INITIAL_POS_Z, (float) pos.z);

    }

    public Vec3 getInitialPos(){
        return new Vec3(this.getEntityData().get(DATA_INITIAL_POS_X),this.getEntityData().get(DATA_INITIAL_POS_Y),this.getEntityData().get(DATA_INITIAL_POS_Z));
    }

    @Override
    public float getScale() {
        return scale;
    }


    public void setSenderId(int entityId){
        this.getEntityData().set(DATA_SENDER_ID, entityId);
    }

    public int getSenderId(){
        return this.getEntityData().get(DATA_SENDER_ID);
    }

    @Nullable
    public LivingEntity getSenderLivingEntity(){
        Entity e = level.getEntity(getSenderId());
        if (e != null && e instanceof LivingEntity)
            return (LivingEntity) e;
        return null;
    }


    //endregion

    public SpellEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    public  static SpellEntity create(EntityType<? extends SpellEntity> entityType, Level level, LivingEntity sender) {
       SpellEntity spellEntity = entityType.create(level);
       spellEntity.setSenderId(sender.getId());
       Vec3 position = new Vec3(sender.position().x,sender.position().y +sender.getEyeHeight(),sender.position().z);
       spellEntity.setInitialPos(position);
       spellEntity.setPos(position);
       spellEntity.setRot(sender.getYRot(),sender.getXRot());
       return spellEntity;
    }


    //region Animation
    public void onSpellAnimationEnd(){
        this.remove(RemovalReason.DISCARDED);
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


    //endregion

    //region Override Methods

    @Override
    public void tick() {

        if (this.tickCount >= animation().animationLength){
            this.onSpellAnimationEnd();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_SENDER_ID, 0);
        //Set initial sender position
        this.getEntityData().define(DATA_INITIAL_POS_X, 0f);
        this.getEntityData().define(DATA_INITIAL_POS_Y, 0f);
        this.getEntityData().define(DATA_INITIAL_POS_Z, 0f);
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
        return null;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }



    //endregion
}

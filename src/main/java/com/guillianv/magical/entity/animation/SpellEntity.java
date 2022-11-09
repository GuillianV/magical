package com.guillianv.magical.entity.animation;

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

    protected float scale = 1f;


    public void onSpellAnimationEnd(){

        this.remove(RemovalReason.DISCARDED);
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


    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
    }


    @Override
    public void tick() {
        if (this.tickCount >= animation().animationLength){
            this.onSpellAnimationEnd();
        }
    }
}

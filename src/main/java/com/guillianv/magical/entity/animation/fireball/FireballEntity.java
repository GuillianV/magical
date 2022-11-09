package com.guillianv.magical.entity.animation.fireball;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class FireballEntity extends SpellEntity {


    private double speed = 1;

    private static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_X = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_Y = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LOOK_ANGLE_Z = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);



    public FireballEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
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
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation("animation.fireball.idle", ILoopType.EDefaultLoopTypes.LOOP);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Magical.MOD_ID, "animations/fireball.animation.json")).getAnimation("animation.fireball.idle");
    }

    @Override
    public void onSpellAnimationEnd() {

    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_LOOK_ANGLE_X, 0f);
        this.getEntityData().define(DATA_LOOK_ANGLE_Y, 0f);
        this.getEntityData().define(DATA_LOOK_ANGLE_Z, 0f);
    }

    @Override
    public void tick() {

        this.setPos(this.getX() + this.getLookAngle().x * speed , this.getY() +  this.getLookAngle().y * speed, this.getZ() + this.getLookAngle().z * speed);
        if (this.tickCount > 80){
            this.remove(RemovalReason.DISCARDED);
        }

    }
}

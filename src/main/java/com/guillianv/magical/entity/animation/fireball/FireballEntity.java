package com.guillianv.magical.entity.animation.fireball;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Random;

public class FireballEntity extends SpellEntity {


    private double speed = 1;
    Random rand = new Random();


    BlockPos oldPos = new  BlockPos(Vec3.ZERO);
    BlockPos oldPos2 = new  BlockPos(Vec3.ZERO);


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




    private void fireBallExplode(Level level){
        Player player = level.players().get(getSender());
        level.explode(player, DamageSource.MAGIC, new  ExplosionDamageCalculator(),this.getX(),this.getY(),this.getZ(),2f,true, Explosion.BlockInteraction.BREAK);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void remove(RemovalReason removalReason) {

        if (level.isClientSide()){
            LevelLightEngine levelLightEngine =  level.getLightEngine();
            levelLightEngine.checkBlock(oldPos);
            levelLightEngine.checkBlock(oldPos2);

        }
        super.remove(removalReason);
    }




    @Override
    public void tick() {

        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY()+this.getEyeHeight(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.LARGE_SMOKE);
        areaeffectcloud.setRadius(0.5f);
        areaeffectcloud.setRadiusPerTick(0.5f);
        areaeffectcloud.setDuration(1);
        level.addParticle(ParticleTypes.FLAME,this.getX(),this.getY()+this.getEyeHeight(),this.getZ(),rand.nextDouble(0.1),rand.nextDouble(0.1),rand.nextDouble(0.1));
        level.addFreshEntity(areaeffectcloud);




        Block block = level.getBlockState(new BlockPos(this.position())).getBlock();
        if (block != Blocks.AIR){
            fireBallExplode(level);
            return;
        }


        Entity entity = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT,this,getX(),getY(),getZ(),getBoundingBox());
        if (entity!=null){
            fireBallExplode(level);
            entity.hurt(DamageSource.MAGIC,1);
            return;
        }

        this.setPos(this.getX() + this.getLookAngle().x * speed , this.getY() +  this.getLookAngle().y * speed, this.getZ() + this.getLookAngle().z * speed);
        if (this.tickCount > 80){
            fireBallExplode(level);
            return;
        }


        if (level.isClientSide()){
            BlockPos closestPos = new BlockPos(this.position().x,this.position().y,this.position().z);
            LevelLightEngine levelLightEngine =  level.getLightEngine();
            levelLightEngine.onBlockEmissionIncrease(closestPos,25);
            levelLightEngine.checkBlock(oldPos2);

            oldPos2 = oldPos;
            oldPos = closestPos;

        }


    }

}

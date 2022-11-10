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
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.io.Console;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class FireballEntity extends SpellEntity {


    private double speed = 2;
    private float explosionRadius = 2f;
    private float particlesSmokeRadius = 0.5f;
    private float particlesSmokeRadiusPerTick = 0.5f;
    private int particlesSmokeDuration = 1;
    private float entityDamageDealth= 3;
    private int expirationTime = 80;



    private Random rand = new Random();


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
        level.explode(player, DamageSource.MAGIC, new  ExplosionDamageCalculator(),this.getX(),this.getY(),this.getZ(),explosionRadius,true, Explosion.BlockInteraction.BREAK);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void remove(RemovalReason removalReason) {

        super.remove(removalReason);
    }




    @Override
    public void tick() {

        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY()+this.getEyeHeight(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.LARGE_SMOKE);
        areaeffectcloud.setRadius(particlesSmokeRadius);
        areaeffectcloud.setRadiusPerTick(particlesSmokeRadiusPerTick);
        areaeffectcloud.setDuration(particlesSmokeDuration);
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
            entity.hurt(DamageSource.MAGIC,entityDamageDealth);
            return;
        }


        this.setPos(this.getX() + this.getLookAngle().x * speed , this.getY() +  this.getLookAngle().y * speed, this.getZ() + this.getLookAngle().z * speed);
        if (this.tickCount > expirationTime){
            fireBallExplode(level);
            return;
        }



    }

}

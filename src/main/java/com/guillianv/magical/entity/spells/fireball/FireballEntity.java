package com.guillianv.magical.entity.spells.fireball;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.fireball.model.FireballModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Random;

public class FireballEntity extends SpellEntity {


    private double speed = 1;
    private float explosionRadius = 2f;
    private float particlesSmokeRadius = 0.5f;
    private float particlesSmokeRadiusPerTick = 0.5f;
    private int particlesSmokeDuration = 1;
    private float entityDamageDealth= 3;
    private int expirationTime = 80;
    private Random rand = new Random();



    public FireballEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    private void fireBallExplode(Level level){

        if (level.isClientSide())
            return;

        LivingEntity livingEntity = getSenderLivingEntity();
        if (livingEntity != null){
            level.explode(livingEntity, DamageSource.MAGIC, new  ExplosionDamageCalculator(),this.getX(),this.getY(),this.getZ(),explosionRadius,true, Explosion.BlockInteraction.BREAK);
            this.remove(RemovalReason.DISCARDED);
        }else {
            this.remove(RemovalReason.UNLOADED_WITH_PLAYER);
        }


    }


    public void SpawnParticles(){
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY()+this.getEyeHeight(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.LARGE_SMOKE);
        areaeffectcloud.setRadius(particlesSmokeRadius);
        areaeffectcloud.setRadiusPerTick(particlesSmokeRadiusPerTick);
        areaeffectcloud.setDuration(particlesSmokeDuration);
        level.addParticle(ParticleTypes.FLAME,this.getX(),this.getY()+this.getEyeHeight(),this.getZ(),rand.nextDouble(0.1),rand.nextDouble(0.1),rand.nextDouble(0.1));
        level.addFreshEntity(areaeffectcloud);

    }

    //region Animation


    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(FireballModel.animationName, ILoopType.EDefaultLoopTypes.LOOP);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(FireballModel.geoAnimation).getAnimation(FireballModel.animationName);
    }

    //endregion

    //region Override Methods

    @Override
    public boolean Init() {

        setPos(position().x + getLookAngle().x,position().y + getLookAngle().y,position().z + getLookAngle().z);

        return super.Init();
    }
    @Override
    public void tick() {

        SpawnParticles();

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

    @Override
    public void remove(RemovalReason removalReason) {

        super.remove(removalReason);
    }


    @Override
    public String spellDescription() {
        return "Destruction";
    }

    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.FIREBALL.getKey().location();
        return resourceLocation.toString();
    }


    @Override
    public void onSpellAnimationEnd() {

    }

    //endregion


}

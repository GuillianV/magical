package com.guillianv.magical.entity.spells.fireball;

import com.guillianv.magical.capabilites.PlayerSpellsProvider;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.IUpgradable;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.fireball.model.FireballModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireballEntity extends SpellEntity {

    private final UpgradeProperty damages = new UpgradeProperty(new TranslatableContents("entity_property.damages"),7d,1d) ;
    private final UpgradeProperty speed = new UpgradeProperty(new TranslatableContents("entity_property.speed"),1d,0.1d) ;

    private final UpgradeProperty explosionRadius = new UpgradeProperty(new TranslatableContents("entity_property.explosion_radius"),2d,1d) ;

    private final UpgradeProperty expirationTime = new UpgradeProperty(new TranslatableContents("entity_property.expiration_time"),80d,10d) ;


    private final float particlesSmokeRadius = 0.5f;
    private final float particlesSmokeRadiusPerTick = 0.5f;
    private final int particlesSmokeDuration = 1;
    private Random rand = new Random();



    public FireballEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    private void fireBallExplode(Level level){

        if (level.isClientSide())
            return;

        LivingEntity livingEntity = getSenderLivingEntity();
        if (livingEntity != null){
            level.explode(livingEntity, DamageSource.MAGIC, new  ExplosionDamageCalculator(),this.getX(),this.getY(),this.getZ(), (float) explosionRadius.value,true, Explosion.BlockInteraction.BREAK);
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

        Upgrade(getSpellLvl());
        SpawnParticles();

        Block block = level.getBlockState(new BlockPos(this.position())).getBlock();
        if (block != Blocks.AIR){
            fireBallExplode(level);
            return;
        }


        LivingEntity entity = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT,this,getX(),getY(),getZ(),getBoundingBox());
        if (entity!=null){
            fireBallExplode(level);
            entity.hurt(DamageSource.explosion(entity), (float) damages.value);
            return;
        }


        this.setPos(this.getX() + this.getLookAngle().x * speed.value , this.getY() +  this.getLookAngle().y * speed.value , this.getZ() + this.getLookAngle().z * speed.value );
        if (this.tickCount > expirationTime.value){
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


    @Override
    public void Upgrade(int level) {
        super.Upgrade(level);
        damages.level = level;
        damages.value = damages.defaultvalue + damages.upgradeValue * level;
        speed.level = level;
        speed.value = speed.defaultvalue + speed.upgradeValue * level;
        explosionRadius.level = level;
        explosionRadius.value = explosionRadius.defaultvalue + explosionRadius.upgradeValue * level;
        expirationTime.level = level;
        expirationTime.value = expirationTime.defaultvalue + expirationTime.upgradeValue * level;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(damages);
        upgradeProperties.add(speed);
        upgradeProperties.add(explosionRadius);
        upgradeProperties.add(expirationTime);
        return upgradeProperties;

    }


    //endregion


}

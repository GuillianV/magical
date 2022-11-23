package com.guillianv.magical.entity.animation.celestial_blessing;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.celestial_blessing.model.CelestialBlessingModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class CelestialBlessingEntity extends SpellEntity {


    private int absorptionDuration = 1200;

    private int absorptionAmplifier = 0;

    private int instantHealDuration = 1;

    private int instantHealAmplifier = 0;

    private int regenerationDuration = 80;

    private int regenerationAmplifier = 0;



    public CelestialBlessingEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(CelestialBlessingModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(CelestialBlessingModel.geoAnimation).getAnimation(CelestialBlessingModel.animationName);
    }

    //endregion

    //region Override Methods



    @Override
    public boolean Init() {

        setPos(position().x ,position().y - getSenderLivingEntity().getEyeHeight() ,position().z );

        LivingEntity livingEntity =  getSenderLivingEntity();
        livingEntity.removeAllEffects();
        livingEntity.clearFire();
        MobEffectInstance absorption = new MobEffectInstance(MobEffects.ABSORPTION,absorptionDuration,absorptionAmplifier);
        MobEffectInstance heal = new MobEffectInstance(MobEffects.HEAL,instantHealDuration,instantHealAmplifier);
        MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION,regenerationDuration,regenerationAmplifier);



        livingEntity.addEffect(absorption);
        livingEntity.addEffect(heal);
        livingEntity.addEffect(regeneration);

        this.setScale(2.5f);

        return super.Init();
    }


    public void SpawnParticles(){
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY()+this.getEyeHeight(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.CLOUD);
        areaeffectcloud.setRadius(1.5f);
        areaeffectcloud.setDuration(5);
     //   level.addParticle(ParticleTypes.CLOUD,this.getX(),this.getY()+this.getEyeHeight(),this.getZ(),random.nextDouble()-0.5f,random.nextDouble()-0.5f,random.nextDouble()-0.5f);
        level.addFreshEntity(areaeffectcloud);

    }



    @Override
    public void tick() {

        LivingEntity sender =getSenderLivingEntity();

        if (sender != null)
            setPos(sender.position());

        super.tick();
    }



    @Override
    public String spellDescription() {
        return "Celestial Blessing";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.CELESTIAL_BLESSING.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

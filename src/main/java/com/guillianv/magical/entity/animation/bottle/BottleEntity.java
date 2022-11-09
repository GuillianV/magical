package com.guillianv.magical.entity.animation.bottle;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class BottleEntity extends SpellEntity {

    boolean spawnPoison = false;
    private int areaRadius = 4 ;
    private int areaDuration = 100; //effet de 10s
    private int effectDuration = 100; //2.5s de poison
    private int effectAmplifier = 1; //Poison lvl 1


    public BottleEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation("animation.bottle.play", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Magical.MOD_ID, "animations/bottle.animation.json")).getAnimation("animation.bottle.play");
    }

    @Override
    public void tick() {
        super.tick();


        if (this.tickCount >=  animation().animationLength - animation().animationLength / 3 && !spawnPoison){
            spawnPoison = true;
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setParticle(ParticleTypes.WITCH);
            areaeffectcloud.setRadius(areaRadius);
            areaeffectcloud.setDuration(areaDuration);
             areaeffectcloud.setRadiusPerTick((7.0F - areaeffectcloud.getRadius()) / (float)areaeffectcloud.getDuration());


            MobEffectInstance mobEffect = new MobEffectInstance(MobEffects.POISON, effectDuration, effectAmplifier);
            areaeffectcloud.addEffect(mobEffect);
            this.level.addFreshEntity(areaeffectcloud);

        }

    }
}

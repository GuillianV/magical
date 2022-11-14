package com.guillianv.magical.entity.animation.bottle;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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
    public void setLookAngle(Vec3 lookAngle) {
        super.setLookAngle(lookAngle);

        BlockHitResult ray = rayTrace(level, level.players().get(this.getEntityData().get(DATA_SENDER_ID)), ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();


    }

    protected static BlockHitResult rayTrace(Level level, Player player, ClipContext.Fluid fluidMode) {
        double range = 15;

        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vector3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return level.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, fluidMode, player));
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

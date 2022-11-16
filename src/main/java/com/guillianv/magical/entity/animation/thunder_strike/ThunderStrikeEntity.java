package com.guillianv.magical.entity.animation.thunder_strike;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class ThunderStrikeEntity extends SpellEntity {


    public ThunderStrikeEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    boolean thundered = false;

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation("animation.thunder_strike.play", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Magical.MOD_ID, "animations/thunder_strike.animation.json")).getAnimation("animation.thunder_strike.play");
    }

    //endregion

    //region Override Methods

    @Override
    public boolean Init() {


        BlockHitResult ray = BlockUtils.simpleRayTrace(level, this,this.getInitialPos() ,this.getXRot(),this.getYRot(), ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();

        if (ray.distanceTo(getSenderLivingEntity()) > 10000){
            return false;
        }

        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);
        return super.Init();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount >=  animation().animationLength - animation().animationLength / 2 && !thundered){
            thundered = true;

            if (!level.isClientSide()){
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.setPos(this.position());
                lightningBolt.setDamage(7);
                level.addFreshEntity(lightningBolt);
            }



        }

    }


    @Override
    public String spellDescription() {
        return "Tempest";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.THUNDER_STRIKE.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

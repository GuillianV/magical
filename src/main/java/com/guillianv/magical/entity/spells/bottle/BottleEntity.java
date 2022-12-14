package com.guillianv.magical.entity.spells.bottle;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.bottle.model.BottleModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
    private int areaDuration = 150; //effet de 10s
    private int effectDuration = 150; //2.5s de poison
    private int effectAmplifier = 2; //Poison lvl 1


    public BottleEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }
    public void SpawnEffect(){
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.WITCH);
        areaeffectcloud.setRadius(areaRadius);
        areaeffectcloud.setDuration(areaDuration);
        areaeffectcloud.setRadiusPerTick((7.0F - areaeffectcloud.getRadius()) / (float)areaeffectcloud.getDuration());


        MobEffectInstance mobEffect = new MobEffectInstance(MobEffects.POISON, effectDuration, effectAmplifier);
        areaeffectcloud.addEffect(mobEffect);
        this.level.addFreshEntity(areaeffectcloud);
    }

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(BottleModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(BottleModel.geoAnimation).getAnimation(BottleModel.animationName);
    }

    //endregion

    //region Override Methods

    @Override
    public boolean Init() {


        BlockHitResult ray = BlockUtils.simpleRayTrace(level, this,this.getInitialPos() ,this.getXRot(),this.getYRot(), ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();

        if (ray.distanceTo(getSenderLivingEntity()) > 1000){
            return false;
        }

        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);

        return super.Init();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount >=  animation().animationLength - animation().animationLength / 3 && !spawnPoison){
            spawnPoison = true;
            SpawnEffect();
        }

    }


    @Override
    public String spellDescription() {
        return "Repudiate";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.BOTTLE.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

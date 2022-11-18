package com.guillianv.magical.entity.animation.earth_fist;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.earth_fist.model.EarthFistModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.BlockSnapshot;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class EarthFistEntity extends SpellEntity {


    public EarthFistEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    boolean fisted = false;

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(EarthFistModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(EarthFistModel.geoAnimation).getAnimation(EarthFistModel.animationName);
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

        if (this.tickCount >=  animation().animationLength - animation().animationLength / 4 && !fisted){
            fisted = true;

            if (!level.isClientSide()){



                


                PistonMovingBlockEntity entity = new PistonMovingBlockEntity(new BlockPos(position()),level.getBlockState(new BlockPos(position())));

                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.setPos(this.position());
                lightningBolt.setDamage(7);
                level.addFreshEntity(lightningBolt);
            }



        }

    }


    @Override
    public String spellDescription() {
        return "Terrestrial Impact";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.THUNDER_STRIKE.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

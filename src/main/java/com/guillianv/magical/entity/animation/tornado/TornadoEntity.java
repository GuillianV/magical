package com.guillianv.magical.entity.animation.tornado;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.throwable_block.ThrowableBlockEntity;
import com.guillianv.magical.entity.animation.tornado.model.TornadoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.List;
import java.util.Optional;

public class TornadoEntity extends SpellEntity {

    private float speed = 0.5f;

    public TornadoEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(TornadoModel.animationName, ILoopType.EDefaultLoopTypes.LOOP);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(TornadoModel.geoAnimation).getAnimation(TornadoModel.animationName);
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
        double equalize = 0;

            Block bottomBlock = level.getBlockState(new BlockPos(position().x,position().y-1,position().z)).getBlock();
            Block topBlock = level.getBlockState(new BlockPos(position().x,position().y+1,position().z)).getBlock();
            Block frontBlock  = level.getBlockState(new BlockPos(position().x + getLookAngle().x,position().y + 1,position().z + getLookAngle().z)).getBlock();

            if (bottomBlock == Blocks.AIR )
                equalize = -1;
            else if (topBlock == Blocks.AIR && frontBlock != Blocks.AIR || topBlock != Blocks.AIR && frontBlock != Blocks.AIR)
                equalize = +1;

        this.setPos(this.getX() + this.getLookAngle().x * speed , this.getY() +  equalize , this.getZ() + this.getLookAngle().z * speed);

        if (!level.isClientSide()){


            List<Entity> list  = this.level.getEntities(this, new AABB(this.getX() - 2.0D, this.getY() - 2.0D, this.getZ() - 2.0D, this.getX() + 2.0D, this.getY() + 6.0D + 2.0D, this.getZ() + 2.0D), Entity::isAlive);

            for(Entity entity : list) {

                entity.hurt(DamageSource.FALL,5);
                entity.setDeltaMovement(0,1f + speed/1.5f,0);
            }


        }else{

            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 1f, 0.8F + this.random.nextFloat() * 0.2F, false);
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 1f, 0.8F + this.random.nextFloat() * 0.2F, false);

        }



        if (this.tickCount > 150){
            this.remove(RemovalReason.DISCARDED);

        }

    }


    @Override
    public String spellDescription() {
        return "Tornado";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.TORNADO.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

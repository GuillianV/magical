package com.guillianv.magical.entity.animation.earth_fist;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.earth_fist.model.EarthFistModel;
import com.guillianv.magical.entity.animation.throwable_block.ThrowableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.BlockSnapshot;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class EarthFistEntity extends SpellEntity {

    private int radius = 1;

    private float maxProjection = 3f;
    private float damage = 15f;

    private float maxDamage = 20f;

    public EarthFistEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }



    boolean fisted = false;
    private int earthShakeZ= 0;

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

        if (ray.distanceTo(getSenderLivingEntity()) > 1000){
            return false;
        }



        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);

        earthShakeZ = -radius;

        this.setScale(2.5f);

        return super.Init();
    }


    @Override
    public void tick() {
        super.tick();

        if (this.tickCount >=  animation().animationLength - animation().animationLength / 4 && !fisted){
            fisted = true;

            if (level.isClientSide()){
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_LAND, SoundSource.WEATHER, 2f, 0.8F + this.random.nextFloat() * 0.2F, false);
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2f, 0.8F + this.random.nextFloat() * 0.2F, false);

            }


            List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - 2.0D, this.getY() - 2.0D, this.getZ() - 2.0D, this.getX() + 2.0D, this.getY() + 6.0D + 2.0D, this.getZ() + 2.0D), Entity::isAlive);
            for(Entity entity : list1) {



                Vec3 force =  new  Vec3( entity.position().x- this.position().x , entity.position().y - this.position().y ,entity.position().z - this.position().z ).normalize();
                float dist = this.distanceTo(entity);

                float ratio = 1/ dist;

                float damages = ratio * damage;
                if (damages > maxDamage)
                    damages = maxDamage;

                entity.hurt(DamageSource.ANVIL,damages);

                float projection = ratio;
                if (projection > this.maxProjection)
                    projection = maxProjection;



                entity.setDeltaMovement(force.x * projection,0.4f* projection,force.z* projection);
            }

        }

        if (fisted && earthShakeZ <= radius && !level.isClientSide()){


            int r = -radius;
            while (r <= (radius)){


                Vec3 pos = new Vec3(position().x + r,position().y-1,position().z + earthShakeZ);

                Block block = level.getBlockState(new BlockPos(pos)).getBlock();
                if (block != Blocks.BEDROCK && level.removeBlock(new BlockPos(pos),true)){

                    block.getDescriptionId();
                    String blockTextureValue;
                    if (block == Blocks.GRASS_BLOCK)
                        blockTextureValue = "dirt";
                    else
                        blockTextureValue =block.getDescriptionId().replaceAll("block.minecraft.","");

                    blockTextureValue = "minecraft:textures/block/"+blockTextureValue+".png";


                    ThrowableBlockEntity throwableBlockEntity = new ThrowableBlockEntity(ModEntityTypes.THROWABLE_BLOCK.get(),level);
                    throwableBlockEntity.setPos(pos);
                    throwableBlockEntity.setDeltaMovement(random.nextFloat()-0.5f,1.4f,random.nextFloat()-0.5f);
                    level.addFreshEntity(throwableBlockEntity);

                    Optional<Resource> resourceLocation = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(blockTextureValue));
                    if (resourceLocation.isPresent()){
                        throwableBlockEntity.setTextureId(blockTextureValue);

                        throwableBlockEntity.setBlockId(Block.getId(block.defaultBlockState()));

                    }


                }

                r++;
            }
            earthShakeZ++;
        }

    }


    @Override
    public String spellDescription() {
        return "Terrestrial Impact";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.EARTH_FIST.getKey().location();
        return resourceLocation.toString();
    }

    //endregion
}

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
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.BlockSnapshot;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Optional;
import java.util.Random;

public class EarthFistEntity extends SpellEntity {


    public EarthFistEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    boolean fisted = false;

    private int radius = 1;

    private int earthShakeX = 0;
    private int earthShakeY= 0;
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

        if (ray.distanceTo(getSenderLivingEntity()) > 10000){
            return false;
        }



        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);

        earthShakeX = -radius;
        earthShakeY = 0;
        earthShakeZ = -radius;


        return super.Init();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount >=  animation().animationLength - animation().animationLength / 4 && !fisted){
            fisted = true;

            if (level.isClientSide()){
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.WEATHER, 10.0F, 0.8F + this.random.nextFloat() * 0.2F, false);

            }

        }

        if (fisted){


            int r = 0;
            while (r <= (radius+radius +1)*3){



            if (!level.isClientSide()){

                Vec3 pos = new  Vec3(position().x ,position().y-1,position().z );

                if (earthShakeX < radius){
                    pos = new Vec3(position().x + earthShakeX,position().y-1,position().z + earthShakeZ);
                    earthShakeX++;
                }

                if (earthShakeX == radius && earthShakeZ < radius){
                    earthShakeZ++;
                    earthShakeX = -radius;
                }




                Block block = level.getBlockState(new BlockPos(pos)).getBlock();
                if (level.removeBlock(new BlockPos(pos),true)){

                    block.getDescriptionId();
                    String blockTextureValue = block.getDescriptionId().replaceAll("block.minecraft.","");
                    blockTextureValue = "minecraft:textures/block/"+blockTextureValue+".png";


                    ThrowableBlockEntity throwableBlockEntity = new ThrowableBlockEntity(ModEntityTypes.THROWABLE_BLOCK.get(),level);
                    throwableBlockEntity.setPos(pos);
                    throwableBlockEntity.setDeltaMovement(random.nextFloat()-0.5f,0.4f,random.nextFloat()-0.5f);
                    level.addFreshEntity(throwableBlockEntity);

                    Optional<Resource> resourceLocation = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(blockTextureValue));
                    if (resourceLocation.isPresent()){
                        throwableBlockEntity.setTextureId(blockTextureValue);

                        throwableBlockEntity.setBlockId(Block.getId(block.defaultBlockState()));

                    }


                }



            }

                r++;
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

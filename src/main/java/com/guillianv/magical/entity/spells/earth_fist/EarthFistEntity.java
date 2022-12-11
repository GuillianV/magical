package com.guillianv.magical.entity.spells.earth_fist;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.earth_fist.model.EarthFistModel;
import com.guillianv.magical.entity.spells.throwable_block.ThrowableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EarthFistEntity extends SpellEntity {




    private final UpgradeProperty damages = new UpgradeProperty(new TranslatableContents("entity_property.damages"),15d,1d) ;

    private final UpgradeProperty maxDamages = new UpgradeProperty(new TranslatableContents("entity_property.max_damages"),20d,1d) ;
    private final UpgradeProperty maxProjection = new UpgradeProperty(new TranslatableContents("entity_property.max_projection"),3d,0.1d) ;

    private final UpgradeProperty effectRadius = new UpgradeProperty(new TranslatableContents("entity_property.effect_radius"),1d,1d) ;


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

        this.Upgrade(getSpellLvl());
        earthShakeZ = (int) -effectRadius.value;

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


            List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - (this.effectRadius.value + this.getBbWidth()), this.getY() -(this.effectRadius.value + this.getBbWidth()), this.getZ() - (this.effectRadius.value + this.getBbWidth()), this.getX() + (this.effectRadius.value + this.getBbWidth()), this.getY() + 6.0D + (this.effectRadius.value + this.getBbWidth()), this.getZ() + (this.effectRadius.value + this.getBbWidth())), Entity::isAlive);
            for(Entity entity : list1) {



                Vec3 force =  new  Vec3( entity.position().x- this.position().x , entity.position().y - this.position().y ,entity.position().z - this.position().z ).normalize();
                float dist = this.distanceTo(entity);

                float ratio = 1/ dist;

                float damages = (float) (ratio * this.damages.value);
                if (damages > maxDamages.value)
                    damages = (float) maxDamages.value;

                entity.hurt(DamageSource.ANVIL,damages);

                float projection = ratio;
                if (projection > this.maxProjection.value)
                    projection = (float) this.maxProjection.value;



                entity.setDeltaMovement(force.x * projection,0.4f* projection,force.z* projection);
            }

        }

        if (fisted && earthShakeZ <= effectRadius.value && !level.isClientSide()){


            int r = (int) -effectRadius.value;
            while (r <= (effectRadius.value)){


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


    @Override
    public void Upgrade(int level) {
        super.Upgrade(level);
        damages.level = level;
        damages.value = damages.defaultvalue + damages.upgradeValue * level;
        maxDamages.level = level;
        maxDamages.value = maxDamages.defaultvalue + maxDamages.upgradeValue * level;
        effectRadius.level = level;
        effectRadius.value = effectRadius.defaultvalue + effectRadius.upgradeValue * level;
        maxProjection.level = level;
        maxProjection.value = maxProjection.defaultvalue + maxProjection.upgradeValue * level;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(damages);
        upgradeProperties.add(maxDamages);
        upgradeProperties.add(effectRadius);
        upgradeProperties.add(maxProjection);
        return upgradeProperties;

    }


    //endregion
}

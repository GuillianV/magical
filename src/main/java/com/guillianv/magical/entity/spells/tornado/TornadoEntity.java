package com.guillianv.magical.entity.spells.tornado;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.IUpgradable;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.throwable_block.ThrowableBlockEntity;
import com.guillianv.magical.entity.spells.tornado.model.TornadoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TornadoEntity extends SpellEntity {


    private UpgradeProperty speed = new UpgradeProperty(new TranslatableContents("entity_property.speed"), 0.5d,0.1d);

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
        double magn = Math.sqrt(Math.pow(getLookAngle().x(),2) + Math.pow(getLookAngle().z(),2)   );
        Vec3 power = new Vec3(1/magn * getLookAngle().x,0,1/magn * getLookAngle().z);
        setPos(position().x + power.x*2,position().y ,position().z + power.z*2);
        setScale(4.5f);
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
        double equalize = 0;


            double magn = Math.sqrt(Math.pow(getLookAngle().x(),2) + Math.pow(getLookAngle().z(),2)   );

            Vec3 power = new Vec3(1/magn * getLookAngle().x,0,1/magn * getLookAngle().z);

            Block bottomBlock = level.getBlockState(new BlockPos(position().x,position().y-1,position().z)).getBlock();
            Block frontBlock  = level.getBlockState(new BlockPos(position().x + power.x,position().y ,position().z + power.z)).getBlock();

            if (bottomBlock == Blocks.AIR )
                equalize = -0.4;
            else if ( frontBlock != Blocks.AIR)
                equalize = +0.4;

        this.setPos(this.getX() + power.x * speed.value  , this.getY() +  equalize , this.getZ() + power.z * speed.value );

        if (!level.isClientSide()){


            List<Entity> list  = this.level.getEntities(this, new AABB(this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D, this.getX() + 3.0D, this.getY() + 6.0D + 3.0D, this.getZ() + 3.0D), Entity::isAlive);

            for(Entity entity : list) {

                if (entity != getSenderLivingEntity())
                {
                    entity.hurt(DamageSource.FALL,5);
                    entity.setDeltaMovement(0,1f + speed.value /1.5f,0);
                }

            }

            if (this.tickCount%5 == 0){

                BlockPos bottomBackPos = new BlockPos(this.getX() - power.x + random.nextInt(2)-1,this.getY()-1,this.getZ()- power.z + random.nextInt(2)-1) ;
                Block block = level.getBlockState(bottomBackPos).getBlock();
                if (block != Blocks.BEDROCK && level.removeBlock(bottomBackPos,true)){

                    block.getDescriptionId();
                    String blockTextureValue;
                    if (block == Blocks.GRASS_BLOCK)
                        blockTextureValue = "dirt";
                    else
                        blockTextureValue = block.getDescriptionId().replaceAll("block.minecraft.","");


                    blockTextureValue = "minecraft:textures/block/"+blockTextureValue+".png";
                    ThrowableBlockEntity throwableBlockEntity = new ThrowableBlockEntity(ModEntityTypes.THROWABLE_BLOCK.get(),level);
                    throwableBlockEntity.setPos(bottomBackPos.getX(),bottomBackPos.getY()+1,bottomBackPos.getZ());
                    throwableBlockEntity.setDeltaMovement((random.nextDouble()-0.5d *3d),1.4f,(random.nextDouble()-0.5d *3d));
                    level.addFreshEntity(throwableBlockEntity);

                    Optional<Resource> resourceLocation = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(blockTextureValue));
                    if (resourceLocation.isPresent()){
                        throwableBlockEntity.setTextureId(blockTextureValue);

                        throwableBlockEntity.setBlockId(Block.getId(block.defaultBlockState()));

                    }


                }

            }

            if (this.tickCount%10 == 0){
                SpawnParticles();
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

    @Override
    public void Upgrade() {
        speed.value = speed.value + speed.upgradeValue;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(speed);
        return upgradeProperties;

    }

    //endregion
}

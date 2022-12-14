package com.guillianv.magical.entity.spells.thunder_strike;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.IUpgradable;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.thunder_strike.model.ThunderStrikeModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
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

import java.util.ArrayList;
import java.util.List;

public class ThunderStrikeEntity extends SpellEntity {


    private UpgradeProperty damages = new UpgradeProperty(new TranslatableContents("entity_property.damages"),7d,1d) ;

    public ThunderStrikeEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }
    boolean thundered = false;


    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(ThunderStrikeModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(ThunderStrikeModel.geoAnimation).getAnimation(ThunderStrikeModel.animationName);
    }

    //endregion

    //region Override Methods

    @Override
    public boolean Init() {


        BlockHitResult ray = BlockUtils.simpleRayTrace(level, this,this.getInitialPos() ,this.getXRot(),this.getYRot(), ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();

        LivingEntity livingEntity = getSenderLivingEntity();
        if (livingEntity != null && ray.distanceTo(livingEntity) > 1000){
            return false;
        }

        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);
        setScale(2.5f);
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
                lightningBolt.setDamage((float) damages.value);
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


    @Override
    public void Upgrade(int level) {
        super.Upgrade(level);
        damages.level = level;
        damages.value = damages.defaultvalue + damages.upgradeValue * level;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(damages);
        return upgradeProperties;

    }


    //endregion
}

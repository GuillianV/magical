package com.guillianv.magical.entity.spells.celestial_blessing;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.celestial_blessing.model.CelestialBlessingModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.ArrayList;
import java.util.List;

public class CelestialBlessingEntity extends SpellEntity {



    private int instantHealDuration = 1;


    private final UpgradeProperty absorptionDuration = new UpgradeProperty(new TranslatableContents("entity_property.absorption_duration"),1200d,300d) ;

    private final UpgradeProperty absorptionAmplifier = new UpgradeProperty(new TranslatableContents("entity_property.absorption_amplifier"),0d,1d) ;
    private final UpgradeProperty instantHealAmplifier = new UpgradeProperty(new TranslatableContents("entity_property.instant_heal_amplifier"),0d,1d) ;


    private final UpgradeProperty regenerationDuration = new UpgradeProperty(new TranslatableContents("entity_property.regeneration_duration"),80d,20d) ;

    private final UpgradeProperty regenerationAmplifier = new UpgradeProperty(new TranslatableContents("entity_property.regeneration_amplifier"),0d,1d) ;



    public CelestialBlessingEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    //region Animation

    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(CelestialBlessingModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(CelestialBlessingModel.geoAnimation).getAnimation(CelestialBlessingModel.animationName);
    }

    //endregion

    //region Override Methods



    @Override
    public boolean Init() {

        this.Upgrade(this.getSpellLvl());

        setPos(position().x ,position().y - getSenderLivingEntity().getEyeHeight() ,position().z );

        LivingEntity livingEntity =  getSenderLivingEntity();
        livingEntity.removeAllEffects();
        livingEntity.clearFire();
        MobEffectInstance absorption = new MobEffectInstance(MobEffects.ABSORPTION,(int) absorptionDuration.value,(int) absorptionAmplifier.value);
        MobEffectInstance heal = new MobEffectInstance(MobEffects.HEAL,instantHealDuration,(int)instantHealAmplifier.value);
        MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION,(int)regenerationDuration.value,(int)regenerationAmplifier.value);



        livingEntity.addEffect(absorption);
        livingEntity.addEffect(heal);
        livingEntity.addEffect(regeneration);

        this.setScale(2.5f);

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
        super.tick();

        LivingEntity sender =getSenderLivingEntity();

        if (sender != null)
            setPos(sender.position());


    }



    @Override
    public String spellDescription() {
        return "Celestial Blessing";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.CELESTIAL_BLESSING.getKey().location();
        return resourceLocation.toString();
    }

    @Override
    public void Upgrade(int level) {
        super.Upgrade(level);
        absorptionAmplifier.level = level;
        absorptionAmplifier.value = absorptionAmplifier.defaultvalue + absorptionAmplifier.upgradeValue * level;
        absorptionDuration.level = level;
        absorptionDuration.value = absorptionDuration.defaultvalue + absorptionDuration.upgradeValue * level;
        instantHealAmplifier.level = level;
        instantHealAmplifier.value = instantHealAmplifier.defaultvalue + instantHealAmplifier.upgradeValue * level;
        regenerationAmplifier.level = level;
        regenerationAmplifier.value = regenerationAmplifier.defaultvalue + regenerationAmplifier.upgradeValue * level;
        regenerationDuration.level = level;
        regenerationDuration.value = regenerationDuration.defaultvalue + regenerationDuration.upgradeValue * level;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(absorptionAmplifier);
        upgradeProperties.add(absorptionDuration);
        upgradeProperties.add(instantHealAmplifier);
        upgradeProperties.add(regenerationAmplifier);
        upgradeProperties.add(regenerationDuration);
        return upgradeProperties;

    }

    //endregion
}

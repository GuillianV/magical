package com.guillianv.magical.entity.spells.fire_rain;

import com.guillianv.magical.blocks.utils.BlockUtils;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.spells.SpellEntity;
import com.guillianv.magical.entity.spells.UpgradeProperty;
import com.guillianv.magical.entity.spells.fire_rain.model.FireRainModel;
import com.guillianv.magical.entity.spells.fire_sword.FireSwordEntity;
import com.guillianv.magical.entity.spells.throwable_block.ThrowableBlockEntity;
import com.guillianv.magical.entity.spells.throwable_block.model.ThrowableBlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireRainEntity  extends SpellEntity {




    private final UpgradeProperty damages = new UpgradeProperty(new TranslatableContents("entity_property.damages"),9d,1d) ;

    private final UpgradeProperty timeBetweenSummon = new UpgradeProperty(new TranslatableContents("entity_property.summon_delais"),3d,-1d) ;


    @Override
    public AnimationBuilder builder() {
        return  new AnimationBuilder().addAnimation(FireRainModel.animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public Animation animation() {
        return GeckoLibCache.getInstance().getAnimations().get(FireRainModel.geoAnimation).getAnimation(FireRainModel.animationName);
    }

    @Override
    public String spellDescription() {
        return "Hell";
    }


    @Override
    public String entityClassId() {
        ResourceLocation resourceLocation = ModEntityTypes.FIRE_SWORD_RAIN.getKey().location();
        return resourceLocation.toString();
    }

    public FireRainEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    public AttributeMap getAttributes() {
        return  new AttributeMap(LivingEntity.createLivingAttributes().build());
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_) {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return   Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getAllSlots() {
        return   Collections.emptyList();
    }

    @Override
    public boolean hasItemInSlot(EquipmentSlot p_21034_) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(builder());


        return PlayState.CONTINUE;
    }

    @Override
    public boolean Init() {


        BlockHitResult ray = BlockUtils.simpleRayTrace(level, this,this.getInitialPos() ,this.getXRot(),this.getYRot(), ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();

        if (ray.distanceTo(getSenderLivingEntity()) > 1000){
            return false;
        }



        Vec3 position = new Vec3(lookPos.getX() +0.5,lookPos.getY() + 1,lookPos.getZ()+0.5);
        setPos(position);


        this.setScale(1f);

        return super.Init();
    }



    @Override
    public void setInvulnerable(boolean invulnerable) {
        super.setInvulnerable(true);
    }

    @Override
    public void tick() {
        super.tick();

        if ( this.tickCount % (int) timeBetweenSummon.value == 0  ){


            if (!this.level.isClientSide()){

               LivingEntity livingEntity = getSenderLivingEntity();
               if (livingEntity != null){
                   int i = 0;
                   while (i < 1){

                       FireSwordEntity fireSwordEntity = new FireSwordEntity(ModEntityTypes.FIRE_SWORD.get(),level);
                       fireSwordEntity.setPos( this.position().x + random.nextInt(20)-10,this.position().y +7 ,this.position().z +random.nextInt(20)-10);
                       fireSwordEntity.setDeltaMovement(0,-0.1f,0);
                       fireSwordEntity.setDamages((float) damages.value);
                       fireSwordEntity.setRotation(0f,0f);
                       level.addFreshEntity(fireSwordEntity);

                       i++;
                   }

               }

            }

        }


    }


    @Override
    public void Upgrade(int level) {
        super.Upgrade(level);
        damages.level = level;
        damages.value = damages.defaultvalue + damages.upgradeValue * level;
        timeBetweenSummon.level = level;
        timeBetweenSummon.value = timeBetweenSummon.defaultvalue + timeBetweenSummon.upgradeValue * level;
    }

    @Override
    public List<UpgradeProperty> ShowProperties() {
        List<UpgradeProperty> upgradeProperties = new ArrayList<>();
        upgradeProperties.add(damages);
        return upgradeProperties;

    }


    //endregion
}

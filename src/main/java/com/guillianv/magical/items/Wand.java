package com.guillianv.magical.items;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class Wand extends Item {


    static float scaleValue = 1;

    protected EntityType<? extends SpellEntity> spellEntityType;

    public Wand(Properties properties) {
        super(properties);
    }

    public void setEntityType(EntityType<? extends SpellEntity> _spellEntityType){
        this.spellEntityType = _spellEntityType;
    }
    

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {



        if (!level.isClientSide() && spellEntityType != null){

            SpellEntity spellEntity = spellEntityType.create(level);
            spellEntity.setPos(new Vec3(player.position().x,player.position().y + player.getEyeHeight(),player.position().z));

            level.addFreshEntity(spellEntity);
            spellEntity.setLookAngle(player.getLookAngle());

        }


        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (level != null && spellEntityType != null){
            components.add(Component.literal("Binded with "+spellEntityType.toString()));
        }


        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        /*
        BottleEntity bottleEntity = new BottleEntity(ModEntityTypes.BOTTLE.get(),useOnContext.getLevel());
        bottleEntity.setPos( new Vec3(useOnContext.getClickedPos().getX(),useOnContext.getClickedPos().getY() ,useOnContext.getClickedPos().getZ()));
        if (!useOnContext.getLevel().isClientSide()){
            useOnContext.getLevel().addFreshEntity(bottleEntity);
        }*/


        return super.useOn(useOnContext);
    }


    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        if(itemStack.getDamageValue() != 0)
            return true;
        return false;
    }


    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }


    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }
}

package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
import java.util.Optional;
import java.util.function.Consumer;

public class Wand extends Item {


    static float scaleValue = 1;


    public Wand(Properties properties) {
        super(properties);
    }

    public void setEntityType(EntityType<? extends SpellEntity> _spellEntityType, ItemStack stack){

        CompoundTag tag = new CompoundTag();
        tag.putString("entity_type",_spellEntityType.getDescriptionId().replaceAll("entity.magical.", Magical.MOD_ID+":"));
        stack.readShareTag(tag);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {



        if (!level.isClientSide()){
            if(player.getItemInHand(interactionHand).hasTag()) {
                CompoundTag compoundTag = player.getItemInHand(interactionHand).getShareTag();
                if (compoundTag.contains("entity_type")){
                    String entityTypeValue = compoundTag.getString("entity_type");
                    Optional<EntityType<?>> typeOptional = EntityType.byString(entityTypeValue);
                    if (typeOptional.isPresent()){
                        EntityType<? extends SpellEntity> spellEntityType = (EntityType<? extends SpellEntity>) typeOptional.get();


                        SpellEntity spellEntity = SpellEntity.create(spellEntityType,level,player);
                        level.addFreshEntity(spellEntity);

                    }
                }



            }


        }


        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (level != null){

                if(itemStack.hasTag()) {
                    CompoundTag compoundTag = itemStack.getShareTag();
                    if (compoundTag.contains("entity_type")){
                        String entityTypeValue = compoundTag.getString("entity_type");
                        Optional<EntityType<?>> typeOptional = EntityType.byString(entityTypeValue);
                        if (typeOptional.isPresent()){

                            components.add(Component.literal("Binded with "+typeOptional.get().getDescriptionId().replaceAll("entity.magical.","")));

                        }}}

        }


        super.appendHoverText(itemStack, level, components, tooltipFlag);
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

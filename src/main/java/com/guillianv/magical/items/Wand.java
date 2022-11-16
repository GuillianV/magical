package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import com.guillianv.magical.items.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class Wand extends Item  {



    static float scaleValue = 1;

    int coolDownUpgrade = 0;

    public Wand(Properties properties, int coolDownUpgrade) {
        super(properties);
        this.coolDownUpgrade = coolDownUpgrade;
    }

    public boolean setEntityType(String entityTypeId, Rarity scrollRarity, ItemStack oldItemStack, ItemStack newItemStack,int scrollBaseCooldow){

        if (oldItemStack.hasTag() && oldItemStack.getTag().contains("entity_type"))
            return false;

        CompoundTag tag = new CompoundTag();
        tag.putString("entity_type",entityTypeId);
        tag.putString("scroll_rarity",scrollRarity.name());
        tag.putInt("scroll_cooldown",scrollBaseCooldow);
        newItemStack.readShareTag(tag);
        return true;
    }



    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag() && pStack.getShareTag().contains("entity_type") ;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {



        if (!level.isClientSide()){
                ItemStack itemStack = player.getItemInHand(interactionHand);
                CompoundTag compoundTag = itemStack.getShareTag();
                if (itemStack.hasTag() && compoundTag.contains("entity_type") && compoundTag.contains("scroll_cooldown")){


                    Optional<EntityType<?>> typeOptional = EntityType.byString(compoundTag.getString("entity_type"));
                    if (typeOptional.isPresent()){

                        EntityType<? extends SpellEntity> spellEntityType = (EntityType<? extends SpellEntity>) typeOptional.get();
                        SpellEntity spellEntity = SpellEntity.create(spellEntityType,level,player);
                        level.addFreshEntity(spellEntity);
                        int maxTickCooldown = compoundTag.getInt("scroll_cooldown");
                        maxTickCooldown = maxTickCooldown - maxTickCooldown * this.coolDownUpgrade / 100;
                        player.getCooldowns().addCooldown(itemStack.getItem(),maxTickCooldown);

                        itemStack.hurtAndBreak(1,player,(player2) -> player2.broadcastBreakEvent(player2.getUsedItemHand()));
                    }
                }






        }


        return super.use(level, player, interactionHand);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (level != null){
                if(itemStack.hasTag() && itemStack.getShareTag().contains("entity_type")) {
                    CompoundTag compoundTag = itemStack.getShareTag();
                    Optional<EntityType<?>> typeOptional = EntityType.byString(compoundTag.getString("entity_type"));
                    if (typeOptional.isPresent()){

                        EntityType<SpellEntity> spellEntityEntityType = (EntityType<SpellEntity>) typeOptional.get();
                        SpellEntity spellEntity = spellEntityEntityType.create(level);
                        String spellDescription = spellEntity.spellDescription();

                        if (compoundTag.contains("scroll_rarity")){

                            String rarityString =compoundTag.getString("scroll_rarity");
                            Rarity spellRarity = ItemUtils.getRarityWithStrings(rarityString);

                            components.add( Component.literal("Binded with scroll of ").append(ItemUtils.colorValueWithRarity(spellDescription,spellRarity)));

                        }else {
                            components.add(Component.literal("Binded with scroll of "+spellDescription));
                        }


                        }
                }
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


}

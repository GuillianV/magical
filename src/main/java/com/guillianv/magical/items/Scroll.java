package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.SpellEntity;
import com.guillianv.magical.items.utils.Ranking;
import com.guillianv.magical.items.utils.ScrollProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Scroll extends Item {

    public EntityType<? extends SpellEntity> entityType;

    public int baseCooldownTick;
    protected int cooldownModifier;

    final static String nbt_initialized = "Scroll_Initialized";
    final static String nbt_revealed = "Scroll_Revealed";
    final static String nbt_cooldown = "Scroll_Cooldown";
    final static String nbt_entity_type = "Scroll_EntityType";
    final static String nbt_rarity = "Scroll_Rarity";

    public Scroll(ScrollProperties properties) {
        super(properties);
        this.entityType = properties.getEntityType();
        this.baseCooldownTick = properties.getBaseCooldown();
        this.cooldownModifier = properties.getCooldownModifier();
    }

    public int getFinalCooldown(){
        Random random = new Random();
        int finalCooldown = this.baseCooldownTick-cooldownModifier + (random.nextInt(cooldownModifier*2+1));
        return finalCooldown;
    }

    public void initialize(ItemStack itemStack,boolean forceInit){
        if (forceInit || !itemStack.hasTag() || (itemStack.hasTag() && !itemStack.getShareTag().contains("Scroll_Initialized"))){

            String entityTypeName = entityType.getDescriptionId().replaceAll("entity.magical.", Magical.MOD_ID+":");
            CompoundTag nbt = new CompoundTag();

            nbt.putBoolean(nbt_initialized,true);
            nbt.putBoolean(nbt_revealed,false);
            nbt.putString(nbt_entity_type,entityTypeName);
            nbt.putInt(nbt_cooldown,getFinalCooldown());
            nbt.putString(nbt_rarity,getRarity(itemStack).name());

            itemStack.readShareTag(nbt);
        }
    }

    public void reveal(ItemStack itemStack,boolean reveal){
        if (itemStack.hasTag() && itemStack.getShareTag().contains(nbt_initialized)){
            CompoundTag nbt = itemStack.getShareTag();
            nbt.putBoolean(nbt_revealed,true);
            itemStack.readShareTag(nbt);
        }
    }


    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int intval, boolean bool) {

        initialize(itemStack,false);
        reveal(itemStack,true);
        super.inventoryTick(itemStack, level, entity, intval, bool);
    }


    @Override
    public boolean isFoil(ItemStack itemStack) {
        if (itemStack.hasTag() && itemStack.getShareTag().contains(Scroll.nbt_revealed) && itemStack.getShareTag().getBoolean(Scroll.nbt_revealed))
            return true;
        else
            return false;
    }

    @Nullable
    public static Scroll getRandomScroll(Rarity rarity){

        Random random = new  Random();
        Collection<RegistryObject<Item>> items= ModItems.ITEMS_SCROLL_COMMON.getEntries();

        switch (rarity){
            case UNCOMMON -> items = ModItems.ITEMS_SCROLL_UNCOMMON.getEntries();
            case RARE -> items = ModItems.ITEMS_SCROLL_RARE.getEntries();
            case EPIC -> items = ModItems.ITEMS_SCROLL_EPIC.getEntries();
        }

        if (items.size() == 0)
            return null;

        RegistryObject<Item> itemRegistryObject = (RegistryObject<Item>) items.toArray()[random.nextInt(items.size())];
        return (Scroll) itemRegistryObject.get();
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {

            if (itemStack.hasTag()){
                CompoundTag compoundTag = itemStack.getShareTag();
                if (compoundTag.contains(Scroll.nbt_initialized) && compoundTag.contains(Scroll.nbt_revealed)){
                    boolean isRevealed = compoundTag.getBoolean(Scroll.nbt_revealed);

                    if (isRevealed){
                        if (compoundTag.contains(Scroll.nbt_cooldown)){
                            components.add(Component.literal("Cooldown : "+ (compoundTag.getInt(Scroll.nbt_cooldown)/20f)+"s ").append(Ranking.getComponentRankValue( baseCooldownTick + cooldownModifier, compoundTag.getInt(Scroll.nbt_cooldown),baseCooldownTick-cooldownModifier )));
                        }
                    }else {
                        components.add(Component.literal("Unrevealed").withStyle(ChatFormatting.DARK_RED));
                    }

                }
            }

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}

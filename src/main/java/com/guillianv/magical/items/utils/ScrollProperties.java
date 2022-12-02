package com.guillianv.magical.items.utils;

import com.guillianv.magical.entity.spells.SpellEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ScrollProperties extends Item.Properties {

    protected int baseCooldown = 100;
    protected int cooldownModifier = 0;

    protected EntityType<? extends SpellEntity> entityType;

    protected Rarity defaultRarity = Rarity.COMMON;

    public ScrollProperties scrollType(EntityType<? extends SpellEntity> scrollType){
        this.entityType = scrollType;
        return this;
    }

    public ScrollProperties baseCooldown(int cooldownDurationTick, int cooldownReductionPercent) {
        if (cooldownDurationTick < 0) {
            throw new RuntimeException("Unable to have cooldown under 0.");
        } else {
            this.baseCooldown = cooldownDurationTick;
            this.cooldownModifier = cooldownReductionPercent;
            return this;
        }
    }

    public ScrollProperties defaultRarity(Rarity rarity){
        this.defaultRarity = rarity;
        return this;
    }

    public EntityType<? extends SpellEntity>  getEntityType(){
        return this.entityType;
    }
    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getCooldownModifier(){
        return this.cooldownModifier;
    }

    public Rarity getDefaultRarity(){
        return this.defaultRarity;
    }

}

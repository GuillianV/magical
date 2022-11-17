package com.guillianv.magical.items.utils;

import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.Random;

public class ScrollProperties extends Item.Properties {

    protected int baseCooldown = 100;
    protected int cooldownModifier = 0;

    protected EntityType<? extends SpellEntity> entityType;


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

    public EntityType<? extends SpellEntity>  getEntityType(){
        return this.entityType;
    }
    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getCooldownModifier(){
        return this.cooldownModifier;
    }
}

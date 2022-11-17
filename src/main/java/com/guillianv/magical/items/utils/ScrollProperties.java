package com.guillianv.magical.items.utils;

import net.minecraft.world.item.Item;

import java.util.Random;

public class ScrollProperties extends Item.Properties {

    protected int baseCooldown = 100;
    protected int cooldownModifier = 0;


    public ScrollProperties baseCooldown(int cooldownDurationTick, int cooldownReductionPercent) {
        if (cooldownDurationTick < 0) {
            throw new RuntimeException("Unable to have cooldown under 0.");
        } else {
            this.baseCooldown = cooldownDurationTick;
            this.cooldownModifier = cooldownReductionPercent;
            return this;
        }
    }

    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getCooldownModifier(){
        return this.cooldownModifier;
    }
}

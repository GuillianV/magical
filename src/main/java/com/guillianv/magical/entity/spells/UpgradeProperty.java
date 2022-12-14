package com.guillianv.magical.entity.spells;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

public class UpgradeProperty {

    private TranslatableContents propertyName;
    private ChatFormatting chatFormatting = ChatFormatting.WHITE;
    public int level;

    public double defaultvalue;
    public double value;
    public double upgradeValue;
    public boolean isTime = false;

    public UpgradeProperty(TranslatableContents translatableContents,double defaultValue,double upgradeValue){
        this.propertyName = translatableContents;
        this.level = 1;
        this.defaultvalue = defaultValue;
        this.value = defaultValue;
        this.upgradeValue = upgradeValue;
    }

    public UpgradeProperty isTime(){
        this.isTime = true;
        return this;
    }

    public UpgradeProperty PropertyColor(ChatFormatting chatFormatting){
        this.chatFormatting = chatFormatting;
        return this;
    }


    public Component getPropertyComponent(){
        return Component.literal(this.propertyName.toString() +" "+ value).withStyle(this.chatFormatting);
    }

}

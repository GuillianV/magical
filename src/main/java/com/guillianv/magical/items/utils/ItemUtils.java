package com.guillianv.magical.items.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;



public class ItemUtils {


    public static Component colorValueWithRarity(String textToColor, Rarity rarity){
        ChatFormatting chatFormatting;
        switch (rarity){

            case COMMON -> chatFormatting = ChatFormatting.WHITE;
            case UNCOMMON -> chatFormatting = ChatFormatting.GREEN;
            case RARE -> chatFormatting = ChatFormatting.AQUA;
            case EPIC -> chatFormatting = ChatFormatting.LIGHT_PURPLE;
            default -> chatFormatting = ChatFormatting.WHITE;
        }

        return Component.literal(textToColor).withStyle(chatFormatting) ;
    }


    public static Rarity getRarityWithStrings(String rarity){
        switch (rarity){

            case "UNCOMMON" -> {
                return Rarity.UNCOMMON;
            }
            case "RARE" -> {
                return Rarity.RARE;
            }
            case "EPIC" -> {
                return Rarity.EPIC;
            }
            default -> {
                return Rarity.COMMON;
            }
        }

    }



}

package com.guillianv.magical.items.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class Ranking {



    public static class ZRank{

        public static String textValue = "Z";
        public static ChatFormatting chatFormatting = ChatFormatting.GOLD;

    }


    public static class SRank{

        public static String textValue = "S";
        public static ChatFormatting chatFormatting = ChatFormatting.RED;

    }


    public static class ARank{

        public static String textValue = "A";
        public static ChatFormatting chatFormatting = ChatFormatting.BLUE;

    }


    public static class BRank{

        public static String textValue = "B";
        public static ChatFormatting chatFormatting = ChatFormatting.GREEN;

    }


    public static class CRank{

        public static String textValue = "C";
        public static ChatFormatting chatFormatting = ChatFormatting.WHITE;

    }

    public static Component getComponentRankValue(float minValue, float actualValue, float maxValue){

        ChatFormatting chatFormatting;
        String rankValue;

        float totalValue = maxValue - minValue;
        float spaceValue = totalValue / 3f;

        if (actualValue == minValue){
            return Component.literal("(").append(Component.literal(CRank.textValue)).withStyle(CRank.chatFormatting).append(Component.literal(")"));
        }else  if (actualValue == maxValue){
            return Component.literal("(").append(Component.literal(ZRank.textValue)).withStyle(ZRank.chatFormatting).append(Component.literal(")"));
        }else {

            if(spaceValue < 0){

                if (actualValue > minValue + spaceValue){
                    return Component.literal("(").append(Component.literal(BRank.textValue)).withStyle(BRank.chatFormatting).append(Component.literal(")"));
                } else if(actualValue < maxValue - spaceValue){
                    return Component.literal("(").append(Component.literal(SRank.textValue)).withStyle(SRank.chatFormatting).append(Component.literal(")"));
                }else if (maxValue - spaceValue <= actualValue && minValue + spaceValue >= actualValue ){
                    return Component.literal("(").append(Component.literal(ARank.textValue)).withStyle(ARank.chatFormatting).append(Component.literal(")"));
                }else {
                    return Component.literal("(").append(Component.literal("?")).withStyle(ChatFormatting.DARK_RED).append(Component.literal(")"));
                }

            }else {

                if (minValue + spaceValue > actualValue){
                    return Component.literal("(").append(Component.literal(BRank.textValue)).withStyle(BRank.chatFormatting).append(Component.literal(")"));
                } else if( maxValue - spaceValue < actualValue){
                    return Component.literal("(").append(Component.literal(SRank.textValue)).withStyle(SRank.chatFormatting).append(Component.literal(")"));
                }else if (maxValue - spaceValue <= actualValue && minValue + spaceValue >= actualValue ){
                    return Component.literal("(").append(Component.literal(ARank.textValue)).withStyle(ARank.chatFormatting).append(Component.literal(")"));
                }else {
                    return Component.literal("(").append(Component.literal("?")).withStyle(ChatFormatting.DARK_RED).append(Component.literal(")"));
                }

            }

        }




    }

}

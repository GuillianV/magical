package com.guillianv.magical.loot;


import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.guillianv.magical.Magical.MOD_ID;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);


    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SCROLL_CHESTS_COMMON =
            LOOT_MODIFIER_SERIALIZERS.register("scroll_in_chest_common", ScrollInChestCommon.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SCROLL_CHESTS_UNCOMMON =
            LOOT_MODIFIER_SERIALIZERS.register("scroll_in_chest_uncommon", ScrollInChestUncommon.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SCROLL_CHESTS_RARE =
            LOOT_MODIFIER_SERIALIZERS.register("scroll_in_chest_rare", ScrollInChestRare.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SCROLL_CHESTS_EPIC =
            LOOT_MODIFIER_SERIALIZERS.register("scroll_in_chest_epic", ScrollInChestEpic.CODEC);


    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
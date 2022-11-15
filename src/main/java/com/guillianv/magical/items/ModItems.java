package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.tabs.ModCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }


    public static final RegistryObject<Item> WAND_NORMAL = ITEMS.register("wand_normal",() -> new Wand(new Item.Properties().tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> SCROLL_BOTTLE = ITEMS.register("scroll_bottle",() -> new Scroll(new Item.Properties().tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.COMMON), ModEntityTypes.BOTTLE.get()));
    public static final RegistryObject<Item> SCROLL_FIREBALL = ITEMS.register("scroll_fireball",() -> new Scroll(new Item.Properties().tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.EPIC), ModEntityTypes.FIREBALL.get()));





}

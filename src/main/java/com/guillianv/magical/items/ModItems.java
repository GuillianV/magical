package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.items.utils.ScrollProperties;
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

    public final static DeferredRegister<Item> ITEMS_SCROLL_COMMON = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);
    public final static DeferredRegister<Item> ITEMS_SCROLL_UNCOMMON = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);
    public final static DeferredRegister<Item> ITEMS_SCROLL_RARE = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);
    public final static DeferredRegister<Item> ITEMS_SCROLL_EPIC = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);


    public static void register(IEventBus bus){
        ITEMS.register(bus);
        ITEMS_SCROLL_COMMON.register(bus);
        ITEMS_SCROLL_UNCOMMON.register(bus);
        ITEMS_SCROLL_RARE.register(bus);
        ITEMS_SCROLL_EPIC.register(bus);
    }

    public static final RegistryObject<Item> WAND_NORMAL = ITEMS.register("wand_normal",() -> new Wand(new Item.Properties().tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).durability(100).setNoRepair().defaultDurability(100).rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> WAND_UNCOMMON = ITEMS.register("wand_uncommon",() -> new Wand(new Item.Properties().tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).durability(150).setNoRepair().defaultDurability(150).rarity(Rarity.UNCOMMON),10));


    //Scroll COMMON
    public static final RegistryObject<Item> SCROLL_BOTTLE = ITEMS_SCROLL_COMMON.register("scroll_bottle",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(80,5).scrollType( ModEntityTypes.BOTTLE.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> TORNADO = ITEMS_SCROLL_COMMON.register("scroll_tornado",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(150,50).scrollType( ModEntityTypes.TORNADO.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.COMMON)));


    //Scroll UNCOMMON
    public static final RegistryObject<Item> SCROLL_EARTH_FIST = ITEMS_SCROLL_EPIC.register("scroll_earth_fist",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(150,15).scrollType(ModEntityTypes.EARTH_FIST.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.UNCOMMON)));



    //Scroll RARE
    public static final RegistryObject<Item> SCROLL_THUNDER_STRIKE = ITEMS_SCROLL_COMMON.register("scroll_thunder_strike",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(100,5).scrollType(ModEntityTypes.THUNDER_STRIKE.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CELESTIAL_BLESSING = ITEMS_SCROLL_COMMON.register("scroll_celestial_blessing",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(600,85).scrollType(ModEntityTypes.CELESTIAL_BLESSING.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.RARE)));


    //Scroll EPIC
    public static final RegistryObject<Item> SCROLL_FIREBALL = ITEMS_SCROLL_EPIC.register("scroll_fireball",() -> new Scroll((ScrollProperties) new ScrollProperties().baseCooldown(60,5).scrollType(ModEntityTypes.FIREBALL.get()).tab(ModCreativeTabs.MAGICAL_TAB).stacksTo(1).rarity(Rarity.EPIC)));





}

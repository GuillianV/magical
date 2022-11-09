package com.guillianv.magical.items;

import com.guillianv.magical.Magical;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Magical.MOD_ID);

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }

    public static final RegistryObject<Item> MYITEM = ITEMS.register("myitem",() -> new Cassette(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

}

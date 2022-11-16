package com.guillianv.magical.items;

import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

import java.util.Collection;
import java.util.Random;

public class Scroll extends Item {

    public EntityType<? extends SpellEntity> entityType;

    public Scroll(Properties properties, EntityType<? extends SpellEntity> _entityType ) {
        super(properties);
        entityType = _entityType;

    }


    @Nullable
    public static Scroll getRandomScroll(Rarity rarity){

        Random random = new  Random();
        Collection<RegistryObject<Item>> items= ModItems.ITEMS_SCROLL_COMMON.getEntries();

        switch (rarity){
            case UNCOMMON -> items = ModItems.ITEMS_SCROLL_UNCOMMON.getEntries();
            case RARE -> items = ModItems.ITEMS_SCROLL_RARE.getEntries();
            case EPIC -> items = ModItems.ITEMS_SCROLL_EPIC.getEntries();
        }

        if (items.size() == 0)
            return null;

        RegistryObject<Item> itemRegistryObject = (RegistryObject<Item>) items.toArray()[random.nextInt(items.size())];
        return (Scroll) itemRegistryObject.get();
    }


}

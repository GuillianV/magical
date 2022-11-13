package com.guillianv.magical.items;

import com.guillianv.magical.entity.animation.SpellEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class Scroll extends Item {

    public EntityType<? extends SpellEntity> entityType;


    public Scroll(Properties properties, EntityType<? extends SpellEntity> _entityType ) {
        super(properties);
        entityType = _entityType;
    }


}

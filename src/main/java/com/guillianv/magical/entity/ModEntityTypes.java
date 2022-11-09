package com.guillianv.magical.entity;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes  {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Magical.MOD_ID);

    public static final RegistryObject<EntityType<BottleEntity>> BOTTLE =
            ENTITY_TYPES.register("bottle",
                    () -> EntityType.Builder.of(BottleEntity::new, MobCategory.MISC).fireImmune().sized(0.5f,0.5f)
                            .build(new ResourceLocation(Magical.MOD_ID, "bottle").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

package com.guillianv.magical.entity;

import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.earth_fist.EarthFistEntity;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import com.guillianv.magical.entity.animation.thunder_strike.ThunderStrikeEntity;
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


    public static final RegistryObject<EntityType<FireballEntity>> FIREBALL =
            ENTITY_TYPES.register("fireball",
                    () -> EntityType.Builder.of(FireballEntity::new, MobCategory.MISC).fireImmune().sized(0.5f,0.5f)
                            .build(new ResourceLocation(Magical.MOD_ID, "fireball").toString()));

    public static final RegistryObject<EntityType<ThunderStrikeEntity>> THUNDER_STRIKE =
            ENTITY_TYPES.register("thunder_strike",
                    () -> EntityType.Builder.of(ThunderStrikeEntity::new, MobCategory.MISC).fireImmune().sized(1.5f,1.5f)
                            .build(new ResourceLocation(Magical.MOD_ID, "thunder_strike").toString()));


    public static final RegistryObject<EntityType<EarthFistEntity>> EARTH_FIST =
            ENTITY_TYPES.register("earth_fist",
                    () -> EntityType.Builder.of(EarthFistEntity::new, MobCategory.MISC).fireImmune().sized(1.5f,1.5f)
                            .build(new ResourceLocation(Magical.MOD_ID, "earth_fist").toString()));




    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

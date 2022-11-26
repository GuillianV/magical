package com.guillianv.magical.blocks.entity;

import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Magical.MOD_ID);

    public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPE.register("altar", ()-> BlockEntityType.Builder.of(AltarBlockEntity::new, ModBlocks.ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<RecognizerBlockEntity>> RECOGNIZER = BLOCK_ENTITY_TYPE.register("recognizer", ()-> BlockEntityType.Builder.of(RecognizerBlockEntity::new, ModBlocks.RECOGNIZER.get()).build(null));


    public static void register(IEventBus bus){
        BLOCK_ENTITY_TYPE.register(bus);
    }
}

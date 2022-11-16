package com.guillianv.magical.loot;


import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.guillianv.magical.items.ModItems;
import com.guillianv.magical.items.Scroll;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ScrollInChestCommon extends LootModifier {
    public static final Supplier<Codec<ScrollInChestCommon>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, ScrollInChestCommon::new)));
    private final Item item;

    protected ScrollInChestCommon(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        Scroll scroll = Scroll.getRandomScroll(Rarity.COMMON);
        if (scroll == null)
            scroll = (Scroll) ModItems.SCROLL_BOTTLE.get();
        this.item = scroll;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if(context.getRandom().nextFloat() >= 0.8f) {
            generatedLoot.add(new ItemStack(item, 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
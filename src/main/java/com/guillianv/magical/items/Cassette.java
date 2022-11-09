package com.guillianv.magical.items;

import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.ModEntityTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Cassette extends Item {


    float scaleValue = 1;


    public Cassette(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {


        Level level = useOnContext.getLevel();
        BottleEntity bottleEntity = new BottleEntity(ModEntityTypes.BOTTLE.get(),level);
        bottleEntity.setPos( useOnContext.getClickLocation());
        useOnContext.getLevel().addFreshEntity(bottleEntity);



        if (useOnContext.getClickedFace() == Direction.UP){
           BlockState blockState = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos());
           Block block = blockState.getBlock();

        }


        return super.useOn(useOnContext);
    }
}

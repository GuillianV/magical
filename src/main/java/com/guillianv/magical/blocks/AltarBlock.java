package com.guillianv.magical.blocks;

import com.guillianv.magical.blocks.entity.ModBlockEntities;
import com.guillianv.magical.blocks.entity.AltarBlockEntity;
import com.guillianv.magical.items.Wand;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);


    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    public AltarBlock(Properties properties) {
        super(properties);
    }


    /* Block entity */

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return ModBlockEntities.ALTAR.get().create(pos,blockState);
    }


    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateNew, boolean pIsMoving) {
        if (blockState.getBlock() != blockStateNew.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof AltarBlockEntity){
                ((AltarBlockEntity)blockEntity).drops();
            }

        }
        super.onRemove(blockState, level, blockPos, blockStateNew, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof AltarBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (AltarBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type,  ModBlockEntities.ALTAR.get(), AltarBlockEntity::tick);
    }
}

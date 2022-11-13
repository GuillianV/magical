package com.guillianv.magical.items;

import com.guillianv.magical.entity.ModEntityTypes;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Wand extends Item {


    static float scaleValue = 1;


    public Wand(Properties properties) {
        super(properties);
    }

    

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

      /*  FireballEntity fireballEntity = new FireballEntity(ModEntityTypes.FIREBALL.get(),level);
        fireballEntity.setPos( new Vec3(player.position().x,player.position().y + player.getEyeHeight(),player.position().z));
        fireballEntity.setLookAngle(player.getLookAngle());
        if (!level.isClientSide()){
           level.addFreshEntity(fireballEntity);
        } */

        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        BottleEntity bottleEntity = new BottleEntity(ModEntityTypes.BOTTLE.get(),useOnContext.getLevel());
        bottleEntity.setPos( new Vec3(useOnContext.getClickedPos().getX(),useOnContext.getClickedPos().getY() ,useOnContext.getClickedPos().getZ()));
        if (!useOnContext.getLevel().isClientSide()){
            useOnContext.getLevel().addFreshEntity(bottleEntity);
        }


        return super.useOn(useOnContext);
    }

}

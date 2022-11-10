package com.guillianv.magical.entity.animation.fireball.render;


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import com.guillianv.magical.entity.animation.fireball.model.FireballModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FireballRenderer extends GeoEntityRenderer<FireballEntity> {

    public static ResourceLocation resourceLocation =  new ResourceLocation(Magical.MOD_ID, "textures/entity/fireball_texture.png");

    @Override
    protected int getBlockLightLevel(FireballEntity fireballEntity, BlockPos blockPos) {
        return super.getBlockLightLevel(fireballEntity,blockPos);
    }

    @Override
    protected int getSkyLightLevel(FireballEntity fireballEntity, BlockPos blockPos) {
        return super.getSkyLightLevel(fireballEntity,blockPos);
    }



    public FireballRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireballModel());
        this.shadowRadius = 0.15f;

    }



    @Override
    public ResourceLocation getTextureLocation(FireballEntity instance) {
        return resourceLocation;
    }

    @Override
    public RenderType getRenderType(FireballEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

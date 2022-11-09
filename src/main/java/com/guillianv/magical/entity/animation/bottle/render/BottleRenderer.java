package com.guillianv.magical.entity.animation.bottle.render;


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import com.guillianv.magical.entity.animation.bottle.model.BottleModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BottleRenderer extends GeoEntityRenderer<BottleEntity> {

    public BottleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BottleModel());
        this.shadowRadius = 0.15f;
    }



    @Override
    public ResourceLocation getTextureLocation(BottleEntity instance) {
        return new ResourceLocation(Magical.MOD_ID, "textures/entity/bottle_texture.png");
    }

    @Override
    public RenderType getRenderType(BottleEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

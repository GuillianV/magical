package com.guillianv.magical.entity.animation.earth_fist.render;


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.earth_fist.EarthFistEntity;
import com.guillianv.magical.entity.animation.earth_fist.model.EarthFistModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class EarthFistRenderer extends GeoEntityRenderer<EarthFistEntity> {

    public EarthFistRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EarthFistModel());
        this.shadowRadius = 1.15f;
        this.shadowStrength = 0.5f;
    }



    @Override
    public ResourceLocation getTextureLocation(EarthFistEntity instance) {
        return EarthFistModel.texture;
    }

    @Override
    public RenderType getRenderType(EarthFistEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(2.5f,2.5f,2.5f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

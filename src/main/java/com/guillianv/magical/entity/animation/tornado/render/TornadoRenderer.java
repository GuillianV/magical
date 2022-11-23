package com.guillianv.magical.entity.animation.tornado.render;


import com.guillianv.magical.entity.animation.tornado.TornadoEntity;
import com.guillianv.magical.entity.animation.tornado.model.TornadoModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class TornadoRenderer extends GeoEntityRenderer<TornadoEntity> {

    public TornadoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TornadoModel());
        this.shadowRadius = 1.15f;
        this.shadowStrength = 0.5f;
    }



    @Override
    public ResourceLocation getTextureLocation(TornadoEntity instance) {
        return TornadoModel.texture;
    }

    @Override
    public RenderType getRenderType(TornadoEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

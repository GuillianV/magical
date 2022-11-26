package com.guillianv.magical.blocks.render;


import com.guillianv.magical.blocks.entity.RecognizerBlockEntity;
import com.guillianv.magical.blocks.model.RecognizerBlockModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class RecognizerBlockRenderer extends GeoBlockRenderer<RecognizerBlockEntity> {

    public RecognizerBlockRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn ) {
        super(rendererDispatcherIn, new RecognizerBlockModel());

    }

    @Override
    public RenderType getRenderType(RecognizerBlockEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                    int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}

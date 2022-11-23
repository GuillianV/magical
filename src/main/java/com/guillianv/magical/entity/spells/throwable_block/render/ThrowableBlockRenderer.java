package com.guillianv.magical.entity.spells.throwable_block.render;


import com.guillianv.magical.entity.spells.throwable_block.ThrowableBlockEntity;
import com.guillianv.magical.entity.spells.throwable_block.model.ThrowableBlockModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ThrowableBlockRenderer extends GeoEntityRenderer<ThrowableBlockEntity> {

    public ThrowableBlockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThrowableBlockModel());
        this.shadowRadius = 1f;
        this.shadowStrength = 1f;
    }



    @Override
    public ResourceLocation getTextureLocation(ThrowableBlockEntity instance) {


        return  new ResourceLocation(instance.getTextureId()); //  ThrowableBlockModel.texture;
    }

    @Override
    public RenderType getRenderType(ThrowableBlockEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

package com.guillianv.magical.entity.spells.fire_sword.render;


import com.guillianv.magical.entity.spells.fire_sword.FireSwordEntity;
import com.guillianv.magical.entity.spells.fire_sword.model.FireSwordModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FireSwordRenderer extends GeoEntityRenderer<FireSwordEntity> {

    public FireSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireSwordModel());
        this.shadowRadius = 1.15f;
        this.shadowStrength = 0.5f;
    }



    @Override
    public ResourceLocation getTextureLocation(FireSwordEntity instance) {
        return FireSwordModel.texture;
    }

    @Override
    public RenderType getRenderType(FireSwordEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

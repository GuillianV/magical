package com.guillianv.magical.entity.spells.celestial_blessing.render;


import com.guillianv.magical.entity.spells.celestial_blessing.CelestialBlessingEntity;
import com.guillianv.magical.entity.spells.celestial_blessing.model.CelestialBlessingModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CelestialBlessingRenderer extends GeoEntityRenderer<CelestialBlessingEntity> {

    public CelestialBlessingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CelestialBlessingModel());
        this.shadowRadius = 0f;
        this.shadowStrength = 0f;
    }



    @Override
    public ResourceLocation getTextureLocation(CelestialBlessingEntity instance) {
        return CelestialBlessingModel.texture;
    }

    @Override
    public RenderType getRenderType(CelestialBlessingEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

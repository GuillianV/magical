package com.guillianv.magical.entity.spells.fire_rain.render;


import com.guillianv.magical.entity.spells.fire_rain.FireRainEntity;
import com.guillianv.magical.entity.spells.fire_rain.model.FireRainModel;
import com.guillianv.magical.entity.spells.fire_sword.model.FireSwordModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FireRainRenderer extends GeoEntityRenderer<FireRainEntity> {

    public FireRainRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireRainModel());
        this.shadowRadius = 1.15f;
        this.shadowStrength = 0.5f;
    }



    @Override
    public ResourceLocation getTextureLocation(FireRainEntity instance) {
        return FireRainModel.texture;
    }

    @Override
    public RenderType getRenderType(FireRainEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(animatable.getScale(), animatable.getScale(), animatable.getScale());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

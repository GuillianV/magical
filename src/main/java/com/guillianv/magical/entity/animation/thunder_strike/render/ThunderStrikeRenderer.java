package com.guillianv.magical.entity.animation.thunder_strike.render;


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.thunder_strike.ThunderStrikeEntity;
import com.guillianv.magical.entity.animation.thunder_strike.model.ThunderStrikeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ThunderStrikeRenderer extends GeoEntityRenderer<ThunderStrikeEntity> {

    public ThunderStrikeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThunderStrikeModel());
        this.shadowRadius = 1.15f;
        this.shadowStrength = 0.5f;
    }



    @Override
    public ResourceLocation getTextureLocation(ThunderStrikeEntity instance) {
        return new ResourceLocation(Magical.MOD_ID, "textures/entity/thunder_strike_texture.png");
    }

    @Override
    public RenderType getRenderType(ThunderStrikeEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(2.5f,2.5f,2.5f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

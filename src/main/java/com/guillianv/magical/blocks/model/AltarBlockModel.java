package com.guillianv.magical.blocks.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.entity.AltarBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AltarBlockModel extends AnimatedGeoModel<AltarBlockEntity> {
	

	@Override
	public ResourceLocation getModelResource(AltarBlockEntity object) {
		return new ResourceLocation(Magical.MOD_ID, "geo/altar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AltarBlockEntity object) {
		return new ResourceLocation(Magical.MOD_ID, "textures/block/altar_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(AltarBlockEntity animatable) {
		return new ResourceLocation(Magical.MOD_ID, "animations/altar.animation.json");
	}
}


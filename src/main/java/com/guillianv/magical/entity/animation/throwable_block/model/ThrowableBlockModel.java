package com.guillianv.magical.entity.animation.throwable_block.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.throwable_block.ThrowableBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ThrowableBlockModel extends AnimatedGeoModel<ThrowableBlockEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/throwable_block.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/throwable_block_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/throwable_block.animation.json");

	public static String animationName =  "animation.throwable_block.play";

	@Override
	public ResourceLocation getModelResource(ThrowableBlockEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(ThrowableBlockEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(ThrowableBlockEntity animatable) {
		return geoAnimation;
	}

}


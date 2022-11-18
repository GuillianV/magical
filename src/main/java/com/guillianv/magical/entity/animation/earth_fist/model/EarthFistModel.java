package com.guillianv.magical.entity.animation.earth_fist.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.earth_fist.EarthFistEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EarthFistModel extends AnimatedGeoModel<EarthFistEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/earth_fist.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/earth_fist_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/earth_fist.animation.json");

	public static String animationName =  "animation.earth_fist.play";

	@Override
	public ResourceLocation getModelResource(EarthFistEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(EarthFistEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(EarthFistEntity animatable) {
		return geoAnimation;
	}

}


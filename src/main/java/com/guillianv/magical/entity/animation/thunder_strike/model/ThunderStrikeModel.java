package com.guillianv.magical.entity.animation.thunder_strike.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.thunder_strike.ThunderStrikeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ThunderStrikeModel extends AnimatedGeoModel<ThunderStrikeEntity> {


	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/thunder_strike.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/thunder_strike_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/thunder_strike.animation.json");

	public static String animationName =  "animation.thunder_strike.play";


	@Override
	public ResourceLocation getModelResource(ThunderStrikeEntity object) {
		return ThunderStrikeModel.geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(ThunderStrikeEntity object) {
		return ThunderStrikeModel.texture;
	}

	@Override
	public ResourceLocation getAnimationResource(ThunderStrikeEntity animatable) {
		return ThunderStrikeModel.geoAnimation;
	}

}


package com.guillianv.magical.entity.spells.fire_rain.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.spells.fire_rain.FireRainEntity;
import com.guillianv.magical.entity.spells.fire_sword.FireSwordEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireRainModel extends AnimatedGeoModel<FireRainEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/fire_sword.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/fire_sword_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/fire_sword.animation.json");

	public static String animationName =  "animation.fire_sword.summon";

	@Override
	public ResourceLocation getModelResource(FireRainEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(FireRainEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(FireRainEntity animatable) {
		return geoAnimation;
	}

}


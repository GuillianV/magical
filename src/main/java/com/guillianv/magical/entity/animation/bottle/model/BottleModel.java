package com.guillianv.magical.entity.animation.bottle.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.bottle.BottleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BottleModel extends AnimatedGeoModel<BottleEntity> {


	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/bottle.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/bottle_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/bottle.animation.json");

	public static String animationName =  "animation.bottle.play";


	@Override
	public ResourceLocation getModelResource(BottleEntity object) {
		return BottleModel.geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(BottleEntity object) {
		return BottleModel.texture;
	}

	@Override
	public ResourceLocation getAnimationResource(BottleEntity animatable) {
		return BottleModel.geoAnimation;
	}

}


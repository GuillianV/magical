package com.guillianv.magical.entity.spells.fire_sword.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.spells.fire_sword.FireSwordEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireSwordModel extends AnimatedGeoModel<FireSwordEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/fire_sword.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/fire_sword_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/fire_sword.animation.json");

	public static String animationName =  "animation.fire_sword.idle";

	@Override
	public ResourceLocation getModelResource(FireSwordEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(FireSwordEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(FireSwordEntity animatable) {
		return geoAnimation;
	}

}


package com.guillianv.magical.entity.spells.celestial_blessing.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.spells.celestial_blessing.CelestialBlessingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CelestialBlessingModel extends AnimatedGeoModel<CelestialBlessingEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/celestial_blessing.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/celestial_blessing_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/celestial_blessing.animation.json");

	public static String animationName =  "animation.celestial_blessing.play";

	@Override
	public ResourceLocation getModelResource(CelestialBlessingEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(CelestialBlessingEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(CelestialBlessingEntity animatable) {
		return geoAnimation;
	}

}


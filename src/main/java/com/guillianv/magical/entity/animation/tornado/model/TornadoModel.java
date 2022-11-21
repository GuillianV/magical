package com.guillianv.magical.entity.animation.tornado.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.tornado.TornadoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TornadoModel extends AnimatedGeoModel<TornadoEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/tornado.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/tornado_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/tornado.animation.json");

	public static String animationName =  "animation.tornado.idle";

	@Override
	public ResourceLocation getModelResource(TornadoEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(TornadoEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(TornadoEntity animatable) {
		return geoAnimation;
	}

}


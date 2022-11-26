package com.guillianv.magical.blocks.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.blocks.entity.AltarBlockEntity;
import com.guillianv.magical.blocks.entity.RecognizerBlockEntity;
import com.guillianv.magical.blocks.render.RecognizerBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RecognizerBlockModel extends AnimatedGeoModel<RecognizerBlockEntity> {

	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/recognizer_block.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/block/recognizer_block_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/recognizer_block.animation.json");

	public static String animationName =  "animation.recognizer_block.idle";

	@Override
	public ResourceLocation getModelResource(RecognizerBlockEntity object) {
		return geoModel;
	}

	@Override
	public ResourceLocation getTextureResource(RecognizerBlockEntity object) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(RecognizerBlockEntity animatable) {
		return geoAnimation;
	}
}


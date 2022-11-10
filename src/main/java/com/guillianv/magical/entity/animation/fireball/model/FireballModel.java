package com.guillianv.magical.entity.animation.fireball.model;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireballModel extends AnimatedGeoModel<FireballEntity> {
	
	@Override
	public ResourceLocation getModelResource(FireballEntity object) {
		return new ResourceLocation(Magical.MOD_ID, "geo/fireball.geo.json");
	}



	@Override
	public ResourceLocation getTextureResource(FireballEntity object) {
		return new ResourceLocation(Magical.MOD_ID, "textures/entity/fireball_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(FireballEntity animatable) {
		return new ResourceLocation(Magical.MOD_ID, "animations/fireball.animation.json");
	}

}


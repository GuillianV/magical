package com.guillianv.magical.entity.animation.fireball.model;
import com.guillianv.magical.Magical;
import com.guillianv.magical.entity.animation.fireball.FireballEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireballModel extends AnimatedGeoModel<FireballEntity> {



	public static ResourceLocation geoModel = new ResourceLocation(Magical.MOD_ID, "geo/fireball.geo.json");

	public static ResourceLocation texture = new ResourceLocation(Magical.MOD_ID, "textures/entity/fireball_texture.png");

	public static ResourceLocation geoAnimation = new ResourceLocation(Magical.MOD_ID, "animations/fireball.animation.json");

	public static String animationName =  "animation.fireball.idle";


	@Override
	public ResourceLocation getModelResource(FireballEntity object) {
		return FireballModel.geoModel;
	}



	@Override
	public ResourceLocation getTextureResource(FireballEntity object) {
		return FireballModel.texture;
	}

	@Override
	public ResourceLocation getAnimationResource(FireballEntity animatable) {
		return FireballModel.geoAnimation;
	}

}


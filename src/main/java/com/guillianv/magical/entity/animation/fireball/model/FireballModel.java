package com.guillianv.magical.entity.animation.fireball.model;
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


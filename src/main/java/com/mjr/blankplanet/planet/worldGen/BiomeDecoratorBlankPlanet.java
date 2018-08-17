package com.mjr.blankplanet.planet.worldGen;

import com.mjr.mjrlegendslib.world.BiomeDecoratorBase;

import net.minecraft.world.World;

public class BiomeDecoratorBlankPlanet extends BiomeDecoratorBase {

	private World currentWorld;

	public BiomeDecoratorBlankPlanet() {

	}

	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}

	@Override
	protected void decorate() {

	}

}

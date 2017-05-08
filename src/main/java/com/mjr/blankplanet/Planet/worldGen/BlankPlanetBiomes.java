package com.mjr.blankplanet.Planet.worldGen;

import net.minecraft.world.biome.Biome;

public class BlankPlanetBiomes extends Biome {

	public static final Biome blankplanet = new BiomeGenBlankPlanet(new BiomeProperties("blankplanet").setRainfall(0.0F).setRainDisabled());

	BlankPlanetBiomes(BiomeProperties properties) {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCreatureList.clear();
	}

	@Override
	public float getSpawningChance() {
		return 0.01F;
	}
}

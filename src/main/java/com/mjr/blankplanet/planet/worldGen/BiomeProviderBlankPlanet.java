package com.mjr.blankplanet.planet.worldGen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeProviderSpace;
import net.minecraft.world.biome.Biome;

public class BiomeProviderBlankPlanet extends BiomeProviderSpace {

	@Override
	public Biome getBiome() {
		return BlankPlanetBiomes.blankplanet;
	}

}
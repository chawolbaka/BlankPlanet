package com.mjr.blankplanet.Planet.worldGen;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBlankPlanet extends BlankPlanetBiomes {

	public BiomeGenBlankPlanet(BiomeProperties properties) {
		super(properties);
		BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.DRY);
	}

}

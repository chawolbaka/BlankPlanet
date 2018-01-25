package com.mjr.blankplanet.planet.worldGen;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBlankPlanet extends BlankPlanetBiomes {

	public BiomeGenBlankPlanet(BiomeProperties properties) {
		super(properties);
	}

	public void registerTypes() {
		BiomeDictionary.addTypes(this, BiomeDictionary.Type.VOID);
	}
}

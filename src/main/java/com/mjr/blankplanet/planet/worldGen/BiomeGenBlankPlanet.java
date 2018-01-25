package com.mjr.blankplanet.planet.worldGen;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import com.mjr.blankplanet.BlankPlanet;
import com.mjr.blankplanet.Constants;

public class BiomeGenBlankPlanet extends BlankPlanetBiomes {

	public BiomeGenBlankPlanet(BiomeProperties properties) {
		super(properties);
		Biome.registerBiome(BlankPlanet.biomeid, Constants.TEXTURE_PREFIX + this.getBiomeName(), this);
	}

	public void registerTypes() {
		BiomeDictionary.addTypes(this, BiomeDictionary.Type.VOID);
	}
}

package com.mjr.blankplanet.Planet.worldGen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerBlankPlanet extends WorldChunkManagerSpace {

	@Override
	public BiomeGenBase getBiome() {
		return BlankPlanetBiomes.blankplanet;
	}

}

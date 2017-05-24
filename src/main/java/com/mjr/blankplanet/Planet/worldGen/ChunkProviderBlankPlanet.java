package com.mjr.blankplanet.planet.worldGen;

import java.util.List;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import com.google.common.collect.Lists;

public class ChunkProviderBlankPlanet extends ChunkProviderCustom {

	private final BiomeDecoratorBlankPlanet BiomeDecorator = new BiomeDecoratorBlankPlanet();

	public ChunkProviderBlankPlanet(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled);
	}

	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return this.BiomeDecorator;
	}

	@Override
	protected Biome[] getBiomesForGeneration() {
		return new Biome[] { BlankPlanetBiomes.blankplanet };
	}

	@Override
	protected int getSeaLevel() {
		return 64;
	}

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators() {
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		return generators;
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(Blocks.AIR, (byte) 1);
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(Blocks.AIR, (byte) 1);
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(Blocks.AIR, (byte) 1);
	}

	@Override
	public double getHeightModifier() {
		return 12;
	}

	@Override
	public double getSmallFeatureHeightModifier() {
		return 26;
	}

	@Override
	public double getMountainHeightModifier() {
		return 95;
	}

	@Override
	public double getValleyHeightModifier() {
		return 50;
	}

	@Override
	public int getCraterProbability() {
		return 2000;
	}

	@Override
	public void onChunkProvide(int arg0, int arg1, ChunkPrimer arg2) {

	}

	@Override
	public void onPopulate(int cX, int cZ) {

	}
}

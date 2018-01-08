package com.mjr.blankplanet.planet.worldGen;

import java.util.List;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderOverworld;

import com.mjr.mjrlegendslib.world.BiomeDecoratorBase;

/**
 * Do not include this prefab class in your released mod download.
 */
public abstract class ChunkProviderNoBedRock extends ChunkProviderOverworld {
	protected final Random rand;

	private final Gradient noiseGen1;
	private final Gradient noiseGen2;
	private final Gradient noiseGen3;
	private final Gradient noiseGen4;
	private final Gradient noiseGen5;
	private final Gradient noiseGen6;
	private final Gradient noiseGen7;

	protected final World worldObj;

	private Biome[] biomesForGeneration = this.getBiomesForGeneration();

	private final double TERRAIN_HEIGHT_MOD = this.getHeightModifier();
	private final double SMALL_FEATURE_HEIGHT_MOD = this.getSmallFeatureHeightModifier();
	private final double MOUNTAIN_HEIGHT_MOD = this.getMountainHeightModifier();
	private final double VALLEY_HEIGHT_MOD = this.getValleyHeightModifier();

	// DO NOT CHANGE
	private final int MID_HEIGHT = this.getSeaLevel();
	private static final int CHUNK_SIZE_X = 16;
	private static final int CHUNK_SIZE_Y = 256;
	private static final int CHUNK_SIZE_Z = 16;
	private static final double MAIN_FEATURE_FILTER_MOD = 4;
	private static final double LARGE_FEATURE_FILTER_MOD = 8;
	private static final double SMALL_FEATURE_FILTER_MOD = 8;

	private List<MapGenBaseMeta> worldGenerators;

	public ChunkProviderNoBedRock(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled, "");
		this.worldObj = par1World;
		this.rand = new Random(seed);

		this.noiseGen1 = new Gradient(this.rand.nextLong(), 4, 0.25F);
		this.noiseGen2 = new Gradient(this.rand.nextLong(), 4, 0.25F);
		this.noiseGen3 = new Gradient(this.rand.nextLong(), 4, 0.25F);
		this.noiseGen4 = new Gradient(this.rand.nextLong(), 2, 0.25F);
		this.noiseGen5 = new Gradient(this.rand.nextLong(), 1, 0.25F);
		this.noiseGen6 = new Gradient(this.rand.nextLong(), 1, 0.25F);
		this.noiseGen7 = new Gradient(this.rand.nextLong(), 1, 0.25F);
	}

	public void generateTerrain(int chunkX, int chunkZ, ChunkPrimer primer) {
		this.noiseGen1.setFrequency(0.015F);
		this.noiseGen2.setFrequency(0.01F);
		this.noiseGen3.setFrequency(0.01F);
		this.noiseGen4.setFrequency(0.01F);
		this.noiseGen5.setFrequency(0.01F);
		this.noiseGen6.setFrequency(0.001F);
		this.noiseGen7.setFrequency(0.005F);

		for (int x = 0; x < ChunkProviderNoBedRock.CHUNK_SIZE_X; x++) {
			for (int z = 0; z < ChunkProviderNoBedRock.CHUNK_SIZE_Z; z++) {
				final double baseHeight = this.noiseGen1.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.TERRAIN_HEIGHT_MOD;
				final double smallHillHeight = this.noiseGen2.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.SMALL_FEATURE_HEIGHT_MOD;
				double mountainHeight = Math.abs(this.noiseGen3.getNoise(chunkX * 16 + x, chunkZ * 16 + z));
				double valleyHeight = Math.abs(this.noiseGen4.getNoise(chunkX * 16 + x, chunkZ * 16 + z));
				final double featureFilter = this.noiseGen5.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * ChunkProviderNoBedRock.MAIN_FEATURE_FILTER_MOD;
				final double largeFilter = this.noiseGen6.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * ChunkProviderNoBedRock.LARGE_FEATURE_FILTER_MOD;
				final double smallFilter = this.noiseGen7.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * ChunkProviderNoBedRock.SMALL_FEATURE_FILTER_MOD - 0.5;
				mountainHeight = this.lerp(smallHillHeight, mountainHeight * this.MOUNTAIN_HEIGHT_MOD, this.fade(this.clamp(mountainHeight * 2, 0, 1)));
				valleyHeight = this.lerp(smallHillHeight, valleyHeight * this.VALLEY_HEIGHT_MOD - this.VALLEY_HEIGHT_MOD + 9, this.fade(this.clamp((valleyHeight + 2) * 4, 0, 1)));

				double yDev = this.lerp(valleyHeight, mountainHeight, this.fade(largeFilter));
				yDev = this.lerp(smallHillHeight, yDev, smallFilter);
				yDev = this.lerp(baseHeight, yDev, featureFilter);

				for (int y = 0; y < ChunkProviderNoBedRock.CHUNK_SIZE_Y; y++) {
					if (y < this.MID_HEIGHT + yDev) {
						primer.setBlockState(x, y, z, this.getStoneBlock().getBlock().getStateFromMeta(this.getStoneBlock().getMetadata()));
						// idArray[this.getIndex(x, y, z)] = this.getStoneBlock().getBlock();
						// metaArray[this.getIndex(x, y, z)] = this.getStoneBlock().getMetadata();
					}
				}
			}
		}
	}

	private double lerp(double d1, double d2, double t) {
		if (t < 0.0) {
			return d1;
		} else if (t > 1.0) {
			return d2;
		} else {
			return d1 + (d2 - d1) * t;
		}
	}

	private double fade(double n) {
		return n * n * n * (n * (n * 6 - 15) + 10);
	}

	private double clamp(double x, double min, double max) {
		if (x < min) {
			return min;
		}
		if (x > max) {
			return max;
		}
		return x;
	}

	@Override
	// public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
		final int var5 = 20;
		final float var6 = 0.03125F;
		this.noiseGen4.setFrequency(var6 * 2);
		for (int var8 = 0; var8 < 16; ++var8) {
			for (int var9 = 0; var9 < 16; ++var9) {
				final int var12 = (int) (this.noiseGen4.getNoise(x * 16 + var8, z * 16 + var9) / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int var13 = -1;
				Block var14 = this.getGrassBlock().getBlock();
				byte var14m = this.getGrassBlock().getMetadata();
				Block var15 = this.getDirtBlock().getBlock();
				byte var15m = this.getDirtBlock().getMetadata();

				for (int var16 = ChunkProviderNoBedRock.CHUNK_SIZE_Y - 1; var16 >= 0; --var16) {
					final int index = this.getIndex(var8, var16, var9);

					if (var16 <= 0 + this.rand.nextInt(5)) {
						primer.setBlockState(var8, var16, var9, Blocks.AIR.getDefaultState());
						// arrayOfIDs[index] = Blocks.AIR;
					} else {
						// final Block var18 = arrayOfIDs[index];
						Block var18 = primer.getBlockState(var8, var16, var9).getBlock();

						if (Blocks.AIR == var18) {
							var13 = -1;
						} else if (var18 == this.getStoneBlock().getBlock()) {
							// arrayOfMeta[index] = this.getStoneBlock().getMetadata();

							if (var13 == -1) {
								if (var12 <= 0) {
									var14 = Blocks.AIR;
									var14m = 0;
									var15 = this.getStoneBlock().getBlock();
									var15m = this.getStoneBlock().getMetadata();
								} else if (var16 >= var5 - -16 && var16 <= var5 + 1) {
									var14 = this.getGrassBlock().getBlock();
									var14m = this.getGrassBlock().getMetadata();
									var14 = this.getDirtBlock().getBlock();
									var14m = this.getDirtBlock().getMetadata();
								}

								var13 = var12;

								if (var16 >= var5 - 1) {
									// arrayOfIDs[index] = var14;
									// arrayOfMeta[index] = var14m;
									primer.setBlockState(var8, var16, var9, var14.getStateFromMeta(var14m));
								} else {
									// arrayOfIDs[index] = var15;
									// arrayOfMeta[index] = var15m;
									primer.setBlockState(var8, var16, var9, var15.getStateFromMeta(var15m));
								}
							} else if (var13 > 0) {
								--var13;
								// arrayOfIDs[index] = var15;
								// arrayOfMeta[index] = var15m;
								primer.setBlockState(var8, var16, var9, var15.getStateFromMeta(var15m));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
		// final Block[] ids = new Block[32768 * 2];
		// final byte[] meta = new byte[32768 * 2];
		this.generateTerrain(x, z, primer);
		this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.replaceBiomeBlocks(x, z, primer, this.biomesForGeneration);

		if (this.worldGenerators == null) {
			this.worldGenerators = this.getWorldGenerators();
		}

		for (MapGenBaseMeta generator : this.worldGenerators) {
			generator.generate(this.worldObj, x, z, primer);
		}

		this.onChunkProvide(x, z, primer);

		final Chunk var4 = new Chunk(this.worldObj, primer, x, z);
		final byte[] var5 = var4.getBiomeArray();

		for (int var6 = 0; var6 < var5.length; ++var6) {
			var5[var6] = (byte) Biome.getIdForBiome(this.biomesForGeneration[var6]);
		}

		var4.generateSkylightMap();
		return var4;
	}

	private int getIndex(int x, int y, int z) {
		return (x * 16 + z) * 256 + y;
	}

	public void decoratePlanet(World par1World, Random par2Random, int par3, int par4) {
		this.getBiomeGenerator().decorate(par1World, par2Random, par3, par4);
	}

	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		int var4 = x * 16;
		int var5 = z * 16;
		this.worldObj.getBiome(new BlockPos(var4 + 16, 0, var5 + 16));
		this.rand.setSeed(this.worldObj.getSeed());
		final long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		final long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(x * var7 + z * var9 ^ this.worldObj.getSeed());
		this.decoratePlanet(this.worldObj, this.rand, var4, var5);
		this.onPopulate(x, z);

		BlockFalling.fallInstantly = false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		Biome biomegenbase = this.worldObj.getBiome(pos);
		return biomegenbase.getSpawnableList(creatureType);
	}

	/**
	 * Do not return null
	 *
	 * @return The biome generator for this world, handles ore, flower, etc generation. See GCBiomeDecoratorBase.
	 */
	protected abstract BiomeDecoratorBase getBiomeGenerator();

	/**
	 * Do not return null, have at least one biome for generation
	 *
	 * @return Biome instance for generation
	 */
	protected abstract Biome[] getBiomesForGeneration();

	/**
	 * @return The average terrain level. Default is 64.
	 */
	protected abstract int getSeaLevel();

	/**
	 * List of all world generators to use. Caves, ravines, structures, etc.
	 * <p/>
	 * Return an empty list for no world generators. Do not return null.
	 *
	 * @return
	 */
	protected abstract List<MapGenBaseMeta> getWorldGenerators();

	/**
	 * The grass block to be generated. Doesn't have to be grass of course.
	 *
	 * @return BlockMetaPair instance containing ID and metadata for grass block.
	 */
	protected abstract BlockMetaPair getGrassBlock();

	/**
	 * The dirt block to be generated. Doesn't have to be dirt of course.
	 *
	 * @return BlockMetaPair instance containing ID and metadata for dirt block.
	 */
	protected abstract BlockMetaPair getDirtBlock();

	/**
	 * The stone block to be generated. Doesn't have to be stone of course.
	 *
	 * @return BlockMetaPair instance containing ID and metadata for stone block.
	 */
	protected abstract BlockMetaPair getStoneBlock();

	/**
	 * @return Base height modifier
	 */
	public abstract double getHeightModifier();

	/**
	 * @return Height modifier for small hills
	 */
	public abstract double getSmallFeatureHeightModifier();

	/**
	 * @return Height modifier for mountains
	 */
	public abstract double getMountainHeightModifier();

	/**
	 * @return Height modifier for valleys
	 */
	public abstract double getValleyHeightModifier();

	public abstract void onChunkProvide(int cX, int cZ, ChunkPrimer primer);

	public abstract void onPopulate(int cX, int cZ);
}

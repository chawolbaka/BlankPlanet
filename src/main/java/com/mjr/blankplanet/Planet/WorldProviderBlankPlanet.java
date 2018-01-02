package com.mjr.blankplanet.Planet;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import com.mjr.blankplanet.BlankPlanet;
import com.mjr.blankplanet.Planet.worldGen.ChunkProviderBlankPlanet;
import com.mjr.blankplanet.Planet.worldGen.WorldChunkManagerBlankPlanet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderBlankPlanet extends WorldProviderSpace implements IGalacticraftWorldProvider, ISolarLevel {
	@Override
	public Vector3 getFogColor() {
		return new Vector3(0, 0, 0);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0, 0, 0);
	}

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public long getDayLength() {
		return BlankPlanet.daylength;
	}

	@Override
	public boolean shouldForceRespawn() {
		return true;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return ChunkProviderBlankPlanet.class;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return WorldChunkManagerBlankPlanet.class;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		return BlankPlanet.star;
	}

	@Override
	public int getAverageGroundLevel() {
		return 70;
	}

	@Override
	public boolean canCoordinateBeSpawn(int var1, int var2) {
		return true;
	}

	@Override
	public float getGravity() {
		return BlankPlanet.gravity;
	}

	@Override
	public int getHeight() {
		return 800;
	}

	@Override
	public double getMeteorFrequency() {
		return 0.0D;
	}

	@Override
	public double getFuelUsageMultiplier() {
		return BlankPlanet.fuel;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= BlankPlanet.rocketTier;
	}

	@Override
	public float getFallDamageModifier() {
		return BlankPlanet.fallDamage;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return BlankPlanet.soundvol;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return BlankPlanet.blankPlanet;
	}

	@Override
	public boolean hasBreathableAtmosphere() {
		return BlankPlanet.breathable;
	}

	@Override
	public float getThermalLevelModifier() {
		return BlankPlanet.thermal;
	}

	@Override
	public float getWindLevel() {
		return BlankPlanet.windLevel;
	}

	@Override
	public double getSolarEnergyMultiplier() {
		return BlankPlanet.solar;
	}

	@Override
	public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_) {
		if (BlankPlanet.onlyDay)
			return 1.0F;
		else if (BlankPlanet.onlyNight)
			return 0.0F;
		else
			return super.calculateCelestialAngle(p_76563_1_, p_76563_3_);
	}
}

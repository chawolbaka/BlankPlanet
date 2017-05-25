package com.mjr.blankplanet.Planet;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.mjr.blankplanet.BlankPlanet;

public class TeleportTypeBlankPlanet implements ITeleportType {
	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			GCPlayerStats stats = GCPlayerStats.get(player);
			return new Vector3(stats.getCoordsTeleportedFromX(), 103.0, stats.getCoordsTeleportedFromZ());
		}
		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, 103.0, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		if (BlankPlanet.spawnParachest) {
			final double x = (rand.nextDouble() * 2 - 1.0D) * 5.0D;
			final double z = (rand.nextDouble() * 2 - 1.0D) * 5.0D;

			return new Vector3(player.posX + x, 230.0D, player.posZ + z);
		} else
			return null;
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
		if (BlankPlanet.makelandingplatform) {
			BlockPos playerPos = player.getPosition();
			int X = playerPos.getX();
			int Z = playerPos.getZ();
			if(newWorld.isAirBlock(new BlockPos(X, 100, Z)) && newWorld == DimensionManager.getWorld(BlankPlanet.dimensionid)){
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						newWorld.setBlockState(new BlockPos(X + i, 100, Z + j), Blocks.stone.getDefaultState());
					}
				}
				
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						newWorld.setBlockState(new BlockPos(X - i, 100, Z + j), Blocks.stone.getDefaultState());
					}
				}
				
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						newWorld.setBlockState(new BlockPos(X + i, 100, Z - j), Blocks.stone.getDefaultState());
					}
				}
				
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						newWorld.setBlockState(new BlockPos(X - i, 100, Z - j), Blocks.stone.getDefaultState());
					}
				}
			}
		}
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub
	}
}

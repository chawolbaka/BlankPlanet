package com.mjr.blankplanet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TeleportBlock extends Block {

	protected TeleportBlock(Material material) {
		super(material);
		this.setCreativeTab(BlankPlanet.BlocksTab);
		this.setHardness(3f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		int id = BlankPlanet.dimensionid;
		if (!world.isRemote) {
			if (BlankPlanet.reqiureXp) {
				if (player.experienceLevel >= BlankPlanet.xpAmount) {
					player.addExperienceLevel(-BlankPlanet.xpAmount);
				} else {
					return false;
				}
			}
			if (player.dimension == id) {
				int wX = DimensionManager.getProvider(0).getSpawnPoint().getX();
				int wY = DimensionManager.getProvider(0).getSpawnPoint().getY();
				int wZ = DimensionManager.getProvider(0).getSpawnPoint().getZ();
				tptoworld((EntityPlayerMP) player, 0, wX, wY, wZ);
			} else {
				tptoworld((EntityPlayerMP) player, id, BlankPlanet.spawnX, BlankPlanet.spawnY + 3, BlankPlanet.spawnZ);
				if (BlankPlanet.makelandingplatform) {
					if (DimensionManager.getWorld(id).isAirBlock(new BlockPos(BlankPlanet.spawnX, BlankPlanet.spawnY, BlankPlanet.spawnZ))) {
						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX + i, BlankPlanet.spawnY, BlankPlanet.spawnZ + j), Blocks.stone.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX - i, BlankPlanet.spawnY, BlankPlanet.spawnZ + j), Blocks.stone.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX + i, BlankPlanet.spawnY, BlankPlanet.spawnZ - j), Blocks.stone.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX - i, BlankPlanet.spawnY, BlankPlanet.spawnZ - j), Blocks.stone.getDefaultState());
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	private void tptoworld(EntityPlayerMP pl, int id, double xpos, double ypos, double zpos) {
		MinecraftServer mcServer = MinecraftServer.getServer();
		ServerConfigurationManager cm = pl.mcServer.getConfigurationManager();

		int oldid = pl.dimension;
		WorldServer worldserver = mcServer.worldServerForDimension(pl.dimension);
		pl.dimension = id;
		WorldServer worldserver1 = mcServer.worldServerForDimension(pl.dimension);

		pl.playerNetServerHandler.sendPacket(new S07PacketRespawn(pl.dimension, pl.worldObj.getDifficulty(), pl.worldObj.getWorldInfo().getTerrainType(), pl.theItemInWorldManager.getGameType()));
		worldserver.removePlayerEntityDangerously(pl);
		pl.isDead = false;

		pl.setLocationAndAngles(xpos, ypos, zpos, pl.rotationYaw, pl.rotationPitch);
		worldserver1.spawnEntityInWorld(pl);
		worldserver1.updateEntityWithOptionalForce(pl, false);
		pl.setWorld(worldserver1);

		WorldServer worldserverx = pl.getServerForPlayer();

		if (worldserver != null) {
			worldserver.getPlayerManager().removePlayer(pl);
		}
		worldserverx.getPlayerManager().addPlayer(pl);
		worldserverx.theChunkProviderServer.loadChunk((int) pl.posX >> 4, (int) pl.posZ >> 4);

		pl.playerNetServerHandler.setPlayerLocation(pl.posX, pl.posY, pl.posZ, pl.rotationYaw, pl.rotationPitch);
		pl.theItemInWorldManager.setWorld(worldserver1);
		cm.updateTimeAndWeatherForPlayer(pl, worldserver1);
		if (!BlankPlanet.clearinv)
			cm.syncPlayerInventory(pl);

		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(pl, oldid, id);
	}
}

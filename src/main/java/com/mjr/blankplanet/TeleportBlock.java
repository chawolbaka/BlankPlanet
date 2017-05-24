package com.mjr.blankplanet;

import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class TeleportBlock extends Block {

	protected TeleportBlock(Material material) {
		super(material);
		this.setCreativeTab(BlankPlanet.BlocksTab);
		this.setHardness(3f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX + i, BlankPlanet.spawnY, BlankPlanet.spawnZ + j), Blocks.STONE.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX - i, BlankPlanet.spawnY, BlankPlanet.spawnZ + j), Blocks.STONE.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX + i, BlankPlanet.spawnY, BlankPlanet.spawnZ - j), Blocks.STONE.getDefaultState());
							}
						}

						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								DimensionManager.getWorld(id).setBlockState(new BlockPos(BlankPlanet.spawnX - i, BlankPlanet.spawnY, BlankPlanet.spawnZ - j), Blocks.STONE.getDefaultState());
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
		final WorldServer world = (WorldServer) pl.world;
		WorldUtil.transferEntityToDimension(pl, id, world);
	}
}

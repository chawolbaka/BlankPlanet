package com.mjr.blankplanet.Planet.handlers;

import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

import com.mjr.blankplanet.BlankPlanet;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class ServerHandler {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayer) {
			if (BlankPlanet.teleportOnJoin) {

			} else if (BlankPlanet.teleportOnJoinEvery) {
				tptoworld((EntityPlayerMP) event.player, BlankPlanet.spawnWorld);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.player instanceof EntityPlayer) {
			if (BlankPlanet.teleportOnDeath) {
				tptoworld((EntityPlayerMP) event.player, BlankPlanet.spawnOnDealth);
			}
		}
	}

	private void tptoworld(EntityPlayerMP pl, int id) {
		final WorldServer world = (WorldServer) pl.worldObj;
		WorldUtil.transferEntityToDimension(pl, id, world);
	}
}

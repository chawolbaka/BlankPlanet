package com.mjr.blankplanet.handlers;

import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import com.mjr.blankplanet.BlankPlanet;
import com.mjr.blankplanet.handlers.capabilities.CapabilityProviderStats;
import com.mjr.blankplanet.handlers.capabilities.CapabilityStatsHandler;
import com.mjr.blankplanet.handlers.capabilities.IStatsCapability;

public class ServerHandler {
	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent.Entity event) {
		if (event.getObject() instanceof EntityPlayerMP) {
			event.addCapability(CapabilityStatsHandler.BP_PLAYER_PROP, new CapabilityProviderStats((EntityPlayerMP) event.getObject()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayer) {
			if (BlankPlanet.teleportOnJoin) {
				IStatsCapability stats = event.player.getCapability(CapabilityStatsHandler.BP_STATS_CAPABILITY, null);
				if (stats.getFirstSpawn() == false) {
					tptoworld((EntityPlayerMP) event.player, BlankPlanet.spawnWorld);
					stats.setFirstSpawn(true);
				}
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

package com.mjr.blankplanet.planet;

import com.mjr.blankplanet.BlankPlanet;

import micdoodle8.mods.galacticraft.api.event.oxygen.GCCoreOxygenSuffocationEvent;
import micdoodle8.mods.galacticraft.core.event.EventWakePlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlankPlanetEvents {
	@SubscribeEvent
	public void GCCoreOxygenSuffocationEvent(GCCoreOxygenSuffocationEvent.Pre event) {
		if (event.getEntityLiving().worldObj.provider.getDimension() == BlankPlanet.dimensionid) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void GCCoreEventWakePlayer(EventWakePlayer event) {
		if (event.getEntityLiving().worldObj.provider.getDimension() == BlankPlanet.dimensionid) {
			event.getEntityPlayer().heal(5.0F);
		}
	}
}

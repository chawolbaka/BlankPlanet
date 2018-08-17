package com.mjr.blankplanet.client.handler;

import com.mjr.blankplanet.planet.WorldProviderBlankPlanet;
import com.mjr.mjrlegendslib.util.MCUtilities;

import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		final Minecraft minecraft = MCUtilities.getClient();
		final WorldClient world = minecraft.theWorld;

		if (world != null) {
			// Planets
			if (world.provider instanceof WorldProviderBlankPlanet) {
				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRenderer());
				}
			}
		}
	}
}
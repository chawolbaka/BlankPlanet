package com.mjr.blankplanet;

import com.mjr.blankplanet.client.handler.ClientHandler;
import com.mjr.mjrlegendslib.util.RegisterUtilities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RegisterUtilities.registerEventHandler(new ClientHandler());
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public static void registerHandlers() {

	}
}

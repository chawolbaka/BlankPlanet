package com.mjr.blankplanet;

import java.io.File;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.mjr.blankplanet.Planet.BlankPlanetEvents;
import com.mjr.blankplanet.Planet.TeleportTypeBlankPlanet;
import com.mjr.blankplanet.Planet.WorldProviderBlankPlanet;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Constants.modID, name = Constants.modName, version = Constants.modVersion, dependencies = "required-after:GalacticraftCore;")
public class BlankPlanet {

	@SidedProxy(clientSide = "com.mjr.blankplanet.ClientProxy", serverSide = "com.mjr.blankplanet.CommonProxy")
	public static CommonProxy proxy;

	@Instance(Constants.modID)
	public static BlankPlanet instance;

	public static CreativeTabs BlocksTab = new CreativeTabs("BlankPlanetTab") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(GCBlocks.solarPanel);
		}
	};

	public static Planet blankPlanet;

	public static Block teleport = new TeleportBlock(Material.rock).setBlockName("teleport").setBlockTextureName(Constants.TEXTURE_PREFIX + "teleport");

	public static int dimensionid;

	public static int biomeid;
	public static String biomename;

	public static int rocketTier;
	public static int spawnX;
	public static int spawnY;
	public static int spawnZ;
	public static int xpAmount;

	public static boolean clearinv;
	public static boolean makelandingplatform;
	public static boolean reqiureXp;
	public static boolean breathable;

	public static float gravity;
	public static float fallDamage;
	public static float windLevel;
	public static float thermal;
	public static float soundvol;
	public static float star;

	public static double solar;
	public static double fuel;

	public static long daylength;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File("config/BlankPlanet.cfg"));
		config.load();

		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnX", "Teleporter SpawnX");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnY", "Teleporter SpawnY");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnY", "Teleporter SpawnY");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "Clear inventory when teleport", "Clear inventory when using teleporter");

		dimensionid = config.get(Configuration.CATEGORY_GENERAL, "Dimension id", "-99").getInt();
		biomeid = config.get(Configuration.CATEGORY_GENERAL, "Biome id", "199").getInt();
		biomename = config.get(Configuration.CATEGORY_GENERAL, "Biome name", "BlankPlanet").getString();
		rocketTier = config.get(Configuration.CATEGORY_GENERAL, "Rocket reqiured", "3").getInt();
		breathable = config.get(Configuration.CATEGORY_GENERAL, "Breathable Atmosphere", false).getBoolean(false);
		gravity = (float) config.get(Configuration.CATEGORY_GENERAL, "Gravity", "0.058").getDouble();
		daylength = config.get(Configuration.CATEGORY_GENERAL, "Day Length", "24000").getInt();
		solar = config.get(Configuration.CATEGORY_GENERAL, "Solar Energy Multiplier", "8.0").getDouble();
		fuel = config.get(Configuration.CATEGORY_GENERAL, "Fuel Usage Multiplier", "1.0").getDouble();
		fallDamage = (float) config.get(Configuration.CATEGORY_GENERAL, "Fall Damage Multiplier", "0.3").getDouble();
		windLevel = (float) config.get(Configuration.CATEGORY_GENERAL, "Wind Level", "0.0").getDouble();
		thermal = (float) config.get(Configuration.CATEGORY_GENERAL, "Thermal Level Multiplier", "0.0").getDouble();
		soundvol = (float) config.get(Configuration.CATEGORY_GENERAL, "Sound Vol Reduction", "10.0").getDouble();
		star = (float) config.get(Configuration.CATEGORY_GENERAL, "Star Brightness", "1.0").getDouble();
		spawnX = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnX", "0").getInt();
		spawnY = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnY", "100").getInt();
		spawnZ = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnZ", "0").getInt();
		clearinv = config.get(Configuration.CATEGORY_GENERAL, "Clear inventory when using teleporter", false).getBoolean(false);
		makelandingplatform = config.get(Configuration.CATEGORY_GENERAL, "Make Landing Platforms", true).getBoolean(true);
		reqiureXp = config.get(Configuration.CATEGORY_GENERAL, "Teleporter reqiures Xp", false).getBoolean(false);
		xpAmount = config.get(Configuration.CATEGORY_GENERAL, "Teleporter Xp amount", "1").getInt();
		config.save();
		MinecraftForge.EVENT_BUS.register(new BlankPlanetEvents());
		BlankPlanet.proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BlankPlanet.blankPlanet = new Planet("BlackHole").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		BlankPlanet.blankPlanet.setTierRequired(rocketTier);
		BlankPlanet.blankPlanet.setRingColorRGB(0.1F, 0.9F, 0.6F);
		BlankPlanet.blankPlanet.setPhaseShift(0.8F);
		BlankPlanet.blankPlanet.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.50F, 2.50F));
		BlankPlanet.blankPlanet.setRelativeOrbitTime(166.84118291347207009857612267251F);
		BlankPlanet.blankPlanet.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/blankplanet.png"));
		BlankPlanet.blankPlanet.setDimensionInfo(dimensionid, WorldProviderBlankPlanet.class);

		GalaxyRegistry.registerPlanet(blankPlanet);

		GalacticraftRegistry.registerTeleportType(WorldProviderBlankPlanet.class, new TeleportTypeBlankPlanet());

		GalacticraftRegistry.registerRocketGui(WorldProviderBlankPlanet.class, new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/RocketGui.png"));

		GameRegistry.registerBlock(teleport, "teleport");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		BlankPlanet.proxy.postInit(event);
	}
}

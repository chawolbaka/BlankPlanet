package com.mjr.blankplanet;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.google.common.collect.ObjectArrays;
import com.mjr.blankplanet.handlers.ServerHandler;
import com.mjr.blankplanet.handlers.capabilities.CapabilityStatsHandler;
import com.mjr.blankplanet.planet.BlankPlanetEvents;
import com.mjr.blankplanet.planet.TeleportTypeBlankPlanet;
import com.mjr.blankplanet.planet.WorldProviderBlankPlanet;
import com.mjr.blankplanet.planet.worldGen.BlankPlanetBiomes;
import com.mjr.blankplanet.util.ClientUtilities;
import com.mjr.mjrlegendslib.itemBlock.ItemBlockDefault;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.RegisterUtilities;
import com.mjr.mjrlegendslib.world.biomes.BiomeGenBase;

@Mod(modid = Constants.modID, name = Constants.modName, version = Constants.modVersion, dependencies = "required-after:mjrlegendslib@[1.12.2-1.0.5,);required-after:galacticraftcore;required-after:galacticraftplanets;required-after:forge@(1.12.2-14.23.1.2555,);")
public class BlankPlanet {

	@SidedProxy(clientSide = "com.mjr.blankplanet.ClientProxy", serverSide = "com.mjr.blankplanet.CommonProxy")
	public static CommonProxy proxy;

	@Instance(Constants.modID)
	public static BlankPlanet instance;

	public static CreativeTabs BlocksTab = new CreativeTabs("BlankPlanetTab") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(GCBlocks.solarPanel));
		}
	};

	// Block/Item/Biome Events Registering Lists
	public static List<Item> itemList = new ArrayList<>();
	public static List<Block> blocksList = new ArrayList<>();
	public static List<BiomeGenBase> biomesList = new ArrayList<>();

	public static Planet blankPlanet;

	public static Block teleport = new TeleportBlock(Material.ROCK).setUnlocalizedName("teleport");

	public static int dimensionid;
	public static int biomeid;
	public static String biomename;
	public static boolean makelandingplatform;

	public static int spawnX;
	public static int spawnY;
	public static int spawnZ;
	public static int xpAmount;
	public static boolean reqiureXp;
	public static boolean clearinv;

	public static int rocketTier;
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
	public static boolean onlyDay;
	public static boolean onlyNight;

	public static boolean teleportOnDeath;
	public static int spawnOnDealth;
	public static boolean teleportOnJoin;
	public static boolean teleportOnJoinEvery;
	public static int spawnWorld;
	public static boolean spawnParachest;

	public static final String CATEGORY_SPAWN = "advanced spawn options";
	public static final String CATEGORY_DIMENSION = "dimension options";
	public static final String CATEGORY_WORLD = "world options";

	public static DimensionType blackHole = DimensionType.register("blankPlanet", "BlankPlanet", dimensionid, WorldProviderBlankPlanet.class, false);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws NoSuchMethodException {
		Configuration config = new Configuration(new File("config/BlankPlanet.cfg"));
		config.load();

		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnX", "Teleporter SpawnX");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnY", "Teleporter SpawnY");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "SpawnY", "Teleporter SpawnY");
		config.renameProperty(Configuration.CATEGORY_GENERAL, "Clear inventory when teleport", "Clear inventory when using teleporter");

		dimensionid = config.get(CATEGORY_DIMENSION, "Dimension id", "-99").getInt();
		biomeid = config.get(CATEGORY_DIMENSION, "Biome id", "199").getInt();
		biomename = config.get(CATEGORY_DIMENSION, "Biome name", "BlankPlanet").getString();
		makelandingplatform = config.get(CATEGORY_DIMENSION, "Make Landing Platforms", true).getBoolean(true);

		rocketTier = config.get(CATEGORY_WORLD, "Rocket reqiured", "3").getInt();
		breathable = config.get(CATEGORY_WORLD, "Breathable Atmosphere", false).getBoolean(false);
		gravity = (float) config.get(CATEGORY_WORLD, "Gravity", "0.058").getDouble();
		daylength = config.get(CATEGORY_WORLD, "Day Length", "24000").getInt();
		solar = config.get(CATEGORY_WORLD, "Solar Energy Multiplier", "8.0").getDouble();
		fuel = config.get(CATEGORY_WORLD, "Fuel Usage Multiplier", "1.0").getDouble();
		fallDamage = (float) config.get(CATEGORY_WORLD, "Fall Damage Multiplier", "0.3").getDouble();
		windLevel = (float) config.get(CATEGORY_WORLD, "Wind Level", "0.0").getDouble();
		thermal = (float) config.get(CATEGORY_WORLD, "Thermal Level Multiplier", "0.0").getDouble();
		soundvol = (float) config.get(CATEGORY_WORLD, "Sound Vol Reduction", "10.0").getDouble();
		star = (float) config.get(CATEGORY_WORLD, "Star Brightness", "1.0").getDouble();
		onlyDay = config.get(CATEGORY_WORLD, "Always Day Time", false).getBoolean(false);
		onlyNight = config.get(CATEGORY_WORLD, "Always Night Time", false, "WILL GET IGNORED IF 'Always Daytime' IS ENABLED").getBoolean(false);

		spawnX = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnX", "0").getInt();
		spawnY = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnY", "100").getInt();
		spawnZ = config.get(Configuration.CATEGORY_GENERAL, "Teleporter SpawnZ", "0").getInt();
		// clearinv = config.get(Configuration.CATEGORY_GENERAL, "Clear inventory when using teleporter", false).getBoolean(false);
		reqiureXp = config.get(Configuration.CATEGORY_GENERAL, "Teleporter reqiures Xp", false).getBoolean(false);
		xpAmount = config.get(Configuration.CATEGORY_GENERAL, "Teleporter Xp amount", "1").getInt();
		spawnParachest = config.get(Configuration.CATEGORY_GENERAL, "Spawn Parachest on Teleport", false).getBoolean(false);

		teleportOnDeath = config.get(CATEGORY_SPAWN, "Teleport Player on Death (skip bed spawns)", false).getBoolean(false);
		spawnOnDealth = config.get(CATEGORY_SPAWN, "Number ID of Dimesnion for 'Teleport Player on Death' option", "" + dimensionid).getInt();

		teleportOnJoin = config.get(CATEGORY_SPAWN, "Teleport Player only when they first join the world", false).getBoolean(false);
		teleportOnJoinEvery = config.get(CATEGORY_SPAWN, "Teleport Player on everytime they join world", false).getBoolean(false);
		spawnWorld = config.get(CATEGORY_SPAWN, "Number ID of Dimesnion for 'First/Everytime join world options'", "" + dimensionid).getInt();

		config.save();
		RegisterUtilities.registerEventHandler(new BlankPlanetEvents());
		RegisterUtilities.registerEventHandler(new ServerHandler());
		BlankPlanet.proxy.preInit(event);

		registerBlock(teleport, ItemBlockDefault.class, teleport.getUnlocalizedName().substring(5));
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (MCUtilities.isClient()) {
			ClientUtilities.registerBlockJson(Constants.TEXTURE_PREFIX, BlankPlanet.teleport, 0, BlankPlanet.teleport.getUnlocalizedName().substring(5));
		}
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

		GalacticraftRegistry.registerRocketGui(WorldProviderBlankPlanet.class, new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/rocket_gui.png"));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CapabilityStatsHandler.register();

		BlankPlanet.proxy.postInit(event);
	}

	public static void registerBiomes() {
		BlankPlanetBiomes.blankplanet.setRegistryName(Constants.TEXTURE_PREFIX + "blank_planet");
		BlankPlanet.biomesList.add(BlankPlanetBiomes.blankplanet);
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object... itemCtorArgs) throws NoSuchMethodException {
		if (block.getRegistryName() == null) {
			block.setRegistryName(name);
		}
		BlankPlanet.blocksList.add(block);
		if (itemclass != null) {
			ItemBlock item = null;
			Class<?>[] ctorArgClasses = new Class<?>[itemCtorArgs.length + 1];
			ctorArgClasses[0] = Block.class;
			for (int idx = 1; idx < ctorArgClasses.length; idx++) {
				ctorArgClasses[idx] = itemCtorArgs[idx - 1].getClass();
			}

			try {
				Constructor<? extends ItemBlock> constructor = itemclass.getConstructor(ctorArgClasses);
				item = constructor.newInstance(ObjectArrays.concat(block, itemCtorArgs));
			} catch (Exception e) {
				e.printStackTrace();
			}
			BlankPlanet.itemList.add(item);
			if (item.getRegistryName() == null) {
				item.setRegistryName(name);
			}
		}
	}

	public static void registerItem(Item item, String name) {
		if (item.getRegistryName() == null) {
			item.setRegistryName(name);
		}
		BlankPlanet.itemList.add(item);
	}

	@Mod.EventBusSubscriber(modid = Constants.modID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerBlocksEvent(RegistryEvent.Register<Block> event) {
			for (Block block : BlankPlanet.blocksList) {
				event.getRegistry().register(block);
			}
		}

		@SubscribeEvent
		public static void registerItemsEvent(RegistryEvent.Register<Item> event) {
			for (Item item : BlankPlanet.itemList) {
				event.getRegistry().register(item);
			}
		}

		@SubscribeEvent
		public static void registerBiomesEvent(RegistryEvent.Register<Biome> event) {
			// Register Biomes
			BlankPlanet.registerBiomes();

			for (BiomeGenBase biome : BlankPlanet.biomesList) {
				event.getRegistry().register(biome);
				if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
					biome.registerTypes();
				}
			}
		}
	}
}

package com.mjr.blankplanet.Planet.worldGen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import com.mjr.blankplanet.BlankPlanet;

public class BiomeDecoratorBlankPlanet extends BiomeDecoratorSpace {
	
    private World currentWorld;

    public BiomeDecoratorBlankPlanet()
    {

    }

	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}

	@Override
	protected void decorate() {
		
	}

}

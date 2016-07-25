package com.mjr.blankplanet.Planet.worldGen;

import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.world.biome.BiomeGenBase;

import com.mjr.blankplanet.BlankPlanet;

public class BlankPlanetBiomes extends BiomeGenBase {
	
	public static final BiomeGenBase blankplanet = new BiomeGenBlankPlanet(BlankPlanet.biomeid).setBiomeName(BlankPlanet.biomename);
	
	@SuppressWarnings("unchecked")
	BlankPlanetBiomes(int var1)
    {
        super(var1);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedZombie.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedSpider.class, 10, 4, 4));
        this.rainfall = 0F;
    }

    @Override
    public BlankPlanetBiomes setColor(int var1)
    {
        return (BlankPlanetBiomes) super.setColor(var1);
    }

    @Override
    public float getSpawningChance()
    {
        return 0.01F;
    }
}

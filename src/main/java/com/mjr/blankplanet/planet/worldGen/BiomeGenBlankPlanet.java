package com.mjr.blankplanet.planet.worldGen;

import com.mjr.blankplanet.BlankPlanet;

public class BiomeGenBlankPlanet extends BlankPlanetBiomes {

	public BiomeGenBlankPlanet(int par1) {
		super(par1);
		this.setBiomeName(BlankPlanet.biomename);
		this.setColor(16711680);
		this.setHeight(new Height(2.5F, 0.4F));
	}

}

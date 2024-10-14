package ru.bclib.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import ru.bclib.BCLib;
import ru.bclib.config.ConfigKeeper.StringArrayEntry;

import java.util.Collections;

public class Configs {
	// Client and Server-Config must be the first entries. They are not part of the Auto-Sync process
	// But will be needed by other Auto-Sync Config-Files
	@Environment(EnvType.CLIENT)

	//public static final ClientConfig CLIENT_CONFIG = new ClientConfig();

	public static final ClientConfig CLIENT_CONFIG = testMethod();

	public static final ServerConfig SERVER_CONFIG = new ServerConfig();
	
	public static final GeneratorConfig GENERATOR_CONFIG = new GeneratorConfig();
	public static final MainConfig MAIN_CONFIG = new MainConfig();
	
	public static final PathConfig RECIPE_CONFIG = new PathConfig(BCLib.MOD_ID, "recipes");
	public static final PathConfig BIOMES_CONFIG = new PathConfig(BCLib.MOD_ID, "biomes", false);
	
	public static final String MAIN_PATCH_CATEGORY = "patches";

	public static ClientConfig testMethod() {
		System.out.println("I am being called");
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			System.out.println("IA1");
			return new ClientConfig();
		}
		else {
			System.out.println("IA2");
			return null;
		}
	}
	
	public static void save() {
		MAIN_CONFIG.saveChanges();
		RECIPE_CONFIG.saveChanges();
		GENERATOR_CONFIG.saveChanges();
		BIOMES_CONFIG.saveChanges();
	}
	
	static {
		BIOMES_CONFIG.keeper.registerEntry(new ConfigKey("end_land_biomes", "force_include"), new StringArrayEntry(Collections.EMPTY_LIST));
		BIOMES_CONFIG.keeper.registerEntry(new ConfigKey("end_void_biomes", "force_include"), new StringArrayEntry(Collections.EMPTY_LIST));
		BIOMES_CONFIG.keeper.registerEntry(new ConfigKey("nether_biomes", "force_include"), new StringArrayEntry(Collections.EMPTY_LIST));
	}
}

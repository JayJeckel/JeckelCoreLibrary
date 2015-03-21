package jeckelcorelibrary.core.configs;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
	public ConfigHandler(final String modId, final String modName, ConfigHandlerValues values)
	{
		this.ModId = modId;
		this.ModName = modName;
		this.Values = values;
	}

	public final String ModId;
	public final String ModName;
	public final ConfigHandlerValues Values;

	public Configuration getConfig() { return this._config; }
	private Configuration _config;

	public void initialize(FMLPreInitializationEvent event)
	{
		this._config = new Configuration(new File(event.getModConfigurationDirectory(), this.ModName + ".cfg"));
		this.syncConfig();
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equals(this.ModId)) { this.syncConfig(); }
	}

	public void syncConfig()
	{
		this.Values.update(this.getConfig());

		if(this._config.hasChanged()) { this._config.save(); }
	}
}

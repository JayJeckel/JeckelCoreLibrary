package jeckelcorelibrary.experimental;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * @deprecated Experimental! Don't use this class.
 */
@Deprecated
public class ConfigManager
{
	public void initialize(final String modId, final String modName, final FMLPreInitializationEvent event)
	{
		this._modId = modId;
		this._modName = modName;
		this._config = new Configuration(new File(event.getModConfigurationDirectory(), modName + ".cfg")/*event.getSuggestedConfigurationFile()*/);
		this.syncConfig();
		FMLCommonHandler.instance().bus().register(this);
	}

	public String getModId() { return this._modId; }
	private String _modId;

	public String getModName() { return this._modName; }
	private String _modName;

	public Configuration getConfig() { return this._config; }
	private Configuration _config;

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equals(this._modId)) { this.syncConfig(); }
	}

	public void syncConfig()
	{
		if(this._config.hasChanged()) { this._config.save(); }
	}
}

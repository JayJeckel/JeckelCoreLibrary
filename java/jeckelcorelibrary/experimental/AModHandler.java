package jeckelcorelibrary.experimental;

import cpw.mods.fml.common.ModMetadata;

/**
 * @deprecated Experimental! Don't use this class.
 */
@Deprecated
public abstract class AModHandler
{
	public AModHandler()
	{
	}

	public Object getMod() { return this; }

	public ModMetadata getMeta() { return this._metadata; }
	private ModMetadata _metadata = null;

	/*public String getId() { return MODID; }
	public String getName() { return this._metadata.name; }

	@Override public IProxyHandler getModProxy() { return PROXY; }

	@Override public Configuration getModConfig() { return this._config.getConfig(); }
	private ConfigHandler _config;

	@Override public SimpleNetworkWrapper getModNetwork() { return this._network; }
	private SimpleNetworkWrapper _network;

	@Override public int popNextMessageId() { return _nextMessageId++; }
	private static int _nextMessageId = 0;

	@Mod.EventHandler
	public void preInitialize(FMLPreInitializationEvent event)
	{
		this._metadata = event.getModMetadata();
		this._network = NetworkRegistry.INSTANCE.newSimpleChannel(this.getModId());
		this._config = new ConfigHandler();
		this._config.initialize(this, event);

		ContentInitializer.init(this, null);
	}

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		PROXY.init(this);
	}

	@Mod.EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
	}*/
}

package jeckelcorelibrary;

import jeckelcorelibrary.JeckelCoreLibrary.Refs;
import jeckelcorelibrary.base.configs.AConfigFactory;
import jeckelcorelibrary.base.configs.AConfigScreen;
import jeckelcorelibrary.core.UpdateChecker;
import jeckelcorelibrary.core.commands.InfoModCommand;
import jeckelcorelibrary.core.configs.ConfigHandler;
import jeckelcorelibrary.core.configs.ConfigHandlerValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod
(
modid = Refs.ModId,
useMetadata = true,
canBeDeactivated = false,
guiFactory = Refs.ConfigFactoryTypeName
)
public class JeckelCoreLibrary
{
	@Mod.Instance (value = Refs.ModId)
	public static JeckelCoreLibrary INSTANCE;

	public JeckelCoreLibrary() { }

	@Mod.EventHandler
	public void preInitialize(FMLPreInitializationEvent event)
	{
		GlobalRefs.initialize(event);
		Refs.initialize(event);
	}

	@Mod.EventHandler public void initialize(FMLInitializationEvent event) { }

	@Mod.EventHandler public void postInitialization(FMLPostInitializationEvent event) { }

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new InfoModCommand(Refs.getMetadata(), Refs.getUpdateChecker(), "Display info about the mod."));
	}

	@Mod.EventHandler
	public void imcCallback(FMLInterModComms.IMCEvent event)
	{
		for (final FMLInterModComms.IMCMessage imc : event.getMessages())
		{
			final String senderId = imc.getSender();

			if (imc.key.startsWith("register.tab"))
			{
				GlobalRefs.getTabManager().receiveMessage(imc.key, senderId, imc.getStringValue());
				continue;
			}
		}
	}

	static class Refs
	{
		public static final String ModId = "jeckelcorelibrary";
		public static final String ModName = "JeckelCoreLibrary";

		public static final String ConfigFactoryTypeName = Refs.ModId + ".JeckelCoreLibrary$ConfigFactory";

		public static Logger getLogger() { return _logger; }
		private static Logger _logger;

		public static ModMetadata getMetadata() { return _metadata; }
		private static ModMetadata _metadata;

		public static ConfigHandler getConfigHandler() { return _configHandler; }
		private static ConfigHandler _configHandler;

		public static ConfigValues getConfigValues() { return _configValues; }
		private static ConfigValues _configValues;

		public static UpdateChecker getUpdateChecker() { return _updateChecker; }
		private static UpdateChecker _updateChecker;

		public static void initialize(FMLPreInitializationEvent event)
		{
			_logger = LogManager.getLogger(Refs.ModName);
			_metadata = event.getModMetadata();

			_configValues = new ConfigValues();
			_configHandler = new ConfigHandler(Refs.ModId, Refs.ModName, Refs.getConfigValues());
			_updateChecker = new UpdateChecker(Refs.ModName, Refs.getMetadata().version, Refs.getLogger());

			_configHandler.initialize(event);
			_updateChecker.initialize(event);
		}

		public static boolean isSinglePlayer() { return MinecraftServer.getServer().isSinglePlayer(); }

		public static boolean isMultiPlayer() { return MinecraftServer.getServer().isDedicatedServer(); }
	}


	static class ConfigValues extends ConfigHandlerValues
	{
		private static final long serialVersionUID = -8779638875777450841L;

		public ConfigValues()
		{
			this.add(this._updateChecking);
		}

		public boolean isUpdateCheckingEnabled() { return this._updateChecking.getValue(); }
		protected final ConfigValueBoolean _updateChecking = new ConfigValueBoolean("EnableUpdateChecking", Configuration.CATEGORY_GENERAL,
				"Control automatic update checking.\n.Setting this option to false will disable version checking.",
				true);

		@Override public void update(final Configuration config)
		{
			super.update(config);

			Refs.getUpdateChecker().enable(this.isUpdateCheckingEnabled());
		}
	}

	public static class ConfigScreen extends AConfigScreen
	{
		public ConfigScreen(GuiScreen parent)
		{
			super(parent, Refs.ModId, Refs.ModName, Refs.getConfigHandler().getConfig());
		}

	}

	public static class ConfigFactory extends AConfigFactory
	{
		@Override public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigScreen.class; }
	}
}

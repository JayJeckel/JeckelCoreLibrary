package jeckelcorelibrary.base.configs;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public abstract class AConfigScreen extends GuiConfig
{
	public AConfigScreen(GuiScreen parent, String modId, String modName, Configuration config)
	{
		super(parent, new ConfigElement<Object>(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				modId,
				false, false,
				modName + " " + "Configuration");
	}
}

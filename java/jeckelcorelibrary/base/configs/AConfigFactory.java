package jeckelcorelibrary.base.configs;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public abstract class AConfigFactory implements IModGuiFactory
{
	@Override public void initialize(Minecraft minecraftInstance) { }

	@Override public abstract Class<? extends GuiScreen> mainConfigGuiClass();

	@Override public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { return null; }

	@Override public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { return null; }
}

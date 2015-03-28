package jeckelcorelibrary.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * Static helper class to centralize rendering related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class RenderUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private RenderUtil() { }

	/**
	 * Bind a texture to the render engine.
	 * @param mc Minecraft instance.
	 * @param location Resource to bind.
	 */
	public static void bindTexture(Minecraft mc, ResourceLocation location)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(location);
	}

	/**
	 * Bind the default Minecraft blocks texture to the render engine.
	 * @param mc Minecraft instance.
	 */
	public static void bindBlocksTexture(Minecraft mc)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	}

	/**
	 * Bind the default Minecraft items texture to the render engine.
	 * @param mc Minecraft instance.
	 */
	public static void bindItemsTexture(Minecraft mc)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
	}
}

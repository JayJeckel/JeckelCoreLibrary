package jeckelcorelibrary.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * Static helper class to centralize rendering related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public class RenderUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private RenderUtil() { }

	public static void bindTexture(Minecraft mc, ResourceLocation location)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(location);
	}
}

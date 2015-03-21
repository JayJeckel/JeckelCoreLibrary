package jeckelcorelibrary.utils;

import java.util.Random;

/**
 * Static helper class to centralize various useful methods
 * that do not fit in any other util.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class MathUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private MathUtil() { }

	public static final Random rand = new Random();

	public static int getScaledValue(final int value, final int max, final int scale)
	{
		if (value <= 0 || max <= 0 || scale <= 0) { return 0; }
		return value * scale / max;
	}

	public static int getScaledValue(final int value, final int max, final int scale, final boolean invert)
	{
		if (!invert) { return getScaledValue(value, max, scale); }

		if (value < 0 || max <= 0 || scale <= 0) { return 0; }
		return getScaledValue((value == 0 ? max : max - value), max, scale);
	}

	public static double getRadians(final double degrees)
	{
		return (Math.PI / 180.0) * degrees;
	}

	public static double getDegrees(final double radians)
	{
		return (180.0 / Math.PI) * radians;
	}

	public static double wrapDegrees(final double degrees)
	{
		return (degrees < 0.0 ? degrees + 360.0 : degrees) % 360.0;
	}

	public static int max(final int a, final int b) { return (a >= b ? a : b); }

	public static float max(final float a, final float b) { return (a >= b ? a : b); }

	public static double max(final double a, final double b) { return (a >= b ? a : b); }

	public static int min(final int a, final int b) { return (a <= b ? a : b); }

	public static float min(final float a, final float b) { return (a <= b ? a : b); }

	public static double min(final double a, final double b) { return (a <= b ? a : b); }

	public static int clamp(final int value, final int lower, final int upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	public static float clamp(final float value, final float lower, final float upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	public static double clamp(final double value, final double lower, final double upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}
}

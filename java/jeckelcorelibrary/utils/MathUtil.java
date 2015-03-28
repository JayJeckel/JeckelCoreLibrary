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

	/**
	 * Random object for general use.
	 */
	public static final Random rand = new Random();

	/**
	 * Lerp value between 0 and max using scale.
	 * @param value Value to scale.
	 * @param max Max value.
	 * @param scale Value scale.
	 * @return Scaled value.
	 */
	public static int getScaledValue(final int value, final int max, final int scale)
	{
		if (value <= 0 || max <= 0 || scale <= 0) { return 0; }
		return value * scale / max;
	}

	/**
	 * Lerp value between 0 and max using scale.
	 * @param value Value to scale.
	 * @param max Max value.
	 * @param scale Value scale.
	 * @param invert Invert to lerp between max and 0.
	 * @return Scaled value.
	 */
	public static int getScaledValue(final int value, final int max, final int scale, final boolean invert)
	{
		if (!invert) { return getScaledValue(value, max, scale); }

		if (value < 0 || max <= 0 || scale <= 0) { return 0; }
		return getScaledValue((value == 0 ? max : max - value), max, scale);
	}

	/**
	 * Convert degrees to radians.
	 * @param degrees Degree value to convert.
	 * @return Value in radians.
	 */
	public static double getRadians(final double degrees) { return (Math.PI / 180.0) * degrees; }

	/**
	 * Convert radians to degrees.
	 * @param radians Radian value to convert.
	 * @return Value in degrees.
	 */
	public static double getDegrees(final double radians) { return (180.0 / Math.PI) * radians; }

	/**
	 * Wrap degrees between 0 and 360.
	 * @param degrees Raw degree value.
	 * @return Wrapped degree value.
	 */
	public static double wrapDegrees(final double degrees)
	{
		double output = degrees;
		while (output < 0.0) { output += 360.0; }
		return output % 360.0;
		//return (degrees < 0.0 ? degrees + 360.0 : degrees) % 360.0;
	}

	/**
	 * Return absolute value.
	 * @param value Value to absolute.
	 * @return Absoluted value.
	 */
	public static short abs(final short value) { return (value < 0 ? (short)-value : value); }

	/**
	 * Return absolute value.
	 * @param value Value to absolute.
	 * @return Absoluted value.
	 */
	public static int abs(final int value) { return (value < 0 ? -value : value); }

	/**
	 * Return absolute value.
	 * @param value Value to absolute.
	 * @return Absoluted value.
	 */
	public static long abs(final long value) { return (value < 0 ? -value : value); }

	/**
	 * Return absolute value.
	 * @param value Value to absolute.
	 * @return Absoluted value.
	 */
	public static float abs(final float value) { return (value < 0 ? -value : value); }

	/**
	 * Return absolute value.
	 * @param value Value to absolute.
	 * @return Absoluted value.
	 */
	public static double abs(final double value) { return (value < 0 ? -value : value); }

	/**
	 * Return the greater of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static short max(final short a, final short b) { return (a >= b ? a : b); }

	/**
	 * Return the greater of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static int max(final int a, final int b) { return (a >= b ? a : b); }

	/**
	 * Return the greater of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static long max(final long a, final long b) { return (a >= b ? a : b); }

	/**
	 * Return the greater of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static float max(final float a, final float b) { return (a >= b ? a : b); }

	/**
	 * Return the greater of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static double max(final double a, final double b) { return (a >= b ? a : b); }

	/**
	 * Return the lesser of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static short min(final short a, final short b) { return (a <= b ? a : b); }

	/**
	 * Return the lesser of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static int min(final int a, final int b) { return (a <= b ? a : b); }

	/**
	 * Return the lesser of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static long min(final long a, final long b) { return (a <= b ? a : b); }

	/**
	 * Return the lesser of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static float min(final float a, final float b) { return (a <= b ? a : b); }

	/**
	 * Return the lesser of the two values.
	 * @param a First value.
	 * @param b Second value.
	 * @return Larger value.
	 */
	public static double min(final double a, final double b) { return (a <= b ? a : b); }

	/**
	 * Return the value if it is greater than lower and less than upper, otherwise
	 * return lower or upper, respectively.
	 * @param value Value to clamp.
	 * @param lower Lower bounds of clamp.
	 * @param upper Upper bounds of clamp.
	 * @return Value clamped between lower and upper.
	 */
	public static short clamp(final short value, final short lower, final short upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	/**
	 * Return the value if it is greater than lower and less than upper, otherwise
	 * return lower or upper, respectively.
	 * @param value Value to clamp.
	 * @param lower Lower bounds of clamp.
	 * @param upper Upper bounds of clamp.
	 * @return Value clamped between lower and upper.
	 */
	public static int clamp(final int value, final int lower, final int upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	/**
	 * Return the value if it is greater than lower and less than upper, otherwise
	 * return lower or upper, respectively.
	 * @param value Value to clamp.
	 * @param lower Lower bounds of clamp.
	 * @param upper Upper bounds of clamp.
	 * @return Value clamped between lower and upper.
	 */
	public static long clamp(final long value, final long lower, final long upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	/**
	 * Return the value if it is greater than lower and less than upper, otherwise
	 * return lower or upper, respectively.
	 * @param value Value to clamp.
	 * @param lower Lower bounds of clamp.
	 * @param upper Upper bounds of clamp.
	 * @return Value clamped between lower and upper.
	 */
	public static float clamp(final float value, final float lower, final float upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}

	/**
	 * Return the value if it is greater than lower and less than upper, otherwise
	 * return lower or upper, respectively.
	 * @param value Value to clamp.
	 * @param lower Lower bounds of clamp.
	 * @param upper Upper bounds of clamp.
	 * @return Value clamped between lower and upper.
	 */
	public static double clamp(final double value, final double lower, final double upper)
	{
		if (value < lower) { return lower; }
		else if (value > upper) { return upper; }
		else { return value; }
	}
}

package jeckelcorelibrary.utils;

/**
 * Static helper class to centralize time related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public class TimeUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private TimeUtil() { }

	//final double percent = ((double)amount / (double)cap) * 100.0D;
	//String.format("%.1f", percent) + "%"
	public static final int tick = 1;
	public static final int sec = 20;
	public static final int min = sec * 60;
	public static final int hour = min * 60;
	public static final int day = hour * 24;

	public static final String[] suffixesDescend = new String[] { "d", "h", "m", "s", "t" };
	public static final String[] suffixesAscend = new String[] { "t", "s", "m", "h", "d" };

	public static int calTicks(final int ticks, final int secs, final int mins, final int hours, final int days)
	{
		int count = ticks;
		count += secs * 20;
		count += mins * 60 * 20;
		count += hours * 60 * 60 * 20;
		count += days * 24 * 60 * 60 * 20;
		return count;
	}

	public static String getTimeString(final int ticks, final boolean descending, final boolean full)
	{
		final int[] times = new int[5];

		int count = ticks;

		count -= (times[0] = count / day) * day;
		count -= (times[1] = count / hour) * hour;
		count -= (times[2] = count / min) * min;
		count -= (times[3] = count / sec) * sec;
		count -= (times[4] = count / tick) * tick;

		if (full)
		{
			String format;
			if (descending) { format = String.format("%%d%s %%d%s %%d%s %%d%s %%d%s", (Object[])suffixesDescend); }
			else { format = String.format("%%d%s %%d%s %%d%s %%d%s %%d%s", (Object[])suffixesAscend); }
			if (descending) { return String.format(format, times[0], times[1], times[2], times[3], times[4]); }
			else { return String.format(format, times[4], times[3], times[2], times[1], times[0]); }
		}

		String text = "";
		for (int i = 0; i < times.length; i++)
		{
			final int index = (descending ? i : times.length - i - 1);
			if (times[index] > 0)
			{
				if (text.length() > 0) { text += " "; }
				text += times[index] + suffixesDescend[index];
			}
		}
		return text;
	}
}

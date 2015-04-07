package jeckelcorelibrary.utils;

/**
 * Static helper class to centralize time related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class TimeUtil
{
	/** This is a "static" class and should not be instanced. */
	private TimeUtil() { }

	/** Number of ticks in a tick, ie 1. */
	public static final int tick = 1;

	/** Number of ticks in a second. */
	public static final int sec = tick * 20;

	/** Number of ticks in a minute. */
	public static final int min = sec * 60;

	/** Number of ticks in a hour. */
	public static final int hour = min * 60;

	/** Number of ticks in a day. */
	public static final int day = hour * 24;

	/** Array of time suffixes in descending order. */
	public static final String[] suffixesDescend = new String[] { "d", "h", "m", "s", "t" };

	/** Array of time suffixes in ascending order. */
	public static final String[] suffixesAscend = new String[] { "t", "s", "m", "h", "d" };

	/**
	 * Calculate the number of ticks in given amounts of time.
	 * @param ticks Additional ticks to add to tick total.
	 * @param secs Number of seconds in time amount.
	 * @param mins Number of minutes in time amount.
	 * @param hours Number of hours in time amount.
	 * @param days Number of days in time amount.
	 * @return Total ticks in the given amounts of time.
	 */
	public static int calTicks(final int ticks, final int secs, final int mins, final int hours, final int days)
	{
		int count = ticks;
		count += secs * sec;
		count += mins * min;
		count += hours * hour;
		count += days * day;
		return count;
	}

	/**
	 * Calculate the time values representing the total number of ticks.
	 * @param tickTotal Total number of ticks.
	 * @return TickTime instance representing the time values.
	 */
	public static TickTime calTimes(final int tickTotal)
	{
		final int[] times = new int[5];
		int count = tickTotal;
		count -= (times[0] = count / day) * day;
		count -= (times[1] = count / hour) * hour;
		count -= (times[2] = count / min) * min;
		count -= (times[3] = count / sec) * sec;
		count -= (times[4] = count / tick) * tick;
		return new TickTime(times[4], times[3], times[2], times[1], times[0]);
	}

	/**
	 * Get text displaying time values for a total number of ticks.
	 * @param tickTotal Total number of ticks.
	 * @param descending Order of time values.
	 * @param verbose Include values that are zero.
	 * @return Formatted display text of all time values.
	 */
	public static String getTimeString(final int tickTotal, final boolean descending, final boolean verbose)
	{
		final TickTime time = new TickTime(tickTotal);
		return time.getDisplay(descending, true, verbose);
	}

	/**
	 * Data class to contain tick values related to time.
	 */
	public static class TickTime
	{
		/**
		 * Construct instance based on a total tick value.
		 * The constructor will calculate the individual time values.
		 * @param tickTotal Total ticks.
		 */
		public TickTime(final int tickTotal)
		{
			int count = tickTotal;
			count -= (this.days = count / day) * day;
			count -= (this.hours = count / hour) * hour;
			count -= (this.mins = count / min) * min;
			count -= (this.secs = count / sec) * sec;
			count -= (this.ticks = count / tick) * tick;
		}

		/**
		 * Construct instance based on individual time values.
		 * @param ticks Additional ticks to add to tick total.
		 * @param secs Number of seconds in time amount.
		 * @param mins Number of minutes in time amount.
		 * @param hours Number of hours in time amount.
		 * @param days Number of days in time amount.
		 */
		public TickTime(final int ticks, final int secs, final int mins, final int hours, final int days)
		{
			this.ticks = ticks;
			this.secs = secs;
			this.mins = mins;
			this.hours = hours;
			this.days = days;
		}

		/** Number of sub-second ticks the instance represents. */
		public final int ticks;

		/** Number of seconds the instance represents. */
		public final int secs;

		/** Number of minutes the instance represents. */
		public final int mins;

		/** Number of hours the instance represents. */
		public final int hours;

		/** Number of days the instance represents. */
		public final int days;

		/**
		 * Get the time value at the index using the given order.
		 * @param index Index of time value.
		 * @param descending Order of time values.
		 * @return Specific time value.
		 */
		public int getValue(final int index, final boolean descending)
		{
			switch (index)
			{
				case 0: { return (descending ? this.days : this.ticks); }
				case 1: { return (descending ? this.hours : this.secs); }
				case 2: { return (descending ? this.mins : this.mins); }
				case 3: { return (descending ? this.secs : this.hours); }
				case 4: { return (descending ? this.ticks : this.days); }
				default: { throw new IndexOutOfBoundsException("Index must range between 0 and 4, inclusive."); }
			}
		}

		/**
		 * Get the time suffix at the index using the given order.
		 * @param index Index of time value.
		 * @param descending Order of time values.
		 * @return Specific time suffix.
		 */
		public String getSuffix(final int index, final boolean descending)
		{
			if (index < 0 || index > 4) { throw new IndexOutOfBoundsException("Index must range between 0 and 4, inclusive."); }
			final String[] suffixes = (descending ? suffixesDescend : suffixesAscend);
			return suffixes[index];
		}

		/**
		 * Get the display text of the time value at the index using the given order.
		 * @param index Index of the time value.
		 * @param descending Order of time values.
		 * @param fixedWidth Prefix single digit values with a blank space.
		 * @return Formatted display text of a time value.
		 */
		public String getDisplay(final int index, final boolean descending, final boolean fixedWidth)
		{
			final int value = this.getValue(index, descending);
			final String suffix = this.getSuffix(index, descending);

			return (fixedWidth && value < 10 ? " " : "") + value + suffix;
		}

		/**
		 * Get the complete display text for all time values using the given order..
		 * @param descending Order of time values.
		 * @param fixedWidth Prefix single digit values with a blank space.
		 * @param verbose Include values that are zero.
		 * @return Formatted display text of all time values.
		 */
		public String getDisplay(final boolean descending, final boolean fixedWidth, final boolean verbose)
		{
			String output = "";
			for (int index = 0; index < 5; index++)
			{
				final int value = this.getValue(index, descending);
				if (verbose || value > 0)
				{
					if (output.length() > 0) { output += " "; }
					output += this.getDisplay(index, descending, fixedWidth);
				}
			}
			return output;
		}
	}
}

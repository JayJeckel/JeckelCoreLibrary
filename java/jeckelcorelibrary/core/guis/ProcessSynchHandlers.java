package jeckelcorelibrary.core.guis;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.base.guis.ASynchHandler;

/**
 * Common process related synch handlers.
 */
public class ProcessSynchHandlers
{
	/**
	 * Handler for the process time value.
	 */
	public static class Time extends ASynchHandler
	{
		public Time(final ITickProcess process)
		{
			super(-1);
			this._process = process;
		}

		protected final ITickProcess _process;

		@Override protected int queryWorldValue() { return this._process.getTime(); }

		@Override protected void commitValue(final int value) { this._process.setTime(value); }
	}

	/**
	 * Handler for the process time max value.
	 */
	public static class TimeMax extends ASynchHandler
	{
		public TimeMax(final ITickProcess process)
		{
			super(-1);
			this._process = process;
		}

		protected final ITickProcess _process;

		@Override protected int queryWorldValue() { return this._process.getTimeMax(); }

		@Override protected void commitValue(final int value) { this._process.setTimeMax(value); }
	}
}

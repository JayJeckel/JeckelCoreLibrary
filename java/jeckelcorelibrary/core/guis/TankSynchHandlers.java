package jeckelcorelibrary.core.guis;

import jeckelcorelibrary.base.guis.ASynchHandler;
import jeckelcorelibrary.utils.FluidUtil;
import jeckelcorelibrary.utils.MathUtil;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Common tank related synch handlers.
 */
public class TankSynchHandlers
{
	/**
	 * Handler for the tank fluid id value.
	 */
	public static class FluidId extends ASynchHandler
	{
		public FluidId(final FluidTank tank)
		{
			super(-1);
			this._tank = tank;
		}

		protected final FluidTank _tank;

		@Override protected int queryWorldValue() { return FluidUtil.getId(this._tank.getFluid()); }

		@Override protected void commitValue(final int value)
		{
			this._tank.setFluid((value <= -1 ? null : new FluidStack(value, this._tank.getFluidAmount())));
		}
	}

	/**
	 * Handler for the tank fluid amount value.
	 */
	public static class Amount extends ASynchHandler
	{
		public Amount(final FluidTank tank)
		{
			super(0);
			this._tank = tank;
		}

		protected final FluidTank _tank;

		@Override protected int queryWorldValue() { return this._tank.getFluidAmount(); }

		@Override protected void commitValue(final int value)
		{
			if (this._tank.getFluid() == null) { return; }
			this._tank.setFluid(new FluidStack(FluidUtil.getId(this._tank.getFluid()), value));
		}
	}

	/*public static class AmountBucket extends ASynchHandler
	{
		public AmountBucket(final FluidTank tank)
		{
			super(0);
			this._tank = tank;
		}

		protected final FluidTank _tank;

		@Override protected int queryWorldValue() { return this._tank.getFluidAmount() / 32000; }

		@Override protected void commitValue(final int value)
		{
			if (this._tank.getFluid() == null) { return; }
			this._tank.setFluid(new FluidStack(FluidUtil.getId(this._tank.getFluid()), value * 32000));
		}
	}*/

	public static class AmountPrimary extends ASynchHandler
	{
		public AmountPrimary(final FluidTank tank)
		{
			super(0);
			this._tank = tank;
		}

		protected final FluidTank _tank;

		@Override protected int queryWorldValue() { return Math.min(32000, this._tank.getFluidAmount()); }

		@Override protected void commitValue(final int value)
		{
			if (value < 0) { return; }
			if (this._tank.getFluid() == null) { return; }
			this._tank.setFluid(new FluidStack(FluidUtil.getId(this._tank.getFluid()), value));
		}
	}

	public static class AmountAdditional extends ASynchHandler
	{
		public AmountAdditional(final FluidTank tank)
		{
			this(tank, 1);
		}

		public AmountAdditional(final FluidTank tank, final int count)
		{
			super(0);
			this._tank = tank;
			this._count = count;
		}

		protected final FluidTank _tank;
		protected final int _count;

		@Override protected int queryWorldValue() { return MathUtil.clamp(this._tank.getFluidAmount() - (this._count * 32000), 0, 32000); }

		@Override protected void commitValue(final int value)
		{
			if (value <= 0) { return; }
			if (this._tank.getFluid() == null) { return; }
			this._tank.setFluid(new FluidStack(FluidUtil.getId(this._tank.getFluid()), (this._count * 32000) + value));
		}
	}

	/**
	 * Handler for the tank fluid capacity value.
	 */
	public static class Capacity extends ASynchHandler
	{
		public Capacity(final FluidTank tank)
		{
			super(0);
			this._tank = tank;
		}

		protected final FluidTank _tank;

		@Override protected int queryWorldValue() { return this._tank.getCapacity(); }

		@Override protected void commitValue(final int value) { this._tank.setCapacity(value); }
	}
}

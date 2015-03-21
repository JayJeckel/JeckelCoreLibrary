package jeckelcorelibrary.core.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class BasicFluidTank extends FluidTank
{
    public BasicFluidTank(int capacity)
    {
        super(capacity);
    }

    public BasicFluidTank(FluidStack stack, int capacity)
    {
    	super(stack, capacity);
    }

    public BasicFluidTank(Fluid fluid, int amount, int capacity)
    {
    	super(fluid, amount, capacity);
    }
    
    public boolean isEmpty() { return this.fluid == null || this.fluid.amount <= 0; }
    public boolean isFull() { return this.fluid != null && this.fluid.amount >= this.capacity; }
    
    public int getSpace()
    {
    	if (this.isEmpty()) { return this.getCapacity(); }
    	else if (this.isFull()) { return 0; }
    	return this.getCapacity() - this.getFluidAmount();
    }
    
    public boolean canDrainSome(int maxAmount) { return this.drain(maxAmount, false) != null; }
    public boolean canDrainAll(int amount) { return (!this.isEmpty() && this.fluid.amount >= amount); }
    
    public boolean canFillSome(FluidStack liquid) { return this.fill(liquid, false) > 0; }
    public boolean canFillAll(FluidStack liquid)
    {
    	//if (this.getFluid() != null && !this.getFluid().isFluidEqual(liquid)) { return false; }
    	int used = this.fill(liquid, false);
    	return used > 0 && used == liquid.amount;
    }
    
    public FluidStack drain(int maxAmount) { return this.drain(maxAmount, true); }
}

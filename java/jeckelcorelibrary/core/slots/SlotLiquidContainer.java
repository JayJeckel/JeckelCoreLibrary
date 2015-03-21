package jeckelcorelibrary.core.slots;

import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLiquidContainer extends Slot
{
    public SlotLiquidContainer(IInventory inventory, int slotIndex, int x, int y)
    {
        this(inventory, slotIndex, x, y, true, true, true);
    }

    public SlotLiquidContainer(IInventory inventory, int slotIndex, int x, int y, boolean useIcon)
    {
        this(inventory, slotIndex, x, y, true, true, useIcon);
    }

    public SlotLiquidContainer(IInventory inventory, int slotIndex, int x, int y, boolean allowEmpty, boolean allowFilled)
    {
        this(inventory, slotIndex, x, y, allowEmpty, allowFilled, true);
    }

    public SlotLiquidContainer(IInventory inventory, int slotIndex, int x, int y, boolean allowEmpty, boolean allowFilled, boolean useIcon)
    {
        super(inventory, slotIndex, x, y);
        this.allowEmpty = allowEmpty;
        this.allowFilled = allowFilled;
        this.useIcon = useIcon;

        if (!allowEmpty && !allowFilled)
        {
        	throw new IllegalArgumentException("Empty and filled containers both disabled. One or both container types must be allowed.");
        }
        
        if (this.useIcon)
        {
        	//int iconCol = 4;
        	//if (this.allowEmpty && this.allowFilled) { iconCol = 4; }
        	//else if (this.allowEmpty) { iconCol = 5; }
        	//else if (this.allowFilled) { iconCol = 6; }
			//this.setBackgroundIconIndex(iconCol + 0 * 16);
			//this.setBackgroundIconTexture(CoreTextures.sheetIcons);
        }
    }
    
    public final boolean allowEmpty;
    public final boolean allowFilled;
    public final boolean useIcon;
    
    @Override public boolean isItemValid(ItemStack stack)
    {
    	if (stack == null) { return false; }
    	else if (FluidUtil.isEmptyContainer(stack)) { return this.allowEmpty; }
    	else if (FluidUtil.isFilledContainer(stack)) { return this.allowFilled; }
    	return false;
    }
}

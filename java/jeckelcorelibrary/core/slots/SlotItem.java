package jeckelcorelibrary.core.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotItem extends Slot
{
    public SlotItem(IInventory inventory, int id, int x, int y, ItemStack stack)
    {
        super(inventory, id, x, y);
        this.match = stack.copy();
        this.limit = 64;
    }

    public SlotItem(IInventory inventory, int id, int x, int y, ItemStack stack, int limit)
    {
        super(inventory, id, x, y);
        this.match = stack.copy();
        this.limit = limit;
    }

    public final ItemStack match;
    public final int limit;

    @Override public boolean isItemValid(ItemStack stack)
    {
    	return OreDictionary.itemMatches(this.match, stack, false);
    }

    @Override public int getSlotStackLimit()
    {
    	return this.limit;
    }
}

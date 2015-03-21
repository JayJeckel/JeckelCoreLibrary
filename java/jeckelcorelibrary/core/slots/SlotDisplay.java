package jeckelcorelibrary.core.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDisplay extends Slot
{
    public SlotDisplay(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
    }
    
    @Override public boolean isItemValid(ItemStack itemStack) { return false; }
    
    @Override public boolean canTakeStack(EntityPlayer player) { return false; }
}

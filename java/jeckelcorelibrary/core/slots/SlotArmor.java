package jeckelcorelibrary.core.slots;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotArmor extends Slot
{
    /**
     * The armor type that can be placed on that slot, it uses the same values of armorType field on ItemArmor.
     */
    public final int armorType;
    public boolean useIcon = true;

    public SlotArmor(IInventory inventory, int slotIndex, int x, int y, int armorType)
    {
    	this(inventory, slotIndex, x, y, armorType, true);
    }

    public SlotArmor(IInventory inventory, int slotIndex, int x, int y, int armorType, boolean useIcon)
    {
        super(inventory, slotIndex, x, y);
        this.armorType = armorType;
        this.useIcon = useIcon;
    }

    @Override public int getSlotStackLimit() { return 1; }

    @Override public boolean isItemValid(ItemStack itemStack)
    {
        //return itemStack == null ? false : (itemStack.getItem() instanceof ItemArmor ? ((ItemArmor)itemStack.getItem()).armorType == this.armorType : (itemStack.getItem().itemID != Block.pumpkin.blockID && itemStack.getItem().itemID != Item.skull.itemID ? false : this.armorType == 0));
        Item item = (itemStack == null ? null : itemStack.getItem());
        return item != null && item.isValidArmor(itemStack, armorType, null);
    }

    @SideOnly(Side.CLIENT)
    @Override public IIcon getBackgroundIconIndex()
    {
    	//if (!this.useIcon) { return null; }
        return ItemArmor.func_94602_b(this.armorType);
    }
}

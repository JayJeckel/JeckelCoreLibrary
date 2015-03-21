package jeckelcorelibrary.base.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class ATileInventory extends ATileBase implements IInventory
{
	public ATileInventory()
	{
		this.dummy = false;
	}

	public ATileInventory(int inventorySize)
	{
		this(inventorySize, false);
	}

	public ATileInventory(int inventorySize, boolean dummy)
	{
		this._slotCount = inventorySize;
		this._slotArray = new ItemStack[this._slotCount];
		this.dummy = dummy;
	}

	public final boolean dummy;


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	@Override public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		this.readNBTInventory(tagCompound);
	}

	@Override public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		this.writeNBTInventory(tagCompound);
	}


	// ##################################################
	//
	// IInventory
	//
	// ##################################################

	protected int _slotCount = -1;
	protected ItemStack[] _slotArray;

	@Override public String getInventoryName() { return this.hasCustomName() ? this.getCustomName() : this.getTileName(); }

	@Override public boolean hasCustomInventoryName() { return this.hasCustomName(); }

	@Override public int getSizeInventory() { return (this._slotArray == null ? 0 : this._slotArray.length); }

	@Override public int getInventoryStackLimit() { return 64; }

	@Override public ItemStack getStackInSlot(int slotIndex) { return this._slotArray[slotIndex]; }

	@Override public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		if (this.getStackInSlot(slotIndex) == null) { return null; }
		ItemStack stack = this.getStackInSlot(slotIndex);
		this._slotArray[slotIndex] = null;
		return stack;
	}

	@Override public ItemStack decrStackSize(int slotIndex, int stackChange)
	{
		if (this.getStackInSlot(slotIndex) == null) { return null; }
		if (this.getStackInSlot(slotIndex).stackSize <= stackChange)
		{
			ItemStack stack = this.getStackInSlot(slotIndex);
			this._slotArray[slotIndex] = null;
			this.markDirty();
			return stack;
		}
		ItemStack stack = this.getStackInSlot(slotIndex).splitStack(stackChange);
		if (this.getStackInSlot(slotIndex).stackSize == 0)
		{
			this._slotArray[slotIndex] = null;
		}
		this.markDirty();
		return stack;
	}

	@Override public void setInventorySlotContents(int slotIndex, ItemStack stack)
	{
		this._slotArray[slotIndex] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}

	@Override public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.getDistanceFrom(player.posX + 0.5, player.posY, player.posZ + 0.5) <= 64.0;
	}

	@Override public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override public void openInventory() { }

	@Override public void closeInventory() { }

	public void readNBTInventory(NBTTagCompound tagCompound)
	{
		if (this._slotCount < 0) { this._slotCount = tagCompound.getInteger("SlotCount"); }

		// Slot Array
		if (tagCompound.hasKey("SlotArray"))
		{
			NBTTagList tagList = tagCompound.getTagList("SlotArray", 10);
			this._slotArray = new ItemStack[this._slotCount];
			for (int tagIndex = 0; tagIndex < tagList.tagCount(); ++tagIndex)
			{
				NBTTagCompound tag = (NBTTagCompound)tagList.getCompoundTagAt(tagIndex);
				int slotIndex = tag.getByte("Slot") & 255;

				if (slotIndex >= 0 && slotIndex < this._slotArray.length)
				{
					this._slotArray[slotIndex] = ItemStack.loadItemStackFromNBT(tag);
				}
			}
		}
		else
		{
			this._slotArray = null;
		}
	}

	public void writeNBTInventory(NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("SlotCount", this._slotCount);

		// Slot Array
		if (this._slotArray != null)
		{
			NBTTagList tagList = new NBTTagList();
			for (int slotIndex = 0; slotIndex < this._slotArray.length; ++slotIndex)
			{
				if (this._slotArray[slotIndex] != null)
				{
					NBTTagCompound slotTag = new NBTTagCompound();
					slotTag.setByte("Slot", (byte)slotIndex);
					this._slotArray[slotIndex].writeToNBT(slotTag);
					tagList.appendTag(slotTag);
				}
			}
			tagCompound.setTag("SlotArray", tagList);
		}
	}
}

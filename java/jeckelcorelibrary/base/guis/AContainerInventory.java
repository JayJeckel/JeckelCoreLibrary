package jeckelcorelibrary.base.guis;

import java.lang.reflect.Constructor;

import jeckelcorelibrary.core.guis.SynchList;
import jeckelcorelibrary.core.slots.SlotArmor;
import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AContainerInventory extends Container
{
	public AContainerInventory(final EntityPlayer player, final IInventory inventory, final int width, final int height)
	{
		this._synchList = new SynchList();
		this._player = player;
		this._inventory = inventory;

		this._width = width;
		this._height = height;
	}

	protected final EntityPlayer _player;

	protected final IInventory _inventory;

	protected final SynchList _synchList;

	protected int _width = 176;

	protected int _height = 166;


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	protected int getMergeSlotCount(final int slotIndex)
	{
		return this._inventory.getSizeInventory();
	}

	protected boolean isValidSlotItem(final EntityPlayer player, final int slotIndex, final ItemStack stack)
	{
		return false;
	}


	// ##################################################
	//
	// Synchronization Methods
	//
	// ##################################################

	@Override public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		this._synchList.sendAll(crafting, this);
	}

	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			this._synchList.sendChanges(crafting, this);
		}

		this._synchList.synchChanges();
	}

	@Override public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		this._synchList.updateProgress(index, value);
	}


	// ##################################################
	//
	// Slot Construction Methods
	//
	// ##################################################

	/**
	 * Construct a slot instance using the default constructor.
	 * @param slotClass The slot class to instantiate.
	 * @param inventory Inventory to pass to the slot constructor.
	 * @param id Slot id to pass to the slot constructor.
	 * @param x X position on the screen to pass to the slot constructor.
	 * @param y Y position on the screen to pass to the slot constructor.
	 * @return A new slot instance or null or thrown an exceoption.
	 */
	public Slot ConstructSlot(Class<? extends Slot> slotClass, IInventory inventory, int id, int x, int y)
	{
		Constructor<? extends Slot> ctor;
		try
		{
			ctor = slotClass.getDeclaredConstructor(IInventory.class, int.class, int.class, int.class);
			return (Slot) ctor.newInstance(inventory, id, x, y);
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}


	// ##################################################
	//
	// Local Inventory Slot Methods
	//
	// ##################################################

	public void addInventorySlots(final int startX, final int startY, final int colCount, final int rowCount)
	{
		this.addInventorySlots(this._inventory, startX, startY, colCount, rowCount, 0);
	}

	public void addInventorySlots(final int startX, final int startY, final int colCount, final int rowCount, final int startIndex)
	{
		this.addInventorySlots(this._inventory, startX, startY, colCount, rowCount, startIndex);
	}

	public void addInventorySlots(final int startX, final int startY, final int colCount, final int rowCount, final int startIndex, final Class<? extends Slot> slotClass)
	{
		this.addInventorySlots(this._inventory, startX, startY, colCount, rowCount, startIndex, slotClass);
	}

	public void addInventorySlots(final IInventory inventory, final int startX, final int startY, final int colCount, final int rowCount)
	{
		this.addInventorySlots(inventory, startX, startY, colCount, rowCount, 0);
	}

	public void addInventorySlots(final IInventory inventory, final int startX, final int startY, final int colCount, final int rowCount, final int startIndex)
	{
		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex)
		{
			for (int colIndex = 0; colIndex < colCount; ++colIndex)
			{
				int id = startIndex + colIndex + (rowIndex * colCount);
				int x = startX + (colIndex * 18);
				int y = startY + (rowIndex * 18);
				this.addSlotToContainer(new Slot(inventory, id, x, y));
			}
		}
	}

	public void addInventorySlots(final IInventory inventory, final int startX, final int startY, final int colCount, final int rowCount, final Class<? extends Slot> slotClass)
	{
		this.addInventorySlots(inventory, startX, startY, colCount, rowCount, 0, slotClass);
	}

	public void addInventorySlots(final IInventory inventory, final int startX, final int startY, final int colCount, final int rowCount, final int startIndex, final Class<? extends Slot> slotClass)
	{
		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex)
		{
			for (int colIndex = 0; colIndex < colCount; ++colIndex)
			{
				int id = startIndex + colIndex + (rowIndex * colCount);
				int x = startX + (colIndex * 18);
				int y = startY + (rowIndex * 18);
				this.addSlotToContainer(this.ConstructSlot(slotClass, inventory, id, x, y));
			}
		}
	}


	// ##################################################
	//
	// Player Inventory Slot Methods
	//
	// ##################################################

	protected void addPlayerInventorySlots(IInventory inventory, int startX, int screenHeight)
	{
		for (int rowIndex = 0; rowIndex < 3; ++rowIndex)
		{
			for (int colIndex = 0; colIndex < 9; ++colIndex)
			{
				int id = colIndex + rowIndex * 9 + 9;
				int x = startX + (colIndex * 18);
				int y = screenHeight - 82 + (rowIndex * 18);
				this.addSlotToContainer(new Slot(inventory, id, x, y));
			}
		}
	}

	protected void addPlayerHotbarSlots(IInventory inventory, int startX, int screenHeight)
	{
		for (int colIndex = 0; colIndex < 9; ++colIndex)
		{
			this.addSlotToContainer(new Slot(inventory, colIndex, startX + colIndex * 18,  screenHeight - 24));
		}
	}

	protected void addPlayerArmorSlots(IInventory inventory, int startX, int startY)
	{
		for (int rowIndex = 3; rowIndex >= 0; rowIndex--)
		{
			this.addSlotToContainer(new SlotArmor(inventory, 36 + rowIndex, startX, startY + (3 - rowIndex) * 18, 3 - rowIndex));
		}
	}


	// ##################################################
	//
	// Container Methods
	//
	// ##################################################

	@Override public boolean canInteractWith(EntityPlayer player)
	{ return this._inventory.isUseableByPlayer(player); }

	/**
	 * This override of the default Container method is a simple implementation
	 * that provides standard shift-clicking between the hotbar and player
	 * inventory and out of the local inventory into the player inventory, but
	 * no transferring into the local slots.
	 *
	 * Called when a player shift-clicks on a slot.
	 * You should override this for any real shift-clicking functionality, but
	 * at least you won't crash if you don't.
	 *
	 * @param player Player doing the transferring.
	 * @param slotIndex Index of the slot being transferred.
	 * @return Null if the entire stack was transfered or a stack containing
	 * the remaining items.
	 */
	@Override public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		ItemStack outStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack())
		{
			final int inventoryCount = this._inventory.getSizeInventory();
			final int playerCount = inventoryCount + 27;
			final int hotbarCount = playerCount + 9;
			final int totalCount = this.inventorySlots.size();
			ItemStack slotStack = slot.getStack();
			outStack = slotStack.copy();

			if (slotIndex < inventoryCount)
			{
				if (!this.mergeItemStack(slotStack, inventoryCount, totalCount, true)) { return null; }
			}
			else
			{
				boolean done = false;
				for (int index = 0; index < inventoryCount; )
				{
					final int count = this.getMergeSlotCount(index);
					if (count <= 0) { index++; continue; }
					if (!this.isValidSlotItem(player, index, slotStack)) { index += count; continue; }

					if (!this.mergeItemStack(slotStack, index, index + count, false)) { return null; }
					index += count;
					done = true;
				}
				if (done) { }
				else if (slotIndex >= inventoryCount && slotIndex < playerCount)
				{
					if (!this.mergeItemStack(slotStack, playerCount, hotbarCount, false))
					{
						return null;
					}
				}
				else if (slotIndex >= playerCount && slotIndex < hotbarCount)
				{
					if (!this.mergeItemStack(slotStack, inventoryCount, playerCount, false))
					{
						return null;
					}
				}
				//else if (!this.mergeItemStack(slotStack, 0, inventorySize, false)) { return null; }
			}

			if (slotStack.stackSize == 0) { slot.putStack((ItemStack)null); }
			else { slot.onSlotChanged(); }

			if (slotStack.stackSize == outStack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, slotStack);
		}

		return outStack;
	}

	/**
	 * This override of the default Container method makes the method
	 * aware of inventory and slot stack size limitations, which the
	 * default method lacks.
	 *
	 * Merges the given item stack with the first available slot that is either
	 * empty or contains a matching stack. If the first slot can not hold all of
	 * the merging items, then the method will progress to the next valid slot and
	 * so on until all items are merged or all slots have been cycled through. Any
	 * left over items will remain in the passed item stack, as it is edited in place.
	 *
	 * IMPORTANT: The second index is the last index you want checked PLUS ONE. If you
	 * want to merge slot indexes 5 through 12, then you would pass the arguments
	 * 5 and 13, respectively. In other words, the first arg is the start index and
	 * the second arg is the start index plus the count of how many slots to merge over.
	 *
	 * @param stack The item stack to merge.
	 * @param firstIndex The slot index to start merging with.
	 * @param lastIndexPlusOne The slot index after the last to merge with.
	 * @param reverse Start merging at the last slot index and work toward the first.
	 * @return False if the entire stack was merged successfully or true if any items
	 * remain after merging.
	 */
	@Override protected boolean mergeItemStack(ItemStack stack, final int firstIndex, final int lastIndexPlusOne, final boolean reverse)
	{
		boolean flag1 = false;
		int k = firstIndex;

		if (reverse)
		{
			k = lastIndexPlusOne - 1;
		}

		Slot slot;
		ItemStack slotStack;

		if (stack.isStackable())
		{
			while (stack.stackSize > 0 && (!reverse && k < lastIndexPlusOne || reverse && k >= firstIndex))
			{
				slot = (Slot)this.inventorySlots.get(k);
				slotStack = slot.getStack();
				int max = InvUtil.getMaxStackSize(stack, slotStack, this._inventory, slot);

				if (slotStack != null && slotStack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, slotStack))
				{
					int total = slotStack.stackSize + stack.stackSize;

					//if (l <= stack.getMaxStackSize())
					if (total <= max)
					{
						stack.stackSize = 0;
						slotStack.stackSize = total;
						slot.onSlotChanged();
						flag1 = true;
					}
					//else if (slotStack.stackSize < stack.getMaxStackSize())
					else if (slotStack.stackSize < max)
					{
						stack.stackSize -= max - slotStack.stackSize;
						slotStack.stackSize = max;
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (reverse) { --k; }
				else { ++k; }
			}
		}

		if (stack.stackSize > 0)
		{
			if (reverse)
			{
				k = lastIndexPlusOne - 1;
			}
			else
			{
				k = firstIndex;
			}

			while (!reverse && k < lastIndexPlusOne || reverse && k >= firstIndex)
			{
				slot = (Slot)this.inventorySlots.get(k);
				slotStack = slot.getStack();
				int max = InvUtil.getMaxStackSize(stack, slotStack, this._inventory, slot);

				if (slotStack == null)
				{
					final int amount = (max < stack.stackSize ? max : stack.stackSize);
					final ItemStack output = stack.copy();
					output.stackSize = amount;
					slot.putStack(output);
					slot.onSlotChanged();
					stack.stackSize -= amount;
					flag1 = true;
					break;
				}

				if (reverse)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}

		return flag1;
	}
}

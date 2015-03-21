package jeckelcorelibrary;

import jeckelcorelibrary.JeckelCoreLibrary.Refs;
import jeckelcorelibrary.core.tabs.ISharedTabRegistrant;
import jeckelcorelibrary.core.tabs.ISharedTabRegistry;
import jeckelcorelibrary.core.tabs.MappedCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SharedTabManager
{
	private MappedCreativeTab _tabMachines = null;

	public void receiveMessage(final String key, final String senderId, final String className)
	{
		Class<?> registerClass;
		try { registerClass = Class.forName(className); }
		catch (ClassNotFoundException e) { e.printStackTrace(); return; }


		final Object obj;
		try { obj = registerClass.newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); return; }

		if (obj instanceof ISharedTabRegistrant)
		{
			((ISharedTabRegistrant)obj).initialize(this.getMachineRegistry());
		}
	}

	private ISharedTabRegistry getMachineRegistry()
	{
		if (this._tabMachines == null)
		{
			this._tabMachines = new MappedCreativeTab(Refs.ModId + ".machines.name");
			this._tabMachines.setIconItemStack(new ItemStack(Blocks.furnace));
		}
		return this._tabMachines;
	}

	public void addMachineStack(final String modId, final Item item) { this.getMachineRegistry().addMisc(modId, item); }

	public void addMachineStack(final String modId, final Item item, final int meta) { this.getMachineRegistry().addMisc(modId, item, meta); }

	public void addMachineStack(final String modId, final Block block) { this.getMachineRegistry().addMisc(modId, block); }

	public void addMachineStack(final String modId, final Block block, final int meta) { this.getMachineRegistry().addMisc(modId, block, meta); }

	public void addMachineStack(final String modId, final ItemStack stack) { this.getMachineRegistry().addMisc(modId, stack); }

	public void addMachineBlock(final String modId, final ItemStack stack) { this.getMachineRegistry().addBlock(modId, stack); }

	public void addMachineItem(final String modId, final ItemStack stack) { this.getMachineRegistry().addItem(modId, stack); }

}

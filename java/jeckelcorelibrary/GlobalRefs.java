package jeckelcorelibrary;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class GlobalRefs
{

	public static SharedTabManager getTabManager() { return _tabManager; }
	private static SharedTabManager _tabManager = null;

	public static void initialize(FMLPreInitializationEvent event)
	{
		_tabManager = new SharedTabManager();
	}
}

package jeckelcorelibrary.base.items;

import net.minecraft.item.Item;

public abstract class AItem extends Item
{
	public AItem(final String modId, final String itemName)
	{
		this._rawName = itemName;
		this.setUnlocalizedName(this.getRawName());
		this.setTextureName(modId + ":" + this.getRawName());
		this.setMaxDamage(16);
	}

	public String getRawName() { return this._rawName; }
	private final String  _rawName;
}

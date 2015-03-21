package jeckelcorelibrary.core.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemRenderer implements IItemRenderer
{
	private TileEntity tile;
	private TileEntitySpecialRenderer render;
	
	public ItemRenderer(TileEntitySpecialRenderer render, TileEntity tile)
	{
		this.tile = tile;
		this.render = render;
	}

	@Override public boolean handleRenderType(ItemStack stack, ItemRenderType type) { return true; }

	@Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) { return true; }

	@Override public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
	{
		if(type == IItemRenderer.ItemRenderType.ENTITY)
		{
			GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		}
		this.render.renderTileEntityAt(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
	}
}

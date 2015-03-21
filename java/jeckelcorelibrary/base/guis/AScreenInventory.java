package jeckelcorelibrary.base.guis;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.utils.MathUtil;
import jeckelcorelibrary.utils.RenderUtil;
import jeckelcorelibrary.utils.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class AScreenInventory extends GuiContainer
{
	public AScreenInventory(final EntityPlayer player, IInventory inventory, Container container, int xSize, int ySize)
	{
		super(container);
		this._player = player;
		this._inventory = inventory;

		this.xSize = xSize;
		this.ySize = ySize;
	}

	protected final EntityPlayer _player;

	protected final IInventory _inventory;

	public ResourceLocation getResourceLocation() { return this._resource; }
	protected void setResourceLocation(final ResourceLocation resource) { this._resource = resource; }
	protected void setResourceLocation(final String modId, final String subPath) { this._resource = new ResourceLocation(modId, "textures/guis/" + subPath);; }
	private ResourceLocation _resource;

	public String getTitle() { return StatCollector.translateToLocal(this._inventory.getInventoryName()); }

	protected int getMouseX() { return (Mouse.getEventX() * this.width / this.mc.displayWidth) - this.guiLeft; }
	protected int getMouseY() { return (this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1) - this.guiTop; }


	// ##################################################
	//
	// Do Draw Methods
	//
	// ##################################################

	protected void doDrawTitle()
	{
		this.drawTextLeft(this.getTitle(), 8, 6);
	}

	protected void doDrawBackground()
	{
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}


	// ##################################################
	//
	// On Draw Methods
	//
	// ##################################################

	protected void onDrawOverlays() { }

	protected void onDrawTexts() { }

	protected void onDrawTooltips(int x, int y) { }


	// ##################################################
	//
	// Root Draw Methods
	//
	// ##################################################

	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.doDrawTitle();
		this.onDrawTexts();
		this.onDrawTooltips(this.getMouseX(), this.getMouseY());
	}

	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.bindDefaultTexture();
		this.doDrawBackground();

		this.bindDefaultTexture();
		this.onDrawOverlays();
	}


	// ##################################################
	//
	// Bind Texture Methods
	//
	// ##################################################

	protected void bindDefaultTexture()
	{
		this.bindTexture(this.getResourceLocation());
	}

	protected void bindTexture(ResourceLocation resource)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(resource);
	}


	// ##################################################
	//
	// Draw Text Methods
	//
	// ##################################################

	protected void drawTextCenter(String text, int y)
	{
		int textWidth = this.fontRendererObj.getStringWidth(text);
		this.fontRendererObj.drawString(text, (this.xSize - textWidth) / 2, y, 4210752);
	}

	protected void drawTextLeft(String text, int x, int y)
	{
		this.fontRendererObj.drawString(text, x, y, 4210752);
	}

	protected void drawTextRight(String text, int x, int y)
	{
		int textWidth = this.fontRendererObj.getStringWidth(text);
		this.fontRendererObj.drawString(text, (x - textWidth), y, 4210752);
	}

	/*protected void drawText(String text, int alignment, int padX, int padY)
	{
		int textWidth = this.fontRendererObj.getStringWidth(text);
		if (alignment < 1) { this.fontRendererObj.drawString(text, padX, padY, 4210752); }
		else if (alignment > 1) { this.fontRendererObj.drawString(text, this.xSize - textWidth - padX, padY, 4210752); }
		else { this.fontRendererObj.drawString(text, padX + ((this.xSize - textWidth) / 2), padY, 4210752); }
	}*/


	// ##################################################
	//
	// Draw Image Methods
	//
	// ##################################################

	protected void drawImage(Rectangle dest, Point source)
	{
		this.drawTexturedModalRect(this.guiLeft + dest.x, this.guiTop + dest.y, source.x, source.y, dest.width, dest.height);
	}

	protected void drawImageScaledWidth(Rectangle dest, Point source, int scaledValue, boolean reverse)
	{
		final Rectangle scaledDest;
		final Point scaledSource;
		if (reverse)
		{
			final int offset = dest.width - scaledValue;
			scaledDest = new Rectangle(dest.x + offset, dest.y, scaledValue, dest.height);
			scaledSource = new Point(source.x + offset, source.y);
		}
		else
		{
			scaledDest = new Rectangle(dest.x, dest.y, scaledValue, dest.height);
			scaledSource = new Point(source.x, source.y);
		}
		this.drawImage(scaledDest, scaledSource);
	}

	protected void drawImageScaledHeight(Rectangle dest, Point source, int scaledValue, boolean reverse)
	{
		final Rectangle scaledDest;
		final Point scaledSource;
		if (reverse)
		{
			final int offset = dest.height - scaledValue;
			scaledDest = new Rectangle(dest.x, dest.y + offset, dest.width, scaledValue);
			scaledSource = new Point(source.x, source.y + offset);
		}
		else
		{
			scaledDest = new Rectangle(dest.x, dest.y, dest.width, scaledValue);
			scaledSource = new Point(source.x, source.y);
		}
		this.drawImage(scaledDest, scaledSource);
	}

	protected void drawImageInvertedScaledHeight(Rectangle dest, Point source, int scaledValue, boolean reverse)
	{
		final Rectangle scaledDest;
		final Point scaledSource;
		if (reverse)
		{
			//final int offset = dest.height - scaledValue;
			scaledDest = new Rectangle(dest.x, dest.y + scaledValue, dest.width, dest.height - scaledValue);
			scaledSource = new Point(source.x, source.y + scaledValue);
		}
		else
		{
			scaledDest = new Rectangle(dest.x, dest.y, dest.width, dest.height - scaledValue);
			scaledSource = new Point(source.x, source.y);
		}
		this.drawImage(scaledDest, scaledSource);
	}


	// ##################################################
	//
	// Draw Tooltip Methods
	//
	// ##################################################

	protected void drawTankTooltip(final int x, final int y, final FluidTank tank)
	{
		final boolean empty = (tank.getFluidAmount() <= 0);
		final String title = String.format("Tank (%s)", (empty ? "Empty" : tank.getFluid().getLocalizedName()));

		final int cap = tank.getCapacity();
		final int amount = tank.getFluidAmount();
		String text;
		if (amount == 0) { text = String.format("Empty/%d mb", cap); }
		else if (amount == cap) { text = String.format("Full/%d mb", cap); }
		else  { text = String.format("%d/%d mb", amount, cap); }
		this.drawTooltip(x, y, title, text);
	}

	protected void drawTooltip(int x, int y, List<String> lines)
	{
		this.drawTooltip(x, y, lines.toArray(new String[0]));
		//this.drawHoveringText(lines, x, y, this.fontRendererObj);
	}

	protected void drawTooltip(int x, int y, String... lines)
	{
		//this.drawTooltip(x, y, Arrays.asList(lines));
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		List<String> lineList = Arrays.asList(lines);
		if (!lineList.isEmpty())
		{
			int textWidth = 0;

			for (int index = 0; index < lineList.size(); ++index)
			{
				int lineWidth = this.fontRendererObj.getStringWidth((String)lineList.get(index));
				if (lineWidth > textWidth) { textWidth = lineWidth; }
			}

			int xDraw = x + 12;
			int yDraw = y - 12;
			int var9 = 8;

			if (lineList.size() > 1) { var9 += 2 + (lineList.size() - 1) * 10; }

			this.zLevel = 300.0F;
			GuiScreen.itemRender.zLevel = 300.0F;
			int var10 = -267386864;
			this.drawGradientRect(xDraw - 3, yDraw - 4, xDraw + textWidth + 3, yDraw - 3, var10, var10);
			this.drawGradientRect(xDraw - 3, yDraw + var9 + 3, xDraw + textWidth + 3, yDraw + var9 + 4, var10, var10);
			this.drawGradientRect(xDraw - 3, yDraw - 3, xDraw + textWidth + 3, yDraw + var9 + 3, var10, var10);
			this.drawGradientRect(xDraw - 4, yDraw - 3, xDraw - 3, yDraw + var9 + 3, var10, var10);
			this.drawGradientRect(xDraw + textWidth + 3, yDraw - 3, xDraw + textWidth + 4, yDraw + var9 + 3, var10, var10);
			int var11 = 1347420415;
			int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
			this.drawGradientRect(xDraw - 3, yDraw - 3 + 1, xDraw - 3 + 1, yDraw + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(xDraw + textWidth + 2, yDraw - 3 + 1, xDraw + textWidth + 3, yDraw + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(xDraw - 3, yDraw - 3, xDraw + textWidth + 3, yDraw - 3 + 1, var11, var11);
			this.drawGradientRect(xDraw - 3, yDraw + var9 + 2, xDraw + textWidth + 3, yDraw + var9 + 3, var12, var12);

			for (int var13 = 0; var13 < lineList.size(); ++var13)
			{
				String var14 = (String)lineList.get(var13);
				this.fontRendererObj.drawStringWithShadow(var14, xDraw, yDraw, -1);
				if (var13 == 0) { yDraw += 2; }
				yDraw += 10;
			}

			this.zLevel = 0.0F;
			GuiScreen.itemRender.zLevel = 0.0F;
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}


	// ##################################################
	//
	// Draw Fluid Methods
	//
	// ##################################################

	public IIcon bindFluidTexture(FluidStack liquid)
	{
		if (liquid == null || liquid.amount <= 0 || liquid.getFluid() == null) { return null; }

		RenderUtil.bindTexture(this.mc, TextureMap.locationBlocksTexture);
		return liquid.getFluid().getStillIcon();
	}

	public void drawFluid(Rectangle rect, FluidStack fluid, int capacity)
	{
		if (fluid == null) { return; }

		IIcon icon = this.bindFluidTexture(fluid);
		int h = MathUtil.getScaledValue(fluid.amount, capacity, rect.height);
		this.drawTexturedModelRectFromIcon(this.guiLeft + rect.x, this.guiTop + rect.y + rect.height - h,
				icon, rect.width, h);
	}

	protected void drawFluidTank(final Rectangle rect, final IFluidTank tank)
	{
		this.drawFluid(rect, tank.getFluid(), tank.getCapacity());

		this.bindDefaultTexture();
		this.drawTexturedModalRect(this.guiLeft + rect.x, this.guiTop + rect.y, 0, 209, rect.width, rect.height);
	}


	// ##################################################
	//
	// Draw Text Methods
	//
	// ##################################################

	protected void drawPlayer(Rectangle rect, float xSize, float ySize, boolean invertView)
	{
		int x = this.guiLeft + rect.x + (rect.width / 2);
		int y = this.guiTop + rect.y + rect.height - 5;
		float xPoint = x - xSize;
		float yPoint = y - 50 - ySize;
		drawPlayer(this.mc, x, y, 30, xPoint, yPoint, invertView);
	}

	protected void drawPlayer(Minecraft mc, int x, int y, int scale, float xPoint, float yPoint, boolean invertView)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 50.0F);
		GL11.glScalef((-scale), scale, scale);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		if (invertView)
		{
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);// Show player's back.
		}
		float var6 = mc.thePlayer.renderYawOffset;
		float var7 = mc.thePlayer.rotationYaw;
		float var8 = mc.thePlayer.rotationPitch;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan(yPoint / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		if (invertView)
		{
			mc.thePlayer.renderYawOffset = -((float)Math.atan(xPoint / 40.0F) * 20.0F);
			mc.thePlayer.rotationYaw = -((float)Math.atan(xPoint / 40.0F) * 40.0F);
		}
		else
		{
			mc.thePlayer.renderYawOffset = (float)Math.atan(xPoint / 40.0F) * 20.0F;
			mc.thePlayer.rotationYaw = (float)Math.atan(xPoint / 40.0F) * 40.0F;
		}
		mc.thePlayer.rotationPitch = -((float)Math.atan(yPoint / 40.0F)) * 20.0F;
		mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw;
		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		mc.thePlayer.renderYawOffset = var6;
		mc.thePlayer.rotationYaw = var7;
		mc.thePlayer.rotationPitch = var8;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}


	// ##################################################
	//
	// Draw Process Methods
	//
	// ##################################################

	protected void drawProcessOverlay(final ITickProcess process, final OverlayInfo info)
	{
		if (info.overlay != null) { this.drawImage(info.dest, info.overlay); }
		final int scaled = process.getTimeScaled((info.horizontal ? info.dest.width : info.dest.height), info.invert);
		if (info.horizontal) { this.drawImageScaledWidth(info.dest, info.source, scaled, info.reverse); }
		else { this.drawImageScaledHeight(info.dest, info.source, scaled, info.reverse); }
	}

	public static enum ProcessTooltipProgressFormat
	{
		TIME_DESCEND,
		TIME_ASCEND,
		PERCENT,
		COUNTER
	}

	protected void drawProcessTooltip(final int x, final int y, final String title, final ITickProcess process)
	{
		this.drawProcessTooltip(x, y, title, process, ProcessTooltipProgressFormat.TIME_DESCEND);
	}

	protected void drawProcessTooltip(final int x, final int y, final String title, final ITickProcess process, final ProcessTooltipProgressFormat format)
	{
		final boolean active = process.isProcessing();
		if (!active)
		{
			this.drawTooltip(x, y, title, "Inactive");
			return;
		}

		String text;// = (!active ? "Inactive" : process.getTime() + "/" + process.getTimeMax());
		switch (format)
		{
			case TIME_DESCEND:
			{
				text = TimeUtil.getTimeString(process.getTimeMax() - process.getTime(), true, false);
				break;
			}
			case TIME_ASCEND:
			{
				text = TimeUtil.getTimeString(process.getTimeMax() - process.getTime(), false, false);
				break;
			}
			case PERCENT:
			{
				final double percent = ((double)process.getTime() / (double)process.getTimeMax()) * 100.0D;
				text = String.format("%.1f", percent) + "%";
				break;
			}
			case COUNTER:
			default:
			{
				text = process.getTime() + "/" + process.getTimeMax();
			}
		}
		this.drawTooltip(x, y, title, text);
	}

	@SideOnly(Side.CLIENT)
	public static class OverlayInfo
	{
		public OverlayInfo(final Rectangle dest, final Point source, final Point overlay, final boolean horizontal, final boolean reverse, final boolean invert)
		{
			this.dest = dest;
			this.source = source;
			this.overlay = overlay;

			this.horizontal = horizontal;
			this.reverse = reverse;
			this.invert = invert;
		}

		public final Rectangle dest;
		public final Point source;
		public final Point overlay;

		public final boolean horizontal;
		public final boolean reverse;
		public final boolean invert;

		public boolean contains(final int x, final int y)
		{
			return this.dest.contains(x, y);
		}
	}

}

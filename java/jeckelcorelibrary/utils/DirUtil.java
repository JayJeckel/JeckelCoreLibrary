package jeckelcorelibrary.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Static helper class to centralize direction related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class DirUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private DirUtil() { }

	/**
	 * Array of degree angle of horizontal rotation for each of the directions.
	 */
	private static final float[] _angles = new float[] { 0.0F, 0.0F, 0.0F, 180.0F, -90.0F, 90.0F };

	/**
	 * The default direction if no other variables apply.
	 */
	public static final ForgeDirection DEFAULT_FRONT = ForgeDirection.SOUTH;

	/**
	 * Get rotation angle, in degrees, for a direction.
	 * @param dir Direction to convert to angle.
	 * @return Rotation angle of the direction.
	 */
	public static float getAngle(ForgeDirection dir) { return getAngle(dir.ordinal()); }

	/**
	 * Get rotation angle, in degrees, for a direction ordinal.
	 * @param dir Direction ordinal to convert to angle.
	 * @return Rotation angle of the direction ordinal.
	 */
	public static float getAngle(int dir) { return (dir < 0 || dir >= _angles.length ? 0.0F : _angles[dir]); }

	/**
	 * Reverse a direction.
	 * @param dir Direction to reverse.
	 * @return Reversed, ie opposite, direction.
	 */
	public static ForgeDirection reverse(final ForgeDirection dir) { return dir.getOpposite(); }

	/**
	 * Reverse a direction ordinal.
	 * @param dir Direction ordinal to reverse.
	 * @return Reversed, ie opposite, direction ordinal.
	 */
	public static ForgeDirection reverse(final int dir) { return ForgeDirection.getOrientation(dir).getOpposite(); }

	/**
	 * Rotate a direction to the left, ie counter clockwise.
	 * @param dir Direction to rotate.
	 * @return Left, ie counter clockwise, rotated direction.
	 */
	public static ForgeDirection left(final ForgeDirection dir) { return dir.getRotation(ForgeDirection.DOWN); }

	/**
	 * Rotate a direction ordinal to the left, ie counter clockwise.
	 * @param dir Direction ordinal to rotate.
	 * @return Left, ie counter clockwise, rotated direction ordinal.
	 */
	public static ForgeDirection left(final int dir) { return ForgeDirection.getOrientation(dir).getRotation(ForgeDirection.DOWN); }

	/**
	 * Rotate a direction to the right, ie clockwise.
	 * @param dir Direction to rotate.
	 * @return Right, ie clockwise, rotated direction.
	 */
	public static ForgeDirection right(final ForgeDirection dir) { return dir.getRotation(ForgeDirection.UP); }

	/**
	 * Rotate a direction ordinal to the right, ie clockwise.
	 * @param dir Direction ordinal to rotate.
	 * @return Right, ie clockwise, rotated direction ordinal.
	 */
	public static ForgeDirection right(final int dir) { return ForgeDirection.getOrientation(dir).getRotation(ForgeDirection.UP); }

	/**
	 * Returns the first side that isn't occupied by an opaque block.
	 */
	public static int getDefaultSide(World world, int x, int y, int z)
	{
		int direction = DirUtil.DEFAULT_FRONT.ordinal();
		if (!world.isRemote)
		{
			Block negZ = world.getBlock(x, y, z - 1);
			Block posZ = world.getBlock(x, y, z + 1);
			Block negX = world.getBlock(x - 1, y, z);
			Block posX = world.getBlock(x + 1, y, z);

			if (negZ.isOpaqueCube() && !posZ.isOpaqueCube())
			{ direction = ForgeDirection.SOUTH.ordinal(); }
			if (posZ.isOpaqueCube() && !negZ.isOpaqueCube())
			{ direction = ForgeDirection.NORTH.ordinal(); }
			if (negX.isOpaqueCube() && !posX.isOpaqueCube())
			{ direction = ForgeDirection.EAST.ordinal(); }
			if (posX.isOpaqueCube() && !negX.isOpaqueCube())
			{ direction = ForgeDirection.WEST.ordinal(); }
		}
		return direction;
	}

	/**
	 * Returns the side that the entity is facing.
	 */
	public static int getDefaultSide(EntityLivingBase living)
	{
		int side = MathHelper.floor_double((living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (side == 0) { return ForgeDirection.NORTH.ordinal(); }
		else if (side == 1) { return ForgeDirection.EAST.ordinal(); }
		else if (side == 2) { return ForgeDirection.SOUTH.ordinal(); }
		else if (side == 3) { return ForgeDirection.WEST.ordinal(); }
		return DirUtil.DEFAULT_FRONT.ordinal();
	}
}

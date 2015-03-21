package jeckelcorelibrary.core;

public class BlockPosition
{
	public BlockPosition(final BlockPosition pos)
	{
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
	}

	public BlockPosition(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final int x;

	public final int y;

	public final int z;

	public BlockPosition copy() { return new BlockPosition(this); }

	public boolean equals(BlockPosition pos) { return this.x == pos.x && this.y == pos.y && this.z == pos.z; }
}

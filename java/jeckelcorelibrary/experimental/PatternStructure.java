package jeckelcorelibrary.experimental;

/**
 * @deprecated Experimental! Don't use this class.
 */
@Deprecated
public class PatternStructure implements IPatternStructure
{
	public PatternStructure(int sizeX, int sizeY, int sizeZ)
	{
		this._width = sizeX;
		this._height = sizeY;
		this._length = sizeZ;

		this._pattern = new boolean[this._width][this._height][this._length];
		for (int x = 0; x < this.getWidth(); x++)
		{
			for (int y = 0; y < this.getHeight(); y++)
			{
				for (int z = 0; z < this.getLength(); z++)
				{
					this.setValue(x, y, z, true);
				}
			}
		}
	}

	@Override public int getMinX(int coord) { return coord - (this.getWidth() / 2); }
	@Override public int getMaxX(int coord) { return coord + (this.getWidth() / 2) + 1; }
	@Override public int getWidth() { return this._width; }
	private int _width;

	@Override public int getMinY(int coord) { return coord; }
	@Override public int getMaxY(int coord) { return coord + this.getHeight(); }
	@Override public int getHeight() { return this._height; }
	private int _height;

	@Override public int getMinZ(int coord) { return coord - (this.getLength() / 2); }
	@Override public int getMaxZ(int coord) { return coord + (this.getLength() / 2) + 1; }
	@Override public int getLength() { return this._length; }
	private int _length;

	@Override public boolean getValue(int x, int y, int z) { return this._pattern[x][y][z]; }
	@Override public IPatternStructure setValue(int x, int y, int z, boolean value) { this._pattern[x][y][z] = value; return this; }
	private boolean[][][] _pattern;

	public void print()
	{
		System.out.println("Width:" + this.getWidth());
		System.out.println("Height:" + this.getHeight());
		System.out.println("Length:" + this.getLength());
		System.out.println();

		for (int offsetY = 0; offsetY < this.getHeight(); offsetY++)
		{
			System.out.println("Level: " + offsetY);
			for (int offsetZ = 0; offsetZ < this.getLength(); offsetZ++)
			{
				System.out.print("R" + offsetZ + ": ");
				for (int offsetX = 0; offsetX < this.getWidth(); offsetX++)
				{
					if (offsetX > 0) { System.out.print(","); }
					System.out.print(this.getValue(offsetX, offsetY, offsetZ));
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}

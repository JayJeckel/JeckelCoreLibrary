package jeckelcorelibrary.experimental;

/**
 * @deprecated Experimental! Don't use this interface.
 */
@Deprecated
public interface IPatternStructure
{
	public int getMinX(int coord);
	public int getMaxX(int coord);
	public int getWidth();

	public int getMinY(int coord);
	public int getMaxY(int coord);
	public int getHeight();

	public int getMinZ(int coord);
	public int getMaxZ(int coord);
	public int getLength();

	public boolean getValue(int x, int y, int z);
	public IPatternStructure setValue(int x, int y, int z, boolean value);

	public void print();
}

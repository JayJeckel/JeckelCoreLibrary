package jeckelcorelibrary.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Static helper class to centralize various useful methods
 * that do not fit in any other util.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class CoreUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private CoreUtil() { }

	/**
	 * Return sorted list of items in the given collection.
	 * @param c Collection of items to sort.
	 * @return New list containing items sorted.
	 */
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c)
	{
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}
}

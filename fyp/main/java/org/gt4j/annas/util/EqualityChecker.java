/*
 * annas : Graph implementation and algorithm package
 * 
 * Project Info:  http://annas.googlecode.com
 * Project Creator:  Sam Wilson
 * 
 * Copyright (C) 2008-2014  Sam Wilson
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gt4j.annas.util;

/**
 * This class provides an equivalent method to the equals method. For use with
 * {@link org.gt4j.annas.util.ArraySet}
 * 
 * @author Sam
 * 
 * @param <T> Type to objects to compare for equality
 */
public interface EqualityChecker<T> {

	/**
	 * Checks if the objects are equal
	 * 
	 * @param a first object
	 * @param b second object
	 * @return true if the objects are equal
	 */
	public boolean check(Object a, Object b);
}

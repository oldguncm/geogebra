/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

*/

package geogebra.main;

import geogebra.kernel.geos.GeoElement;

public interface GeoElementSelectionListener {
	
	public void geoElementSelected(GeoElement geo, boolean addToSelection);
	
}

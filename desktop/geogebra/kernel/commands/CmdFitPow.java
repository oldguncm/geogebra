package geogebra.kernel.commands;

import geogebra.kernel.Kernel;
import geogebra.kernel.geos.GeoElement;
import geogebra.kernel.geos.GeoList;

/** 
 * FitPow[<List of points>]
 * 
 * @author Hans-Petter Ulven
 * @version 07.04.08
 */
class CmdFitPow extends CmdOneListFunction{

    public CmdFitPow(Kernel kernel) {super(kernel);}
    
	final protected GeoElement doCommand(String a, GeoList b)
	{
		return kernel.FitPow(a, b);
	}

}// class CmdFitPow

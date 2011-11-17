package geogebra3D.kernel3D.commands;

import geogebra.kernel.Kernel;
import geogebra.kernel.arithmetic.Command;
import geogebra.kernel.commands.CmdOrthogonalLine;
import geogebra.kernel.commands.CmdOrthogonalVector;
import geogebra.kernel.commands.CmdUnitOrthogonalVector;
import geogebra.kernel.geos.GeoElement;
import geogebra.kernel.geos.GeoLine;
import geogebra.kernel.kernelND.GeoCoordSys2D;
import geogebra.kernel.kernelND.GeoLineND;
import geogebra.kernel.kernelND.GeoPointND;
import geogebra.main.MyError;
import geogebra3D.kernel3D.GeoPlane3D;

/**
 * UnitOrthogonalVector[ <GeoPlane3D> ] 
 */
public class CmdUnitOrthogonalVector3D extends CmdUnitOrthogonalVector {
	
	
	
	public CmdUnitOrthogonalVector3D(Kernel kernel) {
		super(kernel);
	}
	
	

	public GeoElement[] process(Command c) throws MyError {
	    int n = c.getArgumentNumber();
	    boolean[] ok = new boolean[n];
	    GeoElement[] arg;

	    switch (n) {
	    case 1 :
	    	arg = resArgs(c);
			if (ok[0] = (arg[0] instanceof GeoCoordSys2D)) {
				GeoElement[] ret =
				{
						(GeoElement) kernel.getManager3D().UnitOrthogonalVector3D(
								c.getLabel(),
								(GeoCoordSys2D) arg[0])};
				return ret;
			} 
	    	
	    }
	    

	    return super.process(c);
	}
	
}

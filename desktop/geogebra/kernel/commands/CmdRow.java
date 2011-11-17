package geogebra.kernel.commands;

import geogebra.kernel.Kernel;
import geogebra.kernel.arithmetic.Command;
import geogebra.kernel.geos.GeoElement;
import geogebra.main.MyError;

/*
 * Row[ <GeoElement> ]
 */
public class CmdRow extends CommandProcessor {

	public CmdRow(Kernel kernel) {
		super(kernel);
	}

	public GeoElement[] process(Command c) throws MyError {
		int n = c.getArgumentNumber();
		GeoElement[] arg;

		switch (n) {
		case 1:
			// Name[ <GeoElement> ]
			arg = resArgs(c);			
			if (arg[0].getLabel() != null && GeoElement.isSpreadsheetLabel(arg[0].getLabel())) {
				GeoElement[] ret = { kernel.Row(c.getLabel(),
						arg[0]) };
				return ret;
			}
			else
			{
				throw argErr(app, c.getName(), arg[0]);
			}


		default:
			throw argNumErr(app, c.getName(), n);
		}
	}

}

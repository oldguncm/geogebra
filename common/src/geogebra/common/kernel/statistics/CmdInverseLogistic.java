package geogebra.common.kernel.statistics;

import geogebra.common.kernel.Kernel;
import geogebra.common.kernel.arithmetic.Command;
import geogebra.common.kernel.commands.CommandProcessor;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.kernel.geos.GeoNumberValue;
import geogebra.common.main.MyError;

/**
 * InvarseNormal[ <Number>, <Number>,<Number> ]
 * 
 * adapted from CmdMax by Michael Borcherds 2008-01-20
 */
public class CmdInverseLogistic extends CommandProcessor {

	/**
	 * Create new command processor
	 * 
	 * @param kernel
	 *            kernel
	 */
	public CmdInverseLogistic(Kernel kernel) {
		super(kernel);
	}

	@Override
	public GeoElement[] process(Command c) throws MyError {
		int n = c.getArgumentNumber();
		boolean[] ok = new boolean[n];
		GeoElement[] arg;

		switch (n) {
		case 3:			
			arg = resArgs(c);
			if ((ok[0] = arg[0] instanceof GeoNumberValue) &&
				(ok[1] = arg[1] instanceof GeoNumberValue) &&
				(ok[2] = arg[2] instanceof GeoNumberValue)) 
			{

				AlgoInverseLogistic algo = new AlgoInverseLogistic(cons, c.getLabel(),
						(GeoNumberValue) arg[0], (GeoNumberValue) arg[1], (GeoNumberValue) arg[2]);

				GeoElement[] ret = {algo.getResult() };
				return ret;
				
			}
			throw argErr(app, c.getName(),getBadArg(ok,arg));

		default:
			throw argNumErr(app, c.getName(), n);
		}
	}

}

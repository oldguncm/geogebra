package geogebra.common.kernel.statistics;

import geogebra.common.kernel.Kernel;
import geogebra.common.kernel.arithmetic.Command;
import geogebra.common.kernel.commands.CommandProcessor;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.kernel.geos.GeoNumberValue;
import geogebra.common.main.MyError;

/**
 *InversePoisson
 */
public class CmdInversePoisson extends CommandProcessor {

	/**
	 * Create new command processor
	 * 
	 * @param kernel
	 *            kernel
	 */
	public CmdInversePoisson(Kernel kernel) {
		super(kernel);
	}

	@Override
	public GeoElement[] process(Command c) throws MyError {
		int n = c.getArgumentNumber();
		boolean[] ok = new boolean[n];
		GeoElement[] arg;

		switch (n) {
		case 2:
			arg = resArgs(c);
			if ((ok[0] = arg[0] instanceof GeoNumberValue)
					&& (ok[1] = arg[1] instanceof GeoNumberValue)) {
				
				AlgoInversePoisson algo = new AlgoInversePoisson(cons, c.getLabel(),
						(GeoNumberValue) arg[0], (GeoNumberValue) arg[1]);

				GeoElement[] ret = { algo.getResult() };
				return ret;

			} else if (!ok[0])
				throw argErr(app, c.getName(), arg[0]);
			else
				throw argErr(app, c.getName(), arg[1]);

		default:
			throw argNumErr(app, c.getName(), n);
		}
	}

}

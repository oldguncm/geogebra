package org.geogebra.commands;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.geogebra.common.kernel.commands.AlgebraProcessor;
import org.geogebra.common.kernel.parser.ParseException;
import org.geogebra.common.util.debug.Log;
import org.geogebra.desktop.main.AppDNoGui;
import org.geogebra.desktop.main.LocalizationD;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
	static AppDNoGui app;
	static AlgebraProcessor ap;

	@BeforeClass
	public static void setupCas() {
		app = new AppDNoGui(new LocalizationD(3), true);
		app.setLanguage(Locale.US);
	}
	
	@Test
	public void testBrackets(){
		try {
			
			long l = System.currentTimeMillis();
			app.getKernel().getParser().
parseGeoGebraExpression(
							"{{{{{{{{{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}}");
			app.getKernel().getParser().
			parseGeoGebraExpression("(((((((((((((((((((((((1)))))))))))))))))))))))");
			app.getKernel().getParser().
			parseGeoGebraExpression("If[x>1,If[x>2,If[x>3,If[x>4,If[x>1,If[x>2,If[x>3,If[x>4,If[x>1,If[x>2,If[x>3,If[x>4,If[x>1,If[x>2,If[x>3,If[x>4,If[x>1,If[x>2,If[x>3,If[x>4,If[x>1,If[x>2,If[x>3,If[x>4,42" +
					"]]]]]]]]]]]]]]]]]]]]]]]]");
			l = System.currentTimeMillis() -l;
			Log.debug("TIME" + l);
			assertTrue("Too long:" + l, l < 400);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  
	 */
	@Test
	public void testInvalid() {
		long l = System.currentTimeMillis();
		try {


			app.getKernel().getParser().parseGeoGebraExpression(
					"x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/(x/()))))))))))))))))))))");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		l = System.currentTimeMillis() - l;
		Log.debug("TIME" + l);
		assertTrue("Too long:" + l, l < 4000);
	}
	
	/**
	 * Test for || brackets
	 */
	@Test
	public void testAbsValue(){

			try {
				app.getKernel().getParser().
				parseGeoGebraExpression("|1|");
				app.getKernel().getParser().
				parseGeoGebraExpression("(1|1|1)");
				app.getKernel().getParser().
				parseGeoGebraExpression("(a||b)");
			} catch (ParseException e) {
				assertNull(e);
			}
			try {
				app.getKernel().getParser().
				parseGeoGebraExpression("|1|2|3|");
			} catch (ParseException e) {
				assertNotNull(e);
			}
			

	}
}
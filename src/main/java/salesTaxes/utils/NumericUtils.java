package salesTaxes.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumericUtils
{
	private static final DecimalFormat percFormat = new DecimalFormat( "0.00%" );
	
	public static double parsePerc(final String s) throws ParseException
	{
		return percFormat.parse( s ).doubleValue();
	}
	
	public static double roundTo05( double d )
	{
		return Math.round( d * 20.0 ) / 20.0;
	}
}

package salesTaxes.models;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import salesTaxes.utils.NumericUtils;

public class Market
{
	private double				taxRate			= 0;
	private double				importDutyRate	= 0;
	private HashSet<String>		noTaxGoods		= new HashSet<String>();

	private static final String	IMPORTED_PREFIX	= "IMPORTED ";
	private static final String	VALUE_PREFIX	= "at ";

	/**
	 * Buil a Market object with configured tax and duty rates and "no basic sales tax" goods list
	 * @param confIs Input stream for the configuration properties file
	 * @throws IOException
	 * @throws ParseException
	 */
	public Market( InputStream confIs ) throws IOException, ParseException
	{
		Properties conf = new Properties();
		conf.load( confIs );
		taxRate = NumericUtils.parsePerc( conf.getProperty( "SALES_TAX_RATE" ) );
		importDutyRate = NumericUtils.parsePerc( conf.getProperty( "IMPORT_DUTY_RATE" ) );
		String[] exceptionGoods = conf.getProperty( "NO_TAX_GOODS" ).split( "," );

		for( String goodType: exceptionGoods )
		{
			InputStream goodsIs = this.getClass().getClassLoader().getResourceAsStream( goodType + ".txt" );
			LineIterator li = IOUtils.lineIterator( goodsIs, "UTF-8" );
			while( li.hasNext() )
			{
				noTaxGoods.add( li.nextLine().trim().toUpperCase() );
			}
			goodsIs.close();
		}
	}

	/**
	 * Fill the provided ShoppingBasket with the items read from the stream 
	 * @param basket The receipt to fill in
	 * @param is The stream providing the items
	 * @throws IOException
	 */
	public void fill( ShoppingBasket basket, InputStream is ) throws IOException
	{
		LineIterator li = IOUtils.lineIterator( is, "UTF-8" );
		while( li.hasNext() )
		{
			basket.add( parseLine( li.nextLine().trim() ) );
		}
	}

	/**
	 * Parse an input line to Items instance.<br/>
	 * <b>Format:</b><br/>
	 * quantity [imported ]good type at price<br/>
	 * <b>eg:</b><br/>
	 * 1 imported book at 12.50<br/>
	 * 5 apple at 3.25<br/>
	 * @param line The input line
	 * @return
	 */
	public Items parseLine( String line )
	{
		Items items = new Items();

		items.setQuantity( Integer.parseInt( line.substring( 0, line.indexOf( " " ) ) ) );

		String description = line.substring( line.indexOf( " " ), line.lastIndexOf( VALUE_PREFIX ) ).trim();
		
		boolean imported = description.toUpperCase().indexOf( IMPORTED_PREFIX ) > -1;
		items.setImported( imported );
		items.setImportDutyRate( imported ? importDutyRate : 0 );
		if( imported )
		{
			description = description.replaceFirst( "(?i)" + IMPORTED_PREFIX, "" ).replaceAll( "( )+", " " );
		}
		items.setDescription( description );
		items.setTaxRate( noTaxGoods.contains( description.toUpperCase() ) ? 0 : taxRate );
		items.setValue( Double.parseDouble( line.substring( line.lastIndexOf( VALUE_PREFIX ) + VALUE_PREFIX.length() ) ) );

		return items;
	}

}

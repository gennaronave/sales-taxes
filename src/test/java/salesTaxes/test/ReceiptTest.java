package salesTaxes.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import salesTaxes.models.Market;
import salesTaxes.models.ShoppingBasket;

public class ReceiptTest
{
	@Test
	public void fillAndPrintCart() throws Exception
	{
		InputStream is = null;
		
		is = this.getClass().getClassLoader().getResourceAsStream("app-config.properties");
		Market market = new Market( is );
		is.close();

		test( market, 1 );
		test( market, 2 );
		test( market, 3 );
	}
	
	private void test( Market market, int index ) throws IOException
	{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream( String.format( "input%d.txt", index ) );
		ShoppingBasket basket = market.fillShoppingBasket( is );
		is.close();
		System.out.println( "Output 3" );
		System.out.println( basket );
		assertEquals( String.format( "Failed \"input%d.txt\" test", index ), 
				FileUtils.readFileToString( new File( this.getClass().getClassLoader().getResource( String.format( "output%d.txt", index ) ).getFile() ) ),
				basket.receipt() );
	}
}

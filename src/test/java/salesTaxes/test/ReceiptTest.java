package salesTaxes.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
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
		ShoppingBasket basket = null;
		
		is = this.getClass().getClassLoader().getResourceAsStream("app-config.properties");
		Market market = new Market( is );
		is.close();

		
		basket = new ShoppingBasket();
		is = this.getClass().getClassLoader().getResourceAsStream( "input1.txt" );
		market.fill( basket, is );
		is.close();
		System.out.println( "Output 1" );
		System.out.println( basket );
		assertEquals( "Failed \"input1.txt\" test", 
				FileUtils.readFileToString( new File( this.getClass().getClassLoader().getResource("output1.txt").getFile() ) ),
				basket.getReceipt() );
		

		basket = new ShoppingBasket();
		is = this.getClass().getClassLoader().getResourceAsStream( "input2.txt" );
		market.fill( basket, is );
		is.close();
		System.out.println( "Output 2" );
		System.out.println( basket );
		assertEquals( "Failed \"input2.txt\" test", 
				FileUtils.readFileToString( new File( this.getClass().getClassLoader().getResource("output2.txt").getFile() ) ),
				basket.getReceipt() );

		basket = new ShoppingBasket();
		is = this.getClass().getClassLoader().getResourceAsStream( "input3.txt" );
		market.fill( basket, is );
		is.close();
		System.out.println( "Output 3" );
		System.out.println( basket );
		assertEquals( "Failed \"input3.txt\" test", 
				FileUtils.readFileToString( new File( this.getClass().getClassLoader().getResource("output3.txt").getFile() ) ),
				basket.getReceipt() );
	}
}

package salesTaxes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingBasket implements Serializable
{
	private static final long	serialVersionUID	= 7196970699394305820L;

	List<Items> itemsList = new ArrayList<Items>();
	
	public void add( Items items )
	{
		itemsList.add( items );
	}
	
	public double getTaxes()
	{
		double taxes = 0;
		for( Items items: itemsList )
		{
			taxes += items.getTaxValue();
		}
		
		return taxes;
	}
	
	public double getTaxedTotal()
	{
		return getTotal() + getTaxes();
	}
	
	public double getTotal()
	{
		double tot = 0;
		for( Items items: itemsList )
		{
			tot += items.getValue();
		}
		
		return tot;
	}

	/**
	 * @return The receipt for all the items in the basket
	 */
	public String receipt()
	{
		StringBuffer sb = new StringBuffer();
		
		for( Items items: itemsList )
		{
			sb.append( items );
			sb.append( "\r\n" );
		}
		
		sb.append( String.format( Locale.US, "Sales Taxes: %.2f\r\n", getTaxes() ) );
		sb.append( String.format( Locale.US, "Total: %.2f\r\n", getTaxedTotal() ) );
		
		return sb.toString();
	}
	
	@Override
	public String toString()
	{
		return receipt();
	}
}

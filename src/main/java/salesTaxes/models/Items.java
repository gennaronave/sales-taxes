package salesTaxes.models;

import java.io.Serializable;
import java.util.Locale;

import salesTaxes.utils.NumericUtils;

public class Items implements Serializable
{
	private static final long	serialVersionUID	= -6076985408589798113L;

	private int		quantity		= 0;
	private String	description		= null;
	private boolean	imported		= false;
	private double	importDutyRate	= 0;
	private double	taxRate			= 0;
	private double	value			= 0;
	
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity( int quantity )
	{
		this.quantity = quantity;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription( String description )
	{
		this.description = description;
	}
	public boolean isImported()
	{
		return imported;
	}
	public void setImported( boolean imported )
	{
		this.imported = imported;
	}
	public double getImportDutyRate()
	{
		return importDutyRate;
	}
	public void setImportDutyRate( double importDutyRate )
	{
		this.importDutyRate = importDutyRate;
	}
	public double getTaxRate()
	{
		return taxRate;
	}
	public void setTaxRate( double taxRate )
	{
		this.taxRate = taxRate;
	}
	public double getValue()
	{
		return value;
	}
	public void setValue( double value )
	{
		this.value = value;
	}
	
	public double getTaxValue()
	{
		return NumericUtils.roundTo05( value * ( taxRate + (isImported() ? importDutyRate : 0) ) );
	}
	
	public double getTaxedValue()
	{
		return getValue() + getTaxValue();
	}
	
	@Override
	public String toString()
	{
		return String.format( Locale.US, "%d %s%s: %.2f", getQuantity(), isImported() ? "imported " : "",getDescription(), getTaxedValue() );
	}

}

package net.charliemeyer.jbioinfo.algorithms.kmeansclustering;

import java.util.ArrayList;

import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

public class KMeansPoint {
	
	private ArrayList<Double> coordinates;
	private int index;
	
	public KMeansPoint(int index)
	{
		coordinates = new ArrayList<Double>();
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void fillZeros(int dimension)
	{
		coordinates.clear();
		for(int i = 0; i < dimension; i++)
		{
			coordinates.add(0.0d);
		}
	}
	
	public void addCoordinate(double value)
	{
		coordinates.add(value);
	}
	
	public int getDimension()
	{
		return coordinates.size();
	}
	
	public double getCoordinate(int dimension)
	{
		return coordinates.get(dimension);
	}
	
	public void setCoordinate(double value, int dimension)
	{
		coordinates.set(dimension, value);
	}
	
	public double distanceFrom(KMeansPoint other) throws JBioinfoException
	{
		double accum = 0;
		if(getDimension() != other.getDimension())
		{
			throw new JBioinfoException("Point dimensions do not match");
		}
		for(int i = 0; i < getDimension(); i++)
		{
			accum = accum + Math.abs(getCoordinate(i)-other.getCoordinate(i));
		}
		return Math.sqrt(accum);
	}
	
	public String toString()
	{
		String retval = "KMeansPoint: (";
		for(Double d : coordinates)
		{
			retval += d+", ";
		}
		retval = retval.substring(0, retval.length()-2);
		retval += ")";
		return retval;
	}
	
	public boolean equals(KMeansPoint other) throws JBioinfoException
	{
		if(getDimension() != other.getDimension())
		{
			throw new JBioinfoException("Point dimensions do not match");
		}
		
		for(int i = 0; i < getDimension(); i++)
		{
			if(getCoordinate(i) != other.getCoordinate(i))
			{
				return false;
			}
		}
		return true;
	}
}

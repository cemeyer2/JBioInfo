package net.charliemeyer.jbioinfo.algorithms.kmeansclustering;

import java.util.ArrayList;

import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

import org.apache.commons.math.stat.descriptive.moment.Mean;

public class KMeansCluster 
{
	ArrayList<KMeansPoint> points;
	KMeansPoint center;

	public KMeansCluster(KMeansPoint center)
	{
		points = new ArrayList<KMeansPoint>();
		this.center = center;
	}

	public KMeansPoint getCenter()
	{
		return center;
	}

	public int getMagnitude()
	{
		return points.size();
	}

	public void clear()
	{
		points.clear();
	}

	public KMeansPoint getPoint(int index)
	{
		return points.get(index);
	}

	public void addPoint(KMeansPoint point)
	{
		points.add(point);
	}

	public String toString()
	{
		String retval = "Cluster: center: "+center+" points: ";
		for(KMeansPoint p : points)
		{
			retval += p.getIndex()+", ";
		}
		return retval;
	}

	protected void recomputeCenter()
	{
		for(int i = 0; i < center.getDimension(); i++)
		{
			if(points.size() > 0)
			{
				Mean mean = new Mean();
				for(KMeansPoint point : points)
				{
					mean.increment(point.getCoordinate(i));
				}
				center.setCoordinate(mean.getResult(), i);
			}
		}
	}
	
	public boolean containsPoint(KMeansPoint pt) throws JBioinfoException
	{
		for(KMeansPoint point : points)
		{
			if(point.equals(pt))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean equals(KMeansCluster other) throws JBioinfoException
	{
		boolean allAgree = true;
		
		for(KMeansPoint pt : points)
		{
			if(other.containsPoint(pt) == false)
			{
				allAgree = false;
			}
		}
		return allAgree;
	}
	
	public final ArrayList<KMeansPoint> getPoints()
	{
		return points;
	}
}

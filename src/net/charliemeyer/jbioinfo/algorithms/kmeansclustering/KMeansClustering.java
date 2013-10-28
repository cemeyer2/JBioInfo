package net.charliemeyer.jbioinfo.algorithms.kmeansclustering;

import java.util.ArrayList;

import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

public class KMeansClustering 
{
	private ArrayList<KMeansPoint> points, clusterCenters;

	public KMeansClustering(ArrayList<KMeansPoint> points, ArrayList<KMeansPoint> clusterCenters)
	{
		this.points = points;
		this.clusterCenters = clusterCenters;
	}

	public ArrayList<KMeansCluster> cluster() throws JBioinfoException
	{
		ArrayList<KMeansCluster> clusters = new ArrayList<KMeansCluster>();
		
		//init the clusters
		for(KMeansPoint point : clusterCenters)
		{
			clusters.add(new KMeansCluster(point));
		}
		
		while(true)
		{
			ArrayList<KMeansPoint> lastIteration = new ArrayList<KMeansPoint>();
			for(KMeansCluster cluster : clusters)
			{
				KMeansPoint p = new KMeansPoint(-1);
				for(int i = 0; i < cluster.getCenter().getDimension(); i++)
				{
					p.addCoordinate(cluster.getCenter().getCoordinate(i));
				}
				lastIteration.add(p);
			}

			
			for(KMeansPoint point : points)
			{
				int closestClusterIndex = -1;
				double minDistance = Double.MAX_VALUE;
				for(int i = 0; i < clusters.size(); i++)
				{
					double distance = point.distanceFrom(clusters.get(i).getCenter());
					if(distance < minDistance)
					{
						minDistance = distance;
						closestClusterIndex = i;
					}
				}
				clusters.get(closestClusterIndex).addPoint(point);
			}
						
			for(KMeansCluster cluster : clusters)
			{
				cluster.recomputeCenter();
			}		
			
			if(!centersChanging(clusters, lastIteration))
			{
				break;
			}
			for(KMeansCluster c : clusters)
			{
				c.clear();
			}
		}

		return clusters;
	}

	private boolean centersChanging(ArrayList<KMeansCluster> clusters, ArrayList<KMeansPoint> lastIter)
	{
		try
		{
			for(int i = 0; i < clusters.size(); i++)
			{
				if(!clusters.get(i).getCenter().equals(lastIter.get(i)))
				{
					return true;
				}
			}
		}
		catch(JBioinfoException ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		return false;
	}
}

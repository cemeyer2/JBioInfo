package net.charliemeyer.cs466.assignment3;

import java.util.ArrayList;

import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansCluster;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansClustering;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansPoint;
import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

public class Question3 {
	public static void main(String[] args) throws JBioinfoException
	{
		for(int i = 1; i <= 20; i++)
		{
			double d = 0.05*i;
			int iters = 10;
			double[] rands = new double[iters];
			for(int j = 0; j < iters; j++)
			{
				ArrayList<KMeansPoint> centers = generateCenters(20,4);
				ArrayList<KMeansCluster> givenClusters = getInitialClusters(20, 4, d, centers);
				
				ArrayList<KMeansPoint> allPoints = new ArrayList<KMeansPoint>();
				for(KMeansCluster cl : givenClusters)
				{
					allPoints.addAll(cl.getPoints());
				}
				
				KMeansClustering clustering = new KMeansClustering(allPoints, centers);
				
				ArrayList<KMeansCluster> clustered = clustering.cluster();
				
				rands[j] = randIndex(givenClusters, clustered);
				System.out.println("rand="+rands[j]);
			}
			System.out.println("d="+d+"\t\t\t\tmean="+new Mean().evaluate(rands)+"\t\t\t\tstddev="+new StandardDeviation().evaluate(rands));
		}
	}
	
	public static ArrayList<KMeansPoint> generateCenters(int n, int k)
	{
		//generate clusters
		ArrayList<KMeansPoint> centers = new ArrayList<KMeansPoint>();
		for(int i = 0; i < k; i++)
		{
			KMeansPoint center = new KMeansPoint(-1);
			center.fillZeros(k);
			center.setCoordinate(1, i);
			centers.add(center);
		}
		return centers;
	}
	
	public static ArrayList<KMeansCluster> getInitialClusters(int n, int k, double d, ArrayList<KMeansPoint> centers)
	{
		//sample points
		int pid = 1;
		ArrayList<KMeansPoint> points = new ArrayList<KMeansPoint>();
		ArrayList<KMeansCluster> clusters = new ArrayList<KMeansCluster>();
		for(KMeansPoint c : centers)
		{
			KMeansCluster cl = new KMeansCluster(c);
			for(int i = 0; i < n/k; i++)
			{
				KMeansPoint p = new KMeansPoint(pid);
				p.fillZeros(k);
				for(int dim = 0; dim < p.getDimension(); dim++)
				{
					double val = c.getCoordinate(dim)+Question2.getRandomValue(d);
					p.setCoordinate(val,dim);
				}
				points.add(p);
				cl.addPoint(p);
				pid++;
			}
			clusters.add(cl);
		}
		return clusters;
	}
	
	private static double choose(double n, double k)
	{
		return factorial(n) / (factorial(k)*factorial(n-k));
	}
	
	private static double factorial(double val)
	{
		if(val <= 1)
		{
			return 1;
		}
		else
		{
			return factorial(val-1)*val;
		}
	}
	
	private static int inSameCluster(KMeansPoint pt1, KMeansPoint pt2, ArrayList<KMeansCluster> clusters) throws JBioinfoException
	{
		for(int i = 0; i < clusters.size(); i++)
		{
			KMeansCluster cl = clusters.get(i);
			if(cl.containsPoint(pt1) && cl.containsPoint(pt2))
			{
				return i;
			}
		}
		return -1;
	}
	
	private static double randIndex(ArrayList<KMeansCluster> given, ArrayList<KMeansCluster> clustered) throws JBioinfoException
	{
		double agreements = 0;
		double disagreements = 0;
		ArrayList<KMeansPoint> allPts = new ArrayList<KMeansPoint>();
		for(int i = 0; i < given.size(); i++)
		{
			KMeansCluster givenCl = given.get(i);
			allPts.addAll(givenCl.getPoints());
		}
		
		for(int i = 0; i < allPts.size(); i++)
		{
			KMeansPoint pt1 = allPts.get(i);
			for(int j = i+1; j < allPts.size(); j++)
			{
				KMeansPoint pt2 = allPts.get(j);
				
				int samegiven = inSameCluster(pt1, pt2, given);
				int samecluster = inSameCluster(pt1, pt2, clustered);
				if(samegiven >= 0 && samecluster >= 0)
				{
					agreements++;
				}
				if(samegiven == -1 && samecluster == -1)
				{
					disagreements++;
				}
			}
		}
		return (agreements+disagreements)/choose(allPts.size(),2);
	}
}

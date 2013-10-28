package net.charliemeyer.cs466.assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansCluster;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansClustering;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansPoint;
import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

import org.apache.commons.math.stat.descriptive.moment.Mean;

public class Question2 {
	public static void main(String[] args) throws JBioinfoException, IOException
	{
		int n = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		String ptsfile = args[2];
		String clstrfile = args[3];
		
		//generate clusters
		ArrayList<KMeansPoint> centers = new ArrayList<KMeansPoint>();
		for(int i = 0; i < k; i++)
		{
			KMeansPoint center = new KMeansPoint(-1);
			center.fillZeros(k);
			center.setCoordinate(1, i);
			centers.add(center);
		}
		//sample points
		int pid = 1;
		double d = 0.1;
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
					double val = c.getCoordinate(dim)+getRandomValue(d);
					p.setCoordinate(val,dim);
				}
				points.add(p);
				cl.addPoint(p);
				pid++;
			}
			clusters.add(cl);
		}
		
		
		FileWriter out = new FileWriter(new File(ptsfile));
		for(KMeansPoint p : points)
		{
			out.write(p.getIndex()+"\t");
			for(int i = 0; i < p.getDimension(); i++)
				out.write(p.getCoordinate(i)+"\t");
			out.write("\n");
		}
		out.flush();
		out.close();
		
		out = new FileWriter(new File(clstrfile));
		
		int i = 1;
		for(KMeansCluster cluster : clusters)
		{
			out.write(i+"\t");
			for(int p = 0; p < cluster.getMagnitude(); p++)
				out.write(cluster.getPoint(p).getIndex()+"\t");
			out.write("\n");
			i++;
		}
		out.flush();
		out.close();
	}
	
	public static double getRandomValue(double d)
	{
		Mean m = new Mean();
		for(int i = 0; i < 100; i++)
		{
			m.increment(Math.random());
		}
		double mean = m.getResult();
		mean -= .5;
		return mean*Math.pow((1200*d),0.5);
	}
}

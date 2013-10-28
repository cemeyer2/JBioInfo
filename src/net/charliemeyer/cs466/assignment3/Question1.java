package net.charliemeyer.cs466.assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansCluster;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansClustering;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansPoint;
import net.charliemeyer.jbioinfo.algorithms.kmeansclustering.KMeansPointReader;
import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

public class Question1 
{
	public static void main(String[] args) throws IOException, JBioinfoException
	{
		String infile = args[0];
		int k = Integer.parseInt(args[1]);
		int m = Integer.parseInt(args[2]);
		int n = Integer.parseInt(args[3]);
		String outfile = args[4];
		ArrayList<KMeansPoint> points = KMeansPointReader.read(infile);
		
		
		
		double max = Double.MIN_VALUE;
		
		for(KMeansPoint p : points)
		{
			for(int i = 0; i < p.getDimension(); i++)
			{
				if(p.getCoordinate(i) > max)
				{
					max = p.getCoordinate(i);
				}
			}
		}
		//compute cluster centers
		ArrayList<KMeansPoint> clusterCenters = new ArrayList<KMeansPoint>();
		for(int i = 0; i < k; i++)
		{
			KMeansPoint p = new KMeansPoint(0);
			p.fillZeros(points.get(0).getDimension());
			for(int j = 0; j < p.getDimension(); j++)
			{
				p.setCoordinate(Math.random()*max, j);
			}
			clusterCenters.add(p);
		}
		
		KMeansClustering clustering = new KMeansClustering(points, clusterCenters);
		ArrayList<KMeansCluster> clusters = clustering.cluster();
		
		FileWriter out = new FileWriter(new File(outfile));
		
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
}

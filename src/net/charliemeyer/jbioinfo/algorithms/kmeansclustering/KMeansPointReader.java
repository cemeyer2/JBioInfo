package net.charliemeyer.jbioinfo.algorithms.kmeansclustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KMeansPointReader {

	public static ArrayList<KMeansPoint> read(String filename) throws IOException
	{
		return read(new File(filename));
	}
	
	public static ArrayList<KMeansPoint> read(File f) throws IOException
	{
		ArrayList<KMeansPoint> points = new ArrayList<KMeansPoint>();
		
		BufferedReader in = new BufferedReader(new FileReader(f));
		String str = "";
		
		while((str = in.readLine()) != null)
		{
			String[] split = str.split("\t");
			KMeansPoint point = new KMeansPoint(Integer.parseInt(split[0]));
			for(int i = 1; i < split.length; i++)
			{
				point.addCoordinate(Double.parseDouble(split[i]));
			}
			points.add(point);
		}
		return points;
	}
}

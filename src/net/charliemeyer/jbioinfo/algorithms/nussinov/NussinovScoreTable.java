package net.charliemeyer.jbioinfo.algorithms.nussinov;

import java.util.ArrayList;

public class NussinovScoreTable {
	
	private ArrayList<ArrayList<Double>> table;
	
	public NussinovScoreTable(int size)
	{
		table = new ArrayList<ArrayList<Double>>(size);
		for(int i = 0; i < size; i++)
		{
			ArrayList<Double> list = new ArrayList<Double>();
			for(int j = 0; j < size; j++)
			{
				list.add(null);
			}
			table.add(list);
		}
	}
	
	
	public Double getScore(int i, int j)
	{
		return table.get(i).get(j);
	}
	
	public void setScore(int i, int j, double score)
	{
		table.get(i).set(j, score);
	}
	
	public void print()
	{
		int size = table.size();
		
		for(int j = 0; j < size; j++)
		{
			for(int i = 0; i < size; i++)
			{
				System.out.print(getScore(i,j)+" ");
			}
			System.out.println();
		}
	}
}

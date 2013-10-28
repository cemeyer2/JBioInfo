package net.charliemeyer.jbioinfo.algorithms.lcs;

import java.util.ArrayList;

public class LCSNode 
{
	private int seq1pos, seq2pos;
	
	private ArrayList<LCSEdge> edges;
	
	private double score;
	
	protected LCSNode(int seq1pos, int seq2pos)
	{
		this.seq1pos = seq1pos;
		this.seq2pos = seq2pos;
		
		this.edges = new ArrayList<LCSEdge>();
	}
	
	protected double getScore()
	{
		return score;
	}
	
	protected void setScore(double score)
	{
		this.score = score;
	}
	
	protected void addEdge(LCSEdge edge)
	{
		this.edges.add(edge);
	}
	
	protected LCSEdge getNorth()
	{
		return getEdge(0,-1);
	}
	
	protected LCSEdge getNorthWest()
	{
		return getEdge(-1,-1);
	}
	
	protected LCSEdge getWest()
	{
		return getEdge(-1,0);
	}
	
	protected LCSEdge getEast()
	{
		return getEdge(1,0);
	}
	
	protected LCSEdge getSouth()
	{
		return getEdge(0,1);
	}
	
	protected LCSEdge getSouthEast()
	{
		return getEdge(1,1);
	}
	
	private LCSEdge getEdge(int seq1offset, int seq2offset)
	{
		for(LCSEdge edge : edges)
		{
			if(edge.getFrom().getSeq1pos() == this.getSeq1pos() + seq1offset && 
					edge.getFrom().getSeq2pos() == this.getSeq2pos() + seq2offset)
			{
				return edge;
			}
		}
		for(LCSEdge edge : edges)
		{
			if(edge.getTo().getSeq1pos() == this.getSeq1pos() + seq1offset && 
					edge.getTo().getSeq2pos() == this.getSeq2pos() + seq2offset)
			{
				return edge;
			}
		}
		return null;
	}

	public int getSeq1pos() {
		return seq1pos;
	}

	public int getSeq2pos() {
		return seq2pos;
	}
}

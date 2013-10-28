package net.charliemeyer.jbioinfo.algorithms.lcs;

import java.util.ArrayList;

/**
 * implements the LeastCommonSubsequence algorithm as a manhattan tourist problem
 * @author chuck
 *
 */
public class LongestCommonSubsequenceAlignment 
{
	private char[] sequence1, sequence2;
	private LCSNode[][] nodes;
	
	private double indelScore;
	
	private int i, j, iPrime, jPrime;
	
	private ArrayList<LCSEdge> backtrack;
	
	private LCSScoringMatrix matrix;

	/**
	 * constructor
	 * @param seq1 the first sequence to align
	 * @param seq2 the second sequence to align
	 * @param matrix the scoring matrix to use when scoring this alignment
	 */
	public LongestCommonSubsequenceAlignment(String seq1, String seq2, LCSScoringMatrix matrix)
	{
		sequence1 = seq1.toCharArray();
		sequence2 = seq2.toCharArray();
		nodes = new LCSNode[sequence1.length+1][sequence2.length+1];
		backtrack = new ArrayList<LCSEdge>();
		
		//default scores
		indelScore = 0;
		this.matrix = matrix;
	}
	
	/**
	 * gets the maximum i value, useful when trying to align the entire sequence
	 * @return the max i value
	 */
	public int getIMax()
	{
		return nodes.length-1;
	}
	
	/**
	 * gets the maximum j value, useful when trying to align the entire sequence
	 * @return
	 */
	public int getJMax()
	{
		return nodes[0].length-1;
	}
	
	public int getI()
	{
		return i;
	}
	
	public int getJ()
	{
		return j;
	}
	
	public int getIPrime()
	{
		return iPrime;
	}
	
	public int getJPrime()
	{
		return jPrime;
	}
	
	/**
	 * sets the score for indels
	 * @param indelScore the new score for indels
	 */
	public void setIndelScore(double indelScore)
	{
		this.indelScore = indelScore;
	}
	
	/**
	 * gets the score for indels
	 * @return the score for indels
	 */
	public double getIndelScore()
	{
		return indelScore;
	}
	
	/**
	 * gets the first sequence
	 * @return the first sequence
	 */
	public String getSequence1()
	{
		return new String(sequence1);
	}
	
	/**
	 * gets the second sequence
	 * @return the second sequence
	 */
	public String getSequence2()
	{
		return new String(sequence2);
	}
	
	//builds the graph including edges with weights
	private void buildGraph()
	{
		for(int ii = i; ii <= iPrime; ii++)
		{
			for(int jj = j; jj <= jPrime; jj++)
			{
				LCSNode to = new LCSNode(ii, jj);
				
				setNode(to, ii, jj);
				
				LCSNode north = getNode(ii, jj-1);
				LCSNode west = getNode(ii-1, jj);
				LCSNode northwest = getNode(ii-1, jj-1);
				
				
				addEdge(north, to);
				addEdge(west, to);
				addEdge(northwest, to);
			}
		}
	}
	
	//constructs and adds an edge if possible
	private void addEdge(LCSNode from, LCSNode to)
	{
		if(from != null)
		{
			double weight;
			char seq1char, seq2char;
			if(from.getSeq1pos() == to.getSeq1pos()) //moving vert
			{
				weight = indelScore;
				seq1char = '-';
				seq2char = sequence2[to.getSeq2pos()-1];
			}
			else if (from.getSeq2pos() == to.getSeq2pos()) //moving horz
			{
				weight = indelScore;
				seq1char = sequence1[to.getSeq1pos()-1];
				seq2char = '-';
			}
			else //diagonal 
			{
				seq1char = sequence1[to.getSeq1pos()-1]; //minus 1 since arrays are indexed from 0, sequences from 1
				seq2char = sequence2[to.getSeq2pos()-1];
				
				weight = matrix.getScore(seq1char+"", seq2char+"");
			}
			
			LCSEdge edge = new LCSEdge(weight, from, to, seq1char, seq2char);
			from.addEdge(edge);
			to.addEdge(edge);
		}
	}

	/**
	 * same as calling getScore(0,0,getIMax(),getJMax());
	 * @return the global alignment score
	 * @see LongestCommonSubsequenceAlignment#getScore(int, int, int, int)
	 */
	public double getGlobalAlignmentScore()
	{
		return getScore(0,0,getIMax(),getJMax());
	}
	
	public double getLocalAlignmentScore()
	{
		int ibest = 0, jbest = 0, iprimebest = 0, jprimebest = 0;
		double bestScore = Integer.MIN_VALUE;
		
		for(int i = 0; i <= getIMax(); i++)
		{
			for(int j = 0; j<= getJMax(); j++)
			{
				
				//source point is now (i,j)
				
				getScore(i, j, getIMax(), getJMax());
				
				
				for(int iprime = i; iprime <= getIMax(); iprime++)
				{
					for(int jprime = j; jprime  <= getJMax(); jprime++)
					{
						LCSNode node = nodes[iprime][jprime];
						double tempScore = node.getScore();
						if(tempScore > bestScore)
						{
							bestScore = tempScore;
							ibest = i;
							jbest = j;
							iprimebest = iprime;
							jprimebest = jprime;
						}
					}
				}
			}
		}
		return getScore(ibest, jbest, iprimebest, jprimebest);
	}
	
	/**
	 * scores the graph. each time this is called the graph is reinitialized. this must be called
	 * before calling getSequence1Aligned() or getSequence2Aligned()
	 * @param i the position in the first sequence to start aligning from, in range (0, getIMax())
	 * @param j the position in the second sequence to start aligning from, in range (0, getJMax())
	 * @param iPrime the position in the first sequence to end aligning from, in range (0, getIMax())
	 * @param jPrime the position in the second sequence to end aligning from, in range (0, getJMax())
	 * @return the score based on the scores set in this object and the parameter values
	 * @see LongestCommonSubsequenceAlignment#getIMax()
	 * @see LongestCommonSubsequenceAlignment#getJMax()
	 * @see LongestCommonSubsequenceAlignment#getSequence1Aligned()
	 * @see LongestCommonSubsequenceAlignment#getSequence2Aligned()
	 */
	public double getScore(int i, int j, int iPrime, int jPrime)
	{
		this.i = i;
		this.j = j;
		this.iPrime = iPrime;
		this.jPrime = jPrime;
		
		for(int ii = 0; ii <= getIMax(); ii++)
		{
			for(int jj = 0; jj <= getJMax(); jj++)
			{
				setNode(null,ii,jj);
			}
		}
		System.gc();
		buildGraph();
		
		for(int row = i; row <= iPrime; row++)
		{
			for(int col = j; col <= jPrime; col++)
			{
				LCSNode node = getNode(row, col);
				double nodeScore = Integer.MIN_VALUE;
				
				LCSEdge west = node.getWest();
				if(west != null)
				{
					LCSNode from = west.getFrom();
					if(from.getScore() + west.getWeight() > nodeScore)
					{
						nodeScore = from.getScore() + west.getWeight();
					}
				}
				
				LCSEdge north = node.getNorth();
				if(north != null)
				{
					LCSNode from = north.getFrom();
					if(from.getScore() + north.getWeight() > nodeScore)
					{
						nodeScore = from.getScore() + north.getWeight();
					}
				}
				
				LCSEdge northwest = node.getNorthWest();
				if(northwest != null)
				{
					LCSNode from = northwest.getFrom();
					if(from.getScore() + northwest.getWeight() > nodeScore)
					{
						nodeScore = from.getScore() + northwest.getWeight();
					}
				}
				
				//handle case where source is 0,0
				if(north == null && west == null && northwest == null)
				{
					nodeScore = 0;
				}
				
				node.setScore(nodeScore);
				
			}
		}
		if(nodes[iPrime][jPrime] != null)
		{
			return nodes[iPrime][jPrime].getScore();
		}
		else
		{
			return Double.MIN_VALUE;
		}
	}
	
	//method to init the backtracking algorithm
	private void backtrack()
	{
		backtrack.clear();
		backtrack = backtrack_recursive(iPrime, jPrime, new ArrayList<LCSEdge>());
	}
	
	//performs the backtracking algorithm
	private ArrayList<LCSEdge> backtrack_recursive(int iPrime, int jPrime, ArrayList<LCSEdge> edgesThusFar)
	{
		if(iPrime == i && jPrime == j)
		{
			return edgesThusFar;
		}
		
		LCSNode to = getNode(iPrime, jPrime);
				
		LCSEdge west = to.getWest();
		LCSEdge north = to.getNorth();
		LCSEdge northWest = to.getNorthWest();
		
		if(west != null)
		{
			LCSNode from = west.getFrom();
			double score = to.getScore();
			if(score - west.getWeight() == from.getScore())
			{
				ArrayList<LCSEdge> goingWest = (ArrayList<LCSEdge>)edgesThusFar.clone();
				goingWest.add(0, west);
				return backtrack_recursive(from.getSeq1pos(),from.getSeq2pos(), goingWest);
			}
		}
		
		if(north != null)
		{
			LCSNode from = north.getFrom();
			double score = to.getScore();
			if(score - north.getWeight() == from.getScore())
			{
				ArrayList<LCSEdge> goingNorth = (ArrayList<LCSEdge>)edgesThusFar.clone();
				goingNorth.add(0, north);
				return backtrack_recursive(from.getSeq1pos(),from.getSeq2pos(), goingNorth);
			}
		}
		
		if(northWest != null)
		{
			LCSNode from = northWest.getFrom();
			double score = to.getScore();
			if(score - northWest.getWeight() == from.getScore())
			{
				ArrayList<LCSEdge> goingNorthWest = (ArrayList<LCSEdge>)edgesThusFar.clone();
				goingNorthWest.add(0, northWest);
				return backtrack_recursive(from.getSeq1pos(),from.getSeq2pos(), goingNorthWest);
			}
		}
		return null;
	}
	
	/**
	 * Gets the first sequence when aligned, that is with indels inserted in correct positions
	 * @return the first sequence aligned
	 */
	public String getSequence1Aligned()
	{
		String seq = "";
		backtrack();
		for(LCSEdge edge : backtrack)
		{
			seq += edge.getSeq1char();
		}
		return seq;
	}
	
	/**
	 * gets the second sequence when aligned, that is with indels inserted in correct positions
	 * @return the second sequence aligned
	 */
	public String getSequence2Aligned()
	{
		String seq = "";
		backtrack();
		for(LCSEdge edge : backtrack)
		{
			seq += edge.getSeq2char();
		}
		return seq;
	}
	
	//setter for adding nodes, info hiding is good
	private void setNode(LCSNode node, int seq1pos, int seq2pos)
	{
		nodes[seq1pos][seq2pos] = node;
	}
	
	//getter for getting nodes, info hiding is good
	private LCSNode getNode(int seq1pos, int seq2pos)
	{
		try
		{
			return nodes[seq1pos][seq2pos];
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
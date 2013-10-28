package net.charliemeyer.cs466.assignment2;

import java.io.File;
import java.util.ArrayList;

import net.charliemeyer.jbioinfo.algorithms.lcs.LCSScoringMatrix;
import net.charliemeyer.jbioinfo.algorithms.lcs.LongestCommonSubsequenceAlignment;
import net.charliemeyer.jbioinfo.fasta.Sequence;

import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

public class Question2 
{
	public static void main(String[] args) throws Exception
	{
		ArrayList<Double> lengths = new ArrayList<Double>();
		ArrayList<Double> scores = new ArrayList<Double>();
		
		double piA = 0.25;
		double piC = 0.25;
		double piT = 0.25;
		double piG = 0.25;
		
		LCSScoringMatrix matrix = new LCSScoringMatrix(new File("subs.txt"));
		
		for(int gappenalty = -100; gappenalty >= -1000; gappenalty -= 100)
		{
			lengths.clear();
			scores.clear();
			
			for(int i = 0; i < 1000; i++)
			{
				String seq1 = Sequence.getSequence(500, piA, piC, piG, piT).toString();
				String seq2 = Sequence.getSequence(500, piA, piC, piG, piT).toString();
				LongestCommonSubsequenceAlignment align = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
				align.setIndelScore(gappenalty);
				
				scores.add(align.getGlobalAlignmentScore());
				lengths.add((double)align.getSequence1Aligned().length());	
				if(i % 100 == 0)
				{
					System.out.println(i);
				}
			}
						
			double[] prim_lengths = new double[lengths.size()];
			double[] prim_scores = new double[scores.size()];
			
			for(int i = 0; i < scores.size(); i++)
			{
				prim_scores[i] = scores.get(i);
			}
			
			for(int i = 0; i < lengths.size(); i++)
			{
				prim_lengths[i] = lengths.get(i);
			}
			
			double mean_s = new Mean().evaluate(prim_scores);
			double stdd_s = new StandardDeviation().evaluate(prim_scores);
			double mean_l = new Mean().evaluate(prim_lengths);
			double stdd_l = new StandardDeviation().evaluate(prim_lengths);
			
			System.out.println(gappenalty+"\t"+mean_l+"\t"+stdd_l+"\t"+mean_s+"\t"+stdd_s);
		}
	}
}

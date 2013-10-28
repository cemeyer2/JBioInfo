package net.charliemeyer.cs466.assignment2;

import java.io.File;

import net.charliemeyer.jbioinfo.algorithms.lcs.LCSScoringMatrix;
import net.charliemeyer.jbioinfo.algorithms.lcs.LongestCommonSubsequenceAlignment;
import net.charliemeyer.jbioinfo.fasta.Fasta;
import net.charliemeyer.jbioinfo.fasta.Sequence;

public class Question1 
{
	public static void main(String[] args)
	{
		if(args.length != 4)
		{
			System.err.println("error: usage: ./problem1.sh <fastafile1> <fastafile2> <matrixfile> <gappenalty>");
			System.exit(1);
		}
		Sequence s1 = Fasta.read(args[0]);
		Sequence s2 = Fasta.read(args[1]);
		LCSScoringMatrix matrix = new LCSScoringMatrix(new File(args[2]));
		double gap = Double.parseDouble(args[3]);
		
		LongestCommonSubsequenceAlignment align = new LongestCommonSubsequenceAlignment(s1.toString(), s2.toString(), matrix);
		align.setIndelScore(gap);
		
		double score = align.getGlobalAlignmentScore();
		
		String align1 = align.getSequence1Aligned();
		String align2 = align.getSequence2Aligned();
		
		System.out.println("The optimal alignment between the given sequences has score "+score);
		System.out.println();
		
		while(align1.length() > 60)
		{
			System.out.println(align1.substring(0, 60));
			System.out.println(align2.substring(0,60));
			System.out.println();
			
			align1 = align1.substring(60);
			align2 = align2.substring(60);
		}
		System.out.println(align1);
		System.out.println(align2);
	}
}

package net.charliemeyer.jbioinfo.drivers;

import java.io.File;

import net.charliemeyer.jbioinfo.algorithms.lcs.LCSScoringMatrix;
import net.charliemeyer.jbioinfo.algorithms.lcs.LongestCommonSubsequenceAlignment;
import net.charliemeyer.jbioinfo.fasta.Fasta;
import net.charliemeyer.jbioinfo.fasta.Sequence;

public class LCSDriver 
{
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();
		Sequence f1 = Fasta.read("out.fa");
		Sequence f2 = Fasta.read("out2.fa");
		LCSScoringMatrix matrix = new LCSScoringMatrix(new File("subs.txt"));
		
		//System.out.println(f1);
		
		LongestCommonSubsequenceAlignment align = new LongestCommonSubsequenceAlignment(f1.toString(), f2.toString(),matrix);
		
		System.out.println(align.getGlobalAlignmentScore());
		System.out.println(align.getSequence1Aligned());
		System.out.println(align.getSequence2Aligned());
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}

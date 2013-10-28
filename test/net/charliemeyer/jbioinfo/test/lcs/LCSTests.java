package net.charliemeyer.jbioinfo.test.lcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import net.charliemeyer.jbioinfo.algorithms.lcs.LCSScoringMatrix;
import net.charliemeyer.jbioinfo.algorithms.lcs.LongestCommonSubsequenceAlignment;

import org.junit.Test;

public class LCSTests {
	
	String seq1 = "ATCTGATC";
	String seq2 = "TGCATAC";
	LCSScoringMatrix matrix = new LCSScoringMatrix(new File("subs.txt"));
	
	@Test
	public void constructor()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
		
		assertEquals(seq1, alignment.getSequence1());
		assertEquals(seq2, alignment.getSequence2());
		
		//test that default scores were established
		assertEquals(0, alignment.getIndelScore(),0);
	}
	
	@Test
	public void setScores()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
		
		double indelScore = 50;
		
		alignment.setIndelScore(indelScore);
		
		assertEquals(indelScore, alignment.getIndelScore(), 0);
	}
	
	@Test
	public void simpleGlobalAlignmentAndBacktrack()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
		
		assertEquals(473, alignment.getGlobalAlignmentScore(),0);
		
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("AT-C-TGATC", alignment.getSequence1Aligned());
		assertEquals("-TGCAT-A-C", alignment.getSequence2Aligned());
	}
	
	@Test
	public void localAlignments()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
		
		assertEquals(282, alignment.getScore(0, 3, 3, 7),0);
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("AT-C", alignment.getSequence1Aligned());
		assertEquals("ATAC", alignment.getSequence2Aligned());
		
		assertEquals(382, alignment.getScore(4, 1, 8, 7),0);
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("G-AT-C", alignment.getSequence1Aligned());
		assertEquals("GCATAC", alignment.getSequence2Aligned());
		
		assertEquals(191, alignment.getScore(6, 0, 8, 4),0);
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("T-C-", alignment.getSequence1Aligned());
		assertEquals("TGCA", alignment.getSequence2Aligned());
	}
	
	@Test
	public void localAlignment()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment(seq1, seq2, matrix);
		
		assertEquals(473, alignment.getLocalAlignmentScore(), 0);
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("AT-C-TGATC", alignment.getSequence1Aligned());
		assertEquals("-TGCAT-A-C", alignment.getSequence2Aligned());
	}
	
	@Test
	public void allIndels()
	{
		LongestCommonSubsequenceAlignment alignment = new LongestCommonSubsequenceAlignment("AAAAA", "TTTTT", matrix);
		assertEquals(0, alignment.getGlobalAlignmentScore(),0);	
		assertTrue(alignment.getSequence1Aligned().length() == alignment.getSequence2Aligned().length());
		assertEquals("-----AAAAA", alignment.getSequence1Aligned());
		assertEquals("TTTTT-----", alignment.getSequence2Aligned());
	}
}

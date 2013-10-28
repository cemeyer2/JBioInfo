package net.charliemeyer.jbioinfo.drivers;

import java.io.File;

import net.charliemeyer.jbioinfo.algorithms.lcs.LCSScoringMatrix;

public class LCSScoringMatrixDriver {
	public static void main(String[] args)
	{
		LCSScoringMatrix matrix = new LCSScoringMatrix(new File("subs.txt"));
		System.out.println(matrix.getScore("C", "C"));
	}
}

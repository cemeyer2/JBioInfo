package net.charliemeyer.jbioinfo.drivers;

import net.charliemeyer.jbioinfo.algorithms.profilematrix.ProfileMatrix;
import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

public class ProfileMatrixDriver {

	public static void main(String[] args) throws JBioinfoException
	{
		ProfileMatrix matrix = new ProfileMatrix();
		
		matrix.addSequence("AGGT");
		matrix.addSequence("CCAT");
		matrix.addSequence("ACGT");
		matrix.addSequence("ACGT");
		matrix.addSequence("CCGT");
		
		System.out.println(matrix.getScore());
		System.out.println(matrix.getFirstConsensus());
	}
}

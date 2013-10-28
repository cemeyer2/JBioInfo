package net.charliemeyer.jbioinfo.test.profilematrix;

import net.charliemeyer.jbioinfo.algorithms.profilematrix.ProfileMatrix;
import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProfileMatrixTests 
{
	@Test
	public void constructor()
	{
		ProfileMatrix matrix = new ProfileMatrix();
		assertEquals(0, matrix.getSequenceCount());
	}
	
	@Test
	public void simpleMatrix()
	{
		ProfileMatrix matrix = new ProfileMatrix();
		try
		{
			matrix.addSequence("AGGT");
			matrix.addSequence("CCAT");
			matrix.addSequence("ACGT");
			matrix.addSequence("ACGT");
			matrix.addSequence("CCGT");
		}
		catch(JBioinfoException ex)
		{
			fail("Should not happen!");
		}
		assertEquals(5, matrix.getSequenceCount());
		assertEquals("AGGT", matrix.getSequence(0));
		assertEquals("CCAT", matrix.getSequence(1));
		assertEquals("ACGT", matrix.getSequence(2));
		assertEquals("ACGT", matrix.getSequence(3));
		assertEquals("CCGT", matrix.getSequence(4));
		
		assertEquals(16, matrix.getScore());
		assertEquals("ACGT", matrix.getFirstConsensus());
	}
	
	@Test
	public void complicatedMatrix()
	{
		ProfileMatrix matrix = new ProfileMatrix();
		try
		{
			matrix.addSequence("AGGTACTT");
			matrix.addSequence("CCATACGT");
			matrix.addSequence("ACGTTAGT");
			matrix.addSequence("ACGTCCAT");
			matrix.addSequence("CCGTACGG");
		}
		catch(JBioinfoException e)
		{
			fail("should not happen");
		}
		
		assertEquals(30, matrix.getScore());
		assertEquals("ACGTACGT", matrix.getFirstConsensus());
	}
	
	@Test
	public void removeSequence()
	{
		ProfileMatrix matrix = new ProfileMatrix();
		try
		{
			matrix.addSequence("ACGT");
		}
		catch(JBioinfoException e)
		{
			fail("should not happen");
		}
		
		assertFalse(matrix.removeSequence("GCAT"));
		assertTrue(matrix.removeSequence("ACGT"));
	}
	
	@Test(expected=JBioinfoException.class)
	public void differentLengthsShouldThrowException() throws JBioinfoException
	{
		ProfileMatrix matrix = new ProfileMatrix();
		matrix.addSequence("ACGT");
		matrix.addSequence("ACGTACGT");
	}
}

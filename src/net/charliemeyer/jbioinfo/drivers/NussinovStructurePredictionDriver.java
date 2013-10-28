package net.charliemeyer.jbioinfo.drivers;

import java.io.IOException;

import net.charliemeyer.jbioinfo.algorithms.nussinov.NussinovStructurePrediction;

public class NussinovStructurePredictionDriver 
{
	public static void main(String[] args) throws IOException
	{
		//String sequence = "GACUAAAAAAAAAAGUC";
		//String sequence = "GACGAAAAAAAAACGUC";
		String sequence = "GAGAUCUCGAGAUCUC";
		NussinovStructurePrediction nsp = new NussinovStructurePrediction(sequence);
		System.out.println(nsp.getScore());
		nsp.createImage("nsp.png");
	}
}

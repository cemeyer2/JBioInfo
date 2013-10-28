package net.charliemeyer.cs466.assignment4;

import java.io.File;
import java.util.LinkedList;

import net.charliemeyer.jbioinfo.algorithms.gibbs.GibbsAligner;
import net.charliemeyer.jbioinfo.algorithms.gibbs.GibbsStoppingCriteria;
import net.charliemeyer.jbioinfo.algorithms.profilematrix.ProfileMatrix;

import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojavax.RichObjectFactory;
import org.biojavax.bio.seq.RichSequence;



public class Question1 
{

	public static void main(String[] args) throws Exception
	{
		File sequenceFile = new File(args[0]);
		int desiredMotifLength = Integer.parseInt(args[1]);
		int trials = 10;
		SequenceIterator sequenceIterator;
		LinkedList<String> concensusMotifs = new LinkedList<String>();

		for(int i = 0; i < trials; i++)
		{
			Class.forName("net.charliemeyer.jbioinfo.fasta.FastaFormat");
			sequenceIterator = RichSequence.IOTools.readFile(sequenceFile, RichObjectFactory.getDefaultNamespace());
			
			long start = System.currentTimeMillis();
			
			GibbsAligner gibbs = new GibbsAligner(desiredMotifLength, sequenceIterator, GibbsStoppingCriteria.HEURISTIC);
			gibbs.iterate();
			
			long end = System.currentTimeMillis();
			
			System.out.println("Trial "+(i+1));
//			System.out.println("Converged after "+gibbs.getIterations()+" iterations");
			System.out.println("Information Content (bits): "+gibbs.getInfoContent());
//			System.out.println("Running time: "+(end-start)+" ms");

			Sequence[] seqs = gibbs.getSequences();
			int[] offSets = gibbs.getOffSets();
			ProfileMatrix matrix = new ProfileMatrix();

			//print out the motif
			for (int j = 0; j < offSets.length; j++) 
			{
				String motif = seqs[j].subStr(offSets[j],offSets[j]+desiredMotifLength -1);
//				System.out.println("motif from seq "+j+": "+motif);
				matrix.addSequence(motif);
			}
			System.out.println("Concensus motif:  "+matrix.getFirstConsensus());
			concensusMotifs.add(matrix.getFirstConsensus());
			System.out.println();
		}
//		System.out.println("Concensus Motifs:");
//		for(String motif : concensusMotifs)
//			System.out.println(motif);
	}
}
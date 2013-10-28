package net.charliemeyer.cs466.assignment1;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import net.charliemeyer.jbioinfo.fasta.Sequence;

public class Question3 
{
	public static void main(String[] args) throws Exception
	{
		File output = new File(args[0]);
		int N = Integer.parseInt(args[1]);
		int L = Integer.parseInt(args[2]);
		double piA = Double.parseDouble(args[3]);
		double piC = Double.parseDouble(args[4]);
		double piG = Double.parseDouble(args[5]);
		double piT = Double.parseDouble(args[6]);
		
		ArrayList<Sequence> seqs = new ArrayList<Sequence>(N);
		
		for(int i = 0; i < N; i++)
		{
			Sequence seq = Sequence.getSequence(L, piA, piC, piG, piT);
			seq.setSequenceId("randomseq");
			seqs.add(seq);
		}
		
		FileWriter out = new FileWriter(output);
		
		for(Sequence seq : seqs)
			out.write(seq.toFastaString());
		out.flush();
		out.close();
	}
}

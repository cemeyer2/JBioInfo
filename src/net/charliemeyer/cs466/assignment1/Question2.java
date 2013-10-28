package net.charliemeyer.cs466.assignment1;

import net.charliemeyer.jbioinfo.fasta.Fasta;
import net.charliemeyer.jbioinfo.fasta.Sequence;

public class Question2 {
	
	public static void main(String[] args)
	{
		String word = args[1];
		Sequence seq = Fasta.read(args[0]);
		boolean verbose = false;
		if(args.length > 2)
		{
			if(args[2].equals("verbose"))
				verbose = true;
		}
		
		int N = seq.countOccurances(word);
		if(verbose)
			System.out.println("debug: There are "+N+" occurances of the word "+word+" in the initial sequence");
		int times = 0;
		int loopcount = 1000;
		for(int i = 0; i < loopcount; i++)
		{
			Sequence seq2 = seq.permute();
			int N2 = seq2.countOccurances(word);
			if(verbose)
				System.out.println("debug: There are "+N2+" occurances of the word "+word+" in the permuted sequence");
			if(N2 >= N)
			{
				if(verbose)
					System.out.println("debug: There are >= occurances in permutation than in original");
				times++;
			}
		}
		double p = (double)times/(double)loopcount;
		System.out.println("p value:" + p);
	}
}

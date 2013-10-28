package net.charliemeyer.cs466.assignment1;

import java.io.File;

import net.charliemeyer.jbioinfo.fasta.Fasta;
import net.charliemeyer.jbioinfo.fasta.Sequence;

public class Question1 
{
	public static void main(String[] args)
	{
		if(args.length != 1)
		{
			System.err.println("Error: usage: Question1 <filename>");
			return;
		}
		
		File f = new File(args[0]);
		
		if(!f.exists())
		{
			System.err.println("Error: file "+args[0]+" does not exist");
			return;
		}
		
		Sequence sequence = Fasta.read(f);
		
		System.out.println(sequence.toFastaString());
	}
}

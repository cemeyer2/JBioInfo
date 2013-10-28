package net.charliemeyer.jbioinfo.fasta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implements the ability to read a file in FASTA format
 * @author chuck
 *
 */
public class Fasta 
{
	public static Sequence read(File file)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));
			String header = in.readLine();
			header = header.substring(1); //remove >
			int firstSpace = header.indexOf(" ");
			String id = "";
			String description = "";
			if(firstSpace != -1)
			{
				id = header.substring(0,firstSpace);
				description = header.substring(firstSpace+1);
			}
			else
			{
				id = header;
			}
			String data = "";
			String buffer = "";
			while((buffer = in.readLine())!= null)
			{
				data += buffer;
			}
			return new Sequence(data,id,description);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return null;
	}
	
	public static Sequence read(String filename)
	{
		File file = new File(filename);
		return read(file);
	}
	
//	public static List<Sequence> readMultiple(String filename)
//	{
//		File file = new File(filename);
//		return readMultiple(file);
//	}
//	
//	public static List<Sequence> readMultiple(File file)
//	{
//		
//	}
//	
//	public static void write(Sequence sequence, String filename)
//	{
//		
//	}
//	
//	public static void write(List<Sequence> sequences, String filename)
//	{
//		
//	}
	
	public static void main(String[] args)
	{
		File f = new File("assn1.file1.fa");
		Sequence seq = read(f);
		System.out.println(seq.toFastaString());
	}
}

package net.charliemeyer.jbioinfo.fasta;

import java.util.ArrayList;
import java.util.List;

public class Sequence
{
	String sequenceData="";
	String sequenceId="";
	String sequenceDescription="";	

	public Sequence(String seqData, String id, String description )
	{
		sequenceData= seqData;
		sequenceId= id;
		sequenceDescription= description;
	}


	public String getSequenceId()
	{
		return sequenceId;
	}
	
	public void setSequenceId(String id)
	{
		this.sequenceId = id;
	}


	public String getSequenceDescription()
	{
		return sequenceDescription;
	}

	public Sequence subsequence(int start, int end)
	{
		return new Sequence(sequenceData.substring(start, end), sequenceId, sequenceDescription);
	}


	public String toString()
	{
		return sequenceData;
	}

	public String toFastaString()
	{
		String buf = "";
		buf += ">"+getSequenceId()+" "+getSequenceDescription()+"\n";

		int start = 0;

		String data = toString();

		while(true)
		{
			start = 80;
			if(start > data.length()-1)
			{
				start = data.length()-1;
			}
			buf += data.substring(0,start)+"\n";
			data = data.substring(start+1);
			if(start != 80)
			{
				break;
			}
		}

		return buf;
	}

	//modified from http://tech.chitgoks.com/2008/04/16/java-count-number-of-instances-of-substring/
	public int countOccurances(String word) {
		int counter = -1;
		int total = 0;
		while (true) 
		{
			if (counter == -1) 
					counter = this.toString().indexOf(word);
			else 
				counter = this.toString().indexOf(word, counter);

			if (counter == -1) 
			{
				break;
			} 
			else 
			{
				total++;
				counter += word.length();
			}
		}
		return total;
	}

	public Sequence permute()
	{
		List<String> nucleotides = new ArrayList<String>(this.toString().length());
		int start = 0;
		while(start < this.toString().length())
		{
			nucleotides.add(this.toString().substring(start, start+1));
			start++;
		}
		String permutation = "";
		while(nucleotides.size() > 0)
		{
			permutation += nucleotides.remove((int)(Math.random()*nucleotides.size()));
		}
		return new Sequence(permutation, this.getSequenceId(), this.getSequenceDescription());
	}
	
	public static Sequence getSequence(int length, double piA, double piC, double piG, double piT) throws Exception
	{
		if(piA + piC + piG + piT != 1)
		{
			throw new Exception("Pi values dont add up to 1");
		}
		String seq = "";
		for(int i = 0; i < length; i++)
		{
			String nuc = "";
			double rand = Math.random();
			if(rand <= piA)
				nuc = "A";
			else if(rand < piA + piC)
				nuc = "C";
			else if(rand < piA + piC + piG)
				nuc = "G";
			else
				nuc = "T";
			
			seq += nuc;
		}
		Sequence sequence = new Sequence(seq, "", "");
		return sequence;
	}
}

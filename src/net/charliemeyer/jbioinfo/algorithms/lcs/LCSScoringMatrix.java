package net.charliemeyer.jbioinfo.algorithms.lcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LCSScoringMatrix 
{
	HashMap<String, Double> scores;
	
	public LCSScoringMatrix(File input)
	{
		scores = new HashMap<String, Double>();
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));
			String line = "";
			line = in.readLine();
			String[] split = line.split("\t");
			while((line = in.readLine()) != null)
			{
				String[] split2 = line.split("\t");
				String toLetter = split2[0];
				for(int i = 1; i < split2.length; i++)
				{
					String fromLetter = split[i];
					Double score = Double.parseDouble(split2[i]);
					scores.put(fromLetter+toLetter, score);
				}
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public double getScore(String from, String to)
	{
		//System.out.println(from+" to "+to);
		return scores.get(from+to);
	}
}
package net.charliemeyer.jbioinfo.enumuerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlphabetEnumerator 
{
	EnumerationNode root;
	String alphabet;
	int desiredDepth;
	List<String> strings;
	FileWriter writer;
	
	public AlphabetEnumerator(String alphabet, int desiredDepth)
	{
		this.alphabet = alphabet;
		this.desiredDepth = desiredDepth;
		//strings = new ArrayList<String>((int)Math.pow(alphabet.length(), desiredDepth));
		try {
			writer = new FileWriter(new File("passwords.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildTree();
	}
	
	private void buildTree()
	{
		root = new EnumerationNode(this, writer, "");
		root = null;
		System.gc();
	}
	
	public int getDesiredDepth()
	{
		return desiredDepth;
	}
	
	public String getAlphabet()
	{
		return alphabet;
	}
	
	public char[] getAlphabetCharArray()
	{
		return getAlphabet().toCharArray();
	}
	
	public List<String> getStrings()
	{
		return strings;
	}
	
	protected void addString(String str)
	{
		strings.add(str);
	}
	
	public static void main(String[] args)
	{
		AlphabetEnumerator ae = new AlphabetEnumerator("ACGT", 10);
	}
}

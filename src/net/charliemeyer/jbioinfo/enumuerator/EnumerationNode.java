package net.charliemeyer.jbioinfo.enumuerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnumerationNode 
{
	List<EnumerationNode> children;
	String contents;
	AlphabetEnumerator alphabetEnumerator;
	FileWriter writer;
	
	public EnumerationNode(AlphabetEnumerator alphabetEnumerator, FileWriter writer, String contents)
	{
		children = new ArrayList<EnumerationNode>();
		this.contents = contents;
		this.alphabetEnumerator = alphabetEnumerator;
		this.writer = writer;
		try {
			writer.append(contents+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildChildren();
	}
	
	private void buildChildren()
	{
		if(getLength() < this.alphabetEnumerator.getDesiredDepth())
		{
			for(char letter : this.alphabetEnumerator.getAlphabetCharArray())
			{
				EnumerationNode child = new EnumerationNode(this.alphabetEnumerator, writer, getString()+letter);
				
				//this.children.add(child);
			}
		}
		else if(getLength() == this.alphabetEnumerator.getDesiredDepth())
		{
			//this.alphabetEnumerator.addString(getString());
		}
	}
	
	public void rebuild()
	{
		
	}
	
	public int getLength()
	{
		return getString().length();
	}
	
	public String getString()
	{
		return contents;
	}
	
	public String toString()
	{
		return getString();
	}
	
	public List<EnumerationNode> getChildren()
	{
		return children;
	}
	
	public boolean isLeaf()
	{
		return getChildren().size() == 0;
	}
}

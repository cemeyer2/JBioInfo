package net.charliemeyer.jbioinfo.algorithms.profilematrix;

import java.util.ArrayList;
import java.util.TreeMap;

import net.charliemeyer.jbioinfo.exceptions.JBioinfoException;

/**
 * A profile matrix is one method to find a consensus string among
 * several. It does this by choosing a the character in each position
 * of all of the sequences in the matrix that has the most occurances
 * @author chuck
 *
 */
public class ProfileMatrix 
{
	ArrayList<String> sequences;
	ArrayList<ProfileMatrixColumn> columns;
	boolean needToRecompute;

	/**
	 * constructs an empty profile matrix
	 */
	public ProfileMatrix()
	{
		sequences = new ArrayList<String>();
		columns = new ArrayList<ProfileMatrixColumn>();
		needToRecompute = true;
	}
	
	/**
	 * gets the number of sequences in this profile matrix
	 * @return the number of sequences in this profile matrix
	 */
	public int getSequenceCount()
	{
		return sequences.size();
	}
	
	/**
	 * gets a sequence in this profile matrix
	 * @param index the location to fetch
	 * @return the sequence at the given index
	 */
	public String getSequence(int index)
	{
		return sequences.get(index);
	}

	/**
	 * adds a sequence to this profile matrix
	 * @param sequence the sequence to add
	 * @throws JBioinfoException if the added sequence length does not match the lengths of the
	 * other sequences already in this matrix
	 */
	public void addSequence(String sequence) throws JBioinfoException
	{
		if(sequences.size() == 0)
		{
			sequences.add(sequence);
		}
		else
		{
			int length = sequences.get(0).length();
			if(sequence.length() != length)
			{
				throw new JBioinfoException("ProfileMatrix: all sequences must be of same length");
			}
			sequences.add(sequence);
		}
		needToRecompute = true;
	}

	/**
	 * removes a sequence from this profile matrix
	 * @param sequence the sequence to remove
	 * @return true if the sequence was in the matrix and now is removed, false if the sequence was never in the matrix
	 */
	public boolean removeSequence(String sequence)
	{
		needToRecompute = true;
		return sequences.remove(sequence);
	}

	/**
	 * computes the score of this profile matrix.
	 * the score is computed by iterating column by column over each of the sequences in this profile matrix
	 * and computing the number of times each character appears in that column. the count of occurances of
	 * the character that occurs the most is added to the score.
	 * @return the score of this profile matrix
	 */
	public int getScore()
	{
		if(needToRecompute)
		{
			columns.clear();
			for(int i = 0; i < sequences.get(0).toCharArray().length; i++)
			{
				ProfileMatrixColumn column = new ProfileMatrixColumn();
				for(String sequence : sequences)
				{
					column.addCharacter(sequence.toCharArray()[i]);
				}
				columns.add(column);
			}
			needToRecompute = false;
		}
		int score = 0;
		for(ProfileMatrixColumn column : columns)
		{
			score += column.getCount();
		}
		return score;
	}

	/**
	 * gets the first consensus string of this profile matrix.
	 * the consensus string is determined by computing which character contributed to the score of this matrix
	 * and appending it to the consensus string. if two characters had the same score, the first character determined
	 * by ASCII value is used, thus why this is considered the "first" consensus string.
	 * @return the first consensus string of this profile matrix
	 */
	public String getFirstConsensus()
	{
		getScore();
		String concensus = "";
		for(ProfileMatrixColumn column : columns)
		{
			concensus += column.getCharacter();
		}
		return concensus;
	}

	private class ProfileMatrixColumn
	{
		TreeMap<Character, Integer> counts;

		protected ProfileMatrixColumn()
		{
			counts = new TreeMap<Character, Integer>();
		}

		protected void addCharacter(char c)
		{
			Integer value = counts.get(c);
			if(value == null)
			{
				value = new Integer(0);
			}
			value = new Integer(value.intValue() + 1);
			counts.put(c, value);
		}

		protected char getCharacter()
		{
			char concensus = '-';
			int max = 0;
			for(char c : counts.keySet())
			{
				int value = counts.get(c);
				if(value > max)
				{
					max = value;
					concensus = c;
				}
			}
			return concensus;
		}

		protected int getCount()
		{
			return counts.get(getCharacter());
		}
	}
}

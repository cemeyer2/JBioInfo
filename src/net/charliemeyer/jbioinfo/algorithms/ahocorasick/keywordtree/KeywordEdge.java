package net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree;

/**
 * A edge in the keyword tree. Can be either an edge that represents a character to be
 * consumed while pattern matching or can be a failure edge used to jump to another
 * portion of the tree without consuming a character.
 * @author chuck
 */
public class KeywordEdge 
{
	private final KeywordNode from, to;
	private final char c;
	private final boolean isFailure;
	
	/**
	 * Constructor used for creating a KeywordEdge that contains
	 * a character to be consumed while pattern matching.
	 * @param from the node from which this edge originates
	 * @param to the node to which this edge terminates
	 * @param c the character that this edge contains
	 * @see KeywordNode
	 */
	public KeywordEdge(KeywordNode from, KeywordNode to, char c)
	{
		this(from, to, c, false);
	}
	
	/**
	 * Constructor used from creating a KeywordEdge that
	 * is a failureEdge used to jump to another portion
	 * of the KeywordTree while pattern matching
	 * @param from the node from which this edge originates
	 * @param to the node to which this edge terminates
	 * @see KeywordNode
	 */
	public KeywordEdge(KeywordNode from, KeywordNode to)
	{
		this(from, to, '-', true);
	}
	
	private KeywordEdge(KeywordNode from, KeywordNode to, char c, boolean isFailure)
	{
		this.from = from;
		this.to = to;
		this.c = c;
		this.isFailure = isFailure;
	}
	
	/**
	 * gets the character that should be consumed if this edge is
	 * followed. This will return '-' if this is a failure edge, ie
	 * isFailure() returns true
	 * @return the character that should be consumed if this edge is followed
	 * @see KeywordEdge#isFailure()
	 */
	public final char getChar()
	{
		return this.c;
	}
	
	/**
	 * returns the KeywordNode from which this edge originates
	 * @return the KeywordNode from which this edge originates
	 * @see KeywordNode
	 */
	public final KeywordNode getFrom()
	{
		return this.from;
	}
	
	/**
	 * returns the KeywordNode to which this edge terminates
	 * @return the KeywordNode to which this edge terminates
	 * @see KeywordNode
	 */
	public final KeywordNode getTo()
	{
		return this.to;
	}
	
	/**
	 * returns true if this is a failure edge, false otherwise
	 * @return true if this is a failure edge, false otherwise
	 */
	public final boolean isFailure()
	{
		return this.isFailure;
	}
}

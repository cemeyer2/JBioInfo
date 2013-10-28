package net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in the keywordtree
 * @author chuck
 * @see KeywordTree
 */
public class KeywordNode 
{
	private final boolean isRoot;
	private final LinkedList<String> words;
	private final HashMap<Character, KeywordEdge> edges;
	private KeywordEdge failureEdge;
	private boolean isTerminal;
	
	/**
	 * Constructs a keywordnode
	 * @param isRoot set to true if this is the root node, false otherwise
	 */
	public KeywordNode(boolean isRoot)
	{
		this.words = new LinkedList<String>();
		this.isRoot = isRoot;
		edges = new HashMap<Character, KeywordEdge>();
	}
	
	/**
	 * Constructs a non-root keywordnode
	 * @see KeywordNode#KeywordNode(boolean)
	 */
	public KeywordNode()
	{
		this(false);
	}

	/**
	 * returns true if this is the root node, false otherwise
	 * @return true if this is the root node, false otherwise
	 */
	public final boolean isRoot() {
		return isRoot;
	}

	/**
	 * returns a list of all the words that this node has. If this node
	 * is not a terminal, then the words list returned will be a single
	 * element list containing only the prefix so far down the tree
	 * @return a list of all the words that this node has
	 * @see KeywordNode#isTerminal()
	 */
	public final List<String> getWords() {
		return words;
	}

	/**
	 * returns an edge (if one exists) that corresponds to the parameter char, null if no edge exists
	 * @param toChar the char that we are looking for an edge for from this node
	 * @return an edge (if one exists) that corresponds to the parameter char, null if no edge exists
	 * @see KeywordEdge
	 */
	public KeywordEdge getNonFailureEdge(char toChar) {
		return edges.get(toChar);
	}
	
	/**
	 * returns a collection of all non-failure edges that this node has
	 * @return a collection of all non-failure edges that this node has
	 * @see KeywordNode#getFailureEdge()
	 */
	public Collection<KeywordEdge> getNonFailureEdges()
	{
		return edges.values();
	}

	/**
	 * adds a non-failure edge to this node. if the parameter edge is a failure edge,
	 * then this method will fail silently. a future version might throw an exception
	 * @param edge the edge to add
	 * @see KeywordEdge#isFailure()
	 */
	protected void addNonFailureEdge(KeywordEdge edge) {
		if(!edge.isFailure())
			edges.put(edge.getChar(), edge);
	}

	/**
	 * returns the failure edge associated with this node or null if none exists
	 * @return the failure edge associated with this node or null if none exists
	 */
	public KeywordEdge getFailureEdge() {
		return failureEdge;
	}

	/**
	 * Sets the failure edge for this node, overwriting one if one is already set.
	 * The parameter edge must be a failure edge, otherwise this method will fail
	 * silently. a future version might throw an exception
	 * @param failureEdge the edge to add
	 * @see KeywordEdge#isFailure()
	 */
	protected void setFailureEdge(KeywordEdge failureEdge) {
		if(failureEdge.isFailure())
			this.failureEdge = failureEdge;
	}
	
	/**
	 * returns true if this node is a leaf in the tree, false otherwise. A leaf is
	 * defined to mean that this node has no non-failure edges
	 * @return true if this node is a leaf in the tree, false otherwise
	 */
	public boolean isLeaf()
	{
		return this.edges.size() == 0;
	}
	
	/**
	 * adds a word to this node
	 * @param word the word to add
	 * @param isTerminal true if the word we are adding is a pattern in the tree, false otherwise
	 */
	protected void addWord(String word, boolean isTerminal)
	{
		this.isTerminal = isTerminal;
		if(!words.contains(word))
			this.words.add(word);
	}
	
	/**
	 * replaces a word in this node
	 * @param index the index of the word to replace
	 * @param word the new word
	 * @param isTerminal true if the word we are adding is a pattern in the tree, false otherwise
	 */
	public void replaceWord(int index, String word, boolean isTerminal)
	{
		this.isTerminal = isTerminal;
		this.words.set(index, word);
	}
	
	/**
	 * returns true if this node represents a complete pattern, false otherwise
	 * @return true if this node represents a complete pattern, false otherwise
	 */
	public final boolean isTerminal()
	{
		return this.isTerminal;
	}
	
	public String toString()
	{
		if(isRoot())
			return "ROOT";
		if(!isTerminal())
			return "";
		String retval = "{";
		for(String word : words)
		{
			retval += word+", ";
		}
		retval = retval.substring(0, retval.length()-2);
		retval+="}";
		return retval;
	}
}

package net.charliemeyer.jbioinfo.algorithms.ahocorasick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree.KeywordEdge;
import net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree.KeywordNode;
import net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree.KeywordTree;

/**
 * a multiple pattern matcher implementing the Aho-Corasick algorithm.
 * Given k patterns, with the max length of any one pattern being n, matching
 * on a text of length m, the running time is O(kn+m) (supposedly, although
 * i would contend that my implementation of the keyword tree takes O(kn^2) to
 * construct, making the overall running time O(kn^2+m)
 * @author chuck
 *
 */
public class AhoCorasickPatternMatcher {
	
	private KeywordTree tree;
	
	/**
	 * constructor
	 */
	public AhoCorasickPatternMatcher()
	{
		tree = new KeywordTree();
	}
	
	/**
	 * adds a pattern to search for to this matcher
	 * @param pattern the pattern to add
	 */
	public void addPattern(String pattern)
	{
		tree.addPattern(pattern);
	}
	
	/**
	 * clears out all search patterns in this matcher
	 */
	public void clearPatterns()
	{
		tree = null; //get rid of the current tree
		System.gc(); //force garbage collection, it should happen anyway, but this cant hurt
		tree = new KeywordTree(); //create an empty tree
	}
	
	/**
	 * returns the keyword tree automaton behind the algorithm
	 * @return the keyword tree automaton behind the algorithm
	 */
	public final KeywordTree getKeywordTree()
	{
		return tree;
	}
	
	/**
	 * Runs the pattern matcher on the supplied text using the patterns already loaded.
	 * @param text the text that we are searching for patterns in
	 * @return a map, with the keys being the patterns found and their associated values
	 * being lists of ints with each int representing the index in the text where that
	 * pattern was found
	 */
	public final Map<String, List<Integer>> match(String text)
	{
		TreeMap<String, List<Integer>> results = new TreeMap<String, List<Integer>>();
		tree.generateFailureEdges();
		KeywordNode current = tree.getRoot();
		char[] arr = text.toCharArray();
		int i = 0;
		while(true)
		{
			if(current.isTerminal())
			{
				//add the matches to our found set
				for(String match : current.getWords())
				{	
					List<Integer> result = results.get(match);
					if(result == null)
					{
						result = new ArrayList<Integer>();
					}
					//if we add multiple patterns at one terminal, then take
					//a failure edge to another terminal, dont add those words
					//that have already been added
					if(!result.contains(i-match.length()))
						result.add(i-match.length());
					results.put(match, result);
				}	
			}
			//exit the loop if we are at the end of the text
			if(i == arr.length)
				break;
			char c = arr[i];
			KeywordEdge edge = current.getNonFailureEdge(c);
			//if we are at the root node and there is no match, consume a character
			//and stay at the root node
			if(edge == null && current.isRoot())
			{
				i++;
				//this could also be removed, since we arent moving, but for completeness
				//i decided to leave it in (so it matches the next if statement
				current = current.getFailureEdge().getTo();
			}
			//if we are not at the root node and there is no match, do not consume
			//a character and move to where the failure edge points to, usually the root
			else if(edge == null && !current.isRoot())
			{
				current = current.getFailureEdge().getTo();
			}
			//else we found a matching edge. check to see if this is a terminal and if it is,
			//add the result to the results set, then check the failure edge of this terminal
			//node to see if it points to another terminal. if it does, add that result to
			//the results set too and then add the outgoing edges of that connected node
			//to the set that we need to check for matches
			//
			//what happens when a non-failure edge from current and a non-failure edge from
			//current->failure->non-failures both have same char that matches
			else
			{				
				current = edge.getTo();
				i++;
			}
		}
		
		return results;
	}

}

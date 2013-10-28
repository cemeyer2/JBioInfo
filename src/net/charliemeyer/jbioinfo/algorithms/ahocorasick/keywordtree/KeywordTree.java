package net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;

/**
 * Represents a keyword tree used for the aho-corasick pattern matching algorithm
 * @author chuck
 *
 */
public class KeywordTree 
{
	private KeywordNode root;
	private final ArrayList<KeywordNode> nodes;
	
	/**
	 * constructor
	 */
	public KeywordTree()
	{
		root = new KeywordNode(true);
		nodes = new ArrayList<KeywordNode>();
		nodes.add(root);
	}
	
	/**
	 * adds a pattern to this tree. After all patterns are added to this tree,
	 * generateFailureEdges should be called. generateFailureEdges should also be
	 * called again if a pattern is added after failaureEdges are generated
	 * @param pattern the pattern to add to this tree
	 * @see KeywordTree#generateFailureEdges()
	 */
	public void addPattern(String pattern)
	{
		char[] arr = pattern.toCharArray();
		KeywordNode current = root;
		String soFar = "";
		for(char c : arr)
		{
			soFar += c;
			KeywordEdge edge = current.getNonFailureEdge(c);
			if(edge == null)
			{
				KeywordNode to = new KeywordNode();
				to.addWord(soFar, false);
				edge = new KeywordEdge(current, to, c);
				current.addNonFailureEdge(edge);
				nodes.add(to);
			}
			current = edge.getTo();
		}
		current.replaceWord(0, pattern, true);
	}
	
	/**
	 * generates the failure edges for this tree. This method should be called
	 * after all patterns have been added and needs to be called again if a pattern
	 * was added to this tree after failure edges have been generated
	 */
	public void generateFailureEdges()
	{
		//add loopback for root
		KeywordEdge loopback = new KeywordEdge(root, root);
		root.setFailureEdge(loopback);
		for(int i = 1; i < nodes.size(); i++)
		{
			KeywordNode node = nodes.get(i);
			String word = node.getWords().get(0);
			for(int suffixStart = 1; suffixStart < word.length(); suffixStart++)
			{
				String suffix = word.substring(suffixStart);
				KeywordNode suffixNode = getSuffixNode(suffix);
				if(suffixNode != null)
				{
					KeywordEdge failureEdge = new KeywordEdge(node, suffixNode);
					node.setFailureEdge(failureEdge);
					if(suffixNode.isTerminal())
						node.addWord(suffixNode.getWords().get(0), true);
					break;
				}
			}
			//default case, go back to the root
			if(node.getFailureEdge() == null)
			{
				node.setFailureEdge(new KeywordEdge(node, root));
			}
		}
		
		root.addWord("ROOT", false);
	}
	
	/**
	 * returns a node in the tree that has a word matching the specified suffix
	 * @param suffix the suffix we are searching for
	 * @return a node in the tree that has a word matching the specified suffix
	 */
	private KeywordNode getSuffixNode(String suffix)
	{
		KeywordNode node = root;
		char[] arr = suffix.toCharArray();
		for(char c : arr)
		{
			KeywordEdge edge = node.getNonFailureEdge(c);
			if(edge == null)
				return null;
			else node = edge.getTo();
		}
		return node;
	}
	
	/**
	 * returns the root node of this tree
	 * @return the root node of this tree
	 */
	public KeywordNode getRoot()
	{
		return this.root;
	}
	
	/**
	 * uses graphviz to write this tree out to a png format file. the root node
	 * of this tree will be red, all terminal nodes will be blue. consuming edges
	 * will be solid lines while failure edges will be dashed lines
	 * @param filename the file name to write out to
	 * @throws IOException if something goes bad
	 */
	public void toImage(String filename) throws IOException
	{
		Graph graph = new Graph("KeywordTree",true,false);
		ArrayList<Node> graphNodes = new ArrayList<Node>();
		//create the nodes
		for(KeywordNode node : nodes)
		{
			Node graphNode = new Node(graph);
			graphNode.setAttribute(Node.LABEL_ATTR, node.toString());
			if(node.isTerminal())
			{
				graphNode.setAttribute(Node.COLOR_ATTR, Color.blue);
			}
			graphNodes.add(graphNode);
		}
		//set the root node to be red
		graphNodes.get(0).setAttribute(Node.COLOR_ATTR, Color.red);
		//create the edges
		for(int i = 0; i < nodes.size(); i++)
		{
			//generate character consuming edges
			for(KeywordEdge edge : nodes.get(i).getNonFailureEdges())
			{
				Edge graphEdge = new Edge(graph, graphNodes.get(i), graphNodes.get(nodes.indexOf(edge.getTo())));
				graphEdge.setAttribute(Edge.LABEL_ATTR, ""+edge.getChar());
			}
			//generate failure edge
			KeywordEdge edge = nodes.get(i).getFailureEdge();
			if(edge != null)
			{
				Edge graphEdge = new Edge(graph, graphNodes.get(nodes.indexOf(edge.getFrom())), graphNodes.get(nodes.indexOf(edge.getTo())));
				graphEdge.setAttribute(Edge.STYLE_ATTR, "dashed");
			}
		}
		
		Process p = Runtime.getRuntime().exec("dot -Tpng -o "+filename);
		graph.printGraph(p.getOutputStream());
	}
}

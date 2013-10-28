package net.charliemeyer.cs466.assignment5;

import att.grappa.Node;

public class NodeDegreeTuple implements Comparable<NodeDegreeTuple> {
	Node node;
	int degree;
	
	public NodeDegreeTuple(Node node, int degree)
	{
		this.node = node;
		this.degree = degree;
	}
	
	public Node getNode()
	{
		return node;
	}
	
	public int getDegree()
	{
		return degree;
	}

	@Override
	public int compareTo(NodeDegreeTuple arg0) {
		return -1*new Integer(degree).compareTo(new Integer(arg0.getDegree()));
	}
}

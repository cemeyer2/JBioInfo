package net.charliemeyer.jbioinfo.grappa;

import java.awt.Color;
import java.io.IOException;

import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;

public class GrappaDriver 
{
	public static void main(String[] args) throws IOException
	{
		Graph graph = new Graph("Demo",false,false);
		
		Node n1 = new Node(graph, "n1");
		Node n2 = new Node(graph, "n2");
		Node n3 = new Node(graph, "n3");
		Node n4 = new Node(graph, "n4");
		Node n5 = new Node(graph, "n5");
		Node n6 = new Node(graph, "n6");
		Node n7 = new Node(graph, "n7");
		Node n8 = new Node(graph, "n8");
		Node n9 = new Node(graph, "n9");
		Node n10 = new Node(graph, "n10");
		
		n1.setAttribute(Node.LABEL_ATTR, "A");
		n2.setAttribute(Node.LABEL_ATTR, "G");
		n9.setAttribute(Node.LABEL_ATTR, "C");
		n10.setAttribute(Node.LABEL_ATTR, "U");
		
		n3.setAttribute(Node.LABEL_ATTR, "A");
		n4.setAttribute(Node.LABEL_ATTR, "A");
		n5.setAttribute(Node.LABEL_ATTR, "A");
		n6.setAttribute(Node.LABEL_ATTR, "A");
		n7.setAttribute(Node.LABEL_ATTR, "A");
		n8.setAttribute(Node.LABEL_ATTR, "A");
		
		Edge e1 = new Edge(graph, n1, n2);
		Edge e2 = new Edge(graph, n2, n3);
		Edge e3 = new Edge(graph, n3, n4);
		Edge e4 = new Edge(graph, n4, n5);
		Edge e5 = new Edge(graph, n5, n6);
		Edge e6 = new Edge(graph, n6, n7);
		Edge e7 = new Edge(graph, n7, n8);
		Edge e8 = new Edge(graph, n8, n9);
		Edge e9 = new Edge(graph, n9, n10);
		Edge e10 = new Edge(graph, n1, n10);
		Edge e11 = new Edge(graph, n2, n9);
		
		e10.setAttribute(Edge.COLOR_ATTR, Color.RED);
		e11.setAttribute(Edge.COLOR_ATTR, Color.RED);
		
		graph.printGraph(System.out);
		
		Process p = Runtime.getRuntime().exec("neato -Tpng -o out.png");
		
		graph.printGraph(p.getOutputStream());
	}
}

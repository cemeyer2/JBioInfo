package net.charliemeyer.cs466.assignment5;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;

import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;

public class Assignment5 
{
	TreeMap<String, Node> nodes;
	TreeMap<String, Edge> edges;
	Graph graph;
	int[] degreeCounts;
	double meanDegree;
	ArrayList<NodeDegreeTuple> tuples;
	
	public static void main(String[] args) throws Exception
	{
		new Assignment5();
	}
	
	public Assignment5() throws Exception
	{
		nodes = new TreeMap<String, Node>();
		edges = new TreeMap<String, Edge>();
		tuples = new ArrayList<NodeDegreeTuple>();

		buildGraph();
		calcDegreeProb();
		printGraph();
		
		parseGO();
	}
	
	private void buildGraph() throws Exception
	{
		String filename = "BIOGRID-ORGANISM-Drosophila_melanogaster-2.0.59.tab.txt";
		BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
		//advance past header stuff
		for(int i = 0; i < 36; i++)
		{
			in.readLine();
		}
		
		graph = new Graph("Network",false,false);
		
		String line = "";
		while((line = in.readLine()) != null)
		{
			String[] split = line.split("\t");
			String id1 = split[0];
			String id2 = split[1];
			Node node1 = getNode(graph, id1);
			Node node2 = getNode(graph, id2);
			Edge edge = getEdge(graph, id1, id2);
		}
		
		in.close();
		createImage("assignment5network.png",graph);
	}
	
	private Node getNode(Graph graph, String id)
	{
		Node node = nodes.get(id);
		if(node == null)
		{
			node = new Node(graph);
			node.setAttribute(Node.LABEL_ATTR, id);
			nodes.put(id, node);
		}
		return node;
	}
	
	private Edge getEdge(Graph graph, String id1, String id2)
	{
		Edge edge = edges.get(id1+"<-->"+id2);
		if(edge == null)
		{
			edge = new Edge(graph, getNode(graph, id1), getNode(graph, id2));
			edges.put(id1+"<-->"+id2, edge);
		}
		return edge;
	}
	
	private void calcDegreeProb()
	{
		int maxDegree = 0;
		Iterator<Node> nodesIter = nodes.values().iterator();
		
		while(nodesIter.hasNext())
		{
			Node node = nodesIter.next();
			int degree = getDegree(node);
			if(degree > maxDegree)
			{
				maxDegree = degree;
			}
			tuples.add(new NodeDegreeTuple(node,degree));
		}
		degreeCounts = new int[maxDegree+1];
		for(int i = 0; i < degreeCounts.length; i++)
		{
			degreeCounts[i] = 0;
		}
		
		nodesIter = nodes.values().iterator();
		while(nodesIter.hasNext())
		{
			Node node = nodesIter.next();
			int degree = getDegree(node);
			degreeCounts[degree] = degreeCounts[degree]+1;
		}
		
		double accum = 0;
		for(int i = 0; i < degreeCounts.length; i++)
		{
			accum += i * degreeCounts[i];
		}
		meanDegree = accum / (double)nodes.size();
		
		Collections.sort(tuples);
	}
	
	private int getDegree(Node node)
	{
		Enumeration edges = node.edgeElements();
		int degree = 0;
		while(edges.hasMoreElements())
		{
			edges.nextElement();
			degree++;
		}
		return degree;
	}
	
	private void printGraph() throws Exception
	{
		FileWriter o = new FileWriter(new File("interactions.txt"));
		Iterator<Node> nodesIter = nodes.values().iterator();
		
		System.out.println("Nodes:");
		o.write("Nodes:\n");
		while(nodesIter.hasNext())
		{
			System.out.println(nodesIter.next().getAttributeValue(Node.LABEL_ATTR));
			o.write(nodesIter.next().getAttributeValue(Node.LABEL_ATTR)+"\n");
		}
		
		System.out.println();
		System.out.println("Edges:");
		
		o.write("\nEdges:\n");
		
		Iterator<Edge> edgesIter = edges.values().iterator();
		
		while(edgesIter.hasNext())
		{
			Edge edge = edgesIter.next();
			Node source = edge.getHead();
			Node target = edge.getTail();
			
			String id1 = (String)source.getAttributeValue(Node.LABEL_ATTR);
			String id2 = (String)target.getAttributeValue(Node.LABEL_ATTR);
			
			System.out.println(id1+"  <--->  "+id2);
			o.write(id1+"  <--->  "+id2+"\n");
		}
		System.out.println();
		
		System.out.println("Graph Statistics:");
		System.out.println("Node count: "+nodes.size());
		System.out.println("Edge count: "+edges.size());
		System.out.println();
		
		o.write("Graph Statistics:\n");
		o.write("Node count: "+nodes.size()+"\n");
		o.write("Edge count: "+edges.size()+"\n");
		o.write("\n");
		
		FileWriter out = new FileWriter(new File("degrees.csv"));
		
		System.out.println("Degree Probabilities:");
		System.out.println("Degree,Count,TotalNodes,Prob");
		out.write("Degree,Count,TotalNodes,Prob\n");
		
		o.write("Degree Probabilities:\n");
		o.write("Degree,Count,TotalNodes,Prob");
		
		for(int i = 0; i < degreeCounts.length; i++)
		{
			double prob = (double)degreeCounts[i]/(double)nodes.size();
			String line = i+","+degreeCounts[i]+","+nodes.size()+","+prob;
			System.out.println(line);
			out.write(line+"\n");
			o.write(line+"\n");
		}
		System.out.println();
		o.write("\n");
		out.flush();
		out.close();
		
		System.out.println("Mean Degree: "+meanDegree);
		o.write("Mean Degree: "+meanDegree+"\n\n");
		
		
		System.out.println();
		System.out.println("100 Highest Degree Nodes:");
		o.write("100 Highest Degree Nodes:\n");
		for(int i = 0; i < 100; i++)
		{
			NodeDegreeTuple tuple = tuples.get(i);
			//System.out.println("Node: "+tuple.getNode().getAttributeValue(Node.LABEL_ATTR)+", degree "+tuple.getDegree());
			String id = (String)tuple.getNode().getAttributeValue(Node.LABEL_ATTR);
			if(id.startsWith("Dmel"))
			{
				id = id.substring(5);
			}
			System.out.println(id+"  --  "+tuple.getDegree());
			o.write(id+"  --  "+tuple.getDegree()+"\n");
		}
		
		System.out.println();
		o.write("\n");
		o.flush();
		o.close();
	}
	
	private void parseGO() throws Exception
	{
		FileWriter o = new FileWriter("interactions.txt",true);
		ArrayList<CategoryScoreTuple> tuples = new ArrayList<CategoryScoreTuple>();
		String filename = "D_melanogaster.hBP.27239";
		BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
		in.readLine();
		in.readLine();
		String line = "";
		while((line = in.readLine()) != null)
		{
			String[] split = line.split("\t");
			try
			{
				tuples.add(new CategoryScoreTuple(split[5], Double.parseDouble(split[4])));
			}
			catch(Throwable t){}
		}
		Collections.sort(tuples);
		
		System.out.println("Categories and scores:");
		o.write("Categories and scores:\n");
		for(int i = 0; i < tuples.size(); i++)
		{
			CategoryScoreTuple tuple = tuples.get(i);
			System.out.println((i+1)+". Category: "+tuple.getCategory()+", score: "+tuple.getScore());
			o.write((i+1)+". Category: "+tuple.getCategory()+", score: "+tuple.getScore()+"\n");
		}
		o.flush();
		o.close();
	}
	
	public void createImage(String filename, Graph graph) throws Exception
	{
		
		
		Process p = Runtime.getRuntime().exec("neato -Tpng -o "+filename);
		
		graph.printGraph(p.getOutputStream());
		p.waitFor();
	}
}

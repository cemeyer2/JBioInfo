package net.charliemeyer.jbioinfo.algorithms.nussinov;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;

public class NussinovStructurePrediction {
	
	private char[] sequence;
	private NussinovScoreTable table;
	
	public NussinovStructurePrediction(String sequence)
	{
		this.sequence = sequence.toCharArray();
		table = new NussinovScoreTable(this.sequence.length);
		
		//init the table
		for(int i = 0; i < this.sequence.length; i++)
		{
			table.setScore(i, i, 0);
			if(i != 0)
			{
				table.setScore(i, i-1, 0);
				//dont know if this is required or not
				//table.setScore(i-1, i, 0);
			}
		}
	}
	
	public double getScore()
	{
		return getScore(0, sequence.length-1);
	}
	
	private double getScore(int i, int j)
	{
		if(table.getScore(i, j) != null)
			return table.getScore(i, j);
		double s1 = getScore(i + 1, j);
		double s2 = getScore(i, j - 1);
		double s3 = 0;
		if(isMatch(sequence[i], sequence[j]))
		{
			s3 = getScore(i+1, j-1) + 1;
		}
		double s4 = 0;
		for(int k = i+1; k < j; k++)
		{
			double temp = getScore(i,k) + getScore(k+1, j);
			if(temp > s4)
			{
				s4 = temp;
			}
		}
		double max = 0;
		if(s1 > max)
			max = s1;
		if(s2 > max)
			max = s2;
		if(s3 > max)
			max = s3;
		if(s4 > max)
			max = s4;
		table.setScore(i, j, max);
		return max;
	}
	
	private boolean isMatch(char a, char b)
	{
		if((a == 'A' && b == 'U') || (a == 'U' && b == 'A'))
			return true;
		if((a == 'C' && b == 'G') || (a == 'G' && b == 'C'))
			return true;
		return false;
	}
	
	public ArrayList<int[]> traceback()
	{
		ArrayList<int[]> pairings = new ArrayList<int[]>();
		Stack<int[]> stack = new Stack<int[]>();
		stack.push(new int[]{0,sequence.length-1});
		while(!stack.isEmpty())
		{
			int[] arr = stack.pop();
			int i = arr[0];
			int j = arr[1];
			if(i >= j)
			{
				continue;
			}
			else if(table.getScore(i+1, j).doubleValue() == table.getScore(i, j).doubleValue())
			{
				stack.push(new int[]{i+1,j});
			}
			else if(table.getScore(i, j-1).doubleValue() == table.getScore(i, j).doubleValue())
			{
				stack.push(new int[]{i,j-1});
			}
			else if(table.getScore(i+1, j-1).doubleValue()+1 == table.getScore(i, j).doubleValue())
			{
				int[] pair = new int[]{i+1,j-1};
				pairings.add(pair);
				stack.push(pair);
			}
			else
			{
				for(int k = i+1; k < j; k++)
				{
					if(table.getScore(i, k).doubleValue()+table.getScore(k+1, j).doubleValue() == table.getScore(i, j).doubleValue())
					{
						stack.push(new int[]{i,k});
						stack.push(new int[]{k+1,j});
						break;
					}
				}
			}
		}
		return pairings;
	}
	
	public void createImage(String filename) throws IOException
	{
		Graph graph = new Graph("NussinovStructurePrediction",false,false);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < sequence.length; i++)
		{
			char c = sequence[i];
			Node node = new Node(graph);
			node.setAttribute(Node.LABEL_ATTR, c+"");
			nodes.add(node);
		}
		for(int i = 0; i < nodes.size()-1; i++)
		{
			@SuppressWarnings("unused")
			Edge edge = new Edge(graph, nodes.get(i+1), nodes.get(i));
		}
		ArrayList<int[]> pairs = traceback();
		for(int[] arr : pairs)
		{
			Node head = nodes.get(arr[0]-1);
			Node tail = nodes.get(arr[1]+1);
			Edge edge = new Edge(graph, tail, head);
			edge.setAttribute(Edge.COLOR_ATTR, Color.RED);
		}
		
		nodes.get(0).setAttribute(Node.LABEL_ATTR, sequence[0]+"_start");
		nodes.get(0).setAttribute(Node.COLOR_ATTR, Color.GREEN);
		nodes.get(nodes.size()-1).setAttribute(Node.LABEL_ATTR, sequence[nodes.size()-1]+"_end");
		nodes.get(nodes.size()-1).setAttribute(Node.COLOR_ATTR, Color.RED);
		
		Process p = Runtime.getRuntime().exec("neato -Tpng -o "+filename);
		
		graph.printGraph(p.getOutputStream());
	}
}

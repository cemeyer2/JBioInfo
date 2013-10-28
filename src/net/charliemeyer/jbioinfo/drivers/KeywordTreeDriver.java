package net.charliemeyer.jbioinfo.drivers;

import java.io.IOException;

import net.charliemeyer.jbioinfo.algorithms.ahocorasick.keywordtree.KeywordTree;

public class KeywordTreeDriver {

	public static void main(String[] args) throws IOException
	{
		KeywordTree tree = new KeywordTree();
		tree.addPattern("he");
		tree.addPattern("she");
		tree.addPattern("his");
		tree.addPattern("hers");
		
		tree.generateFailureEdges();
		
		tree.toImage("KeywordTree.png");
		
		KeywordTree tree2 = new KeywordTree();
		tree2.addPattern("apple");
		tree2.addPattern("apropos");
		tree2.addPattern("banana");
		tree2.addPattern("bandana");
		tree2.addPattern("orange");
		
		//tree2.generateFailureEdges();
		
		tree2.toImage("KeywordTree2.png");
	}
}

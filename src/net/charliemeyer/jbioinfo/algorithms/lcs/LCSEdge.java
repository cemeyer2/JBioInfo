package net.charliemeyer.jbioinfo.algorithms.lcs;

public class LCSEdge {

	private double weight;
	private LCSNode from, to;
	private char seq1char, seq2char;
	
	protected LCSEdge(double weight, LCSNode from, LCSNode to, char seq1char, char seq2char)
	{
		this.weight = weight;
		this.from = from;
		this.to = to;
		this.seq1char = seq1char;
		this.seq2char = seq2char;
	}

	protected double getWeight() {
		return weight;
	}

	protected LCSNode getFrom() {
		return from;
	}

	protected LCSNode getTo() {
		return to;
	}

	protected char getSeq1char() {
		return seq1char;
	}

	protected char getSeq2char() {
		return seq2char;
	}
}

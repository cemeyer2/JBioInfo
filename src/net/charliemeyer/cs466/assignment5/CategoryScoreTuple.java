package net.charliemeyer.cs466.assignment5;

public class CategoryScoreTuple implements Comparable<CategoryScoreTuple> {
	String category;
	double score;
	
	public CategoryScoreTuple(String category, double score)
	{
		this.category = category;
		this.score = score;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public double getScore()
	{
		return score;
	}

	@Override
	public int compareTo(CategoryScoreTuple arg0) {
		return -1*new Double(score).compareTo(new Double(arg0.getScore()));
	}
}

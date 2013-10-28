package net.charliemeyer.jbioinfo.drivers;

import java.util.List;
import java.util.Map;

import net.charliemeyer.jbioinfo.algorithms.ahocorasick.AhoCorasickPatternMatcher;

public class AhoCorasickDriver {
	
	public static void main(String[] args)
	{
		AhoCorasickPatternMatcher matcher = new AhoCorasickPatternMatcher();
		
		matcher.addPattern("he");
		matcher.addPattern("she");
		matcher.addPattern("his");
		matcher.addPattern("hers");
		
		String text = "hishishershershishehehersheheshisesheshe";
		
		Map<String, List<Integer>> results = matcher.match(text);
		
		for(String key : results.keySet())
		{
			System.out.println(key + "  --  "+results.get(key));
		}
	}
}
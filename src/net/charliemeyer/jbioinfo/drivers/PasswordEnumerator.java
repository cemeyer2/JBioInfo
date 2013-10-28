package net.charliemeyer.jbioinfo.drivers;

import java.util.List;

import net.charliemeyer.jbioinfo.enumuerator.AlphabetEnumerator;

public class PasswordEnumerator {
	public static void main(String[] args){
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		AlphabetEnumerator enumerator = new AlphabetEnumerator(alphabet, 8);
		List<String> strings = enumerator.getStrings();
		
	}
}

package com.smart.mis.shared;

public class KeyGenerator {

	private static int key = 10;
	
	public static int genKey(){
		return ++key;
	}
}

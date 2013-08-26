package com.smart.mis.shared.security;

public class Function {
	// 0x00 means NONE
	public static final byte NONE = 0x00; // 0
	public static final byte SALE = 0x01; // 1
	public static final byte PRODUCTION = 0x02; //2
	public static final byte INVENTORY = 0x04; //4
	public static final byte PURCHASING = 0x08; //8
	public static final byte FINANCIAL = 0x10; //16
	public static final byte REPORT = 0x20; //32
	public static final byte SECURITY = 0x40; //64
	public static final byte ALL = 0x7F; //127
}

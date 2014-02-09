package com.smart.mis.shared.security;

public class Role {
	public static final byte NON_USER = 0x00; // 0
	public static final byte STAFF = 0x01; // 1 Basic transaction on sale, production, inventory, purchasing, financial
	public static final byte OWNER = 0x02; // 2 Approved Quotation, Production Plan, Purchasing Request, Report
	public static final byte ADMIN = 0x04; // 4 Security
	//public static final byte ALL = 0x07; // 7 ALL
}

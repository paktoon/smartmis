package com.smart.mis.client.layout;

import com.smartgwt.client.widgets.tree.TreeNode;

public class FunctionTreeNode {

	static class SubjectTreeNode extends TreeNode {  
		public SubjectTreeNode(String nodeId, String parentId, String name) {  
	        setAttribute("nodeId", nodeId);  
	        setAttribute("parentId", parentId);
	        setAttribute("Name", name);  
	    }  
		
		public SubjectTreeNode(String nodeId, String parentId, String name, String icon) {  
	        setAttribute("nodeId", nodeId);  
	        setAttribute("parentId", parentId);
	        setAttribute("Name", name);  
	        setAttribute("icon", icon); 
	    } 
		
		public SubjectTreeNode(String nodeId, String parentId, String name, String icon, String pname) {  
	        setAttribute("nodeId", nodeId);  
	        setAttribute("parentId", parentId);
	        setAttribute("Name", name);  
	        setAttribute("icon", icon); 
	        setAttribute("pName", pname);
	    } 
	}
    
	public static final TreeNode[] getNode(int nodeId){
		switch (nodeId) {
		case 1: 
				return getSaleNode();
		case 2: 
				return getProductionNode();
		case 3: 
				return getInventoryNode();
		case 4: 
				return getPurchasingNode();
		case 5: 
				return getFinancialNode();
		case 6: 
				return getReportNode();
		case 7: 
				return getSecurityNode();
		default : return null;
		}
	}
	
    private static final TreeNode[] saleNode = new TreeNode[] {  
        new SubjectTreeNode("11", "1", "ข้อมูลลูกค้า" , "icons/16/vcard_edit.png"),  
//        new SubjectTreeNode("111", "11", "เพิ่มข้อมูลลูกค้า"),  
//        new SubjectTreeNode("112", "11", "แสดง/แก้ไขข้อมูลลูกค้า"),
        
        new SubjectTreeNode("12", "1", "ใบเสนอราคา", "icons/16/sales-report-icon.png"),  
//        new SubjectTreeNode("121", "12", "จัดทำใบเสนอราคา"),   
//        new SubjectTreeNode("122", "12", "แสดง/แก้ไขใบเสนอราคา") ,   
//        new SubjectTreeNode("123", "12", "อนุมัติใบเสนอราคา"), 
        
        new SubjectTreeNode("13", "1", "รายการขาย" , "icons/16/coins.png"),
//        new SubjectTreeNode("131", "13", "รับคำสั่งซื้อ"),   
//        new SubjectTreeNode("132", "13", "ยกเลิกรายการขาย"),   
//        new SubjectTreeNode("133", "13", "นำส่งสินค้า"),   
//        new SubjectTreeNode("134", "13", "จัดทำใบแจ้งหนี้"),   
//        new SubjectTreeNode("135", "13", "ตรวจสอบสถานะคำสั่งซื้อ"),

        new SubjectTreeNode("16", "1", "ใบแจ้งหนี้"),
        
        new SubjectTreeNode("14", "1", "ตรวจสอบสถานะคำสั่งซื้อ" , "icons/16/folder_out.png"), 
        
        new SubjectTreeNode("15", "1", "รายงาน" , "icons/16/chart_bar.png"), 
        new SubjectTreeNode("151", "15", "การเสนอราคา", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("152", "15", "การขาย", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("153", "15", "การนำส่งสินค้า", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("154", "15", "การแจ้งหนี้", "icons/16/chart_bar.png", "รายงาน")
    };
    
    public static TreeNode[] getSaleNode(){
    	return saleNode;
    }
    
    private static final TreeNode[] productionNode = new TreeNode[] {  
        new SubjectTreeNode("21", "2", "ข้อมูลช่าง", "icons/16/vcard_edit.png"),  
//        new SubjectTreeNode("211", "21", "เพื่มข้อมูลช่าง"),  
//        new SubjectTreeNode("212", "21", "แสดง/แก้ไขข้อมูลช่าง"),
        
        new SubjectTreeNode("22", "2", "ข้อมูลสินค้า", "icons/16/star-silver-icon.png"),   
//        new SubjectTreeNode("221", "22", "เพิ่มข้อมูลสินค้า"),   
//        new SubjectTreeNode("222", "22", "แสดง/แก้ไขข้อมูลสินค้า") ,
        
        new SubjectTreeNode("23", "2", "แผนการผลิต", "icons/16/calendar.png"),
//        new SubjectTreeNode("231", "23", "วางแผนการผลิต"),   
//        new SubjectTreeNode("232", "23", "แสดง/แก้ไขแผนการผลิต"),   
//        new SubjectTreeNode("233", "23", "อนุมัติแผนการผลิต"),
        
        new SubjectTreeNode("24", "2", "ผลิตสินค้า", "icons/16/production-icon.png"),   
//        new SubjectTreeNode("241", "24", "คำสั่งผลิต"),   
//        new SubjectTreeNode("242", "24", "รับสินค้า"),   
//        new SubjectTreeNode("243", "24", "โอนสินค้า"),
        
        new SubjectTreeNode("25", "2", "รายงาน" , "icons/16/chart_bar.png") , 
        new SubjectTreeNode("251", "25", "แผนการผลิต", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("252", "25", "การโอนสินค้า", "icons/16/chart_bar.png", "รายงาน")
    };
    
    public static TreeNode[] getProductionNode(){
    	return productionNode;
    }
    
    private static final TreeNode[] inventoryNode = new TreeNode[] {  
        new SubjectTreeNode("31", "3", "เบิกจ่ายสินค้า"),  
        new SubjectTreeNode("32", "3", "เบิกจ่ายวัตถุดิบ"),
        new SubjectTreeNode("33", "3", "บันทึกรับสินค้า"),
        new SubjectTreeNode("34", "3", "บันทึกรับวัตถุดิบ"),
        new SubjectTreeNode("35", "3", "บันทึกคืนวัตถุดิบ"),
        new SubjectTreeNode("36", "3", "ปรับปรุงยอดคงเหลือ"),
        new SubjectTreeNode("361", "36", "สินค้าคงคลัง"),
        new SubjectTreeNode("362", "36", "วัตถุดิบคงคลัง"),
        
//        new SubjectTreeNode("311", "31", "เบิกจ่าย"),  
//        new SubjectTreeNode("312", "31", "บันทึกรับ"), 
//        new SubjectTreeNode("313", "31", "ปรับปรุงยอด"), 
//        
//        new SubjectTreeNode("32", "3", "วัตถุดิบคงคลัง"),  
//        new SubjectTreeNode("321", "32", "เบิกจ่าย"),   
//        new SubjectTreeNode("322", "32", "บันทึกรับ") ,  
//        new SubjectTreeNode("323", "32", "บันทึกคืน") ,  
//        new SubjectTreeNode("324", "32", "ปรับปรุงยอด") ,
        
        new SubjectTreeNode("37", "3", "รายงาน" , "icons/16/chart_bar.png"), 
        new SubjectTreeNode("371", "37", "สินค้าและวัตถุดิบคงคลัง" , "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("372", "37", "เบิกจ่ายสินค้าและวัตถุดิบ" , "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("373", "37", "รับสินค้าและวัตถุดิบ" , "icons/16/chart_bar.png","รายงาน"),
        
    };
    
    public static TreeNode[] getInventoryNode(){
    	return inventoryNode;
    }
    
    private static final TreeNode[] purchasingNode = new TreeNode[] {  
        new SubjectTreeNode("41", "4", "ข้อมูลวัตถุดิบ" , "icons/16/basket-full-icon.png"),  
//        new SubjectTreeNode("411", "41", "เพิ่มข้อมูลวัตถุดิบ"),  
//        new SubjectTreeNode("412", "41", "แสดง/แก้ไขข้อมูลวัตถุดิบ"),
        
        new SubjectTreeNode("42", "4", "ข้อมูลผู้จำหน่าย", "icons/16/vcard_edit.png"),  
//        new SubjectTreeNode("421", "42", "เพิ่มข้อมูลผู้จำหน่าย"),   
//        new SubjectTreeNode("422", "42", "แสดง/แก้ไขข้อมูลผู้จำหน่าย") ,  
//        new SubjectTreeNode("423", "42", "แสดงประวัติการจัดซื้อ") ,
        
        new SubjectTreeNode("43", "4", "รายการเสนอซื้อ", "icons/16/shopping-cart-add-icon.png"),
//        new SubjectTreeNode("431", "43", "จัดทำรายการเสนอซื้อ"),   
//        new SubjectTreeNode("432", "43", "แสดง/แก้ไขรายการเสนอซื้อ"),  
//        new SubjectTreeNode("433", "43", "อนุมัติรายการเสนอซื้อ"),  
//        new SubjectTreeNode("434", "43", "จัดทำใบสั่งซื้อ"),
        
        new SubjectTreeNode("44", "4", "คำสั่งซื้อ", "icons/16/shopping-cart-insert-icon.png"),
        
        new SubjectTreeNode("45", "4", "รายงาน" , "icons/16/chart_bar.png"),
        new SubjectTreeNode("451", "45", "การเสนอซื้อ", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("452", "45", "คำสั่งซื้อ", "icons/16/chart_bar.png", "รายงาน")
        
    };
    
    public static TreeNode[] getPurchasingNode(){
    	return purchasingNode;
    }
    
    private static final TreeNode[] financialNode = new TreeNode[] {  
        new SubjectTreeNode("51", "5", "บันทึกรับชำระเงิน"),  
        new SubjectTreeNode("52", "5", "จ่ายชำระหนี้ค่าวัตถุดิบ"),  
        new SubjectTreeNode("53", "5", "จ่ายชำระหนี้ค่าจ้างผลิต"),
        
        new SubjectTreeNode("54", "5", "รายงาน" , "icons/16/chart_bar.png"),
        new SubjectTreeNode("541", "54", "การรับชำระเงิน", "icons/16/chart_bar.png", "รายงาน"),   
        new SubjectTreeNode("542", "54", "การจ่ายชำระหนี้", "icons/16/chart_bar.png", "รายงาน")
        
    };
    
    public static TreeNode[] getFinancialNode(){
    	return financialNode;
    }
    
    private static final TreeNode[] reportNode = new TreeNode[] {  
        new SubjectTreeNode("61", "6", "รายงานวิเคราะห์การขายสินค้า" , "icons/16/cubes_all.png"), 
        new SubjectTreeNode("62", "6", "รายงานสรุป", "icons/16/chart_bar.png"),
        new SubjectTreeNode("621", "62", "การผลิตสินค้า", "icons/16/chart_bar.png", "รายงานสรุป"),    
        new SubjectTreeNode("622", "62", "สินค้าและวัตถุดิบคงคลัง", "icons/16/chart_bar.png", "รายงานสรุป"),  
        new SubjectTreeNode("623", "62", "การจัดซื้อวัตถุดิบ", "icons/16/chart_bar.png", "รายงานสรุป"),  
        new SubjectTreeNode("624", "62", "รายรับรายจ่าย", "icons/16/chart_bar.png", "รายงานสรุป")
    };
    
    public static TreeNode[] getReportNode(){
    	return reportNode;
    }
    
    private static final TreeNode[] securityNode = new TreeNode[] {  
        new SubjectTreeNode("71", "7", "สิทธิการใช้งาน" , "icons/16/safe-icon.png"),  
        new SubjectTreeNode("72", "7", "ข้อมูลผู้ใช้ระบบ" , "icons/16/person.png")
    };
    
    public static TreeNode[] getSecurityNode(){
    	return securityNode;
    }
}  

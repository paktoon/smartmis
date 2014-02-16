package com.smart.mis.datastore;


import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.smart.mis.shared.prodution.ProductType;

public class StoreUtil {
	
	public static Boolean insert(String type, String id) {
		if (!hasEntity(type, id)){
			try {
				System.out.println("*** Creating new " + type + " on server!!");
				Entity ent = new Entity(type, id);
				ent.setProperty("test", "test");
				Util.persistEntity(ent);
				return hasEntity(type, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	public static Boolean insertCustomer(String cid, String cus_name,
			String cus_phone, String contact_name, String contact_phone,
			String contact_email, String address, String street, String city,
			String state, Integer postal, String country, String cus_type,
			String bus_type, String cus_group, String url, String zone) {
		if (!hasEntity("CUSTOMER", cid)){
			try {
				Entity ent = new Entity("CUSTOMER", cid);
		        ent.setProperty("cus_name",cus_name);  
		        ent.setProperty("cus_phone", cus_phone);
		        ent.setProperty("contact_name", contact_name);  
		        ent.setProperty("contact_phone", contact_phone); 
		        ent.setProperty("contact_email", contact_email);  
		        ent.setProperty("address", address);
		        ent.setProperty("street", street);
		        ent.setProperty("city", city);
		        ent.setProperty("state", state);
		        ent.setProperty("country", country);
		        ent.setProperty("postal", postal);
		        ent.setProperty("cus_type", cus_type);
		        ent.setProperty("bus_type", bus_type);
		        ent.setProperty("cus_group", cus_group);
		        ent.setProperty("url", url);
		        ent.setProperty("zone", zone);
				Util.persistEntity(ent);
				return hasEntity("CUSTOMER", cid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	public static Boolean insertProduct(String pid, String name, String name_th,
			Double weight, Double price, String type, Integer inStock,
			Integer reserved, String imgUrl, Double size, Double width,
			Double length, Double height, Double diameter, Double thickness,
			Boolean makeToOrder) {
		if (!hasEntity("PRODUCT", pid)){
			try {
				Entity ent = new Entity("PRODUCT", pid);
		        ent.setProperty("name",name);
		        ent.setProperty("name_th",name_th);
		        ent.setProperty("weight", weight);
		        ent.setProperty("price", price);
		        ent.setProperty("type", type);
		        ent.setProperty("unit", ProductType.getUnit(type));  
		        ent.setProperty("inStock", inStock);
		        ent.setProperty("reserved", reserved);
		        ent.setProperty("remain", inStock - reserved);
		        ent.setProperty("imgUrl", imgUrl); 
		        ent.setProperty("size", size);
		        ent.setProperty("width", width); //cm
		        ent.setProperty("length", length); // cm
		        ent.setProperty("height", height); 
		        ent.setProperty("diameter", diameter); //mm
		        ent.setProperty("thickness", thickness); //mm
		        ent.setProperty("makeToOrder", makeToOrder);
				Util.persistEntity(ent);
				return hasEntity("PRODUCT", pid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	public static Boolean insertSmith(String smid, String name, String phone1,
			String phone2, String email, String address, String street,
			String city, String state, Integer postal, String type) {
		if (!hasEntity("SMITH", smid)){
			try {
				Entity ent = new Entity("SMITH", smid);
		        ent.setProperty("name",name);  
		        ent.setProperty("phone1", phone1);
		        ent.setProperty("phone2", phone2);
		        ent.setProperty("email", email);  
		        ent.setProperty("address", address); 
		        ent.setProperty("street", street); 
		        ent.setProperty("city", city); 
		        ent.setProperty("state", state); 
		        ent.setProperty("postal", postal); 
		        ent.setProperty("type", type);
				Util.persistEntity(ent);
				return hasEntity("SMITH", smid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	public static Boolean insertMaterial(String mid, String mat_name, String desc,
			String type, String unit, Double weight, Double safety,
			Double inStock, Double reserved) {
		if (!hasEntity("MATERIAL", mid)){
			try {
				Entity ent = new Entity("MATERIAL", mid);
		        ent.setProperty("mat_name",mat_name);  
		        ent.setProperty("desc", desc);
		        ent.setProperty("type", type); // แร่เงิน, แมกกาไซต์, พลอยประดับ, อื่นๆ
		        ent.setProperty("unit", unit);
		        ent.setProperty("weight", weight);
		        ent.setProperty("safety", safety);
		        ent.setProperty("inStock", inStock);
		        ent.setProperty("reserved", reserved);
		        ent.setProperty("remain", inStock - reserved);
				Util.persistEntity(ent);
				return hasEntity("MATERIAL", mid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	public static Boolean insertSuppiler(String id, String name, String phone1,
			String phone2, String email, String address, String street,
			String city, String state, Integer postal, String fax,
			Integer leadtime, String itemList) {
		if (!hasEntity("SUPPILER", id)){
			try {
				Entity ent = new Entity("SUPPILER", id);
		        ent.setProperty("sup_name", name);  
		        ent.setProperty("sup_phone1", phone1);
		        ent.setProperty("sup_phone2", phone2);
		        ent.setProperty("email", email);  
		        ent.setProperty("address", address);
		        ent.setProperty("street", street);
		        ent.setProperty("city", city);
		        ent.setProperty("state", state);
		        ent.setProperty("postal", postal);
		        ent.setProperty("fax", fax);  
		        ent.setProperty("leadtime", leadtime);
		        ent.setProperty("list", itemList);
				Util.persistEntity(ent);
				return hasEntity("SUPPILER", id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	//SALE
	public static Boolean insertQuotation(String quote_id, String cid,
			String cus_name, String payment_model, Integer credit,
			String cus_type, String bus_type, String cus_group, String zone,
			Date from, Date to, Date delivery, Double total_weight,
			Integer total_amount, Double netExclusive, Date created_date,
			Date modified_date, String created_by, String modified_by,
			String comment, String status) {
		if (!hasEntity("QUOTATION", quote_id)){
			try {
				Entity ent = new Entity("QUOTATION", quote_id);
		        ent.setProperty("cid",cid);  
		        ent.setProperty("cus_name",cus_name);
		        ent.setProperty("payment_model",payment_model);
		        ent.setProperty("credit",credit);
		        
		        ent.setProperty("cus_type",cus_type);
		        ent.setProperty("bus_type",bus_type);
		        ent.setProperty("cus_group",cus_group);
		        ent.setProperty("zone",zone);
		        
		        ent.setProperty("from", from); 
		        ent.setProperty("to", to);
		        ent.setProperty("delivery", delivery);
		        ent.setProperty("total_weight", total_weight);  
		        ent.setProperty("total_amount", total_amount); 
		        ent.setProperty("netExclusive", netExclusive);
		        ent.setProperty("tax", netExclusive * 0.07);
		        ent.setProperty("netInclusive", netExclusive * 1.07);  
		        ent.setProperty("created_date", created_date); 
		        ent.setProperty("created_by", created_by);
		        ent.setProperty("modified_date", modified_date);
		        ent.setProperty("modified_by", modified_by);
		        ent.setProperty("comment", comment);
		        ent.setProperty("status", status);
				Util.persistEntity(ent);
				return hasEntity("QUOTATION", quote_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertQuotationItem(String sub_quote_id, String quote_id,
			String pid, String name, Double weight, Double price, String type,
			String unit, Integer quote_amount, Boolean status) {
		if (!hasEntity("QUOTATION_ITEM", sub_quote_id)){
			try {
				Entity ent = new Entity("QUOTATION_ITEM", sub_quote_id);
		        ent.setProperty("quote_id", quote_id);
		        ent.setProperty("pid", pid);
		        ent.setProperty("name",name); 
		        ent.setProperty("weight", weight);
		        ent.setProperty("price", price);
		        ent.setProperty("type", type);  
		        ent.setProperty("unit", unit); 
		        ent.setProperty("quote_amount", quote_amount);
		        ent.setProperty("status", status);
				Util.persistEntity(ent);
				return hasEntity("QUOTATION_ITEM", sub_quote_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertSale(String sale_id, String quote_id,
			String invoice_id, String cid, String cus_name,
			String payment_model, Integer credit, String cus_type,
			String bus_type, String cus_group, String zone, Date delivery,
			Double total_weight, Integer total_amount, Double netExclusive,
			Date created_date, Date modified_date, String created_by,
			String modified_by, String status, String purchase_id, Date due_date) {
		if (!hasEntity("SALE", sale_id)){
			try {
				Entity ent = new Entity("SALE", sale_id);
		        ent.setProperty("quote_id", quote_id);
		        ent.setProperty("invoice_id", invoice_id);
		        
		        ent.setProperty("cid",cid);  
		        ent.setProperty("cus_name",cus_name);
		        ent.setProperty("payment_model",payment_model);
		        ent.setProperty("credit",credit);
		        
		        ent.setProperty("cus_type",cus_type);
		        ent.setProperty("bus_type",bus_type);
		        ent.setProperty("cus_group",cus_group);
		        ent.setProperty("zone",zone);
		        
		        ent.setProperty("delivery", delivery);
		        ent.setProperty("total_weight", total_weight);  
		        ent.setProperty("total_amount", total_amount); 
		        ent.setProperty("netExclusive", netExclusive);
		        ent.setProperty("tax", netExclusive * 0.07);
		        ent.setProperty("netInclusive", netExclusive * 1.07);  
		        ent.setProperty("created_date", created_date); 
		        ent.setProperty("created_by", created_by);
		        ent.setProperty("modified_date", modified_date);
		        ent.setProperty("modified_by", modified_by);
		        //ent.setProperty("comment", comment);
		        ent.setProperty("status", status);
		        ent.setProperty("purchase_id", purchase_id);
		        ent.setProperty("due_date", due_date);
				Util.persistEntity(ent);
				return hasEntity("SALE", sale_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertSaleItem(String sub_sale_id, String sale_id,
			String pid, String name, Double weight, Double price, String type,
			String unit, Integer sale_amount, Boolean status) {
		if (!hasEntity("SALE_ITEM", sub_sale_id)){
			try {
				Entity ent = new Entity("SALE_ITEM", sub_sale_id);
		        ent.setProperty("sale_id", sale_id);
		        ent.setProperty("pid", pid);
		        ent.setProperty("name",name);  
		        //ent.setProperty("size", size); 
		        ent.setProperty("weight", weight);
		        ent.setProperty("price", price);
		        ent.setProperty("type", type);  
		        ent.setProperty("unit", unit); 
		        ent.setProperty("sale_amount", sale_amount);
		        ent.setProperty("status", status);
				Util.persistEntity(ent);
				return hasEntity("SALE_ITEM", sub_sale_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertInvoice(String invoice_id, String sale_id, String cid,
			String cus_name, String payment_model, Integer credit,
			String cus_type, String bus_type, String cus_group, String zone,
			Date delivery, Double total_weight, Integer total_amount,
			Double netExclusive, Date created_date, Date modified_date,
			String created_by, String modified_by, String status,
			String purchase_id, Date due_date, Date paid_date) {
		if (!hasEntity("INVOICE", invoice_id)){
			try {
				Entity ent = new Entity("INVOICE", invoice_id);
		        ent.setProperty("sale_id", sale_id);
		        //ent.setProperty("quote_id", quote_id);
		        ent.setProperty("cid",cid);  
		        ent.setProperty("cus_name",cus_name);
		        ent.setProperty("payment_model",payment_model);
		        ent.setProperty("credit",credit);

		        ent.setProperty("cus_type",cus_type);
		        ent.setProperty("bus_type",bus_type);
		        ent.setProperty("cus_group",cus_group);
		        ent.setProperty("zone",zone);
		        
		        ent.setProperty("delivery", delivery);
		        ent.setProperty("total_weight", total_weight);  
		        ent.setProperty("total_amount", total_amount); 
		        ent.setProperty("netExclusive", netExclusive);
		        ent.setProperty("tax", netExclusive * 0.07);
		        ent.setProperty("netInclusive", netExclusive * 1.07);  
		        ent.setProperty("created_date", created_date); 
		        ent.setProperty("created_by", created_by);
		        ent.setProperty("modified_date", modified_date);
		        ent.setProperty("modified_by", modified_by);
		        //ent.setProperty("comment", comment);
		        ent.setProperty("status", status); // รอชำระเงิน, ชำระเงินแล้ว, เกินกำหนดชำระเงิน
		        ent.setProperty("purchase_id", purchase_id);
		        ent.setProperty("due_date", due_date);
		        ent.setProperty("paid_date", paid_date);
				Util.persistEntity(ent);
				return hasEntity("INVOICE", invoice_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertDelivery(String delivery_id, String sale_id,
			String invoice_id, String cid, String cus_name, Date delivery,
			Double total_weight, Integer total_amount, Date created_date,
			Date modified_date, String created_by, String modified_by,
			String status, String issued_status, Date sent, Date received,
			String receipt_id) {
		if (!hasEntity("DELIVERY", delivery_id)){
			try {
				Entity ent = new Entity("DELIVERY", delivery_id);
		        ent.setProperty("sale_id", sale_id);
		        ent.setProperty("invoice_id", invoice_id);
		        ent.setProperty("cid",cid);  
		        ent.setProperty("cus_name",cus_name);
		        ent.setProperty("delivery", delivery);
		        ent.setProperty("total_weight", total_weight);  
		        ent.setProperty("total_amount", total_amount); 
		        ent.setProperty("created_date", created_date); 
		        ent.setProperty("created_by", created_by);
		        ent.setProperty("modified_date", modified_date);
		        ent.setProperty("modified_by", modified_by);
		        ent.setProperty("status", status); // กำลังนำส่ง, นำส่งแล้ว
		        ent.setProperty("sent", sent);
		        ent.setProperty("issued_status", issued_status);
		        ent.setProperty("received", received);
		        ent.setProperty("receipt_id", receipt_id);
				Util.persistEntity(ent);
				return hasEntity("DELIVERY", delivery_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	public static Boolean insertDeliveryItem(String sub_delivery_id,
			String delivery_id, String pid, String name, Double sale_weight,
			Double price, String type, String unit, Integer sale_amount,
			Boolean status, Double issued_weight, Integer issued_amount) {
		if (!hasEntity("DELIVERY_ITEM", sub_delivery_id)){
			try {
				Entity ent = new Entity("DELIVERY_ITEM", sub_delivery_id);
		        ent.setProperty("delivery_id", delivery_id);
		        ent.setProperty("pid", pid);
		        ent.setProperty("name",name);
		        ent.setProperty("price", price);
		        ent.setProperty("type", type);  
		        ent.setProperty("unit", unit); 
		        ent.setProperty("status", status);
		        
		        ent.setProperty("sale_weight", sale_weight);
		        ent.setProperty("sale_amount", sale_amount);
		        
		        ent.setProperty("issued_weight", issued_weight);
		        ent.setProperty("issued_amount", issued_amount);
				Util.persistEntity(ent);
				return hasEntity("DELIVERY_ITEM", sub_delivery_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	//END
	public static boolean hasEntity(String type, String id) {
		Entity ent = getEntity(type, id);
		if (ent == null){
			return false;
		} else return true;
	}
	
	public static Entity getEntity(String type, String id) {
	  	Key key = KeyFactory.createKey(type, id);
	  	return Util.findEntity(key);
	}
}

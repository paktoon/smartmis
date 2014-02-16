package com.smart.mis.datastore;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("datastore")
public interface DataStoreService extends RemoteService {
	boolean insert(String type, String id);
	boolean insertCustomer(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String address, String street, String city, String state, Integer postal,String country, String cus_type, String bus_type , String cus_group, String url, String zone);
	boolean insertProduct(String pid, String name, String name_th, Double weight, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder);
	boolean insertSmith(String smid, String name, String phone1, String phone2, String email, String address, String street, String city, String state, Integer postal, String type);
	boolean insertMaterial(String mid, String mat_name, String desc, String type, String unit, Double weight, Double safety, Double inStock, Double reserved);
	boolean insertSuppiler(String id, String name, String phone1, String phone2, String email, String address, String street, String city, String state, Integer postal, String fax, Integer leadtime, String itemList);
	
	//SALE
	boolean insertQuotation(String quote_id, String cid, String cus_name, String payment_model, Integer credit, String cus_type , String bus_type, String cus_group, String zone,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status);
	boolean insertQuotationItem(String sub_quote_id, String quote_id, String pid, String name, Double weight, Double price, String type, String unit, Integer quote_amount, Boolean status);
	boolean insertSale(String sale_id, String quote_id, String invoice_id,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date);
	boolean insertSaleItem(String sub_sale_id, String sale_id, String pid, String name, Double weight, Double price, String type, String unit, Integer sale_amount, Boolean status);
	boolean insertInvoice(String invoice_id, String sale_id ,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date,Date paid_date);
	boolean insertDelivery(String delivery_id, String sale_id, String invoice_id ,String cid, String cus_name, Date delivery ,Double total_weight,Integer total_amount,Date created_date,Date modified_date,String created_by,String modified_by, String status, String issued_status, Date sent, Date received, String receipt_id);
	boolean insertDeliveryItem(String sub_delivery_id, String delivery_id, String pid, String name, Double sale_weight, Double price, String type, String unit, Integer sale_amount, Boolean status, Double issued_weight, Integer issued_amount);
	
	//PRODUCTION//PLAN//ORDER//TRANSFER//RETURN//WAGE
	
	//INVENTORY//NONE
	
	//PURCHASING//REQUEST//ORDER
	
	//FINANCIAL//NONE
}

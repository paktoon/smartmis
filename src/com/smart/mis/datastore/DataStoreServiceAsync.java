package com.smart.mis.datastore;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataStoreServiceAsync {
	void insert(String type, String id,
			AsyncCallback<Boolean> callback);
	//Master
	void insertCustomer(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String address, String street, String city, String state, Integer postal,String country, String cus_type, String bus_type , String cus_group, String url, String zone,
			AsyncCallback<Boolean> callback);
	void insertProduct(String pid, String name, String name_th, Double weight, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder,
			AsyncCallback<Boolean> callback);
	void insertSmith(String smid, String name, String phone1, String phone2, String email, String address, String street, String city, String state, Integer postal, String type,
			AsyncCallback<Boolean> callback);
	void insertMaterial(String mid, String mat_name, String desc, String type, String unit, Double weight, Double safety, Double inStock, Double reserved,
			AsyncCallback<Boolean> callback);
	void insertSuppiler(String id, String name, String phone1, String phone2, String email, String address, String street, String city, String state, Integer postal, String fax, Integer leadtime, String itemList,
			AsyncCallback<Boolean> callback);
	
	//Sale
	void insertQuotation(String quote_id, String cid, String cus_name, String payment_model, Integer credit, String cus_type , String bus_type, String cus_group, String zone,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status,
			AsyncCallback<Boolean> callback);
	void insertQuotationItem(String sub_quote_id, String quote_id, String pid, String name, Double weight, Double price, String type, String unit, Integer quote_amount, Boolean status,
			AsyncCallback<Boolean> callback);
	void insertSale(String sale_id, String quote_id, String invoice_id,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date,
			AsyncCallback<Boolean> callback);
	void insertSaleItem(String sub_sale_id, String sale_id, String pid, String name, Double weight, Double price, String type, String unit, Integer sale_amount, Boolean status,
			AsyncCallback<Boolean> callback);
	void insertInvoice(String invoice_id, String sale_id ,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date,Date paid_date,
			AsyncCallback<Boolean> callback);
	void insertDelivery(String delivery_id, String sale_id, String invoice_id ,String cid, String cus_name, Date delivery ,Double total_weight,Integer total_amount,Date created_date,Date modified_date,String created_by,String modified_by, String status, String issued_status, Date sent, Date received, String receipt_id,
		    AsyncCallback<Boolean> callback);
	void insertDeliveryItem(String sub_delivery_id, String delivery_id, String pid, String name, Double sale_weight, Double price, String type, String unit, Integer sale_amount, Boolean status, Double issued_weight, Integer issued_amount,
		    AsyncCallback<Boolean> callback);
}

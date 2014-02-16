package com.smart.mis.datastore;

import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataStoreServiceImpl extends RemoteServiceServlet implements
	DataStoreService {
	
	@Override
	public boolean insert(String type, String id) {
		return StoreUtil.insert(type, id);
	}

	@Override
	public boolean insertCustomer(String cid, String cus_name,
			String cus_phone, String contact_name, String contact_phone,
			String contact_email, String address, String street, String city,
			String state, Integer postal, String country, String cus_type,
			String bus_type, String cus_group, String url, String zone) {
		// TODO Auto-generated method stub
		return StoreUtil.insertCustomer(cid, cus_name,
				 cus_phone,  contact_name,  contact_phone,
				 contact_email,  address,  street,  city,
				 state,  postal,  country,  cus_type,
				 bus_type,  cus_group,  url,  zone);
	}

	@Override
	public boolean insertProduct(String pid, String name, String name_th,
			Double weight, Double price, String type, Integer inStock,
			Integer reserved, String imgUrl, Double size, Double width,
			Double length, Double height, Double diameter, Double thickness,
			Boolean makeToOrder) {
		// TODO Auto-generated method stub
		return StoreUtil.insertProduct( pid,  name,  name_th,
				 weight,  price,  type,  inStock,
				 reserved,  imgUrl,  size,  width,
				 length,  height,  diameter,  thickness,
				 makeToOrder);
	}

	@Override
	public boolean insertSmith(String smid, String name, String phone1,
			String phone2, String email, String address, String street,
			String city, String state, Integer postal, String type) {
		// TODO Auto-generated method stub
		return StoreUtil.insertSmith( smid,  name,  phone1,
				 phone2,  email,  address,  street,
				 city,  state,  postal,  type);
	}

	
	@Override
	public boolean insertMaterial(String mid, String mat_name, String desc,
			String type, String unit, Double weight, Double safety,
			Double inStock, Double reserved) {
		// TODO Auto-generated method stub
		return StoreUtil.insertMaterial( mid,  mat_name,  desc,
				 type,  unit,  weight,  safety,
				 inStock,  reserved);
	}

	@Override
	public boolean insertSuppiler(String id, String name, String phone1,
			String phone2, String email, String address, String street,
			String city, String state, Integer postal, String fax,
			Integer leadtime, String itemList) {
		// TODO Auto-generated method stub
		return StoreUtil.insertSuppiler( id,  name,  phone1,
				 phone2,  email,  address,  street,
				 city,  state,  postal,  fax,
				 leadtime,  itemList);
	}

	@Override
	public boolean insertQuotation(String quote_id, String cid,
			String cus_name, String payment_model, Integer credit,
			String cus_type, String bus_type, String cus_group, String zone,
			Date from, Date to, Date delivery, Double total_weight,
			Integer total_amount, Double netExclusive, Date created_date,
			Date modified_date, String created_by, String modified_by,
			String comment, String status) {
		// TODO Auto-generated method stub
		return StoreUtil.insertQuotation( quote_id,  cid,
				 cus_name,  payment_model,  credit,
				 cus_type,  bus_type,  cus_group,  zone,
				 from,  to,  delivery,  total_weight,
				 total_amount,  netExclusive,  created_date,
				 modified_date,  created_by,  modified_by,
				 comment,  status);
	}

	@Override
	public boolean insertQuotationItem(String sub_quote_id, String quote_id,
			String pid, String name, Double weight, Double price, String type,
			String unit, Integer quote_amount, Boolean status) {
		// TODO Auto-generated method stub
		return StoreUtil.insertQuotationItem(  sub_quote_id,  quote_id,
				 pid,  name,  weight,  price,  type,
				 unit,  quote_amount,  status);
	}

	@Override
	public boolean insertSale(String sale_id, String quote_id,
			String invoice_id, String cid, String cus_name,
			String payment_model, Integer credit, String cus_type,
			String bus_type, String cus_group, String zone, Date delivery,
			Double total_weight, Integer total_amount, Double netExclusive,
			Date created_date, Date modified_date, String created_by,
			String modified_by, String status, String purchase_id, Date due_date) {
		// TODO Auto-generated method stub
		return StoreUtil.insertSale( sale_id,  quote_id,
				 invoice_id,  cid,  cus_name,
				 payment_model,  credit,  cus_type,
				 bus_type,  cus_group,  zone,  delivery,
				 total_weight,  total_amount,  netExclusive,
				 created_date,  modified_date,  created_by,
				 modified_by,  status,  purchase_id,  due_date);
	}

	@Override
	public boolean insertSaleItem(String sub_sale_id, String sale_id,
			String pid, String name, Double weight, Double price, String type,
			String unit, Integer sale_amount, Boolean status) {
		// TODO Auto-generated method stub
		return StoreUtil.insertSaleItem(  sub_sale_id,  sale_id,
				 pid,  name,  weight,  price,  type,
				 unit,  sale_amount,  status);
	}

	@Override
	public boolean insertInvoice(String invoice_id, String sale_id, String cid,
			String cus_name, String payment_model, Integer credit,
			String cus_type, String bus_type, String cus_group, String zone,
			Date delivery, Double total_weight, Integer total_amount,
			Double netExclusive, Date created_date, Date modified_date,
			String created_by, String modified_by, String status,
			String purchase_id, Date due_date, Date paid_date) {
		// TODO Auto-generated method stub
		return StoreUtil.insertInvoice(  invoice_id,  sale_id,  cid,
				 cus_name,  payment_model,  credit,
				 cus_type,  bus_type,  cus_group,  zone,
				 delivery,  total_weight,  total_amount,
				 netExclusive,  created_date,  modified_date,
				 created_by,  modified_by,  status,
				 purchase_id,  due_date,  paid_date);
	}

	@Override
	public boolean insertDelivery(String delivery_id, String sale_id,
			String invoice_id, String cid, String cus_name, Date delivery,
			Double total_weight, Integer total_amount, Date created_date,
			Date modified_date, String created_by, String modified_by,
			String status, String issued_status, Date sent, Date received,
			String receipt_id) {
		// TODO Auto-generated method stub
		return StoreUtil.insertDelivery(  delivery_id,  sale_id,
				 invoice_id,  cid,  cus_name,  delivery,
				 total_weight,  total_amount,  created_date,
				 modified_date,  created_by,  modified_by,
				 status,  issued_status,  sent,  received,
				 receipt_id);
	}

	@Override
	public boolean insertDeliveryItem(String sub_delivery_id,
			String delivery_id, String pid, String name, Double sale_weight,
			Double price, String type, String unit, Integer sale_amount,
			Boolean status, Double issued_weight, Integer issued_amount) {
		// TODO Auto-generated method stub
		return StoreUtil.insertDeliveryItem(sub_delivery_id,
				 delivery_id,  pid,  name,  sale_weight,
				 price,  type,  unit,  sale_amount,
				 status,  issued_weight,  issued_amount);
	}
}

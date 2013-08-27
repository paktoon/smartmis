package com.smart.mis.datastore;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.smart.mis.shared.security.Function;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.Role;

public class PermissionKind {
	
	public static boolean createPerm(PermissionProfile profile, String creator) {
		if (!hasPerm(profile.getName())){
			try {
				Entity perm = new Entity("PERMISSION", profile.getName());
				perm.setProperty("func", ((Byte) profile.getFunction()).longValue());
				perm.setProperty("role", ((Byte) profile.getRole()).longValue());
				perm.setProperty("status", true);
				perm.setProperty("pid", "PM" + (10000 + Util.getKey("PERMISSION").getId()));
				perm.setProperty("creator", creator);
				perm.setProperty("when", new SimpleDateFormat("dd-MM-yyy").format(Calendar.getInstance().getTime()));
				Util.persistEntity(perm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return hasPerm(profile.getName());
	}
	
	public static PermissionProfile getPerm(String name) {
		Entity perm = getPermEntity(name);
		if (perm != null)
		{
			byte func = ((Long) perm.getProperty("func")).byteValue();
			byte role = ((Long) perm.getProperty("role")).byteValue();
			return new PermissionProfile(name, func, role);
		} else return null;
	}
	
	public static String writeJSON() {
		
		Iterable<Entity> permList = Util.listEntities("PERMISSION",null,null);
		StringBuilder sb = new StringBuilder();
		boolean firstSymbol = true;
		
		sb.append("[");
		for (Entity perm : permList) {
			String name = perm.getKey().getName();
			byte func = ((Long) perm.getProperty("func")).byteValue();
			byte role = ((Long) perm.getProperty("role")).byteValue();
			boolean status = (Boolean) perm.getProperty("status");
			String pid = (String) perm.getProperty("pid");
			String creator = (String) perm.getProperty("creator");
			String when = (String) perm.getProperty("when");
			
		    if (firstSymbol) {
			   firstSymbol = false;
			} else {
			   sb.append(",");
			}
		      
			sb.append("{");
			sb.append("\"pid\": \"");
			sb.append(pid);
			sb.append("\",");
			sb.append("\"name\": \"");
			sb.append(name);
			sb.append("\",");
			sb.append("\"creator\": \"");
			sb.append(creator);
			sb.append("\",");
			sb.append("\"when\": \"");
			sb.append(when);
			sb.append("\",");
			sb.append("\"role\": \"");
			sb.append(getRoleName(role));
			sb.append("\",");
			sb.append("\"status\": \"");
			sb.append(getStatus(status));
			sb.append("\",");
			sb.append("\"cSale\": ");
			sb.append(checkPermFlag(func, Function.SALE));
			sb.append(',');
			sb.append("\"cProd\": ");
			sb.append(checkPermFlag(func, Function.PRODUCTION));
			sb.append(',');
			sb.append("\"cInv\": ");
			sb.append(checkPermFlag(func, Function.INVENTORY));
			sb.append(',');
			sb.append("\"cPurc\": ");
			sb.append(checkPermFlag(func, Function.PURCHASING));
			sb.append(',');
			sb.append("\"cFin\": ");
			sb.append(checkPermFlag(func, Function.FINANCIAL));
			sb.append(',');
			sb.append("\"cRep\": ");
			sb.append(checkPermFlag(func, Function.REPORT));
			sb.append(',');
			sb.append("\"cAdm\": ");
			sb.append(checkPermFlag(func, Function.SECURITY));
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static Entity getPermEntity(String name) {
	  	Key key = KeyFactory.createKey("PERMISSION",name);
	  	return Util.findEntity(key);
	}
	
	public static boolean hasPerm(String name) {
		Entity perm = getPermEntity(name);
		if (perm == null){
			return false;
		} else return true;
	}

	private static boolean checkFlag(byte flag, byte checked){
		return (flag & checked) == checked;
	}
	
	private static String getRoleName(byte role){
		
		String roleName = "Staff";
		if (checkFlag(role, Role.OWNER)) {
			roleName = "Manager";
		} else if (checkFlag(role, Role.ADMIN)) {
			roleName = "Administrator";
		}
		
		try {
			return new String(roleName.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	private static String getStatus(boolean status){
		if (status) return "Active";
		else return "Inactive";
	}
	
	private static String checkPermFlag(byte flag, byte checked){
		if ((flag & checked) == checked) return "true";
		else return "false";
	}
}

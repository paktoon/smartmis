package com.smart.mis.shared.security;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PermissionProfile implements IsSerializable{

	private String _name;
	private byte _funcs = Function.NONE;
	private byte _role = Role.NON_USER;
	private boolean _status;
	
	//Required for IsSerializable Don't removed!!!
	private PermissionProfile() { }
	
	public PermissionProfile(String name) {
		this._name = name;
	}
	
	public PermissionProfile(String name, byte func, byte role) {
		this._name = name;
		this._funcs = func;
		this._role = role;
	}
	
	public PermissionProfile(String name, byte func, byte role, boolean status) {
		this._name = name;
		this._funcs = func;
		this._role = role;
		this._status = status;
	}
	
	public boolean addFunction(byte func){
		try {
			_funcs |= func;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean setRole(byte role){
		try {
			_role |= role;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean removeFunction(byte func){
		try {
			_funcs ^= func;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public byte getFunction(){
		return this._funcs;
	}
	
	public byte getRole(){
		return this._role;
	}
	
	public String getName() {
		return this._name;
	}
	
	//New
	
	public boolean canSale(){
		return checkPermFlag(this._funcs, Function.SALE);
	}
	
	public boolean canProduct(){
		return checkPermFlag(this._funcs, Function.PRODUCTION);
	}
	
	public boolean canInven(){
		return checkPermFlag(this._funcs, Function.INVENTORY);
	}
	
	public boolean canPurchase(){
		return checkPermFlag(this._funcs, Function.PURCHASING);
	}
	
	public boolean canFinance(){
		return checkPermFlag(this._funcs, Function.FINANCIAL);
	}
	
	public boolean canReport(){
		return checkPermFlag(this._funcs, Function.REPORT);
	}
	
	public boolean canAdmin(){
		return checkPermFlag(this._funcs, Function.SECURITY);
	}
	
	public boolean getStatus(){
		return this._status;
	}
	
	private boolean checkPermFlag(byte flag, byte checked){
		return (flag & checked) == checked;
	}
}

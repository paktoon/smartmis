package com.smart.mis.shared.security;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable{
	private String _username; // must have
	private String _password; // must have
	private String _firstname; // must have
	private String _lastname; // must have
	private String _email = "-"; // optional
	private String _position = "-"; // optional
	private String _titile = "";
	private PermissionProfile _profile; // must have
	private boolean _status;
	
	//Required for IsSerializable Don't removed!!!
	private User() { }
	
	public User(String uname, String pwd, String fname, String lname, String email, String position, PermissionProfile profile, String title, boolean status) {
		this._username = uname;
		this._password = pwd;
		this._firstname = fname;
		this._lastname = lname;
		this._email = email;
		this._position = position;
		this._profile = profile;
		this._titile = title;
		this._status = status;
	}
	
	public User(String uname, String pwd, String fname, String lname, PermissionProfile profile, String title, boolean status) {
		this._username = uname;
		this._password = pwd;
		this._firstname = fname;
		this._lastname = lname;
		this._profile = profile;
		this._titile = title;
		this._status = status;
	}
	
	public User(String uname, String pwd, String fname, String lname, String email, String position, String title, boolean status) {
		this._username = uname;
		this._password = pwd;
		this._firstname = fname;
		this._lastname = lname;
		this._email = email;
		this._position = position;
		this._titile = title;
		this._status = status;
	}
	
	public String getUserName(){
		return this._username;
	}
	
	public String getPassWord(){
		return this._password;
	}
	
	public String getFirstName(){
		return this._firstname;
	}
	
	public String getLastName(){
		return this._lastname;
	}
	
	public String getEmail(){
		return this._email;
	}
	
	public String getPosition(){
		return this._position;
	}
	
	public PermissionProfile getProfile(){
		return this._profile;
	}
	
	public String getTitle(){
		return this._titile;
	}
	
	public boolean getStatus(){
		return this._status;
	}
	
	public void setPassword(String pwd){
		this._password = pwd;
	}
}

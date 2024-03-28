package com.stonedt.spider.param;

import lombok.Data;

@Data
public class UserParam {
    private String username;
    private String password;
    private String name;
    private int power;
    private int status;
    private String institutionName;

    private String newPassword;


    public boolean userRegist(){
        boolean status = false;
        if(username==null || username.equals("") || password==null || password.equals("") || name==null || name.equals("")  || power==0 || institutionName==null || institutionName.equals("")  ){
            status = true;
        }
        return status;
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPower() {
		return power;
	}


	public void setPower(int power) {
		this.power = power;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getInstitutionName() {
		return institutionName;
	}


	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
    
    
    
    


}

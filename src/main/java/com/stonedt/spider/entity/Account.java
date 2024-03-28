package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private int id;
    private String username;
    private String password;
    private String site;
    private int status;
    private Date create_time;
    private int create_user_id;
    private Date update_time;
    private int update_user_id;
    private int is_del;
    private String create_user_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public int getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(int update_user_id) {
		this.update_user_id = update_user_id;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public Account(int id, String username, String password, String site, int status, Date create_time,
			int create_user_id, Date update_time, int update_user_id, int is_del, String create_user_name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.site = site;
		this.status = status;
		this.create_time = create_time;
		this.create_user_id = create_user_id;
		this.update_time = update_time;
		this.update_user_id = update_user_id;
		this.is_del = is_del;
		this.create_user_name = create_user_name;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
}

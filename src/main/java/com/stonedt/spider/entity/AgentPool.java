package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AgentPool {
    private int id;
    private String service_name;
    private Date term;
    private String url;
    private int status;
    private Date create_time;
    private int create_user_id;
    private Date update_time;
    private int update_user_id;
    private int is_del;
    private String create_user_name;
    private int term_day;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public Date getTerm() {
		return term;
	}
	public void setTerm(Date term) {
		this.term = term;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public int getTerm_day() {
		return term_day;
	}
	public void setTerm_day(int term_day) {
		this.term_day = term_day;
	}
	public AgentPool(int id, String service_name, Date term, String url, int status, Date create_time,
			int create_user_id, Date update_time, int update_user_id, int is_del, String create_user_name,
			int term_day) {
		super();
		this.id = id;
		this.service_name = service_name;
		this.term = term;
		this.url = url;
		this.status = status;
		this.create_time = create_time;
		this.create_user_id = create_user_id;
		this.update_time = update_time;
		this.update_user_id = update_user_id;
		this.is_del = is_del;
		this.create_user_name = create_user_name;
		this.term_day = term_day;
	}
	public AgentPool() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
}

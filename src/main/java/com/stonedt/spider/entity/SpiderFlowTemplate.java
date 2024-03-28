package com.stonedt.spider.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class SpiderFlowTemplate {
    private int id;
    private String template_name;
    private String xml;
    private Integer environment;
    private Integer status;
    private Date create_time;
    private Date update_time;
    private int create_user_id;
    private String create_user_name;
    private int update_user_id;
    private String update_user_name;
    private Integer is_del;
	private Integer type_id;
	private String typename;
	private String bloomname;
	private String estype;
	private String hbase_table;
	private String kafka_queue_name;
	private String esindex;

    private int temp_use_count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Integer getEnvironment() {
		return environment;
	}

	public void setEnvironment(Integer environment) {
		this.environment = environment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public int getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(int update_user_id) {
		this.update_user_id = update_user_id;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public int getTemp_use_count() {
		return temp_use_count;
	}

	public void setTemp_use_count(int temp_use_count) {
		this.temp_use_count = temp_use_count;
	}
    
    
    
    
    
}
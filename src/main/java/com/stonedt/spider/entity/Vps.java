package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Vps {
    private int id;
    private String type;
    private String environment;
    private String ip;
    private String configure;
    private String port;
    private String password;
    private String remark;
    private Double cpu_count;
    private Double ram_count;
    private Double disk_count;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = configure;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getCpu_count() {
		return cpu_count;
	}
	public void setCpu_count(Double cpu_count) {
		this.cpu_count = cpu_count;
	}
	public Double getRam_count() {
		return ram_count;
	}
	public void setRam_count(Double ram_count) {
		this.ram_count = ram_count;
	}
	public Double getDisk_count() {
		return disk_count;
	}
	public void setDisk_count(Double disk_count) {
		this.disk_count = disk_count;
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
	public Vps(int id, String type, String environment, String ip, String configure, String port, String password,
			String remark, Double cpu_count, Double ram_count, Double disk_count, Date create_time, int create_user_id,
			Date update_time, int update_user_id, int is_del, String create_user_name) {
		super();
		this.id = id;
		this.type = type;
		this.environment = environment;
		this.ip = ip;
		this.configure = configure;
		this.port = port;
		this.password = password;
		this.remark = remark;
		this.cpu_count = cpu_count;
		this.ram_count = ram_count;
		this.disk_count = disk_count;
		this.create_time = create_time;
		this.create_user_id = create_user_id;
		this.update_time = update_time;
		this.update_user_id = update_user_id;
		this.is_del = is_del;
		this.create_user_name = create_user_name;
	}
	public Vps() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
    
    
}

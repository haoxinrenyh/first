package com.stonedt.spider.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author 王子秋
 * ClassNameUserLandingLog
 * @Verdion 版本
 * @ModifieBy 修改人
 * @company Stonedt
 */
public class UserLandingLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String user;
	private String user_name;
	private Integer user_id;
	private Date create_date;
	private Date landing_date;
	private Integer landing_count;
	private String institution_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getLanding_date() {
		return landing_date;
	}
	public void setLanding_date(Date landing_date) {
		this.landing_date = landing_date;
	}
	public Integer getLanding_count() {
		return landing_count;
	}
	public void setLanding_count(Integer landing_count) {
		this.landing_count = landing_count;
	}
	public String getInstitution_name() {
		return institution_name;
	}
	public void setInstitution_name(String institution_name) {
		this.institution_name = institution_name;
	}
	public UserLandingLog(Integer id, String user, String user_name, Integer user_id, Date create_date,
			Date landing_date, Integer landing_count, String institution_name) {
		super();
		this.id = id;
		this.user = user;
		this.user_name = user_name;
		this.user_id = user_id;
		this.create_date = create_date;
		this.landing_date = landing_date;
		this.landing_count = landing_count;
		this.institution_name = institution_name;
	}
	public UserLandingLog() {
		super();
	}
	@Override
	public String toString() {
		return "UserLandingLog [id=" + id + ", user=" + user + ", user_name=" + user_name + ", user_id=" + user_id
				+ ", create_date=" + create_date + ", landing_date=" + landing_date + ", landing_count=" + landing_count
				+ ", institution_name=" + institution_name + "]";
	}
	
	
}

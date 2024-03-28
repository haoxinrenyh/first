package com.stonedt.spider.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author MaPeng
 *
 */
@Data
public class StonedtUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;//用户名
	private String password;//用户密码
	private String name;//用户姓名
	private String company;//公司
	private String job;//职务
	private Integer status;//用户状态
	private Integer user_power;//权限

	private String open_id;//微信id

	private Date create_time;//创建时间
	private Date update_time;//创建时间

}

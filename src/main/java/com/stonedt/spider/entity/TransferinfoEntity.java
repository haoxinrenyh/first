package com.stonedt.spider.entity;
/**
 * 接口信息实体类
 * @author wangyi
 *
 */
public class TransferinfoEntity {
	private int id;
	private int userid;//创建人
	private int transfercount;
	private int typeid;
	private String paramexample;//请求样例
    private String param;//参数信息
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getTransfercount() {
		return transfercount;
	}
	public void setTransfercount(int transfercount) {
		this.transfercount = transfercount;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getParamexample() {
		return paramexample;
	}
	public void setParamexample(String paramexample) {
		this.paramexample = paramexample;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public TransferinfoEntity(int id, int userid, int transfercount, int typeid, String paramexample, String param) {
		super();
		this.id = id;
		this.userid = userid;
		this.transfercount = transfercount;
		this.typeid = typeid;
		this.paramexample = paramexample;
		this.param = param;
	}
	public TransferinfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	
	

}

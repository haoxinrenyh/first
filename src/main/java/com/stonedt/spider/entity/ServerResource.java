package com.stonedt.spider.entity;

import java.io.Serializable;
/**
 * 服务器资源清单与使用状态
 * @author Mapeng
 *
 */
public class ServerResource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String servername;//服务器名称
	private String serveraddress;//服务器地址
	private String ipaddress;//ip地址
	private String diskstorageremain;//磁盘存储(剩余)
	private String systemmemoryremain;//系统内存(剩余)
	private String cpuusage;//cpu使用率
	private String starttime;//启动时间
	private String status;//启动状态
	public ServerResource() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getDiskstorageremain() {
		return diskstorageremain;
	}
	public void setDiskstorageremain(String diskstorageremain) {
		this.diskstorageremain = diskstorageremain;
	}
	public String getSystemmemoryremain() {
		return systemmemoryremain;
	}
	public void setSystemmemoryremain(String systemmemoryremain) {
		this.systemmemoryremain = systemmemoryremain;
	}
	public String getCpuusage() {
		return cpuusage;
	}
	public void setCpuusage(String cpuusage) {
		this.cpuusage = cpuusage;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

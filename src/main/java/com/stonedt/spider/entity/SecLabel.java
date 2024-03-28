package com.stonedt.spider.entity;

public class SecLabel {
	private String seed_name;
	private Integer seed_id;
	private String first_type;
	private Integer num;
	
	
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getFirst_type() {
		return first_type;
	}
	public void setFirst_type(String first_type) {
		this.first_type = first_type;
	}
	public String getSeed_name() {
		return seed_name;
	}
	public void setSeed_name(String seed_name) {
		this.seed_name = seed_name;
	}
	public Integer getSeed_id() {
		return seed_id;
	}
	public void setSeed_id(Integer seed_id) {
		this.seed_id = seed_id;
	}
	@Override
	public String toString() {
		return "SecLabel [seed_name=" + seed_name + ", seed_id=" + seed_id + ", first_type=" + first_type + ", num="
				+ num + "]";
	}
	
	
}

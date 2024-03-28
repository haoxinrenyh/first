package com.stonedt.spider.entity;

/*
 * 统计数量
 */
public class SpiderCount {
	private Integer id;
	private Integer gathering_value; //当前值
	private Integer seed_id;//种子源id
	private Integer website_id;//站点id
	private String create_date;//创建时间
	
	
	public SpiderCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpiderCount(Integer seed_id, Integer website_id, String create_date) {
		super();
		this.seed_id = seed_id;
		this.website_id = website_id;
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "SpiderCount [id=" + id + ", gathering_value=" + gathering_value + ", seed_id=" + seed_id
				+ ", website_id=" + website_id + ", create_date=" + create_date + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGathering_value() {
		return gathering_value;
	}
	public void setGathering_value(Integer gathering_value) {
		this.gathering_value = gathering_value;
	}
	public Integer getSeed_id() {
		return seed_id;
	}
	public void setSeed_id(Integer seed_id) {
		this.seed_id = seed_id;
	}
	public Integer getWebsite_id() {
		return website_id;
	}
	public void setWebsite_id(Integer website_id) {
		this.website_id = website_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}

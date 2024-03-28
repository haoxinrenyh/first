package com.stonedt.spider.entity;
/**
 * 爬虫种子配置
 * @author wangyi
 *
 */
public class MappingSpider {
	private Integer id;
	private String url;
	private Integer website_id;
	private Integer config_id;
	private Integer status;
	private String url_name;
	private Integer ConfigUtilId;
	private String seed_util_name;
	private SpiderSeedConfigUtilOb ssco;
	
	public String getSeed_util_name() {
		return seed_util_name;
	}
	public void setSeed_util_name(String seed_util_name) {
		this.seed_util_name = seed_util_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getWebsite_id() {
		return website_id;
	}
	public void setWebsite_id(Integer website_id) {
		this.website_id = website_id;
	}
	public Integer getConfig_id() {
		return config_id;
	}
	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUrl_name() {
		return url_name;
	}
	public void setUrl_name(String url_name) {
		this.url_name = url_name;
	}
	public Integer getConfigUtilId() {
		return ConfigUtilId;
	}
	public void setConfigUtilId(Integer configUtilId) {
		ConfigUtilId = configUtilId;
	}
	public SpiderSeedConfigUtilOb getSsco() {
		return ssco;
	}
	public void setSsco(SpiderSeedConfigUtilOb ssco) {
		this.ssco = ssco;
	}
	
}

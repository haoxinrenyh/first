package com.stonedt.spider.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SpiderFlowTask {

    private Integer id;
    private String seed_name;// '任务名',
    private Integer seed_type;// '任务类型，1代表html 2json 3代表html>cdata',
    private Integer seed_status;// '任务状态1正常0关闭',
    private String cron;
    private Integer website_id;// '任务对应网站ID'
    private Date create_date;
    private String xml;// 'xml表达式',
    private Integer data_type_id;// '数据类型id',
    private Integer spider_level;
    private String website_name;
    private String website_ico;
    private String typename;
    private String estype;
    private String esindex;
    private String hbase_table;
    private String kafka_queue_name;
    private String bloomname;
    private Integer new_website_type;
    private Integer from_smart_crawler;
    private Date update_time;
    private String sole_sign;
    private Integer errorNum;
    private String seed_url;
    private String customlable;
    private Integer key_sources_flag;
    private Integer spider_type;
    private String create_user;
    private Integer create_user_id;
    private String update_user;
    private Integer update_user_id;
    private Integer type;
    private int is_del;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeed_name() {
		return seed_name;
	}
	public void setSeed_name(String seed_name) {
		this.seed_name = seed_name;
	}
	public Integer getSeed_type() {
		return seed_type;
	}
	public void setSeed_type(Integer seed_type) {
		this.seed_type = seed_type;
	}
	public Integer getSeed_status() {
		return seed_status;
	}
	public void setSeed_status(Integer seed_status) {
		this.seed_status = seed_status;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Integer getWebsite_id() {
		return website_id;
	}
	public void setWebsite_id(Integer website_id) {
		this.website_id = website_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public Integer getData_type_id() {
		return data_type_id;
	}
	public void setData_type_id(Integer data_type_id) {
		this.data_type_id = data_type_id;
	}
	public Integer getSpider_level() {
		return spider_level;
	}
	public void setSpider_level(Integer spider_level) {
		this.spider_level = spider_level;
	}
	public String getWebsite_name() {
		return website_name;
	}
	public void setWebsite_name(String website_name) {
		this.website_name = website_name;
	}
	public String getWebsite_ico() {
		return website_ico;
	}
	public void setWebsite_ico(String website_ico) {
		this.website_ico = website_ico;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getEstype() {
		return estype;
	}
	public void setEstype(String estype) {
		this.estype = estype;
	}
	public String getEsindex() {
		return esindex;
	}
	public void setEsindex(String esindex) {
		this.esindex = esindex;
	}
	public String getHbase_table() {
		return hbase_table;
	}
	public void setHbase_table(String hbase_table) {
		this.hbase_table = hbase_table;
	}
	public String getKafka_queue_name() {
		return kafka_queue_name;
	}
	public void setKafka_queue_name(String kafka_queue_name) {
		this.kafka_queue_name = kafka_queue_name;
	}
	public String getBloomname() {
		return bloomname;
	}
	public void setBloomname(String bloomname) {
		this.bloomname = bloomname;
	}
	public Integer getNew_website_type() {
		return new_website_type;
	}
	public void setNew_website_type(Integer new_website_type) {
		this.new_website_type = new_website_type;
	}
	public Integer getFrom_smart_crawler() {
		return from_smart_crawler;
	}
	public void setFrom_smart_crawler(Integer from_smart_crawler) {
		this.from_smart_crawler = from_smart_crawler;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getSole_sign() {
		return sole_sign;
	}
	public void setSole_sign(String sole_sign) {
		this.sole_sign = sole_sign;
	}
	public Integer getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}
	public String getSeed_url() {
		return seed_url;
	}
	public void setSeed_url(String seed_url) {
		this.seed_url = seed_url;
	}
	public String getCustomlable() {
		return customlable;
	}
	public void setCustomlable(String customlable) {
		this.customlable = customlable;
	}
	public Integer getKey_sources_flag() {
		return key_sources_flag;
	}
	public void setKey_sources_flag(Integer key_sources_flag) {
		this.key_sources_flag = key_sources_flag;
	}
	public Integer getSpider_type() {
		return spider_type;
	}
	public void setSpider_type(Integer spider_type) {
		this.spider_type = spider_type;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Integer getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
    
    
    
    

}

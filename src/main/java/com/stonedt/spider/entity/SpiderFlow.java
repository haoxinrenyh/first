package com.stonedt.spider.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SpiderFlow {

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getTemp_id() {
		return temp_id;
	}
	public void setTemp_id(Integer temp_id) {
		this.temp_id = temp_id;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	private Integer id;
	private String seed_name;// '任务名',
	private Integer seed_type;// '任务类型，1代表html 2json 3代表html>cdata',
	private Integer seed_status;// '任务状态1正常0关闭',
	private String cron;
	private Integer website_id;// '任务对应网站ID',
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 输出格式
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
	// 爬虫唯一标识
	private String sole_sign;
	private Integer errorNum;
	private String seed_url;

	private String customlable;
	private Integer key_sources_flag;
	private Integer spider_type;
	
	//创建人
	private String create_user;
	//创建人id
	private Integer create_user_id;
	//更新人
	private String update_user;
	//更新人ID
	private Integer update_user_id;
	private Integer type;
	private Integer temp_id;
	private int is_del;

	private int spider_count;
	private int spider_day_count;
	
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
	public SpiderFlow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpiderFlow(Integer id, String seed_name, Integer seed_type, Integer seed_status, String cron,
			Integer website_id, Date create_date, String xml, Integer data_type_id, Integer spider_level,
			String website_name, String website_ico, String typename, String estype, String esindex, String hbase_table,
			String kafka_queue_name, String bloomname, Integer new_website_type, Integer from_smart_crawler,
			Date update_time, String sole_sign, Integer errorNum, String seed_url, String customlable,
			Integer key_sources_flag, Integer spider_type, String create_user, Integer create_user_id,
			String update_user, Integer update_user_id, Integer type, Integer temp_id, int is_del) {
		super();
		this.id = id;
		this.seed_name = seed_name;
		this.seed_type = seed_type;
		this.seed_status = seed_status;
		this.cron = cron;
		this.website_id = website_id;
		this.create_date = create_date;
		this.xml = xml;
		this.data_type_id = data_type_id;
		this.spider_level = spider_level;
		this.website_name = website_name;
		this.website_ico = website_ico;
		this.typename = typename;
		this.estype = estype;
		this.esindex = esindex;
		this.hbase_table = hbase_table;
		this.kafka_queue_name = kafka_queue_name;
		this.bloomname = bloomname;
		this.new_website_type = new_website_type;
		this.from_smart_crawler = from_smart_crawler;
		this.update_time = update_time;
		this.sole_sign = sole_sign;
		this.errorNum = errorNum;
		this.seed_url = seed_url;
		this.customlable = customlable;
		this.key_sources_flag = key_sources_flag;
		this.spider_type = spider_type;
		this.create_user = create_user;
		this.create_user_id = create_user_id;
		this.update_user = update_user;
		this.update_user_id = update_user_id;
		this.type = type;
		this.temp_id = temp_id;
		this.is_del = is_del;
	} 
	
	
	
	
	
	
	

}

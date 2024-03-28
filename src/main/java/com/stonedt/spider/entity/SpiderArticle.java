package com.stonedt.spider.entity;

/**
 * 抓取的文章
 * @author wangyi
 *
 */
public class SpiderArticle {
	private Integer article_id;
	private String outline;//概要
	private String title;//标题
	private String content;//正文
	private Integer sentiment;//情感分类
	private String emotionalIndex;//数值
	private String spread_num;//传播数量
	private String read_num;//阅读量
	private String content_num;//评论量
	private String publish_time;//发布时间
	private String source_name;//来源名称
	private String source_url;//来源URL
	private String negative;//负面
	private String positive;//正面
	private String contenthtml;//文本html
	private Integer flag;//默认为0），手动输入：1重大利好、2利好、3利中、4利空、5重大利空'
	private Integer ismark;//样本是否标记更新，0，未更新，1，已更新'
	private String plate_name;//
	private Integer article_type;//默认0；1代表财经新闻,8代表微博，9代表微信公众号
	private Integer seed_id;//正文对应的任务表ID
	private Integer website_id;
	private String avatar_url;
	
	private String article_public_id;
	
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getSentiment() {
		return sentiment;
	}
	public void setSentiment(Integer sentiment) {
		this.sentiment = sentiment;
	}
	public String getEmotionalIndex() {
		return emotionalIndex;
	}
	public void setEmotionalIndex(String emotionalIndex) {
		this.emotionalIndex = emotionalIndex;
	}
	public String getSpread_num() {
		return spread_num;
	}
	public void setSpread_num(String spread_num) {
		this.spread_num = spread_num;
	}
	public String getRead_num() {
		return read_num;
	}
	public void setRead_num(String read_num) {
		this.read_num = read_num;
	}
	public String getContent_num() {
		return content_num;
	}
	public void setContent_num(String content_num) {
		this.content_num = content_num;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getNegative() {
		return negative;
	}
	public void setNegative(String negative) {
		this.negative = negative;
	}
	public String getPositive() {
		return positive;
	}
	public void setPositive(String positive) {
		this.positive = positive;
	}
	public String getContenthtml() {
		return contenthtml;
	}
	public void setContenthtml(String contenthtml) {
		this.contenthtml = contenthtml;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getIsmark() {
		return ismark;
	}
	public void setIsmark(Integer ismark) {
		this.ismark = ismark;
	}
	public String getPlate_name() {
		return plate_name;
	}
	public void setPlate_name(String plate_name) {
		this.plate_name = plate_name;
	}
	public Integer getArticle_type() {
		return article_type;
	}
	public void setArticle_type(Integer article_type) {
		this.article_type = article_type;
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
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getArticle_public_id() {
		return article_public_id;
	}
	public void setArticle_public_id(String article_public_id) {
		this.article_public_id = article_public_id;
	}
	@Override
	public String toString() {
		return "SpiderArticle [article_id=" + article_id + ", outline=" + outline + ", title=" + title + ", content="
				+ content + ", sentiment=" + sentiment + ", emotionalIndex=" + emotionalIndex + ", spread_num="
				+ spread_num + ", read_num=" + read_num + ", content_num=" + content_num + ", publish_time="
				+ publish_time + ", source_name=" + source_name + ", source_url=" + source_url + ", negative="
				+ negative + ", positive=" + positive + ", contenthtml=" + contenthtml + ", flag=" + flag + ", ismark="
				+ ismark + ", plate_name=" + plate_name + ", article_type=" + article_type + ", seed_id=" + seed_id
				+ ", website_id=" + website_id + ", avatar_url=" + avatar_url + ", article_public_id="
				+ article_public_id + "]";
	}
	
}

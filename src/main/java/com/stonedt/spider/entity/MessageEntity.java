package com.stonedt.spider.entity;

public class MessageEntity {
	private String name;

	private String title;
	
	private String source_url;
	
	private String publish_time;
	
	private String sourcewebsitename;
	
	private String article_public_id;

	private String author;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public String getSourcewebsitename() {
		return sourcewebsitename;
	}

	public void setSourcewebsitename(String sourcewebsitename) {
		this.sourcewebsitename = sourcewebsitename;
	}

	public String getArticle_public_id() {
		return article_public_id;
	}

	public void setArticle_public_id(String article_public_id) {
		this.article_public_id = article_public_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "MessageEntity [name=" + name + ", title=" + title + ", source_url=" + source_url + ", publish_time="
				+ publish_time + ", sourcewebsitename=" + sourcewebsitename + ", article_public_id=" + article_public_id
				+ ", author=" + author + "]";
	}
	
	
	
	
	
	
	
}

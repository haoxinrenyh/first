package com.stonedt.spider.entity;

import java.util.List;

public class SeedCountOb {
	private Integer seedid;
	private Integer level;
	private List<SeedCount> count;
	public Integer getSeedid() {
		return seedid;
	}
	public void setSeedid(Integer seedid) {
		this.seedid = seedid;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public List<SeedCount> getCount() {
		return count;
	}
	public void setCount(List<SeedCount> count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "SeedCountOb [seedid=" + seedid + ", level=" + level + ", count=" + count + "]";
	}
	
	
	
}

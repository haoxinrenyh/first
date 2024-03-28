package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.ResearchReport;
import com.stonedt.spider.entity.reportType;

public interface ResearchReportService {
	
	/**
	 * 根据用户选择标签查询相关研报
	 * @param seed_id
	 * @return
	 */
	public List<ResearchReport> getResearchReport(int seed_id);
	
	
	/**
	 * 根据用户选择的标签查询对应的名字 
	 * @param seed_id
	 * @return
	 */
	public String getResearchReportName(int seed_id);


	public ResearchReport getNoticeReport(Integer seed_id);


	public ResearchReport getResearchReportdetail(Integer id,Integer type);


	public List<ResearchReport> getOptionalResearchReport(String code,String rtype);


	public List<reportType> getrtyps(String noticecode);
	
}

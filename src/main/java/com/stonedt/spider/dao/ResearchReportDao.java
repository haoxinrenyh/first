package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.ResearchReport;
import com.stonedt.spider.entity.reportType;

@Mapper
public interface ResearchReportDao {
	/**
	 * 根据用户选择标签查询相关研报
	 * 
	 * @param seed_id
	 * @return
	 */
	public List<ResearchReport> getResearchReport(@Param(value = "seed_id") int seed_id);

	/**
	 * 根据用户选择的标签查询对应的名字
	 * 
	 * @param seed_id
	 * @return
	 */
	public String getResearchReportName(@Param(value = "seed_id") int seed_id);

	public ResearchReport getNoticeReport(Integer seed_id);

	public ResearchReport getResearchReportdetail(Integer id);

	public ResearchReport getOptionalResearchReportdetail(Integer id);

	public List<ResearchReport> getOptionalResearchReport(@Param("code") String code, @Param("rtype") String rtype);

	public ResearchReport getOptionalResearchReportdetail1(Integer id);

	public List<reportType> getrtyps(@Param("code") String code);
}

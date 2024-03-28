package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.ResearchReportDao;
import com.stonedt.spider.entity.ResearchReport;
import com.stonedt.spider.entity.reportType;
import com.stonedt.spider.service.ResearchReportService;
@Service("ResearchReportService")
public class ResearchReportServiceImpl implements ResearchReportService{
	
	@Autowired
	private ResearchReportDao researchReportDao;
	
	/**
	 * 根据用户选择标签查询相关研报
	 * @param seed_id
	 * @return
	 */
	@Override
	public List<ResearchReport> getResearchReport(int seed_id) {
		return researchReportDao.getResearchReport(seed_id);
	}
	/**
	 * 根据用户选择的标签查询对应的名字 
	 * @param seed_id
	 * @return
	 */
	@Override
	public String getResearchReportName(int seed_id) {
		return researchReportDao.getResearchReportName(seed_id);
	}
	@Override
	public ResearchReport getNoticeReport(Integer seed_id) {
		// TODO Auto-generated method stub
		return researchReportDao.getNoticeReport(seed_id);
	}
	@Override
	public ResearchReport getResearchReportdetail(Integer id,Integer type) {
		// TODO Auto-generated method stub
		if(type == 1){
			return researchReportDao.getResearchReportdetail(id);
		}else if(type == 2){
			return researchReportDao.getOptionalResearchReportdetail(id);
		}else{
			return researchReportDao.getOptionalResearchReportdetail1(id);
		}
		
	}
	@Override
	public List<ResearchReport> getOptionalResearchReport(String code,String rtype) {
		// TODO Auto-generated method stub
		return researchReportDao.getOptionalResearchReport(code,rtype);
	}
	@Override
	public List<reportType> getrtyps(String noticecode) {
		// TODO Auto-generated method stub
		return researchReportDao.getrtyps(noticecode);
	}
	
}

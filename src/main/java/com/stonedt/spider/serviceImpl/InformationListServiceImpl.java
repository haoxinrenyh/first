package com.stonedt.spider.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.InformationListDao;
import com.stonedt.spider.entity.InformationEntity;
import com.stonedt.spider.entity.Wechat;
import com.stonedt.spider.service.InformationListService;
import com.stonedt.spider.util.ArticlePicture;
@Service("InformationListService")
public class InformationListServiceImpl implements InformationListService{
	
	@Autowired
	private InformationListDao informationListDao;
	
	
	/**
	 * 根据用户选择的标签查询相关资讯  15条
	 */
	@Override
	public List<InformationEntity> getInformationList(Integer seed_id) {
		
		
		List<InformationEntity> list = informationListDao.getInformationList(seed_id);
		
		List<String> keywords = new ArrayList<String>();
		List<String> stock = new ArrayList<String>();
		List<String> plate = new ArrayList<String>();
		
		//建造关键词假数据  15组
		keywords.add("广东双林,产品规格,抄底指标");
		keywords.add("第一股,主力资金,第一目标");
		keywords.add("碳酸锂, 价格类型电池级, 碳酸锂价格");
		keywords.add("河南地区, 贸易商, 甲醇市场");
		keywords.add("库存数据,下方,向社会,今日关注");
		keywords.add("宝泰隆, 煤化工, 焦炉气制甲醇, 装置运行");
		keywords.add("资金拆借,孟斌,,监管公告,业务规则");
		keywords.add("多操作,合金厂,窄幅震荡,钢厂,市场情绪");
		keywords.add("银行贷款担保,抵押质押,担保文件,股东大会审议");
		keywords.add("本期债券,按年度,扣税,票面利率");
		keywords.add("公司财务总监,公告披露,持有公司股份");
		keywords.add("上投摩根,销售过程中,首募,失败,未能");
		keywords.add("港交所,赵轶,吴亚军,投票权,套现");
		keywords.add("宏观经济,整体环境,安鑫,信用评级");
		keywords.add("激励措施,集体所有制,不断扩大");
		
		/*//建造个股假数据  15组
		stock.add("美锦能源, 雪人股份");
		stock.add("飞鹿股份");
		stock.add("华业资本, 天夏智慧, 苏宁易购");
		stock.add("新华保险, 中国平安");
		stock.add("三安光电");
		stock.add("永泰能源, 广发证券");
		stock.add("顺丰控股");
		stock.add("东方海洋");
		stock.add("中恒电气");
		stock.add("中国动力");
		stock.add("平庄能源, 云煤能源, 山西焦化");
		stock.add("麦捷科技");
		stock.add("中科曙光");
		stock.add("兴业证券, 华泰证券");
		stock.add("药明康德");
		
		//建造板块假数据15组
		plate.add("沪股通,化工行业");
		plate.add("木业家具,山东板块,锂电池");
		plate.add("房地产,股权激励");
		plate.add("化肥行业,化工原料");
		plate.add("参股保险,参股银行,股权激励");
		plate.add("独家药品,医药制造");
		plate.add("国企改革,汽车行业");
		plate.add("创投,独角兽,房地产");
		plate.add("参股期货,酿酒行业");
		plate.add("长江三角,举牌概念");
		plate.add("新疆板块,医疗行业");
		plate.add("ST概念,化工行业,内蒙古");
		plate.add("参股银行,房地产");
		plate.add("长江三角,国企改革,海洋经济");
		plate.add("长江三角,交运设备");*/
		
		
		
		for(int i=0;i<list.size();i++) {
			String stock1 = list.get(i).getStock();
			if(stock1 == null || "".equals(stock1) || "[]".equals(stock1)) {
				stock1 = "-";
			}else {
				if(stock1.length()>15) {
					stock1 = stock1.substring(0, 15);
					stock1 = stock1+"...";
				}
			}
			String plate1 = list.get(i).getPlate();
			if(plate1 ==null || "".equals(plate1) || "[]".equals(plate1)) {
				plate1 = "-";
			}else {
				if(plate1.length()>12) {
					plate1 = plate1.substring(0, 12);
					plate1 = plate1+"...";
				}
				
			}
			String content = list.get(i).getContenthtml().toString();
			Document parse = Jsoup.parse(content);
			content=parse.text();
			content = content.replaceAll(" ", "").trim();
			if(content.equals("") || content == null) {
				content = "...";
			}else {
				if(content.length()<100) {
					content = content.substring(2);
				}else if(content.length()>=100 && content.length()<150){
					content = content.substring(2, 100);
				}else {
					content = content.substring(2, 120);
				}
				content = content + "...";
			}
			list.get(i).setOutLine(content);
			String contenthtml = list.get(i).getContenthtml();
			InformationEntity information = ArticlePicture.getPicture(contenthtml,true,seed_id+"");
			
			list.get(i).setPicC(information.getPicC());
			list.get(i).setPicUrl(information.getPicUrl());
			list.get(i).setKeyword(keywords.get(i));
			list.get(i).setStock(stock1);
			list.get(i).setPlate(plate1);
			list.get(i).setSeed_id(seed_id+"");

			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(date);
			String time = list.get(i).getPublish_time();
			if(time.contains(today)) {
				time = time.substring(11, 16);
			}else {
				time = time.substring(0, 10);
			}
			
			list.get(i).setPublish_time(time);
			
		}
		
		return list;
	}


	@Override
	public InformationEntity getInformationById(int id) {
		InformationEntity info = informationListDao.getInformationById(id);
		String contenthtml = info.getContenthtml();
		//去除正文中的广告
		contenthtml = ArticlePicture.removeAdvertisement(contenthtml);
		info.setContenthtml(contenthtml);
		
		
		return info;
	}
	
	/**
	 * 根据用户选择的子分类，查询对应的大分类
	 * @param seed_id
	 * @return
	 */
	@Override
	public String getFirstType(int seed_id) {
		String first_type = informationListDao.getFirstType(seed_id);
		String name = null;
		List<String> list = new ArrayList<>();
		list.add("117");
		list.add("118");
		list.add("119");
		list.add("120");
		list.add("121");
		list.add("122");
		list.add("123");
		list.add("124");
		list.add("125");
		list.add("129");
		String seed = seed_id+"";
		if("b1".equals(first_type)) {
			name = "行业聚焦";
		}else if("b2".equals(first_type)) {
			if(list.contains(seed)) {
				name = "热点资讯";
			}else {
				name="概念板块";
			}
			
		}else if("b4".equals(first_type)) {
			name = "财经新闻";
		}else if("b5".equals(first_type)) {
			name = "股票市场";
		}else if("b9".equals(first_type)) {
			name = "期货市场";
		}else if("b13".equals(first_type)) {
			name = "政府机构";
		}else if("b14".equals(first_type)) {
			name = "省市地区";
		}else if("b3".equals(first_type)) {
			name = "国际市场";
		}else if("b12".equals(first_type)) {
			name = "央行动态";
		}else if("b15".equals(first_type)) {
			name = "财经媒体";
		}else if("b6".equals(first_type)) {
			name = "固守收益";
		}else if("b8".equals(first_type)) {
			name = "创投并购";
		}else if("b10".equals(first_type)) {
			name = "期货品种";
		}else if("b11".equals(first_type)) {
			name = "外汇市场";
		}
		
		return name;
	}
	
	
	
	

	/**
	 * 根据用户选择的子分类，查询子标签名
	 * @param seed_id
	 * @return
	 */
	@Override
	public String getSeedName(int seed_id) {
		
		return informationListDao.getSeedName(seed_id);
	}


	@Override
	public List<InformationEntity> getInformationListByType(Integer article_type) {
		List<InformationEntity> list = informationListDao.getInformationListByType(article_type);
		
		List<String> keywords = new ArrayList<String>();
		//建造关键词假数据  15组
		keywords.add("广东双林,产品规格,抄底指标");
		keywords.add("第一股,主力资金,第一目标");
		keywords.add("碳酸锂, 价格类型电池级, 碳酸锂价格");
		keywords.add("河南地区, 贸易商, 甲醇市场");
		keywords.add("库存数据,下方,向社会,今日关注");
		keywords.add("宝泰隆, 煤化工, 焦炉气制甲醇, 装置运行");
		keywords.add("资金拆借,孟斌,,监管公告,业务规则");
		keywords.add("多操作,合金厂,窄幅震荡,钢厂,市场情绪");
		keywords.add("银行贷款担保,抵押质押,担保文件,股东大会审议");
		keywords.add("本期债券,按年度,扣税,票面利率");
		keywords.add("公司财务总监,公告披露,持有公司股份");
		keywords.add("上投摩根,销售过程中,首募,失败,未能");
		keywords.add("港交所,赵轶,吴亚军,投票权,套现");
		keywords.add("宏观经济,整体环境,安鑫,信用评级");
		keywords.add("激励措施,集体所有制,不断扩大");
		
		for(int i=0;i<list.size();i++) {
			String content = list.get(i).getContent().toString();
			Document parse = Jsoup.parse(content);
			content=parse.text();
			content = content.trim();
			if(content.length()<100) {
				content = content.substring(2);
			}else if(content.length()>=100 && content.length()<150){
				content = content.substring(2, 100);
			}else {
				content = content.substring(2, 150);
				
			}
			content = content + "...";
			list.get(i).setOutLine(content);
			String contenthtml = list.get(i).getContenthtml();
			InformationEntity information = ArticlePicture.getPicture(contenthtml,false,"");
			list.get(i).setPicC(information.getPicC());
			list.get(i).setPicUrl(information.getPicUrl());
			list.get(i).setKeyword(keywords.get(i));
			String time = list.get(i).getPublish_time().substring(0, 19);
			list.get(i).setPublish_time(time);
			
		}
		return list;
	}


	@Override
	public InformationEntity getInformationSocialById(int id) {
		InformationEntity info = informationListDao.getInformationById(id);
		String contenthtml = info.getContent();
		//去除正文中的广告
		contenthtml = ArticlePicture.removeAdvertisement(contenthtml);
		info.setContenthtml(contenthtml);
		
		return info;
	}


	@Override
	public List<InformationEntity> getWechatInformationList(int id) {
       List<InformationEntity> list = informationListDao.getWechatInformationList(id);
		
		List<String> keywords = new ArrayList<String>();
		List<String> stock = new ArrayList<String>();
		List<String> plate = new ArrayList<String>();
		
		//建造关键词假数据  15组
		keywords.add("广东双林,产品规格,抄底指标");
		keywords.add("第一股,主力资金,第一目标");
		keywords.add("碳酸锂, 价格类型电池级, 碳酸锂价格");
		keywords.add("河南地区, 贸易商, 甲醇市场");
		keywords.add("库存数据,下方,向社会,今日关注");
		keywords.add("宝泰隆, 煤化工, 焦炉气制甲醇, 装置运行");
		keywords.add("资金拆借,孟斌,,监管公告,业务规则");
		keywords.add("多操作,合金厂,窄幅震荡,钢厂,市场情绪");
		keywords.add("银行贷款担保,抵押质押,担保文件,股东大会审议");
		keywords.add("本期债券,按年度,扣税,票面利率");
		keywords.add("公司财务总监,公告披露,持有公司股份");
		keywords.add("上投摩根,销售过程中,首募,失败,未能");
		keywords.add("港交所,赵轶,吴亚军,投票权,套现");
		keywords.add("宏观经济,整体环境,安鑫,信用评级");
		keywords.add("激励措施,集体所有制,不断扩大");
		
		//建造个股假数据  15组
		stock.add("美锦能源, 雪人股份");
		stock.add("飞鹿股份");
		stock.add("华业资本, 天夏智慧, 苏宁易购");
		stock.add("新华保险, 中国平安");
		stock.add("三安光电");
		stock.add("永泰能源, 广发证券");
		stock.add("顺丰控股");
		stock.add("东方海洋");
		stock.add("中恒电气");
		stock.add("中国动力");
		stock.add("平庄能源, 云煤能源, 山西焦化");
		stock.add("麦捷科技");
		stock.add("中科曙光");
		stock.add("兴业证券, 华泰证券");
		stock.add("药明康德");
		
		//建造板块假数据15组
		plate.add("沪股通,化工行业");
		plate.add("木业家具,山东板块,锂电池");
		plate.add("房地产,股权激励");
		plate.add("化肥行业,化工原料");
		plate.add("参股保险,参股银行,股权激励");
		plate.add("独家药品,医药制造");
		plate.add("国企改革,汽车行业");
		plate.add("创投,独角兽,房地产");
		plate.add("参股期货,酿酒行业");
		plate.add("长江三角,举牌概念");
		plate.add("新疆板块,医疗行业");
		plate.add("ST概念,化工行业,内蒙古");
		plate.add("参股银行,房地产");
		plate.add("长江三角,国企改革,海洋经济");
		plate.add("长江三角,交运设备");
		if(list.size()>0) {
		for(int i=0;i<list.size();i++) {
			String stock1 = list.get(i).getStock();
			if(stock1 == null || "".equals(stock1) || "[]".equals(stock1)) {
				stock1 = "-";
			}else {
				if(stock1.length()>12) {
					stock1 = stock1.substring(0, 12);
					stock1 = stock1+"...";
				}
			}
			String plate1 = list.get(i).getPlate();
			if(plate1 ==null || "".equals(plate1) || "[]".equals(plate1)) {
				plate1 = "-";
			}else {
				if(plate1.length()>12) {
					plate1 = plate1.substring(0, 12);
					plate1 = plate1+"...";
				}
				
			}
			String content = list.get(i).getContenthtml().toString();
			Document parse = Jsoup.parse(content);
			content=parse.text();
			content = content.trim();
			if(content.length()<100) {
				content = content.substring(2);
			}else if(content.length()>=100 && content.length()<150){
				content = content.substring(2, 100);
			}else {
				content = content.substring(2, 150);
				
			}
			content = content + "...";
			list.get(i).setOutLine(content);
			String contenthtml = list.get(i).getContenthtml();
			InformationEntity information = ArticlePicture.getPicture(contenthtml,false,"");
			list.get(i).setPicC(information.getPicC());
			list.get(i).setPicUrl(information.getPicUrl());
			list.get(i).setKeyword(keywords.get(i));
			list.get(i).setStock(stock1);
			list.get(i).setPlate(plate1);
			list.get(i).setSeed_id(id+"");
			String time = list.get(i).getPublish_time().substring(0, 19);
			list.get(i).setPublish_time(time);
			
		}
		}
		return list;
		
		
		
	}


	@Override
	public Wechat getWechatName(int id) {
		return informationListDao.getWechatName(id);
	}


	@Override
	public List<InformationEntity> getWeboInformationList(int id) {
		// TODO Auto-generated method stub
		return informationListDao.getWeboInformationList(id);
	}


	@Override
	public InformationEntity getLastinfo(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getLastinfo(infomation);
	}


	@Override
	public InformationEntity getNextinfo(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getNextinfo(infomation);
	}

	/**
	 * 获取当前页码
	 */
	@Override
	public Integer getPagenumInfo(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getPagenumInfo(infomation);
	}

	
	@Override
	public InformationEntity getLastinfoNew(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getLastinfo(infomation);
	}


	@Override
	public InformationEntity getNextinfoNew(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getNextinfo(infomation);
	}

	/**
	 * 获取当前页码
	 */
	@Override
	public Integer getPagenumInfoNew(InformationEntity infomation) {
		// TODO Auto-generated method stub
		return informationListDao.getPagenumInfo(infomation);
	}

	
	/**
	 * 获取对应子标签的大分类
	 */
	@Override
	public String getFirstLabel(int seed_id) {
		return informationListDao.getFirstType(seed_id);
	}


	@Override
	public List<InformationEntity> getNewArticle() {
		List<InformationEntity> list = informationListDao.getNewArticle();
		List<String> keywords = new ArrayList<String>();
		List<String> stock = new ArrayList<String>();
		List<String> plate = new ArrayList<String>();
		
		//建造关键词假数据  15组
		keywords.add("广东双林,产品规格,抄底指标");
		keywords.add("第一股,主力资金,第一目标");
		keywords.add("碳酸锂, 价格类型电池级, 碳酸锂价格");
		keywords.add("河南地区, 贸易商, 甲醇市场");
		keywords.add("库存数据,下方,向社会,今日关注");
		keywords.add("宝泰隆, 煤化工, 焦炉气制甲醇, 装置运行");
		keywords.add("资金拆借,孟斌,,监管公告,业务规则");
		keywords.add("多操作,合金厂,窄幅震荡,钢厂,市场情绪");
		keywords.add("银行贷款担保,抵押质押,担保文件,股东大会审议");
		keywords.add("本期债券,按年度,扣税,票面利率");
		keywords.add("公司财务总监,公告披露,持有公司股份");
		keywords.add("上投摩根,销售过程中,首募,失败,未能");
		keywords.add("港交所,赵轶,吴亚军,投票权,套现");
		keywords.add("宏观经济,整体环境,安鑫,信用评级");
		keywords.add("激励措施,集体所有制,不断扩大");
		
		/*//建造个股假数据  15组
		stock.add("美锦能源, 雪人股份");
		stock.add("飞鹿股份");
		stock.add("华业资本, 天夏智慧, 苏宁易购");
		stock.add("新华保险, 中国平安");
		stock.add("三安光电");
		stock.add("永泰能源, 广发证券");
		stock.add("顺丰控股");
		stock.add("东方海洋");
		stock.add("中恒电气");
		stock.add("中国动力");
		stock.add("平庄能源, 云煤能源, 山西焦化");
		stock.add("麦捷科技");
		stock.add("中科曙光");
		stock.add("兴业证券, 华泰证券");
		stock.add("药明康德");
		
		//建造板块假数据15组
		plate.add("沪股通,化工行业");
		plate.add("木业家具,山东板块,锂电池");
		plate.add("房地产,股权激励");
		plate.add("化肥行业,化工原料");
		plate.add("参股保险,参股银行,股权激励");
		plate.add("独家药品,医药制造");
		plate.add("国企改革,汽车行业");
		plate.add("创投,独角兽,房地产");
		plate.add("参股期货,酿酒行业");
		plate.add("长江三角,举牌概念");
		plate.add("新疆板块,医疗行业");
		plate.add("ST概念,化工行业,内蒙古");
		plate.add("参股银行,房地产");
		plate.add("长江三角,国企改革,海洋经济");
		plate.add("长江三角,交运设备");*/
		
		
		
		for(int i=0;i<list.size();i++) {
			String stock1 = list.get(i).getStock();
			if(stock1 == null || "".equals(stock1) || "[]".equals(stock1)) {
				stock1 = "-";
			}else {
				if(stock1.length()>10) {
					stock1 = stock1.substring(0, 10);
					stock1 = stock1+"...";
				}
			}
			String plate1 = list.get(i).getPlate();
			if(plate1 ==null || "".equals(plate1) || "[]".equals(plate1)) {
				plate1 = "-";
			}else {
				if(plate1.length()>10) {
					plate1 = plate1.substring(0, 10);
					plate1 = plate1+"...";
				}
				
			}
			String content = list.get(i).getContenthtml().toString();
			Document parse = Jsoup.parse(content);
			content=parse.text();
			content = content.replaceAll(" ", "").trim();
			if(content.length()<100) {
				content = content.substring(2);
			}else if(content.length()>=100 && content.length()<150){
				content = content.substring(2, 100);
			}else {
				content = content.substring(2, 120);
			}
			content = content + "...";
			list.get(i).setOutLine(content);
			String contenthtml = list.get(i).getContenthtml();
			InformationEntity information = ArticlePicture.getPicture(contenthtml,true,null);
			
			list.get(i).setPicC(information.getPicC());
			list.get(i).setPicUrl(information.getPicUrl());
			list.get(i).setKeyword(keywords.get(i));
			list.get(i).setStock(stock1);
			list.get(i).setPlate(plate1);

			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(date);
			String time = list.get(i).getPublish_time();
			if(time.contains(today)) {
				time = time.substring(11, 16);
			}else {
				time = time.substring(0, 10);
			}
			
			list.get(i).setPublish_time(time);
			
		}
		return list;
	}


	@Override
	public List<InformationEntity> getNewsByEmotionation(Integer gradenum) {
		List<InformationEntity> list = informationListDao.getNewsByEmotionation(gradenum);
		List<String> keywords = new ArrayList<String>();
		List<String> stock = new ArrayList<String>();
		List<String> plate = new ArrayList<String>();
		
		//建造关键词假数据  15组
		keywords.add("广东双林,产品规格,抄底指标");
		keywords.add("第一股,主力资金,第一目标");
		keywords.add("碳酸锂, 价格类型电池级, 碳酸锂价格");
		keywords.add("河南地区, 贸易商, 甲醇市场");
		keywords.add("库存数据,下方,向社会,今日关注");
		keywords.add("宝泰隆, 煤化工, 焦炉气制甲醇, 装置运行");
		keywords.add("资金拆借,孟斌,,监管公告,业务规则");
		keywords.add("多操作,合金厂,窄幅震荡,钢厂,市场情绪");
		keywords.add("银行贷款担保,抵押质押,担保文件,股东大会审议");
		keywords.add("本期债券,按年度,扣税,票面利率");
		keywords.add("公司财务总监,公告披露,持有公司股份");
		keywords.add("上投摩根,销售过程中,首募,失败,未能");
		keywords.add("港交所,赵轶,吴亚军,投票权,套现");
		keywords.add("宏观经济,整体环境,安鑫,信用评级");
		keywords.add("激励措施,集体所有制,不断扩大");
		
		/*//建造个股假数据  15组
		stock.add("美锦能源, 雪人股份");
		stock.add("飞鹿股份");
		stock.add("华业资本, 天夏智慧, 苏宁易购");
		stock.add("新华保险, 中国平安");
		stock.add("三安光电");
		stock.add("永泰能源, 广发证券");
		stock.add("顺丰控股");
		stock.add("东方海洋");
		stock.add("中恒电气");
		stock.add("中国动力");
		stock.add("平庄能源, 云煤能源, 山西焦化");
		stock.add("麦捷科技");
		stock.add("中科曙光");
		stock.add("兴业证券, 华泰证券");
		stock.add("药明康德");
		
		//建造板块假数据15组
		plate.add("沪股通,化工行业");
		plate.add("木业家具,山东板块,锂电池");
		plate.add("房地产,股权激励");
		plate.add("化肥行业,化工原料");
		plate.add("参股保险,参股银行,股权激励");
		plate.add("独家药品,医药制造");
		plate.add("国企改革,汽车行业");
		plate.add("创投,独角兽,房地产");
		plate.add("参股期货,酿酒行业");
		plate.add("长江三角,举牌概念");
		plate.add("新疆板块,医疗行业");
		plate.add("ST概念,化工行业,内蒙古");
		plate.add("参股银行,房地产");
		plate.add("长江三角,国企改革,海洋经济");
		plate.add("长江三角,交运设备");*/
		
		
		
		for(int i=0;i<list.size();i++) {
			String stock1 = list.get(i).getStock();
			if(stock1 == null || "".equals(stock1) || "[]".equals(stock1)) {
				stock1 = "-";
			}else {
				if(stock1.length()>10) {
					stock1 = stock1.substring(0, 10);
					stock1 = stock1+"...";
				}
			}
			String plate1 = list.get(i).getPlate();
			if(plate1 ==null || "".equals(plate1) || "[]".equals(plate1)) {
				plate1 = "-";
			}else {
				if(plate1.length()>10) {
					plate1 = plate1.substring(0, 10);
					plate1 = plate1+"...";
				}
				
			}
			String content = list.get(i).getContenthtml().toString();
			Document parse = Jsoup.parse(content);
			content=parse.text();
			content = content.replaceAll(" ", "").trim();
			if(content.length()<100) {
				content = content.substring(2);
			}else if(content.length()>=100 && content.length()<150){
				content = content.substring(2, 100);
			}else {
				content = content.substring(2, 120);
			}
			content = content + "...";
			list.get(i).setOutLine(content);
			String contenthtml = list.get(i).getContenthtml();
			InformationEntity information = ArticlePicture.getPicture(contenthtml,true,null);
			
			list.get(i).setPicC(information.getPicC());
			list.get(i).setPicUrl(information.getPicUrl());
			list.get(i).setKeyword(keywords.get(i));
			list.get(i).setStock(stock1);
			list.get(i).setPlate(plate1);

			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(date);
			String time = list.get(i).getPublish_time();
			if(time.contains(today)) {
				time = time.substring(11, 16);
			}else {
				time = time.substring(0, 10);
			}
			
			list.get(i).setPublish_time(time);
			
		}
		return list;
	}
	
}

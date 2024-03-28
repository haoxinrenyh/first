package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SeedConfigDao;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.service.SeedConfigService;
@Service("SeedConfigService")
public class SeedConfigServiceImpl implements SeedConfigService {
	@Autowired
	private SeedConfigDao seedConfigDao;
	
	/**
	 * 新增详情页种子任务配置解析规则
	 * @author lujun
	 *
	 */
	@Override
	public int insertSeedconfig(SpiderSeedConfig spiderSeedConfig) {
		
		return seedConfigDao.insertSeedconfig(spiderSeedConfig);
	}
	/**
	 * 根据seed_id查询详情页种子任务配置解析规则
	 * @author lujun
	 *
	 */
	@Override
	public List<SpiderSeedConfig> listseedconfig(Integer seedId) {
		// TODO Auto-generated method stub
		return seedConfigDao.listseedconfig(seedId);
	}
	
	/**
	 * 根据seed_id修改详情页种子任务配置解析规则
	 * @author lujun
	 *
	 */
	@Override
	public int updateSeedConfig(SpiderSeedConfig spiderSeedConfig) {
		/*// TODO Auto-generated method stub
		spiderSeedConfig.setCreate_date("");//创建时间
		spiderSeedConfig.setId(1);
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeedConfig.setSeed_date_config("");//种子时间配置
		spiderSeedConfig.setSeed_id(1);//种子所属的任务ID
		spiderSeedConfig.setSeed_interval_config("");//种子抓取间隔配置
		spiderSeedConfig.setSeed_list_url("");//种子列表链接
		spiderSeedConfig.setSeed_name("");//种子地址名
		spiderSeedConfig.setSeed_storage_json("");//种子存储json字符串
		spiderSeedConfig.setSeed_text_config("");//种子正文配置
		spiderSeedConfig.setSeed_text_config_type(1);//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(100);//种子线程数量配置
		spiderSeedConfig.setSeed_title_config("");//种子标题配置
		spiderSeedConfig.setSeed_url_config("");//种子链接配置
*/		
		return seedConfigDao.updateSeedConfig(spiderSeedConfig);
	}
	@Override
	public SpiderSeedConfig getseedconfig(Integer seedid) {
		return seedConfigDao.getseedconfig(seedid);
	}
	@Override
	public SpiderSeed selectSeedAtrticleType(Integer seedid) {
		return seedConfigDao.selectSeedAtrticleType(seedid);
	}
	@Override
	public int closeMapping(int id) {
		return seedConfigDao.closeMapping(id);
	}
	@Override
	public int insertForeignSeedconfig(SpiderSeedConfig spiderSeedConfig) {
		return seedConfigDao.insertForeignSeedconfig(spiderSeedConfig);
	}
	@Override
	public SpiderSeedConfig getforeignseedconfig(Integer seedid) {
		// TODO Auto-generated method stub
		return seedConfigDao.getforeignseedconfig(seedid);
	}
	@Override
	public SpiderSeed selectForeignSeedAtrticleType(Integer seedid) {
		return seedConfigDao.selectForeignSeedAtrticleType(seedid);
	}
	@Override
	public int updateForeignSeedConfig(SpiderSeedConfig spiderSeedConfig) {
		// TODO Auto-generated method stub
		return seedConfigDao.updateForeignSeedConfig(spiderSeedConfig);
	}
	@Override
	public int insertSeedconfigtest(SpiderSeedConfig spiderSeedConfig) {
		// TODO Auto-generated method stub
		return seedConfigDao.insertSeedconfigtest(spiderSeedConfig);

	}
	@Override
	public SpiderSeedConfig getseedconfigtest(Integer seedid) {
		// TODO Auto-generated method stub
		return seedConfigDao.getseedconfigtest(seedid);
	}
	@Override
	public SpiderSeed selectSeedAtrticleTypetest(Integer seedid) {
		// TODO Auto-generated method stub
		return seedConfigDao.selectSeedAtrticleTypetest(seedid);
	}
	@Override
	public void updateSeedsFlag(List<Integer> successids) {
		// TODO Auto-generated method stub
		seedConfigDao.updateseedsflag(successids);
	}
	
}

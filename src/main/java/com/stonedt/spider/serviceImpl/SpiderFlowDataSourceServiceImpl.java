package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.SpiderFlowDataSourceDao;
import com.stonedt.spider.entity.SpiderFlowDataSource;
import com.stonedt.spider.service.SpiderFlowDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/5/6 14:02
 * @Copyright
 */
@Service
public class SpiderFlowDataSourceServiceImpl implements SpiderFlowDataSourceService {

    @Autowired
    private SpiderFlowDataSourceDao dataSourceDao;

    /**
     * @author dxk
     * @date 2021/5/6 14:06
      查询所有的数据源
     * @return
     * @throws
     * @since
    */
    @Override
    public List<SpiderFlowDataSource> selectListAll() {
        return dataSourceDao.selectListAll();
    }

}

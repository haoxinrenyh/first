package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.TemporaryUrlDao;
import com.stonedt.spider.service.TemporaryUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/5/6 14:38
 * @Copyright
 */
@Service
public class TemporaryUrlServiceImpl  implements TemporaryUrlService {

    @Autowired
    private TemporaryUrlDao urlDao;
}

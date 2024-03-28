
package com.stonedt.spider.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.DictDao;

/**
* <p></p>
* <p>Title: DictServiceImpl</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date Apr 30, 2020  
*/
@Service
public class DictServiceImpl {
	
	@Autowired
	private DictDao dictDao;

	/**
	 * 查询
	 * @return
	 */
	public List<Map<String, Object>> listHotWords(){
		return dictDao.listHotWords();
	}
	
	public List<Map<String, Object>> listStopWords(){
		return dictDao.listStopWords();
	}
	
	/**
	 * 新增
	 * @param word
	 * @return
	 */
	public Integer saveHotWords(String word) {
		return dictDao.saveHotWords(word);
	}
	public Integer saveStopWords(String word) {
		return dictDao.saveStopWords(word);
	}
	
	/**
	 * 删除
	 * @param word
	 * @return
	 */
	public Integer delHotWords(String word) {
		return dictDao.delHotWords(word);
	}
	public Integer delStopWords(String word) {
		return dictDao.delStopWords(word);
	}
}

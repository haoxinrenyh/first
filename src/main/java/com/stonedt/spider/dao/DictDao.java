
package com.stonedt.spider.dao;
/**
* <p></p>
* <p>Title: DictDao</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date Apr 30, 2020  
*/

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictDao {

	/**
	 * 查询
	 * 
	 * @return
	 */
	List<Map<String, Object>> listHotWords();

	List<Map<String, Object>> listStopWords();

	/**
	 * 新增
	 * 
	 * @param word
	 * @return
	 */
	Integer saveHotWords(String word);

	Integer saveStopWords(String word);

	/**
	 * 删除
	 * 
	 * @param word
	 * @return
	 */
	Integer delHotWords(String word);

	Integer delStopWords(String word);
}

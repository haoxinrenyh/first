package com.stonedt.spider.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class CkickHouseLink {
	
	public static List<Map<String, Object>> search(String sql) {
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //System.out.println("clikchouse查询开始时间:" + sdf.format(new Date()));
		  Connection connection = null;
		  Statement statement = null;
		  List<Map<String, Object>> list = new ArrayList<>();
		  try {
		   Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
		   connection = DriverManager.getConnection("jdbc:clickhouse://192.168.71.83:8123/ent_map", "default", "config");
		   statement = connection.createStatement();
		   ResultSet results = statement.executeQuery(sql);
		   ResultSetMetaData rsmd = results.getMetaData();

		   while (results.next()) {
		    Map<String, Object> map = new HashMap<>();
		    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
		     map.put(rsmd.getColumnName(i), results.getString(rsmd.getColumnName(i)));
		    }
		    list.add(map);
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  } 
		  //System.out.println("clikchouse查询结束时间:" + sdf.format(new Date()));

		  return list;

		 }
	
	
	public static int insert(String sql) {
		 Connection connection = null;
		  Statement statement = null;
		  int result = 0;
		try {
			Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
			connection = DriverManager.getConnection("jdbc:clickhouse://192.168.71.83:8123/ent_map", "default", "config");
			statement = connection.createStatement();
			result = statement.executeUpdate(sql);
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		//String sql = "SELECT id, firm_id, name, abbr, legal_representative, status, establish_time, registered_capital_str, reality_capital_str, registered_capital, reality_capital, approval_date, abstract, uniform_social_credit_code, organization_code, registration_number, taxpayer_identification_number, industry_involved, enterprise_type, business_term, register_organization, address, register_adress, business_scope, website, province, city, `type`, is_completed FROM ent_map.firm_data limit 100;";
		String sql="SELECT id, firm_id, name, abbr, legal_representative, status, establish_time, registered_capital_str, reality_capital_str, registered_capital, reality_capital, approval_date, abstract as companyInfo, uniform_social_credit_code, organization_code, registration_number, taxpayer_identification_number, industry_involved, enterprise_type, business_term, register_organization, address, register_adress, business_scope, website, province, city, `type`, is_completed FROM ent_map.firm_data;";

		List<Map<String, Object>> req = search(sql);
		
//		System.out.println(req);
		for (Map<String, Object> map : req) {
			System.out.println(map);
		}
		
//		String sql = "INSERT INTO `ent_map`.`platform`(id,platform_name, platform_site) VALUES (4, 'aaa', 'ssssssssss');";
//		insert(sql);
	}

}

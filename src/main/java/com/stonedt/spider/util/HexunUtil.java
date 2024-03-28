package com.stonedt.spider.util;

import java.io.IOException;

import org.apache.http.ParseException;

import com.alibaba.fastjson.JSONObject;

public class HexunUtil {
	
	public static void main(String[] args) {
		String jsons = getCurrentPrice("600155%7C600120");
		System.out.println(jsons);
	}

	/**
	 * 获取当前的股价
	 * @return
	 */
	public static String getCurrentPrice(String codes) {
		
		codes = "http://quote.stock.hexun.com/stockdata/stock_quote.aspx?stocklist="+codes;
		String html = null;
	   JSONObject json =  new JSONObject();
		try {
			html = HttpUtil.get(codes);
			//html = html.replaceAll(";indexdataArr = [];NewQuoteListPage.GetData(dataArr,indexdataArr);", "");
			//System.out.println(html);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
		
	}
}

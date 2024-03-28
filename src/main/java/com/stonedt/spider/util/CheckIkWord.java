
package com.stonedt.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
* <p></p>
* <p>Title: CheckIkWord</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date May 6, 2020  
*/

public class CheckIkWord {
	
	private static String es_ik_url = "http://192.168.71.63:6304/ik/participlelist";

	/**
	 * 验证ik分词器是否存在此词
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static boolean checkIkTokens(String content) throws IOException {
		String sendPost = sendPost(es_ik_url, "content=" + content);
		JSONObject parseObject = JSONObject.parseObject(sendPost);
		JSONArray tokensArray = parseObject.getJSONArray("tokens");
		for (int i = 0; i < tokensArray.size(); i++) {
			JSONObject tokenObject = tokensArray.getJSONObject(i);
			String token = tokenObject.getString("token");
			if(StringUtils.equals(content, token)) {
				return true;
			}
		}
		return false;
	}
	
	private static String sendPost(String url, String params) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new PrintWriter(conn.getOutputStream());
        out.print(params);
        out.flush();
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response.toString();
    }
}

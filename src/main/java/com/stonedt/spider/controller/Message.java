package com.stonedt.spider.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.MessageEntity;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.TimesUtil;
import com.stonedt.spider.util.UtilConfig;

@Controller
@RequestMapping("/message")
public class Message {

	@RequestMapping("run")
	public ModelAndView run(ModelAndView mav,
			@RequestParam(defaultValue = "1", required = true, value = "pageNo") Integer pageNo) {
		String sendPost = "";

		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		// 现在时间
		String times = sim.format(date);
		long a = date.getTime() - 86400000;
		Date b = new Date(a);
		// 前一天时间
		String tmiee = sim.format(b);

		String param = "?times=" + tmiee + "&tmiee=" + times;
		try {
//			sendPost = ElasticSearchUtil.sendPost("media/sourcewebsitesearch", param);
			sendPost = ElasticSearchUtil.sendPost(ElasticSearchUtil.sourcewebsitesearch, param);
			JSONObject json = new JSONObject();
			json = JSONObject.parseObject(sendPost);
			JSONArray array = json.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");

			List<MessageEntity> list = new ArrayList<MessageEntity>();
			for (int i = 0; i < array.size(); i++) {
				MessageEntity my = new MessageEntity();
				String name = array.getJSONObject(i).get("key").toString();
				my.setName(name);
				list.add(my);

			}
//			String content = "?times=" + tmiee + "&tmiee=" + times + "&page=" + pageNo + "&searchType=1&author";
			String content = "times=" + tmiee + "&tmiee=" + times + "&page=" + pageNo + "&searchType=1&author";
			String post = ElasticSearchUtil.sendPost("/media/qbsearchcontent", content);
			JSONObject conjson = new JSONObject();
			conjson = JSONObject.parseObject(post);
			JSONArray dataarray = conjson.getJSONArray("data");
			List<MessageEntity> arr = new ArrayList<MessageEntity>();
			String page_count = conjson.getString("page_count");
			String page = conjson.getString("page");
			String count = conjson.getString("count");
			for (int n = 0; n < dataarray.size(); n++) {
				String title = dataarray.getJSONObject(n).getJSONObject("_source").get("title").toString();
				String source_url = dataarray.getJSONObject(n).getJSONObject("_source").get("source_url").toString();
				String publish_time = dataarray.getJSONObject(n).getJSONObject("_source").get("publish_time")
						.toString();
				String sourcewebsitename = dataarray.getJSONObject(n).getJSONObject("_source").get("sourcewebsitename")
						.toString();
				String article_public_id = dataarray.getJSONObject(n).getJSONObject("_source").get("article_public_id")
						.toString();
				String author = dataarray.getJSONObject(n).getJSONObject("_source").get("author").toString();
				if (author.isEmpty()) {
					author = "空";
				}
				MessageEntity me = new MessageEntity();
				me.setArticle_public_id(article_public_id);
				me.setPublish_time(publish_time);
				me.setSource_url(source_url);
				me.setSourcewebsitename(sourcewebsitename);
				me.setTitle(title);
				me.setAuthor(author);
				arr.add(me);
			}

			mav.addObject("page_count", page_count);
			mav.addObject("page", page);
			mav.addObject("count", count);
			mav.addObject("arr", arr);
			mav.addObject("entiy", list);
			mav.setViewName("message");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/webplatform", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject webplatform(String name, String day, ModelAndView mav,
			@RequestParam(defaultValue = "1", required = true, value = "pageNo") Integer pageNo)
			throws UnsupportedEncodingException {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String sendPost = "";

		Date dates = new Date();
		// 现在时间
		String timess = sim.format(dates);
		long bString = dates.getTime() - 86400000;
		Date cString = new Date(bString);
		// 前一天时间
		String tmieeString = sim.format(cString);
		List<MessageEntity> list = new ArrayList<MessageEntity>();
		String param = "?times=" + tmieeString + "&tmiee=" + timess;
		try {
//			sendPost=ElasticSearchUtil.sendPost("/media/sourcewebsitesearch", param);
			sendPost = ElasticSearchUtil.sendPost(ElasticSearchUtil.sourcewebsitesearch, param);
			JSONObject json = new JSONObject();
			json = JSONObject.parseObject(sendPost);
			JSONArray array = json.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");

			for (int i = 0; i < array.size(); i++) {
				MessageEntity my = new MessageEntity();
				String names = array.getJSONObject(i).get("key").toString();
				my.setName(names);
				list.add(my);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray code = new JSONArray();
		Date date = new Date();
		// 现在时间
		String times = sim.format(date);

		JSONObject res = null;

		if (day.equals("1")) {
			long a = date.getTime() - 86400000;
			Date b = new Date(a);
			// 前一天时间
			String tmiee = sim.format(b);
//			code=TimesUtil.getCode(name, pageNo, tmiee, times);
//			res = TimesUtil.getCode(name, pageNo, tmiee, times);
			res = ElasticSearchUtil.getCode(name, pageNo, tmiee, times);
			System.out.println(res);
			code = res.getJSONArray("webnamelist");

		} else if (day.equals("2")) {
			long a = date.getTime() - 604800000;
			Date b = new Date(a);
			// 前一天时间
			String tmiee = sim.format(b);
//			code=TimesUtil.getCode(name, pageNo, tmiee, times);
//			res = TimesUtil.getCode(name, pageNo, tmiee, times);
			res = ElasticSearchUtil.getCode(name, pageNo, tmiee, times);
			System.out.println(res);
			code = res.getJSONArray("webnamelist");

		} else if (day.equals("3")) {

			long a = date.getTime() - 2592000000L;
			Date b = new Date(a);
			// 前一天时间
			String tmiee = sim.format(b);
//			code=TimesUtil.getCode(name, pageNo, tmiee, times);
//			res = TimesUtil.getCode(name, pageNo, tmiee, times);
			res = ElasticSearchUtil.getCode(name, pageNo, tmiee, times);
			System.out.println(res);
			code = res.getJSONArray("webnamelist");

		}

		String pageCount = res.getString("pageCount");
		String page = res.getString("page");
		String totalCount = res.getString("totalCount");
		JSONObject json = new JSONObject();
		json.put("arr", code);
		json.put("entiy", list);
		json.put("pageCount", pageCount);
		json.put("pageNum", page);
		json.put("totalCount", totalCount);
		mav.addObject("pageCount", pageCount);

//		mav.addObject("arr", code);
//		mav.addObject("entiy", list);
//		mav.setViewName("message");
		return json;

	}
}

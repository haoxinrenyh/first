package com.stonedt.spider.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.params.AllClientPNames;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {

	// 一级域名提取
	private static final String RE_TOP1 = "(\\w*\\.?){1}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";

	// 二级域名提取
	private static final String RE_TOP2 = "(\\w*\\.?){2}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";

	// 三级域名提取
	private static final String RE_TOP3 = "(\\w*\\.?){3}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";

	private final static Set<String> PublicSuffixSet = new HashSet<String>(Arrays.asList(new String(
			"com|org|net|gov|edu|co|tv|mobi|info|asia|xxx|onion|cn|com.cn|edu.cn|gov.cn|net.cn|org.cn|jp|kr|tw|com.hk|hk|com.hk|org.hk|se|com.se|org.se")
			.split("\\|")));

	private static Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}(\\d{1,3})");

	/**
	 * 获取url的顶级域名
	 *
	 * @param url
	 * @return
	 */
	public static String getDomainName(URL url) {
		String host = url.getHost();
		if (host.endsWith("."))
			host = host.substring(0, host.length() - 1);
		if (IP_PATTERN.matcher(host).matches())
			return host;

		int index = 0;
		String candidate = host;
		for (; index >= 0;) {
			index = candidate.indexOf('.');
			String subCandidate = candidate.substring(index + 1);
			if (PublicSuffixSet.contains(subCandidate)) {
				return candidate;
			}
			candidate = subCandidate;
		}
		return candidate;
	}

	/**
	 * 获取url的顶级域名
	 *
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getDomainName(String url) throws MalformedURLException {
		return getDomainName(new URL(url));
	}

	/**
	 * 判断两个url顶级域名是否相等
	 *
	 * @param url1
	 * @param url2
	 * @return
	 */
	public static boolean isSameDomainName(URL url1, URL url2) {
		return getDomainName(url1).equalsIgnoreCase(getDomainName(url2));
	}

	/**
	 * 判断两个url顶级域名是否相等
	 *
	 * @param url1
	 * @param url2
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean isSameDomainName(String url1, String url2) throws MalformedURLException {
		return isSameDomainName(new URL(url1), new URL(url2));
	}

	/**
	 * 获取真实url
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String getRealUrlFromBaiduUrl(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		// 设置代理IP
		// client.getHostConfiguration().setProxy("172.22.40.20", 8080);
		GetMethod getMethod = new GetMethod(url);
		// 获取状态码
		int stateCode = client.executeMethod(getMethod);
		String text = getMethod.getResponseBodyAsString();
		// 释放
		getMethod.releaseConnection();
		try {
			if (stateCode == HttpStatus.SC_OK) {
				text = text.split("URL='")[1].split("'")[0];
				// System.out.println("访问成功,网址:"+text);
				return text;
			}
		} catch (Exception e) {

		}

		return "";
	}

	public static String test(String url) {
		Connection.Response res = null;
		int itimeout = 60000;
		try {
			res = Jsoup.connect(url).timeout(itimeout).method(Connection.Method.GET).followRedirects(false).execute();
			return res.header("Location");
		} catch (Exception e1) {
			// e1.printStackTrace();
		}
		return "";
	}

	public static String http302(String url) throws Exception {
		// 创建一个http请求
		HttpClient client = new HttpClient();
		// 用header的请求方式，减少返回值和一些非必要获取的信息
		HttpMethod method = new HeadMethod(url);
		HttpParams params = client.getParams();
		params.setParameter(AllClientPNames.HANDLE_REDIRECTS, false);
		client.executeMethod(method);
		// 获取链接
		// System.out.println("aaaaaaaaaaaaaaaaaa"+method.getURI());
		url = method.getURI().getURI();
		// 释放链接（重要）
		method.releaseConnection();
		return url;
	}

	private static final String RE_TOP_DOMAIN = "(com\\.cn|net\\.cn|gov\\.cn|org\\.nz|org\\.cn|com|net|org|gov|cc|biz|info|cn|co|me)";
	// 一级域名提取
	private static final String RE_TOP_1 = "(\\w*\\.?){1}\\." + RE_TOP_DOMAIN;

	// 二级域名提取
	private static final String RE_TOP_2 = "(\\w*\\.?){2}\\." + RE_TOP_DOMAIN;

	// 三级域名提取
	private static final String RE_TOP_3 = "(\\w*\\.?){3}\\." + RE_TOP_DOMAIN;

	private static final Pattern PATTEN_IP = Pattern.compile("((http://)|(https://))?((\\d+\\.){3}(\\d+))");
	private static final Pattern PATTEN_TOP1 = Pattern.compile(RE_TOP_1);
	private static final Pattern PATTEN_TOP2 = Pattern.compile(RE_TOP_2);
	private static final Pattern PATTEN_TOP3 = Pattern.compile(RE_TOP_3);



	public static String getDomain(String url, int level) {
		Matcher matcher = PATTEN_IP.matcher(url);
		if (matcher.find()) {
			return matcher.group(4);
		}

		switch (level) {
			case 1:
				matcher = PATTEN_TOP1.matcher(url);
				break;
			case 2:
				matcher = PATTEN_TOP2.matcher(url);
				break;
			case 3:
				matcher = PATTEN_TOP3.matcher(url);
				break;
			default:
				return "";
		}
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	public static int getStringNumber(String fileName, String str) throws Exception {
		int num = 0;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String readLine = "";
		StringBuffer stringBuffer = new StringBuffer();
		while ((readLine = bufferedReader.readLine()) != null) {
			stringBuffer.append(readLine);
		}
		for (int i = 0; i < stringBuffer.length(); i++) {
			if (stringBuffer.indexOf(str, i) != -1) {
				i = stringBuffer.indexOf(str, i);
				num++;
			}
		}
		return num;
	}

	public static String getIP(String url) {
		// 使用正则表达式过滤，
		String re = "((http|ftp|https)://)(([a-zA-Z0-9._-]+)|([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(([a-zA-Z]{2,6})|(:[0-9]{1,4})?)";
		String str = "";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(re);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);
		// 若url==http://127.0.0.1:9040或www.baidu.com的，正则表达式表示匹配
		if (matcher.matches()) {
			str = url;
		} else {
			String[] split2 = url.split(re);
			if (split2.length > 1) {
				String substring = url.substring(0, url.length() - split2[1].length());
				str = substring;
			} else {
				str = split2[0];
			}
		}
		return str;
	}

	private static void test() {
		String url1 = "http://so.news.cn/getNews?keyword=%E4%BA%AC%E4%B8%9C%E7%89%A9%E6%B5%81&curPage={pageNo*1-0}&sortField=0&searchFields=1&lang=cn";
		Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
		Matcher matcher = pattern.matcher(url1);
		while (matcher.find()) {
			String group = matcher.group();
			if (group.indexOf("pageNo") != -1) {
				// group = group.replace("pageNo", "");
				String param = group.split("\\*")[1];
				String[] split = param.split("-");
				int ride = Integer.parseInt(split[0]);
				int subtract = Integer.parseInt(split[1]);
				for (int i = 1; i < 4; i++) {
					String url = url1.replace("{" + group + "}", (i * ride - subtract) + "").trim();
					System.out.println(url);
				}
			}
		}
	}

	public static String analysisUrl(String url, String keyword) {
		try {
			Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");

			Matcher matcher = pattern.matcher(url);
			long currentTimeMillis = System.currentTimeMillis();
			while (matcher.find()) {
				String group = matcher.group();
				String replasestr = "";
				if (group.indexOf("pageNo") != -1) {
					replasestr = "{" + group + "}";
				} else if (group.indexOf("keyword") != -1) {
					// group = group.replace("keyword", "");
					if (group.indexOf("@") != -1) {
						String encode = group.split(",")[1];
						replasestr = URLEncoder.encode(keyword, encode);
					} else {
						replasestr = keyword;
					}
				} else if (group.indexOf("timestemp") != -1) {
					String replace = group.replace("timestemp", "");
					if (replace.trim().length() == 0) {
						replasestr = currentTimeMillis + "";
					} else {
						String string = replace.replaceAll("/", "");
						String[] split = string.split("-");
						int divisor = Integer.parseInt(split[0]);
						int subtract = Integer.parseInt(split[1]);
						// split[1];
						replasestr = (currentTimeMillis / divisor - subtract) + "";
					}
				} else if (group.indexOf("random") != -1) {
					int size = Integer.parseInt(group.split("\\*")[1]);
					for (int i = 0; i < size; i++) {
						int random = (int) (Math.random() * 10);
						replasestr += "" + random;
					}
				}

				url = url.replace("{" + group + "}", replasestr);

			}
			return url;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * url转换工具
	 *
	 * @param imgurl
	 * @param url
	 * @return
	 */
	public static String getabsurl(String imgurl, String url) {
		// TODO Auto-generated method stub
		String returnUrl = "";
		if (null != imgurl && !"".equals(imgurl.trim())) {
			imgurl = imgurl.trim();
			while (imgurl.startsWith(" ")) {
				imgurl = imgurl.replaceFirst(" ", "");
			}
			if (imgurl.startsWith("//")) {
				try {
					URL absoluteUrl = new URL(url);
					URL parseUrl = new URL(absoluteUrl, imgurl);
					imgurl = parseUrl.toString();
					returnUrl = imgurl.replaceAll("\\.\\./", "").replaceAll("\\./", "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (imgurl.startsWith("http")) {
				imgurl.replaceAll("\\.\\./", "").replaceAll("\\./", "");
				return imgurl;
			} else if (imgurl.startsWith("/")) {
				try {
					String domainName = getDomainName(url);
					returnUrl = url.substring(0, url.indexOf(domainName)) + domainName + imgurl;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (imgurl.startsWith("./") || imgurl.startsWith("../")) {
				while (imgurl.startsWith("./")) {
					url = beforchangeStandardizedUrl(url);
					imgurl = imgurl.substring(2, imgurl.length());
					int n = url.length() - url.replaceAll("/", "").length();
					if (n > 2) {
						url = url.substring(0, url.lastIndexOf("/") + 1);
					} else {
						url = url + "/";
					}
				}
				while (imgurl.startsWith("../")) {
					url = beforchangeStandardizedUrl(url);
					imgurl = imgurl.substring(3, imgurl.length());
					int n = url.length() - url.replaceAll("/", "").length();
					if (n > 3) {
						url = url.substring(0, url.lastIndexOf("/"));
					}
					if (n > 2) {
						url = url.substring(0, url.lastIndexOf("/") + 1);
					} else {
						url = url + "/";
					}
				}
				returnUrl = url + imgurl;
			} else {
				returnUrl = beforchangeStandardizedUrl(url) + "/" + imgurl;
			}
			returnUrl = returnUrl.replaceAll("\\.\\./", "").replaceAll("\\./", "");

		}
		returnUrl = StandardizedUrl(returnUrl);
		return returnUrl;
	}

	/**
	 * 转换前确认当前页面是否为静态页面
	 *
	 * @param url
	 * @return
	 */
	public static String beforchangeStandardizedUrl(String url) {
		if (null != url && !"".equals(url.trim())) {
			String[] split = url.split("/");
			String endStr = split[split.length - 1];
			if (StringUtils.isNotEmpty(endStr) && (endStr.contains(".htm") || endStr.contains(".sht"))) {
				url = url.replace(endStr, "");
				url.substring(0, url.length() - 2);
			}
		} else {
			url = null;
		}
		return url;
	}

	/**
	 * 标准化url 去除首尾空格 去除锚链接
	 *
	 * @param url
	 * @return
	 */
	public static String StandardizedUrl(String url) {
		if (null != url && !"".equals(url.trim())) {
			if (url.indexOf("javascript:") != -1) {
				return null;
			}
			url = url.trim();
			while (url.endsWith(" ")) {
				url = url.substring(0, url.length() - 1);
			}
			String[] paths = url.split("/");
			if (paths.length > 2) {
				String endWithPath = paths[paths.length - 1];
				if (endWithPath.startsWith("#")) {
					url = url.replace(endWithPath, "");
				} else if (endWithPath.contains("#")) {
					endWithPath = endWithPath.substring(endWithPath.indexOf("#"));
					url = url.replace(endWithPath, "");
				}
			}
			// if(url.endsWith("/")){
			// url = url.substring(0,url.length()-1);
			// }
		} else {
			url = null;
		}
		return url;
	}

	public static void main(String[] args) {
		try {

			boolean flag = isSameDomainName("http://jsjrb.jiangsu.gov.cn/art/2018/11/6/art_79386_9359955.html","http://jsjrb.jiangsu.gov.cn/col/col79386/index.html");
			System.out.println(flag);
			// String url = " https://blog.csdn.net/qq_30908729/article/details/
			// ";
			// System.out.println(url);
			// System.out.println(StandardizedUrl(url));
			// System.out.println(getabsurl("../zhxw/202009/t20200908_5687977.html",
			// "http://www.ynao.cas.cn/xwzx/kydt"));
			// System.out.println(StandardizedUrl("http://quote.eastmoney.com/center/boardlist.html#boards2-90.BK0978"));
			// System.out.println(StandardizedUrl("http://quote.eastmoney.com/center/list.html#absz_0_4"));
			// System.out.println(StandardizedUrl("http://quote.eastmoney.com/center/gridlist.html#options_sh50etf_all"));
//			System.out.println(equals("http://quote.eastmoney.com/center/gridlist.html#table2",
//					"http://quote.eastmoney.com/center/gridlist.html"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isThisWebSite(String urlOne, String urlTwo) {
		try {
			String urlOnerule= getThisWebSiteRule(urlOne);
			String urlTworule= getThisWebSiteRule(urlTwo);
			if(urlTworule.equals(urlOnerule)){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String getThisWebSiteRule(String url) {
		String rule = null;
		try {
			url = StandardizedUrl(url);
			String domainName = getDomainName(url);
			rule = url.split(domainName)[0]+domainName;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;
	}



}

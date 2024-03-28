package com.stonedt.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.EsKeyword;
import com.stonedt.spider.param.ParamES;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.MessageEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ElasticSearchUtil {
    public static final String jobslistApi = "jobs/jobslist";// 列表数据
    public static final String provincelist = "jobs/provincelist";// 招聘省份信息
    public static final String citylist = "jobs/citylist";// 招聘城市
    public static final String jobssourcesApi = "jobs/jobssources";// 渠道占比
    public static final String citylistApi = "jobs/citylist";// 城市占比
    public static final String getjob = "jobs/jobsnumlist";// 岗位以及人数
    public static final String getjobsdetail = "jobs/getjobsdetail";  // 招聘详情信息
    public static final String qbsearchcontent = "media/qbsearchcontent";
    public static final String invitatelist = "invitate/invitatelist"; // 招标列表数据
    public static final String invitatedetail = "invitate/getinvitatedetail"; // 招标详情数据
    public static final String sourcewebsitesearch = "media/sourcewebsitesearch"; // 信息来源
    public static final String patentinformationtype = "patent/patentinformationtype"; // 专利类型接口
    public static final String patentlist = "patent/patentlist"; // 专利列表接口
    public static final String getpatentdetail = "patent/getpatentdetail"; // 专利详情接口
    public static final String yysQbsearchcontent = "yys/qbsearchcontent"; // 资讯文章列表接口
    public static final String getknowabout = "knowabout/knowaboutlist"; // 知乎列表接口
    //	public static final String qbsearchcontent = "media/qbsearchcontent";
    public static final String invitateprovince = "invitate/invitateprovince";  // 查省份信息
    public static final String invitatecity = "invitate/invitatecity"; // 招标查城市信息
    public static final String invitatebusinessType = "invitate/invitatebusinessType"; // 查业务类型
    public static final String invitateinformationtype = "invitate/invitateinformationtype"; // 招标信息类型
    public static final String invitateindustry = "invitate/invitateindustry"; // 招标行业类型
    public static final String twitterlist = "twitter/twitterlist"; // 推特列表
    public static final String foreignmedialist = "foreignmedia/foreignmedialist"; // 外媒列表
    public static final String superdatalist = "/commonsearch/superdatalist"; // 通用街口
    //宏观数据分类接口
    public static final String macrodatacategory = "macrodata/macrodatacategory";
    public static final String wemedialist = "media/wemedialist";
    public static final String yqtQbsearchcontent = "/yqt/qbsearchcontent";  // 请求详情
    public static final String updaterealisticnum = "media/updaterealisticnum";  // 修改数据量

    public static final String getcommondatadetail = "/commonsearch/getcommondatadetail";//通用详情接口

    /**
     * 向指定http://192.168.71.61:9200/stonedt/news/_search发送POST方法的请求
     *
     * @param param 请求参数，请求参数应该是es可读Json的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String method, String param) throws Exception {
        String url = UtilConfig.getURL() + method;
//        System.out.println(url + param);
        PrintWriter out = null;
        BufferedReader in = null;
//        String result = "";
//        System.out.println(url + param);
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        // 打开和URL之间的连接
        //URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
//			conn.setRequestProperty("content-type","application/json; charset=UTF-8");

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//转成utf-8格式
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        // 使用finally块来关闭输出流、输入流
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
    
    public static String doPostJson(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //  httpPost.setHeader("User-Agent", getRandomAgent());
        httpPost.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(jsonStr.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");  //设置编码格式
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse httpResponse = null;
        InputStream inputStream = null;
        StringBuilder response = new StringBuilder();
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent(); //获取content实体内容
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf(response);
    }

    public static String sendPost1(String url, String params) throws IOException {
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


   

    // 請求es
    public static JSONObject getCode(String name, Integer pageNo, String tmiee, String times) {

        JSONObject returnObj = new JSONObject();
        List<MessageEntity> webnamelist = new ArrayList<MessageEntity>();
        JSONObject conjson = new JSONObject();
//		String url =  "/media/qbsearchcontent";
        String url = ElasticSearchUtil.qbsearchcontent;

        String content;
        try {
            content = "times=" + tmiee + "&tmiee=" + times + "&page=" + pageNo + "&searchType=1&author=&source_name=" + URLEncoder.encode(name, "utf-8");
            String post;
            try {
                post = ElasticSearchUtil.sendPost(url, content);
                conjson = JSONObject.parseObject(post);
                JSONArray dataarray = conjson.getJSONArray("data");
                String page_count = conjson.getString("page_count");
                String page = conjson.getString("page");
                String count = conjson.getString("count");
                returnObj.put("pageCount", page_count);
                returnObj.put("page", page);
                returnObj.put("totalCount", count);
                for (int n = 0; n < dataarray.size(); n++) {
                    String title = dataarray.getJSONObject(n).getJSONObject("_source").get("title").toString();
                    String source_url = dataarray.getJSONObject(n).getJSONObject("_source").get("source_url").toString();
                    String publish_time = dataarray.getJSONObject(n).getJSONObject("_source").get("publish_time").toString();
                    String sourcewebsitename = dataarray.getJSONObject(n).getJSONObject("_source").get("sourcewebsitename").toString();
                    String article_public_id = dataarray.getJSONObject(n).getJSONObject("_source").get("article_public_id").toString();
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
                    webnamelist.add(me);
//					returnObj.put("webnamelist", webnamelist);
                }
                returnObj.put("webnamelist", webnamelist);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        return returnObj;

    }

    /**
     * post 以raw的方式发送请求
     */
    public static String sendPostRaw(String params, String url) {
        String body = "";
//        url = es_url + url;
        System.out.println(url);
        try {
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //装填参数
            StringEntity s = new StringEntity(params, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
            System.out.println("请求地址：" + url);
//          System.out.println("请求参数："+nvps.toString());

            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
//           httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }


    public static String sendEs(String param,String esUrl) throws IOException {
        String result = "";
        try {
            String url = esUrl;
            //System.out.println(param);
            System.out.println(url);
            PrintWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setReadTimeout(50 * 1000);
            conn.setConnectTimeout(50 * 1000);
            // 打开和URL之间的连接
            // URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("content-type", "application/json; charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 使用finally块来关闭输出流、输入流

            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String  paramArr_map(int from, int size,List<List<ParamES>> paramList,List<EsKeyword> keywordList){
        JSONObject result_json = new JSONObject();
        if(from!=-1){
            result_json.put("from",from);
            result_json.put("size",size);
        }

        if(  paramList.size()>0 || (keywordList!=null && keywordList.size()>0) )   {
            //参数
            JSONObject query_json = new JSONObject();
            JSONObject bool_json = new JSONObject();
            JSONArray must_jsonArr = new JSONArray();

            //条件参数组装
            if(paramList.size()>0){
                for (List<ParamES> params : paramList){
                    if(params==null || params.size()<=0){
                        continue;
                    }
                    must_jsonArr.add(to_must(params));
                }
            }

            //搜索参数组装
            if( keywordList!=null && keywordList.size()>0 ){
                JSONArray should_jsonArr = new JSONArray();

                for (EsKeyword esKeyword : keywordList) {
                    try {
                        String keyword = esKeyword.getKeyword();

                        List<String> keys = esKeyword.getKeys();
                        for (String key : keys) {
                            if(keyword!=null && !keyword.equals("") && key!=null  && !key.equals("") ){
                                JSONObject match_phrase_json = new JSONObject();
                                match_phrase_json.put(key, keyword);

                                JSONObject should_json = new JSONObject();
                                should_json.put("match_phrase", match_phrase_json );
                                should_jsonArr.add(should_json);
                            }
                        }
                    }catch (Exception e_keyword){
                        e_keyword.printStackTrace();
                    }
                }
                if(should_jsonArr.size()>0){
                    JSONObject must_bool_json = new JSONObject();
                    must_bool_json.put("should",should_jsonArr);
                    JSONObject must_json = new JSONObject();
                    must_json.put("bool",must_bool_json);
                    must_jsonArr.add(must_json);
                }
            }

            //组装所有的单查询
            bool_json.put("must",must_jsonArr);
            query_json.put("bool",bool_json);
            result_json.put("query",query_json);
        }

        return result_json.toJSONString();
    }

    public static String  paramArr_map(int from, int size,List<List<ParamES>> paramList){
        JSONObject result_json = new JSONObject();
        result_json.put("from",from);
        result_json.put("size",size);

        if(  paramList.size()>0 )   {
            //参数
            JSONObject query_json = new JSONObject();
            JSONObject bool_json = new JSONObject();
            JSONArray must_jsonArr = new JSONArray();

            //条件参数组装
            if(paramList.size()>0){
                for (List<ParamES> params : paramList){
                    if(params==null || params.size()<=0){
                        continue;
                    }
                    must_jsonArr.add(to_must(params));
                }
            }

            //组装所有的单查询
            bool_json.put("must",must_jsonArr);
            query_json.put("bool",bool_json);
            result_json.put("query",query_json);
        }

        return result_json.toJSONString();
    }

    public static String paramArr_map(String beingDate, String endDate ,List<List<ParamES>> paramList){
        JSONObject result_json = new JSONObject();

        if( (beingDate!=null && !beingDate.equals("")) || (endDate!=null && !endDate.equals("")) || paramList.size()>0)  {
            //参数
            JSONObject query_json = new JSONObject();
            JSONObject bool_json = new JSONObject();
            JSONArray must_jsonArr = new JSONArray();

            //时间组装
            if ((beingDate != null && !beingDate.equals("")) || (endDate != null && !endDate.equals(""))) {
                JSONObject range_json = new JSONObject();
                JSONObject pTime_json = new JSONObject();
                if (beingDate != null && !beingDate.equals("")) {
                    pTime_json.put("gte", beingDate);
                }
                if (endDate != null && !endDate.equals("")) {
                    pTime_json.put("lte", endDate);
                }
                range_json.put("publish_time", pTime_json);

                JSONObject must_json = new JSONObject();
                must_json.put("range", range_json);
                must_jsonArr.add(must_json);
            }

            //精确查询参数
            if(paramList.size()>0){
                for (List<ParamES> params : paramList){
                    if(params==null || params.size()<=0){
                        continue;
                    }
                    must_jsonArr.add(to_must(params));
                }
            }

            //组装所有的单查询
            bool_json.put("must",must_jsonArr);
            query_json.put("bool",bool_json);
            result_json.put("query",query_json);
        }

        return result_json.toJSONString();
    }


    public static String esCountParamArr_map(String beingDate, String endDate ,List<List<ParamES>> paramList){
        JSONObject result_json = new JSONObject();

        if( (beingDate!=null && !beingDate.equals("")) || (endDate!=null && !endDate.equals("")) || paramList.size()>0)  {
            //参数
            JSONObject query_json = new JSONObject();
            JSONObject bool_json = new JSONObject();
            JSONArray must_jsonArr = new JSONArray();

            //时间组装
            if ((beingDate != null && !beingDate.equals("")) || (endDate != null && !endDate.equals(""))) {
                JSONObject range_json = new JSONObject();
                JSONObject pTime_json = new JSONObject();
                if (beingDate != null && !beingDate.equals("")) {
                    pTime_json.put("gte", beingDate);
                }
                if (endDate != null && !endDate.equals("")) {
                    pTime_json.put("lte", endDate);
                }
                range_json.put("spider_time", pTime_json);

                JSONObject must_json = new JSONObject();
                must_json.put("range", range_json);
                must_jsonArr.add(must_json);
            }

            //精确查询参数
            if(paramList.size()>0){
                for (List<ParamES> params : paramList){
                    if(params==null || params.size()<=0){
                        continue;
                    }
                    must_jsonArr.add(to_must(params));
                }
            }

            //组装所有的单查询
            bool_json.put("must",must_jsonArr);
            query_json.put("bool",bool_json);
            result_json.put("query",query_json);
        }

        return result_json.toJSONString();
    }

    public static JSONObject to_must(List<ParamES> params){
        JSONObject must_json = new JSONObject();
        JSONObject must_bool_json = new JSONObject();
        JSONArray should_jsonArr = new JSONArray();
        for (ParamES param : params) {
            if(param.isEmpty()){
                JSONObject should_json = new JSONObject();
                JSONObject should_query = new JSONObject();
                should_query.put(param.getName(),param.getValue());
                should_json.put(param.getType(),should_query);

                should_jsonArr.add(should_json);

            }
        }
        must_bool_json.put("should",should_jsonArr);
        must_json.put("bool",must_bool_json);
        return must_json;
    }

    public static JSONObject body_toArray(String body){
        JSONObject body_json = JSONObject.parseObject(body);
        JSONObject hits_json = body_json.getJSONObject("hits");

        JSONObject Array = new JSONObject();
        Array.put("data",hits_json.get("hits"));
        Array.put("total",hits_json.get("total"));

        return Array;
    }

    public static String paramDetail(String soleId){
        String param = "{\"query\": {\"match_phrase\": {\"article_public_id\": \""+soleId+"\"}}}";
        return param;
    }

    public static JSONObject body_toMap(String body){
        JSONObject body_json = JSONObject.parseObject(body);
        JSONObject hits_json = body_json.getJSONObject("hits");

        int total = 0;
        if( hits_json.getInteger("total")!=null ){
            total = hits_json.getInteger("total");
        }

        JSONObject map = new JSONObject();
        if( total>0 && hits_json.getJSONArray("hits")!=null && hits_json.getJSONArray("hits").size()>0 ){
            map = hits_json.getJSONArray("hits").getJSONObject(0);
        }

        return map;
    }

    public static ParamES toParamES(String type, String name, Object value){
        if( type==null || type.equals("") || name==null || name.equals("") || value==null || value.equals("") ){
            return new ParamES();
        }
        ParamES newParamES = new ParamES();
        newParamES.setType(type);
        newParamES.setName(name);
        newParamES.setValue(value);
        return newParamES;
    }

    public static String  paramArr_map(int from, int size, String sortTime, String beingDate, String endDate , List<List<ParamES>> paramList){
        JSONObject result_json = new JSONObject();
        result_json.put("from",from);
        result_json.put("size",size);

        //排序
        if(sortTime==null || sortTime.equals("")){
            sortTime = "desc";
        }
        JSONObject sort_json = new JSONObject();
        sort_json.put("create_time",sortTime);
        result_json.put("sort",sort_json);

        //参数组装
        if( (beingDate!=null && !beingDate.equals("")) || (endDate!=null && !endDate.equals(""))  || paramList.size()>0 ) {
            JSONObject query_json = new JSONObject();
            JSONObject bool_json = new JSONObject();
            JSONArray must_jsonArr = new JSONArray();

            //配置时间
            if ((beingDate != null && !beingDate.equals("")) || (endDate != null && !endDate.equals(""))) {
                JSONObject range_json = new JSONObject();
                JSONObject pTime_json = new JSONObject();
                if (beingDate != null && !beingDate.equals("")) {
                    pTime_json.put("gte", beingDate);
                }
                if (endDate != null && !endDate.equals("")) {
                    pTime_json.put("lte", endDate);
                }
                range_json.put("create_time", pTime_json);

                JSONObject must_json = new JSONObject();
                must_json.put("range", range_json);
                must_jsonArr.add(must_json);
            }

            //参数集合
            if(paramList.size()>0){
                for (List<ParamES> params : paramList){
                    if(params==null || params.size()<=0){
                        continue;
                    }
                    must_jsonArr.add(to_must(params));
                }
            }

            //组装所有的单查询
            bool_json.put("must",must_jsonArr);
            query_json.put("bool",bool_json);
            result_json.put("query",query_json);
        }

        return result_json.toJSONString();
    }

}

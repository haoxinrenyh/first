package com.stonedt.spider.util;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class HttpApiUtil {

    /**
     * get请求 ，地址+参数、验证
     */
    public static String getApi(String url, List<Map<String,Object>> headers ) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Accept", "application/json; charset=utf-8");
        for (Map<String,Object> header : headers) {
            httpHeaders.set( String.valueOf(header.get("key")), String.valueOf(header.get("value")) );
        }
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, httpHeaders);

        String result = "";
        try {
            result = restTemplate.getForObject(url, String.class, requestEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * get请求
     */
    public static String get(String url,  Map<String, Object> params, Map<String, String> headersMap) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        try {
            String apiUrl = url;
            if (null != params && params.size() > 0) {
                StringBuffer param = new StringBuffer();
                int i = 0;
                for (String key : params.keySet()) {
                    if (i == 0)
                        param.append("?");
                    else
                        param.append("&");
                    param.append(key).append("=").append(params.get(key));
                    i++;
                }
                apiUrl += param;
            }

            HttpGet httpGet = new HttpGet(apiUrl);
            if (null != headersMap && headersMap.size() > 0) {
                for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.addHeader(new BasicHeader(key, value));
                }
            }
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                org.apache.http.HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, "utf-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 创建httpclient
     */
    public static CloseableHttpClient buildHttpClient() {
        try {
            RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder
                    .create();
            ConnectionSocketFactory factory = new PlainConnectionSocketFactory();
            builder.register("http", factory);
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            SSLContext context = SSLContexts.custom().useTLS()
                    .loadTrustMaterial(trustStore, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            LayeredConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(
                    context,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            builder.register("https", sslFactory);
            Registry<ConnectionSocketFactory> registry = builder.build();
            PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(
                    registry);
            ConnectionConfig connConfig = ConnectionConfig.custom()
                    .setCharset(Charset.forName("utf-8")).build();
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(100000).build();
            manager.setDefaultConnectionConfig(connConfig);
            manager.setDefaultSocketConfig(socketConfig);
            return HttpClientBuilder.create().setConnectionManager(manager)
                    .build();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * post请求 ，地址、参数、验证
     */
    public static String postApi(String url, Map<String, Object> params, List<Map<String,Object>> headers ) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json; charset=utf-8");
        for (Map<String,Object> header : headers) {
            httpHeaders.add( String.valueOf(header.get("key")), String.valueOf(header.get("value")) );
        }

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(params, httpHeaders);

        String result = "";
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
            result = responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

package dong.httpclient;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int SO_TIME_OUT = 10000;
    private static final int CON_REQ_TIME_OUT = 10000;
    private static final int CON_TIME_OUT = 10000;
    private static final int SOCKET_TIME_OUT = 10000;
    private static final int MAX_TOTAL = 200;
    private static final int MAX_ROUTE = 20;
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final HttpClientUtil clientUtil;
    private static PoolingHttpClientConnectionManager cm;
    private static RequestConfig config;

    static {
        init();
        clientUtil = new HttpClientUtil();
    }

    public final int STS_TIMEOUT_CONN = -1;
    public final int STS_TIMEOUT_SOCK = -2;
    public final int STS_OTHER = -9;
    private final HttpRequestRetryHandler retryHander = (exception, executionCount, context) -> {
        if (executionCount >= 2) {
            return false;
        } else if (exception instanceof SocketException) {
            if (!exception.getMessage().startsWith("Connection reset")) {
                logger.error("connection retryRequest :", exception);
                logger.info("connection retryRequest :" + executionCount);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    };

    private HttpClientUtil() {
    }

    private static void init() {
        config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(300);
        cm.setDefaultMaxPerRoute(50);
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).build();
        cm.setDefaultSocketConfig(socketConfig);
    }

    /**
     * 自定义KeepAlive策略
     */
    private ConnectionKeepAliveStrategy myStrategy = (response, context) -> {
        // Honor 'keep-alive' header
        HeaderElementIterator it = new BasicHeaderElementIterator(
                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
        while (it.hasNext()) {
            HeaderElement he = it.nextElement();
            String param = he.getName();
            String value = he.getValue();
            if (value != null && param.equalsIgnoreCase("timeout")) {
                try {
                    return Long.parseLong(value) * 1000;
                } catch(NumberFormatException ignore) {
                }
            }
        }
        HttpHost target = (HttpHost) context.getAttribute(
                HttpClientContext.HTTP_TARGET_HOST);
        if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
            // Keep alive for 5 seconds only
            return 5 * 1000;
        } else {
            // otherwise keep alive for 30 seconds
            return 30 * 1000;
        }
    };

    public static HttpClientUtil getInstance() {
        return clientUtil;
    }

    private CloseableHttpClient getConnection(boolean isRetry) {
        HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config);
        if (isRetry) {
            clientBuilder = clientBuilder.setRetryHandler(this.retryHander);
        }
        clientBuilder.setKeepAliveStrategy(myStrategy);
        return clientBuilder.build();
    }

    private UrlEncodedFormEntity mapToEntity(Map<Object, Object> paraMap, Charset charSet) throws Exception {
        List<NameValuePair> params = new ArrayList();
        Iterator var4 = paraMap.keySet().iterator();

        while (var4.hasNext()) {
            Object key = var4.next();
            params.add(new BasicNameValuePair(key.toString(), paraMap.get(key).toString()));
        }

        return new UrlEncodedFormEntity(params, charSet);
    }

    private String urlAppend(String strUrl, String paras) {
        Args.notNull(strUrl, "url request");
        if (strUrl.indexOf("?") > 0) {
            strUrl = strUrl + "&" + paras;
        } else {
            strUrl = strUrl + "?" + paras;
        }

        return strUrl;
    }

    private HttpResRtn postJson(String strUrl, String strJson, RequestConfig reqConfig, boolean isRetry, Map<String, String> headerMap) {
        Args.notNull(strUrl, "url request");
        Args.notNull(strJson, "json string request");
        HttpResRtn resRtn = new HttpResRtn();
        HttpPost postMethod = null;
        CloseableHttpResponse response = null;

        try {
            StringEntity entity = new StringEntity(strJson, "utf-8");
            entity.setContentEncoding("UTF-8");
            postMethod = new HttpPost(strUrl);
            postMethod.setEntity(entity);
            if (headerMap != null && !headerMap.isEmpty()) {
                Iterator var10 = headerMap.keySet().iterator();

                while (var10.hasNext()) {
                    String key = (String) var10.next();
                    String obj = (String) headerMap.get(key);
                    if (obj != null) {
                        postMethod.addHeader(key, obj);
                    }
                }
            }

            if (null == headerMap || null == headerMap.get("Content-Type")) {
                postMethod.addHeader("Content-Type", "application/json;charset=utf-8");
            }

            if (reqConfig != null) {
                postMethod.setConfig(reqConfig);
            }

            long startTime = System.currentTimeMillis();
            response = this.getConnection(isRetry).execute(postMethod);
            long endTime = System.currentTimeMillis();
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            StatusLine stsLine = response.getStatusLine();
            int stsCode = stsLine.getStatusCode();
            resRtn.setStsCode(stsCode);
            resRtn.setRsnPhrase(stsLine.getReasonPhrase());
            if (stsCode == 200) {
                String strValue = EntityUtils.toString(response.getEntity(), "utf-8");
                resRtn.setValue(strValue);
                logger.info("http response body is:" + strValue);
            }

            response.getEntity().getContent().close();
        } catch (Exception var25) {
            if (!(var25 instanceof ConnectTimeoutException) && !(var25 instanceof HttpHostConnectException)) {
                if (var25 instanceof SocketTimeoutException) {
                    resRtn.setStsCode(-2);
                } else {
                    resRtn.setStsCode(-9);
                }
            } else {
                resRtn.setStsCode(-1);
            }

            resRtn.setRsnPhrase(var25.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.getEntity().getContent().close();
                } catch (Exception var24) {
                    logger.error("response.getEntity().getContent() close is error ", var24);
                }
            }

            if (postMethod != null) {
                postMethod.abort();
            }

        }

        return resRtn;
    }

    public HttpResRtn postJson(String strUrl, String strJson) {
        return this.postJson(strUrl, strJson, (RequestConfig) null, false, (Map) null);
    }

    public HttpResRtn postJson(String strUrl, String strJson, boolean isRetry) {
        return this.postJson(strUrl, strJson, (RequestConfig) null, isRetry, (Map) null);
    }

    public HttpResRtn postJson(String strUrl, String strJson, int socketTimeOut, int conTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(conTimeOut).build();
        return this.postJson(strUrl, strJson, reqConfig, isRetry, (Map) null);
    }

    public HttpResRtn postJson(String strUrl, String strJson, int socketTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(10000).build();
        return this.postJson(strUrl, strJson, reqConfig, isRetry, (Map) null);
    }

    public HttpResRtn postJson(String strUrl, String strJson, int timtOut, Map<String, String> headerMap) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(timtOut).setConnectTimeout(timtOut).setConnectionRequestTimeout(timtOut).build();
        return this.postJson(strUrl, strJson, reqConfig, false, headerMap);
    }

    public HttpResRtn postJson(String strUrl, String strJson, int timtOut, int sockTimeOut, Map<String, String> headerMap) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(sockTimeOut).setConnectTimeout(timtOut).setConnectionRequestTimeout(timtOut).build();
        return this.postJson(strUrl, strJson, reqConfig, false, headerMap);
    }

    public HttpResRtn postJson(String strUrl, String strJson, int timtOut, int sockTimeOut) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(sockTimeOut).setConnectTimeout(timtOut).setConnectionRequestTimeout(timtOut).build();
        return this.postJson(strUrl, strJson, reqConfig, false, null);
    }

    private HttpResRtn postMap(String strUrl, Map<Object, Object> paraMap, RequestConfig reqConfig, boolean isRetry) {
        Args.notNull(strUrl, "url request");
        Args.notNull(paraMap, "json string request");
        HttpResRtn resRtn = new HttpResRtn();
        HttpPost postMethod = null;
        CloseableHttpResponse response = null;

        try {
            UrlEncodedFormEntity urlEntity = this.mapToEntity(paraMap, Consts.UTF_8);
            postMethod = new HttpPost(strUrl);
            postMethod.setEntity(urlEntity);
            if (reqConfig != null) {
                postMethod.setConfig(reqConfig);
            }

            long startTime = System.currentTimeMillis();
            response = this.getConnection(isRetry).execute(postMethod);
            long endTime = System.currentTimeMillis();
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            StatusLine stsLine = response.getStatusLine();
            int stsCode = stsLine.getStatusCode();
            logger.info("statusCode:" + stsCode);
            resRtn.setStsCode(stsCode);
            resRtn.setRsnPhrase(stsLine.getReasonPhrase());
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode:" + statusCode);
            if (stsCode == 200) {
                String strValue = EntityUtils.toString(response.getEntity());
                resRtn.setValue(strValue);
                logger.info("http response body is:" + strValue);
            }
        } catch (ConnectTimeoutException var31) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var31.getMessage());
        } catch (SocketTimeoutException var32) {
            resRtn.setStsCode(-2);
            resRtn.setRsnPhrase(var32.getMessage());
        } catch (HttpHostConnectException var33) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var33.getMessage());
        } catch (Exception var34) {
            resRtn.setStsCode(-9);
            resRtn.setRsnPhrase(var34.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.getEntity().getContent().close();
                } catch (Exception var30) {
                    logger.error("response.getEntity().getContent() close is error ", var30);
                }
            }

            if (postMethod != null) {
                postMethod.abort();
            }

        }

        return resRtn;
    }

    public HttpResRtn postMap(String strUrl, Map<Object, Object> paraMap) throws Exception {
        return this.postMap(strUrl, paraMap, (RequestConfig) null, false);
    }

    public HttpResRtn postMap(String strUrl, Map<Object, Object> paraMap, int socketTimeOut) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(10000).build();
        return this.postMap(strUrl, paraMap, reqConfig, false);
    }

    public HttpResRtn postMap(String strUrl, Map<Object, Object> paraMap, int socketTimeOut, int conTimeOut) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(conTimeOut).build();
        return this.postMap(strUrl, paraMap, reqConfig, false);
    }

    private HttpResRtn getJson(String strUrl, String strJson, RequestConfig reqConfig, boolean isRetry) {
        Args.notNull(strUrl, "url request");
        Args.notNull(strJson, "json string request");
        HttpResRtn resRtn = new HttpResRtn();
        HttpGet getMethod = null;
        CloseableHttpResponse response = null;

        try {
            StringEntity entity = new StringEntity(strJson, "utf-8");
            String strPara = EntityUtils.toString(entity);
            getMethod = new HttpGet(this.urlAppend(strUrl, strPara));
            if (reqConfig != null) {
                getMethod.setConfig(reqConfig);
            }

            long startTime = System.currentTimeMillis();
            response = this.getConnection(isRetry).execute(getMethod);
            long endTime = System.currentTimeMillis();
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            StatusLine stsLine = response.getStatusLine();
            int stsCode = stsLine.getStatusCode();
            logger.info("statusCode:" + stsCode);
            resRtn.setStsCode(stsCode);
            resRtn.setRsnPhrase(stsLine.getReasonPhrase());
            if (stsCode == 200) {
                String strValue = EntityUtils.toString(response.getEntity(), "utf-8");
                resRtn.setValue(strValue);
                logger.info("http response body is:" + strValue);
            }

            response.getEntity().getContent().close();
        } catch (ConnectTimeoutException var31) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var31.getMessage());
        } catch (SocketTimeoutException var32) {
            resRtn.setStsCode(-2);
            resRtn.setRsnPhrase(var32.getMessage());
        } catch (HttpHostConnectException var33) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var33.getMessage());
        } catch (Exception var34) {
            resRtn.setStsCode(-9);
            resRtn.setRsnPhrase(var34.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.getEntity().getContent().close();
                } catch (Exception var30) {
                    logger.error("response.getEntity().getContent() close is error ", var30);
                }
            }

            if (getMethod != null) {
                getMethod.abort();
            }

        }

        return resRtn;
    }

    public HttpResRtn getJson(String strUrl, String strJson) {
        return this.getJson(strUrl, strJson, (RequestConfig) null, false);
    }

    public HttpResRtn getJson(String strUrl, String strJson, boolean isRetry) {
        return this.getJson(strUrl, strJson, (RequestConfig) null, false);
    }

    public HttpResRtn getJson(String strUrl, String strJson, int socketTimeOut, int conTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(conTimeOut).build();
        return this.getJson(strUrl, strJson, reqConfig, isRetry);
    }

    public HttpResRtn getJson(String strUrl, String strJson, int socketTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(10000).build();
        return this.getJson(strUrl, strJson, reqConfig, isRetry);
    }

    private HttpResRtn getMap(String strUrl, Map<Object, Object> paraMap, RequestConfig reqConfig, boolean isRetry) {
        Args.notNull(strUrl, "url request");
        HttpResRtn resRtn = new HttpResRtn();
        HttpGet getMethod = null;
        CloseableHttpResponse response = null;

        try {
            UrlEncodedFormEntity urlEntity = this.mapToEntity(paraMap, Consts.UTF_8);
            String strPara = EntityUtils.toString(urlEntity);
            getMethod = new HttpGet(this.urlAppend(strUrl, strPara));
            if (reqConfig != null) {
                getMethod.setConfig(reqConfig);
            }

            response = this.getConnection(isRetry).execute(getMethod);
            StatusLine stsLine = response.getStatusLine();
            int stsCode = stsLine.getStatusCode();
            logger.info("statusCode:" + stsCode);
            resRtn.setStsCode(stsCode);
            resRtn.setRsnPhrase(stsLine.getReasonPhrase());
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode:" + statusCode);
            if (stsCode == 200) {
                String strValue = EntityUtils.toString(response.getEntity());
                resRtn.setValue(strValue);
                logger.info("http response body is:" + strValue);
            }
        } catch (ConnectTimeoutException var28) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var28.getMessage());
        } catch (SocketTimeoutException var29) {
            resRtn.setStsCode(-2);
            resRtn.setRsnPhrase(var29.getMessage());
        } catch (HttpHostConnectException var30) {
            resRtn.setStsCode(-1);
            resRtn.setRsnPhrase(var30.getMessage());
        } catch (Exception var31) {
            resRtn.setStsCode(-9);
            resRtn.setRsnPhrase(var31.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.getEntity().getContent().close();
                } catch (Exception var27) {
                    logger.error("response.getEntity().getContent() close is error ", var27);
                }
            }

            if (getMethod != null) {
                getMethod.abort();
            }

        }

        return resRtn;
    }

    public HttpResRtn getMap(String strUrl, Map<Object, Object> paraMap) throws Exception {
        return this.getMap(strUrl, paraMap, (RequestConfig) null, false);
    }

    public HttpResRtn getMap(String strUrl, Map<Object, Object> paraMap, boolean isRetry) throws Exception {
        return this.getMap(strUrl, paraMap, (RequestConfig) null, isRetry);
    }

    public HttpResRtn getMap(String strUrl, Map<Object, Object> paraMap, int socketTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(10000).build();
        return this.getMap(strUrl, paraMap, reqConfig, false);
    }

    public HttpResRtn getMap(String strUrl, Map<Object, Object> paraMap, int socketTimeOut, int conTimeOut, boolean isRetry) {
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(conTimeOut).build();
        return this.getMap(strUrl, paraMap, reqConfig, isRetry);
    }

    public class HttpResRtn {
        private int stsCode;
        private String rsnPhrase;
        private String value;

        private HttpResRtn() {
        }

        public int getStsCode() {
            return this.stsCode;
        }

        protected void setStsCode(int stsCode) {
            this.stsCode = stsCode;
        }

        public String getRsnPhrase() {
            return this.rsnPhrase;
        }

        protected void setRsnPhrase(String rsnPhrase) {
            this.rsnPhrase = rsnPhrase;
        }

        public String getValue() {
            return this.value;
        }

        protected void setValue(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Object,Object> map = new HashMap<>();
        map.put("count",10);
        map.put("timeout",100);
        HttpClientUtil.HttpResRtn httpResRtn = HttpClientUtil.getInstance().getMap("http://39.96.116.170:8080/api/workflowListener/poll/batch/callbackSuccessQueue", map);
        System.out.println(httpResRtn);
    }
}

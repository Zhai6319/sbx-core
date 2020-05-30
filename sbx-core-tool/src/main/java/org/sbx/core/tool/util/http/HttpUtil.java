package org.sbx.core.tool.util.http;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



/**
 * <p>HttpUtil class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
@Slf4j
@SuppressWarnings("all")
public class HttpUtil {

    private static int connectTimeOut = 20000;
    private static int readTimeOut = 20000;

    /**
     * http get方式发送消息
     *
     * @param reqUrl
     *            请求url地址
     * @param parameters
     * @param recvEncoding
     * @return
     */
    public static String doGet(String reqUrl, Map<String, String> parameters,
                               String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        BufferedReader rd = null;
        InputStream in = null;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator<Entry<String, String>> iter = parameters.entrySet()
                    .iterator(); iter.hasNext(); params.append("&")) {
                Entry<String, String> element = (Entry<String, String>) iter
                        .next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(),
                        recvEncoding));
            }

            if (params.length() > 0){
                params = params.deleteCharAt(params.length() - 1);
            }
            URL url = new URL(reqUrl+ "?" + params.toString());
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            url_con.setConnectTimeout(connectTimeOut);
            url_con.setReadTimeout(readTimeOut);
            url_con.connect();
            //url_con.setRequestProperty("Accept-Language", "zh-CN");
            //url_con.setRequestProperty("Accept", "*/*");
            //url_con.setRequestProperty("Accept-Encoding","gzip, deflate");
//			url_con.setDoOutput(false);
//			url_con.setDoInput(false);
//			byte b[] = params.toString().getBytes();
//			url_con.getOutputStream().write(b, 0, b.length);
//			url_con.getOutputStream().flush();
//			url_con.getOutputStream().close();
            in = url_con.getInputStream();
            rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            for (; tempLine != null; tempLine = rd.readLine()) {
                temp.append(tempLine);
                temp.append(crlf);
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }finally{
            if(rd != null){
                try {
                    rd.close();
                } catch (IOException e) {
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (url_con != null){
                url_con.disconnect();
            }
        }
        return responseContent;
    }
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
    /**
     * http get方式发送消息
     *
     * @param reqUrl
     * @param recvEncoding
     * @return
     */
    public static String doGet(String reqUrl, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        BufferedReader rd = null;
        InputStream in = null;
        try {
            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            url_con.setConnectTimeout(connectTimeOut);
            url_con.setReadTimeout(readTimeOut);
            url_con.connect();
            in = url_con.getInputStream();
            rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            for (; tempLine != null; tempLine = rd.readLine()) {
                temp.append(tempLine);
                temp.append(crlf);
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            log.error("发送失败", e);
        }finally{
            if(rd != null){
                try {
                    rd.close();
                } catch (IOException e) {
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (url_con != null){
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * http post方式发送消息
     *
     * @param reqUrl
     * @param parameters
     * @param recvEncoding
     * @return
     */
    public static String doPost(String reqUrl, Map<String, String> parameters,
                                String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        BufferedReader rd = null;
        InputStream in = null;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator<Entry<String, String>> iter = parameters.entrySet()
                    .iterator(); iter.hasNext(); params.append("&")) {
                Entry<String, String> element = (Entry<String, String>) iter
                        .next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(),
                        recvEncoding));
            }
            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
            }
            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setConnectTimeout(connectTimeOut);
            url_con.setReadTimeout(readTimeOut);
            url_con.setDoOutput(true);
            byte b[] = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            in = url_con.getInputStream();
            rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            for (; tempLine != null; tempLine = rd.readLine()) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            log.error("发送失败！", e);
        }finally{
            if(rd != null){
                try {
                    rd.close();
                } catch (IOException e) {
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    public static void put(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    public static Object get(HttpSession session, String key) {
        return session.getAttribute(key);
    }

    public static void remove(HttpSession session, String key) {
        session.removeAttribute(key);
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }

    public static String get(HttpServletRequest req, String key) {
        Cookie[] cookies = req.getCookies();
        if (null == cookies) {
            return null;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(key)) {
                return c.getValue();
            }
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
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
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void put(HttpServletResponse resp, String[] keys, String[] values) {
        CookieGenerator g = new CookieGenerator();
        g.setCookiePath("/");
        g.setCookieMaxAge(86400);
        for (int i = 0; i < keys.length; i++) {
            g.setCookieName(keys[i]);
            g.addCookie(resp, values[i]);
        }
    }

    public static void put(HttpServletResponse resp, String[] domains, String[] keys, String[] values) {
        CookieGenerator g = new CookieGenerator();
        g.setCookiePath("/");
        g.setCookieMaxAge(86400);
        for (String domain : domains) {
            g.setCookieDomain(domain);
            for (int i = 0; i < keys.length; i++) {
                g.setCookieName(keys[i]);
                g.addCookie(resp, values[i]);
            }
        }
    }

    public static void remove(HttpServletResponse resp, String[] keys) {
        disableCookie(resp);
		/*
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");

		g.setCookieMaxAge(86400);// 一天时间
		for (int i = 0; i < keys.length; i++) {
			g.setCookieName(keys[i]);
			g.removeCookie(resp);
		}
		*/
    }

    public static void remove(HttpServletResponse resp, String[] domains, String[] keys) {
        disableCookie(resp);
		/*
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");

		for (String domain : domains) {
			g.setCookieDomain(domain);
			for (String key : keys) {
				g.setCookieName(key);
				g.removeCookie(resp);
			}
		}
		*/
    }

    public static void disableCookie(HttpServletResponse resp) {
        CookieGenerator g = new CookieGenerator();
        g.setCookiePath("/");

        //HttpContext.SessionKey.LOGINING_ACTIVE.toString()
        g.setCookieName("active");
        g.addCookie(resp, "1");
    }

    /**
     * map拼接url
     */
    public static String joinUrl(String url, Map<?, ?> map) {
        StringBuffer params = new StringBuffer();
        for(Iterator<?> iter = map.entrySet().iterator(); iter.hasNext(); params.append("&"))
        {
            @SuppressWarnings("rawtypes")
            Entry element = (Entry)iter.next();
            params.append(((String)element.getKey()).toString());
            params.append("=");
            params.append((String)element.getValue().toString());
        }
        return url + "?" + params.toString();
    }

    /**
     * xml请求post
     */
    public static String doPostXml(String reqUrl, Map<String, String> headers, String content, String charset) throws Exception {
        BasicHttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET, charset);
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(3000));
        @SuppressWarnings("resource")
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        URL url = new URL(reqUrl);
        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        HttpPost httpPost = new HttpPost(uri);

        try {
            HttpEntity httpEntity = new StringEntity(content, charset);
            httpPost.setEntity(httpEntity);
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse responseBody = httpClient.execute(httpPost);
            return EntityUtils.toString(responseBody.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求异常");
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * 获取post中的内容(适用于无key的post内容)
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getPostContent(HttpServletRequest request) throws IOException {
        String result = null;
        StringBuffer info = new StringBuffer();
        InputStream in = request.getInputStream();
        BufferedInputStream buf = new BufferedInputStream(in);
        byte[] buffer = new byte[1024];
        int iRead;
        while ((iRead = buf.read(buffer)) != -1) {
            info.append(new String(buffer, 0, iRead, "UTF-8"));
        }
        if (!StringUtils.isBlank(info.toString())) {
            result = info.toString();
        }
        return result;
    }


    public static JSONObject getRepParam(HttpServletRequest req){
        JSONObject paramsObj = new JSONObject();
        Map<String, String[]> params = req.getParameterMap();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                paramsObj.put(key, value);
            }
        }
        return paramsObj;


    }


}

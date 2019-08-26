package com.yixin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;
import java.util.*;
//接口wiki地址：http://wiki.zhidaohulian.com/display/zhidao/gateway

/**
 * @author wangzhiyuan
 * @since 2017/12/17
 * 把登录请求参数按照顺序生成签名，生成sig字段值
 * 蘑菇智行app登录获取签名方法
 */

public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    private static final String SALT = "JGqZw9";

    private static class StrSortTreeMap extends TreeMap<String, Object> {
        private static final long serialVersionUID = 1L;

        public StrSortTreeMap() {
            super(new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });
        }
    }

    /**
     * source: 1 app 2 车机
     *
     * @return
     */
    public static boolean auth(Map<String, Object> paramsMap) {

        if (Objects.isNull(paramsMap) || paramsMap.isEmpty()) {
            return false;
        }

        //复制参数
        Map<String, Object> realParamMap = new HashMap<>(paramsMap);

        Object clientSign = realParamMap.getOrDefault("sig", null);
        if (Objects.isNull(clientSign)) {
            return false;
        }
        realParamMap.remove("sig");
        Object serverSign = createSign(realParamMap, SignUtil.SALT);

        boolean ok = serverSign.equals(clientSign);
        if (! ok) {
            logger.warn("\nserverSign = {}\nclientSign = {}", serverSign, clientSign);
        }
        return serverSign.equals(clientSign);
    }

    public static String createSign(Map<String, Object> map, String key) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StrSortTreeMap treeMap = new StrSortTreeMap();
        for (String k : map.keySet()) {
            treeMap.put(k, map.get(k));
        }
        return createSign(treeMap, key);
    }

    private static String createSign(StrSortTreeMap map, String salt) {
        try {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                queryString.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            queryString.append("key=").append(DigestUtils.sha1Hex(salt));
            return DigestUtils.sha1Hex(queryString.toString()).toUpperCase();
        } catch (Exception e) {
            logger.error("", e);
            return "";
        }
    }

    public static Map<String, Object> showParams(HttpServletRequest request) throws IOException {


        Map<String, Object> map = Maps.newHashMap();

        if ("DELETE".equalsIgnoreCase(request.getMethod()) || "POST".equalsIgnoreCase(request.getMethod())) {
            HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
            HttpServletRequest realRequest = (HttpServletRequest) httpServletRequestWrapper.getRequest();
            String contentType = realRequest.getContentType();
            if (! StringUtils.isEmpty(contentType)) {
                if (contentType.startsWith("application/json")) {
                    String s = getRequestPostStr(request);
                    if (!StringUtils.isEmpty(s)) {
                        map = JSONObject.parseObject(s, new TypeReference<Map<String, Object>>() {
                        });
                    }
                } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
                    map = getParam(request);
                } else if (contentType.startsWith("multipart/form-data")) {
                    map = getParam(realRequest);
                }
            }
        }

        map.putAll(getParam(request));

        return map;
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        if (Objects.isNull(buffer) || buffer.length <= 0) {
            return "";
        }
        String charEncoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(charEncoding)) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }


    /**
     * 获取参数内容
     *
     * @param request
     * @return
     */
    private final static Map<String, Object> getParam(HttpServletRequest request) {

        Map<String, Object> map = Maps.newHashMap();

        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (paramName.startsWith("------WebKitFormBoundary")) {
                continue;
            }
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        return map;
    }

}

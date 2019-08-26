package com.yixin.openAPITest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/*
 * 类名:createSign
 * 描述：openapi对外接口生成签名方法
 * 作者：秦振霞
 * 创建时间：2019年8月26号
 * 版本：v1.0.0
 *
 */
public class createSign {
    public static final String appid="czb";
    public static final String appkey="imSynlBWJSB7zLBhls70rejFy1Qp1PwpwPWjso4Ecpc";
    //生成时间戳
    public static String timestamp = String.valueOf(System.currentTimeMillis());
    //生成随机字符串
    public static String nonce = UUID.randomUUID().toString().replaceAll("-","");


    //签名算法
    public  static String getsign() throws NoSuchAlgorithmException {

        SortedMap<String, String> p=new TreeMap<String, String>();
//        p.put("orderId","15105491297");
//        p.put("orderId","");
//        p.put("ticketCode","6107010940");
        p.put("channels","");
        p.put("orderTypes","10");
        p.put("createdTimeStart","2019-07-01 00:00:00");
        p.put("createdTimeEnd","2019-07-31 23:59:59");
        p.put("_appid", appid);
        p.put("_timestamp", timestamp);
        p.put("_nonce",nonce);



        //拼接字符串
        StringBuilder sb=new StringBuilder();
        sb.append(appkey);

        for (Map.Entry<String, String> me:p.entrySet())
            sb.append(me.getKey()+me.getValue());
        sb.append(appkey);

        //debug
        System.out.println("sb="+sb.toString());

        //md5加密
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(sb.toString().getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if(i<0)i+= 256;
            if(i<16)buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        //debug
        System.out.println("_sign="+buf.toString().toUpperCase());

        return buf.toString().toUpperCase();
    }
}

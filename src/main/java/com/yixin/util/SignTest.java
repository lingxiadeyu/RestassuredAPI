package com.yixin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
//import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.objectMapper;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
/*
 * 类名:SignTest
 * 描述：获取sig签名字段，调登录接口获取token值
 * 作者：吴立中
 * 创建时间：2018年11月2号
 * 版本：v1.0.0
 *
 */
public class SignTest {
    public static void main(String args[]){
//        HashMap<String,Object> param = new HashMap<>();
////        param.put("accelerationX","0.0008851960301399231");
////        param.put("accelerationY","0.009899139404296875");
////        param.put("accelerationZ","0.01658368110656738");
////        param.put("antifraudDeviceId","2018102916433714924105faede02876f961edf39adb3e01171221ab553875");
////        param.put("appVersion","2.0.2.3");
////        param.put("appVersionCode","2023");
////        param.put("gravityX","-0.02214068919420242");
////        param.put("gravityY","-0.3713340759277344");
////        param.put("gravityZ","-0.9282352924346924");
////        param.put("hardWareModel","iPhone 7");
////        param.put("imei","5bd5c6bcf60b07e2397c8edbc1849cd9fcc40223");
////        param.put("lat","39.96865044487847");
////        param.put("lng","116.4109665256076");
////        param.put("locType","1");
////        param.put("netType","WIFI");
////        param.put("os","iOS");
////        param.put("osVersion","11.4.1");
////        param.put("passwd","q11111");
////        param.put("phone","18600228767");
////        param.put("rotationRateX","-0.09426203370094299");
////        param.put("rotationRateY","0.002493759617209435");
////        param.put("rotationRateZ","0.1058494374155998");
////        param.put("simulator","0");
////        param.put("source","3");
////        param.put("timestamp","1541122222404");
//
//        param.put("deviceId","PBVGK16918902121");
//        param.put("source","1");
//        param.put("locType","2");
//        param.put("appVersionCode","10");
//        param.put("phone","18600228776");
//        param.put("cityName","北京市");
//        param.put("pixels","1080*1794");
//        param.put("os","Android");
//        param.put("lng","116.411389");
//        param.put("cityCode","010");
//        param.put("hardWareModel","EVA-AL00");
//        param.put("androidId","6e0bff5173c9fc75");
//        param.put("appVersion","2.0.0.14");
//        param.put("passwd","q111111");
//        param.put("imei","869158020526238");
//        param.put("adCode","110105");
//        param.put("osVersion","7.0");
//        param.put("apSsid","qinzhenxia-test");
//        param.put("netType","WIFI");
//        param.put("lat","39.9683");
//        param.put("apMac","62:f6:77:ac:c8:e4");
//
//
//        String sign = SignUtil.createSign(param,"JGqZw9");
//        System.out.println(sign);
//
//        param.put("sig",sign);
//
////        RestAssured.given().config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("charset=UTF-8", ContentType.JSON))).proxy(8888).formParams(param)
//////                .contentType("application/json").contentType("charset=UTF-8")
//////                .params(param)
////                .when()
////                .post("http://carlife-test.zhidaohulian.com/carlife/login")
////                .getBody().prettyPrint();
//
//        //提示签名校验失败，可能是过了时间，重新抓包观察参数值有什么变化，去掉某些参数看是否有影响
//        //每次登录都需要这些参数看是否会提示
//        //如果会提示就把token值存起来
//        //其他账号登录会怎么样呢？
//        //如果能正常返回token值，则修改runtools类。
//
//
//        HttpClient httpClient = new DefaultHttpClient();
//
//        List<NameValuePair> pairList = new ArrayList<>();
//
//        for(Map.Entry<String,Object> me : param.entrySet()){
//            NameValuePair nvp = new BasicNameValuePair(me.getKey(),me.getValue().toString());
//            pairList.add(nvp);
//        }
//
//        HttpPost hp = new HttpPost("http://carlife-test.zhidaohulian.com/carlife/login");
//        try {
//            hp.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
//            HttpResponse httpresponse = httpClient.execute(hp);
//
//
//
//            // 获取返回数据
//            HttpEntity entity = httpresponse.getEntity();
//            String body = EntityUtils.toString(entity,"UTF-8");
//            System.out.println(body);
//            JSONObject jsonObject=JSONObject.parseObject(body);
//            String result=jsonObject.get("result").toString();
//            JSONObject jsonObjectresult=JSONObject.parseObject(result);
//            System.out.println(jsonObjectresult.get("token"));
//
//
//            if (entity != null) {
//                entity.consumeContent();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }


}

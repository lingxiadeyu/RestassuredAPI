package com.yixin.openAPITest;

import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 类名:openApiOrderget
 * 描述：openapi订单详情接口测试，不校验登录信息
 * 作者：秦振霞
 * 创建时间：2019年8月26号
 * 版本：v1.0.0
 *
 */
public class openApiOrderget {

    RunTools runTools = new RunTools();


    @BeforeTest
    public void setUp(){
        //openapi订单详情接口
        RestAssured.baseURI = "http://test.open.zhidaohulian.com";
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        System.out.println("测试开始："+method.getName());
    }

    @AfterMethod
    public void afterMethod(Method method){
        System.out.println("测试结束："+method.getName());
    }

    @Test
    public void testOAOGget001() throws NoSuchAlgorithmException {
//        runTools.runGetApi("OAOGget","OAOGget")
//                .then()
//                .body("result.orderId",hasToString("15105491297"));

//                .print();

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String _timestamp = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

//        Map<String,String> map = new HashMap<>();
//        map.put("orderId","15105491297");
//        map.put("_appid","czb");
//        map.put("_timestamp",String.valueOf(System.currentTimeMillis()));
//        map.put("_nonce",UUID.randomUUID().toString().replaceAll("-",""));
//        map.put("_sign",getsign());


//        given()
////                .param("orderId","15105491297")
////                .param("orderId","")
//                .param("ticketCode","6107010940")
//                .param("_appid",createSign.appid)
//                .param("_timestamp",createSign.timestamp)
//                .param("_nonce",createSign.nonce)
//                .param("_sign",createSign.getsign())
//                .when()
//                .get("http://open.zhidaohulian.com/api/order/get")
//                .print();


        given()
//                .param("orderId","15105491297")
//                .param("orderId","")
//                .param("ticketCode","6107010940")
                .param("channels","")
                .param("orderTypes","10")
                .param("createdTimeStart","2019-07-01 00:00:00")
                .param("createdTimeEnd","2019-07-31 23:59:59")
                .param("_appid",createSign.appid)
                .param("_timestamp",createSign.timestamp)
                .param("_nonce",createSign.nonce)
                .param("_sign",createSign.getsign())
                .when()
                .get("http://open.zhidaohulian.com/api/order/list")
                .print();

    }

//    //签名算法
//    public  static String getsign() throws NoSuchAlgorithmException {
//
//        SortedMap<String, String> p=new TreeMap<String, String>();
//        p.put("orderId","15105491297");
//        p.put("_appid", appid);
//        p.put("_timestamp", timestamp);
//        p.put("_nonce",nonce);
//
//
//
//        //拼接字符串
//        StringBuilder sb=new StringBuilder();
//        sb.append(appkey);
//
//        for (Map.Entry<String, String> me:p.entrySet())
//            sb.append(me.getKey()+me.getValue());
//        sb.append(appkey);
//
//        //debug
//        System.out.println("sb="+sb.toString());
//
//        //md5加密
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(sb.toString().getBytes());
//        byte b[] = md.digest();
//        int i;
//        StringBuffer buf = new StringBuffer("");
//        for (int offset = 0; offset < b.length; offset++) {
//            i = b[offset];
//            if(i<0)i+= 256;
//            if(i<16)buf.append("0");
//            buf.append(Integer.toHexString(i));
//        }
//
//        //debug
//        System.out.println("_sign="+buf.toString().toUpperCase());
//
//        return buf.toString().toUpperCase();
//    }

}

package com.yixin.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class Account {
    ReadConfigFiles readConfigFiles = new ReadConfigFiles();
    DataBase dataBase = new DataBase();
    WriteConfigFile writeConfigFile=new WriteConfigFile();
    //通过蘑菇app的登陆获取响应 -----------old
//    UapClient uapClient;
//
//    public String getTokenString() {
//        if (uapClient == null) {
//            uapClient = UapClient.getInstance();
//            uapClient.setSsoServerHost("10.2.100.9:8443");
//            uapClient.setUserServerHost("10.2.100.9:8463");
//        }
//        IAuthentication authentication = uapClient.authentication();
////        Result result = authentication.login("17710356858", "123456");
//        Result result = authentication.login("18610413790", "wodemima11");
//
//        if (result.getCode() == ResultCode.OK.value()) {
//            System.out.println("登录成功");
//            String TGT = (String) result.getResult();
////            System.out.println(TGT);
//            return TGT;
//        }
//        return "";
//    }

//    public Response login(String accountTel){
//        Response response=given()
//                .params(readConfigFiles.findAccount(accountTel))
//                .when()
//                .post(readConfigFiles.findUrl("testLoginUrl"));
//        return response;
//    }
    //通过蘑菇app的登陆获取token -----------old
//    public String getTokenWithLogin(String accountTel){
//         String token=given()
//                 .params(readConfigFiles.findAccount(accountTel))
//                 .when()
//                 .post(readConfigFiles.findUrl("testLoginUrl"))
//                 .body()
//                 .path("result.token");
//         return token;
//    }
    //通过手机号码查询openid
    //因ucenter.user_passport_user已删除，故弃用
//    public String getOpenId(String tel){
//        String mobileNum=tel;
//        //通过手机号码查询openid
//        try {
//            List openid=dataBase.readDB("SELECT * FROM ucenter.user_passport_user WHERE mobile='"+mobileNum+"' ORDER BY created_time DESC LIMIT 1","openid");
//            return (String) openid.get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;


    //通过手机号码查询userId
    //因ucenter.user_passport_user已删除，故弃用
//    public String getUserId(String tel){
//        String mobileNum=tel;
//        //通过手机号码查询openid
//        try {
//            List userIdGzh=dataBase.readDB("SELECT * FROM ucenter.user_passport_user WHERE mobile='"+mobileNum+"' ORDER BY created_time DESC LIMIT 1","user_id");
//            return (String) userIdGzh.get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    //从API_para读取userId
    public String getUserId(String userIdName){
        String userId=readConfigFiles.findAccount(userIdName);
        System.out.println("userId="+userId);
        return userId;
    }
    //通过userId查询数据库得到openId
    public String getOpenId(String userIdName){
        String userId=getUserId(userIdName);
        try {
            String  third_party_user_id= (String) dataBase.readDB
                    ("SELECT * FROM ucenter.user_third_party_mapping WHERE user_id='"+userId+"' AND `status`=0 ORDER BY created_time DESC LIMIT 1;","third_party_user_id")
                    .get(0);

            String openId= (String) dataBase.readDB("SELECT * FROM ucenter.third_party_user WHERE id='"+third_party_user_id+"';","openid").get(0);
            return openId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过userId查询加密手机号码，通过加密手机号码获取短信验证码
    public String getValidateCode(String tel,String userIdName){
        String mobileNum=tel;
        //通过手机号码查询openid
        try {
            String userId=getUserId(userIdName);
            given()
                    .param("phone",tel)
                    .post(readConfigFiles.findUrl("sendSMS"));
            List secretTel=dataBase.readDB("SELECT * FROM ucenter.`user` WHERE id='"+userId+"'","mobile");
            List validateCode=dataBase.readDB("SELECT * FROM ucenter.sms WHERE phone='"+secretTel.get(0)+"' ORDER BY created_time DESC LIMIT 1","validate_code");
            return (String) validateCode.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过公众号登陆获取
    public String createTokenGZH(String tel,String userIdName){
        Map<String,Object> map=new HashMap<>();
        map.put("validateCode",getValidateCode(tel,userIdName));
        map.put("openid",getOpenId(userIdName));
        map.put("phone",tel);
        map.put("appId","xintai-test");
        JSONObject json = JSONObject.fromObject(map);
        String token=
                given()
                        .contentType("application/json")
                        .body(json)
                        .when()
                        .post(readConfigFiles.findUrl("testGZHLogin"))
                        .body()
                        .path("result");
        //在手机号前面加tel，yaml只能识别字符串
        String keyname="tel"+tel;
        writeConfigFile.writeTelNumAndToken(keyname,token);
        return token;
    }
    public String getTokenGzh(String tel,String userIdName)  {
        String telExists = readConfigFiles.findtelNumOrToken("telNum");
            if (telExists.equals(tel)){
                String readToken=readConfigFiles.findtelNumOrToken("token");
                return readToken;
            }else {
                return createTokenGZH(tel,userIdName);
            }
        }

//    //修改getTokenGzh方法--秦振霞
//    public String getTokenGzh(String tel,String userIdName)  {
//        String keyname="tel"+tel;
//        String telExists = readConfigFiles.findtelNumOrToken(keyname);
//        if (telExists == null){
//            return createTokenGZH(tel,userIdName);
//        }else {
//            return telExists;
//        }
//    }

    /*
     * 方法名:getTokenWithPrivilegeRefuelMerchant
     * 描述：特权加油商户APP端获取token
     * 作者：秦振霞
     * 创建时间：2018年9月7号
     * 版本：v1.0.0
     *
     */
    public String getTokenWithPrivilegeRefuelMerchant(String accouontTel){
        String token=given()
                .header("Content-Type","application/json")
                .body(readConfigFiles.findAccount("account8767"))
                .when()
                .post(readConfigFiles.findUrl("PRMloginByPassword"))
                .body()
                .path("p2pdata.token");
        return token;
    }

    /*
     * 方法名:getTokenAPP
     * 描述：蘑菇智行APP端获取token
     * 作者：秦振霞
     * 创建时间：2018年11月2号
     * 版本：v1.0.0
     *
     */
    public String getTokenAPP(String testLoginpara){
        //定义一个map，用来存放sig
        Map<String,Object> map=new HashMap();

        //从yaml中获取参数，调签名接口生成sig签名
        String sig=SignUtil.createSign(readConfigFiles.findGetPara(testLoginpara),"JGqZw9");
        map.put("sig",sig);

        //参数中有中文需要设置字符集
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().defaultCharsetForContentType("UTF-8", "application/json"));
        //获取token值
        String token=given()
//                .proxy(8888)  //设置代理，通过charles能抓包
                //APP登录接口用的是表单格式，需要用formParams传参数
                .formParams(readConfigFiles.findGetPara(testLoginpara))
                .formParams(map)
                .when()
                .post(readConfigFiles.findUrl("testLoginUrl"))
                .path("result.token");

//        System.out.println(token);

        //把token写入yaml文件中，下次直接读取，此方法不可行。
//        writeConfigFile.writeTelNumAndToken(testLoginpara,token);

        return token;


    }

    //获取已经存在的token，此方法不可行，1、没办法判断token失效；2、token失效重新插入yaml中key值会重复，再取值会有问题
//    public String getTokenAPP(String testLoginpara)  {
//        String ExistsToken = readConfigFiles.findAccount(testLoginpara);
//        if (ExistsToken == null){
//            return createTokenAPP(testLoginpara);
//        }else {
//            return ExistsToken;
//        }
//    }



}

package com.yixin.test;
import com.yixin.PrivilegeRefuelMerchantBusiness.PRMBDataDependency;
import com.yixin.PrivilegeRefuelMerchantBusiness.PRMBRunTools;
import com.yixin.util.Account;
import com.yixin.util.DataBase;
import com.yixin.util.ReadConfigFiles;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.*;
/*
 * 类名:PrivilegeRefuelMerchant
 * 描述：特权加油站商户端APP接口
 * 作者：秦振霞
 * 创建时间：2018年9月7号
 * 版本：v1.0.0
 *
 */
public class PrivilegeRefuelMerchant {
    PRMBRunTools prmbRunTools=new PRMBRunTools();
    ReadConfigFiles readConfigFiles=new ReadConfigFiles();
    DataBase dataBase=new DataBase();
    PRMBDataDependency prmbDataDependency=new PRMBDataDependency();


    @BeforeTest
    public void SetUp(){
        RestAssured.baseURI="https://test-jy.yxyongche.cn/m-api";
    }
    @BeforeMethod
    public void testStart(Method method){

        System.out.println("开始执行："+method.getName());
    }
    @AfterMethod
    public void testEnd(Method method){

        System.out.println("执行结束:"+method.getName());
    }

    @Test
    public void testPrivilegeRefuelMerchant002(){
        //商户-获取加油站列表接口
        //验证获取的列表是否与数据库中获取的一致
        //验证接口返回200正确
        prmbRunTools.runGetApiWithoutParam("PRMmerchantList","account8767")
                .then()
                .spec(prmbRunTools.respGetWithoutParam("PRMmerchantList","account8767"));

        //编写sql语句
        String sqlstring="select merchant_id from hui_oil.m_admin_merchant where user_id in (select id from hui_oil.m_admin where phone='18600228767')";
        //定义要查询的字段值
        String queryname="merchant_id";

        try {
            //定义一个list用来存储查询数据库返回的结果
            List queryresult=new ArrayList();
            //把查询数据库返回的结果存储到list中
            queryresult.addAll(dataBase.readDB(sqlstring,queryname));

            //再定义一个list用来存储接口返回的id值
            List response=new ArrayList();
            response.addAll(prmbRunTools.runGetApiWithoutParam("PRMmerchantList","account8767")
                    .body()
                    .path("p2pdata.id"));

            //比对两个列表的长度
            if(queryresult.size() == response.size()){
                //定义两个for循环判断列表1中的值与列表2中的值是否一致
                //如果列表1中的值与列表2中的值不一致跳出当前循环执行下一个循环
                for(int i=0;i<queryresult.size();i++){
                    String queryvalue=queryresult.get(i).toString();
                    for(int j=0;j<response.size();j++){
                        String responsevalue=response.get(j).toString();
                        if(!queryvalue.equals(responsevalue)){
                            continue;
                        }
                        System.out.println("数据库查询结果是:"+queryvalue+",接口查询结果是:"+responsevalue);
                    }
                }

            }else {
                System.out.println("list结果长度不一致");
            }



            //定义for循环用来比较两个list中的值
//            if (queryresult.containsAll(response)){
//                System.out.println("比对成功");
//            }else {
//                System.out.println("比对不成功");
//            }

            //定义for循环用来比较两个list的值
//            for (Object object:queryresult){
//                System.out.println(object);
//                if (response.contains(object)){
//                    System.out.println("比对成功");
//                }else {
//                    System.out.println("比对不成功");
//                }
//            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPrivilegeRefuelMerchant003(){
        //版本信息-获取指定版本信息
        //验证版本号为2的版本
        prmbRunTools.runGetApi("PRMversionDetail","PRMversionDetail","account8767")
                .then()
                .body("p2pdata.versionNo",equalTo("2"))
                .spec(prmbRunTools.respGetApi("PRMversionDetail","PRMversionDetail","account8767"));

    }
    @Test
    public void testPrivilegeRefuelMerchant004(){
        //版本信息-获取最新版本
        //验证接口返回200正确
        prmbRunTools.runGetApiWithoutParam("PRMlatestVersion","account8767")
                .then()
                .spec(prmbRunTools.respGetWithoutParam("PRMlatestVersion","account8767"));

    }
    @Test
    public void testPrivilegeRefuelMerchant005(){
        //语言设置-加油站，前台选择加油站后台获取加油站id
        //验证接口返回200正确
        prmbRunTools.runGetApi("PRMupdateMerchantId","PRMupdateMerchantId","account8767")
                .then()
                .spec(prmbRunTools.respGetApi("PRMupdateMerchantId","PRMupdateMerchantId","account8767"));

    }
    @Test
    public void testPrivilegeRefuelMerchant006(){
        //语言设置-推送registrationId，极光推送返回一个推送id传给后台，后台根据推送id推送订单信息到前台
        //验证接口返回200正确
        prmbRunTools.runGetApi("PRMsetRegistrationId","PRMsetRegistrationId","account8767")
                .then()
                .spec(prmbRunTools.respGetApi("PRMsetRegistrationId","PRMsetRegistrationId","account8767"));

    }
    @Test
    public void testPrivilegeRefuelMerchant007() throws SQLException {
        //语言设置-修改
        //验证修改配置后从数据库中获取数据比对是否修改成功
        prmbRunTools.runPostApi("PRMupdateByUser","PRMupdateByUser","account8767")
                .then()
                .spec(prmbRunTools.respPostApi("PRMupdateByUser","PRMupdateByUser","account8767"));

        //调登录获取用户id
        int userid=prmbDataDependency.getuserid();
        //写sql语句根据用户id查询语音设置配置
        String QueryAppVoiceConfig="select * from hui_oil.app_voice_config where user_id='"+userid+"'";
        String Querymerchantsid="merchants_id";
        String Querynum="num";
        String Querystatus="status";

        List QueryResult=new ArrayList();
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querymerchantsid));
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querynum));
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querystatus));

        if(QueryResult.get(0).equals("74") && QueryResult.get(1).equals("3") && QueryResult.get(2).equals("0")){
            System.out.println("修改语音设置成功");
        }else{
            System.out.println("修改语音设置失败");
        }

    }
    @Test
    public void testPrivilegeRefuelMerchant00701() throws SQLException {
        //语言设置-修改
        //验证关闭语音设置
        prmbRunTools.runPostApi("PRMupdateByUser","PRMupdateByUser01","account8767")
                .then()
                .spec(prmbRunTools.respPostApi("PRMupdateByUser","PRMupdateByUser01","account8767"));

        //调登录获取用户id
        int userid=prmbDataDependency.getuserid();
        //写sql语句根据用户id查询语音设置配置
        String QueryAppVoiceConfig="select * from hui_oil.app_voice_config where user_id='"+userid+"'";
        String Querystatus="status";

        List QueryResult=new ArrayList();
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querystatus));

        if(QueryResult.get(0).equals("1")){
            System.out.println("修改语音设置成功");
        }else{
            System.out.println("修改语音设置失败");
        }

    }

    @Test
    public void testPrivilegeRefuelMerchant008() throws SQLException {
        //语言设置-获取
        //从数据库hui-oil.app_voice_config中获取数据进行比对

        //调登录获取用户id
        int userid=prmbDataDependency.getuserid();
        //写sql语句根据用户id查询语音设置配置
        String QueryAppVoiceConfig="select * from hui_oil.app_voice_config where user_id='"+userid+"'";
        String Querymerchantsid="merchants_id";
        String Querynum="num";
        String Querystatus="status";

        List QueryResult=new ArrayList();
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querymerchantsid));
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querynum));
        QueryResult.addAll(dataBase.readDB(QueryAppVoiceConfig,Querystatus));

        String merchantsid=QueryResult.get(0).toString();
        String num=QueryResult.get(1).toString();
        String status=QueryResult.get(2).toString();


        prmbRunTools.runGetApiWithoutParam("PRMconfig","account8767")
                .then()
                .body("p2pdata.merchantsId",hasToString(merchantsid))
                .body("p2pdata.num",hasToString(num))
                .body("p2pdata.status",hasToString(status))
                .spec(prmbRunTools.respGetWithoutParam("PRMconfig","account8767"));


    }
    @Test
    public void testPrivilegeRefuelMerchant009(){
        //订单-列表
        //验证接口返回数据正确
        Map<String,Object> map=new HashMap<>();
        map.put("merchantId",prmbDataDependency.getMerchantid());

        prmbRunTools.runPostApiAndDependMore("PRMlist","PRMlist",map,"account8767")
                .then()
                .spec(prmbRunTools.respPostAndDependMore("PRMlist","PRMlist",map,"account8767"));

//                .getBody().prettyPrint();


    }
    @Test
    public void testPrivilegeRefuelMerchant010() throws SQLException {
        //商户-退出登录
        //验证退出登录成功
        prmbRunTools.runGetApiWithoutParam("PRMdownline","account8767");

        //查询数据库中用户在线状态，on_line=0 在线，1下线
        //获取userid，根据userid查询对应的状态
        int userid=prmbDataDependency.getuserid();
        String Queryonline="select * from hui_oil.app_voice_config where user_id='"+userid+"'";
        String Queryname="on_line";

        List Queryresult=new ArrayList();
        Queryresult.addAll(dataBase.readDB(Queryonline,Queryname));

        //比对数据库中获取的值是否为1，为1代表用户已退出
        if(Queryresult.get(0).equals("1")){
            System.out.println("用户已退出");
        }else{
            System.out.println("用户退出失败");
        }




    }







}

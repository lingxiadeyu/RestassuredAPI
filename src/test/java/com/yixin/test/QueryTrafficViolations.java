package com.yixin.test;
import com.yixin.util.RunTools;
import com.yixin.util.Account;
import com.yixin.util.ReadConfigFiles;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.*;
/*
 * 类名:PrivilegeRefuelMerchant
 * 描述：违章查询接口
 * 作者：秦振霞
 * 创建时间：2018年9月21号
 * 版本：v1.0.0
 *
 */
public class QueryTrafficViolations {
    RunTools runTools=new RunTools();
    Account account=new Account();
    ReadConfigFiles readConfigFiles=new ReadConfigFiles();
    String testLoginpara="testLoginpara8767";

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI="http://test-cfw.yxyongche.cn/cardapi";
    }

    @BeforeMethod
    public void testStart(Method method){
        System.out.println("开始执行："+method.getName());
    }
    @AfterMethod
    public void testEnd(Method method){
        System.out.println("结束执行："+method.getName());
    }

    @Test
    public void testQueryTrafficViolations001(){
        //违章查询-添加车辆-车机接口
        //验证从违章查询添加车辆信息，从添加结果中获取车辆id，然后从查询列表中去比对是否包含
        //验证从违章查询添加的车辆fromtype等于1
        //验证从添加到查询再到删除再到查询的流程

        //如果该车辆已添加，会抛出异常！
        try{
            //调用添加车辆接口，从返回结果中获取carId
            int carId=runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo",testLoginpara)
                    .path("p2pdata.id");
            System.out.println("添加成功，车辆id是"+carId);

            //调用查看车辆列表接口，判断第一位车辆的id是新添加的id，判断fromType等于1
            runTools.runGetApiWithoutParam("QTVqueryCheJiCarlist",testLoginpara)
                    .then()
                    .body("p2pdata.id[0]",equalTo(carId))//新添加的车辆默认在第一位，获取第一位的id
                    .body("p2pdata.fromType[0]",equalTo(1));
            System.out.println("查询成功");

            //调用删除车辆接口，调用spec方法，验证重复删除会报错
            Map map=new HashMap();
            map.put("carId",carId);
            runTools.runGetApiAndDepend("QTVdeleteCar",map,testLoginpara)
                    .then()
                    .statusCode(200)
                    .spec(runTools.respGetAndDepend("QTVdeleteCar",map,testLoginpara));
            System.out.println("删除成功");

            //删除之后再调查询违章车辆列表接口，查询不到该车辆id
            runTools.runGetApiWithoutParam("QTVqueryCheJiCarlist",testLoginpara)
                .then()
                .body("p2pdata.id[0]",not(carId));
            System.out.println("查询成功");


        }catch (IllegalArgumentException e){
            System.out.println("该车辆已添加，请修改API_para.yaml中数据！");
            e.printStackTrace();

        }
    }
    @Test
    public void testQueryTrafficViolations00101(){
        //违章查询-添加车辆-车机接口
        //验证车辆添加重复
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo01",testLoginpara)
                .then()
                .body("errmsg",equalTo("该车辆已添加列表"));
    }
    @Test
    public void testQueryTrafficViolations00102(){
        //违章查询-添加车辆-车机接口
        //验证车牌号相同，车架号、发动机号不同
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo02",testLoginpara)
                .then()
                .body("errmsg",equalTo("请填写正确的车辆信息"));
    }
    @Test
    public void testQueryTrafficViolations00103(){
        //违章查询-添加车辆-车机接口
        //验证车牌号不同，车架号、发动机号相同
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo03",testLoginpara)
                .then()
                .body("errmsg",equalTo("请填写正确的车辆信息"));
    }
    @Test
    public void testQueryTrafficViolations00104(){
        //违章查询-添加车辆-车机接口
        //验证车牌号、车架号不同，发动机号相同
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo04",testLoginpara)
                .then()
                .body("errmsg",equalTo("请填写正确的车辆信息"));
    }

    @Test
    public void testQueryTrafficViolations00105(){
        //违章查询-添加车辆-车机接口
        //验证发动机号不满17位
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo05",testLoginpara)
                .then()
                .body("errmsg",equalTo("请填写正确的车辆信息"));
    }
    @Test
    public void testQueryTrafficViolations00106(){
        //违章查询-添加车辆-车机接口
        //验证车类型cartype错误
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo06",testLoginpara)
                .then()
                .body("errmsg",equalTo("请填写正确的车辆信息"));
    }
    @Test
    public void testQueryTrafficViolations00107(){
        //违章查询-添加车辆-车机接口
        //验证车类型cartype错误
        runTools.runGetApi("QTVaddAndQueryCheJiPeccancyInfo","QTVaddAndQueryCheJiPeccancyInfo07",testLoginpara)
                .then()
                .body("errmsg",containsString("暂不支持城市"));
    }
    @Test
    public void testQueryTrafficViolations003(){
        //违章查询-查询并更新车辆违章信息--车机接口
        //验证查询违章信息正确
        runTools.runGetApi("QTVqueryAndUpdateCheJiPeccancyInfo","QTVqueryAndUpdateCheJiPeccancyInfo",testLoginpara)
                .then()
                .spec(runTools.respGetApi("QTVqueryAndUpdateCheJiPeccancyInfo","QTVqueryAndUpdateCheJiPeccancyInfo",testLoginpara));
    }

}

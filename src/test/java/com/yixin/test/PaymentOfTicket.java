package com.yixin.test;
import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.*;

/*
 * 类名:FuelCard
 * 描述：罚单代缴接口测试
 * 作者：秦振霞
 * 创建时间：2018年10月28号
 * 版本：v1.0.0
 *
 */
public class PaymentOfTicket {
    RunTools runTools=new RunTools();
    private static final String testLoginpara="testLoginpara8767";

    @BeforeTest
    public void setUp(){
        //罚单代缴接口地址
        RestAssured.baseURI="http://test-cfw.yxyongche.cn/ticketapi";
    }
    @BeforeMethod
    public void testStart(Method method){
        System.out.println("--------------TestStart:"+method.getName()+"------------");
    }
    @AfterMethod
    public void testAfter(Method method){
        System.out.println("--------------TestAfter:"+method.getName()+"------------");
    }
    @Test
    public void testPaymentOfTicket001(){
        //查询罚单详情
        //验证罚单号在第三方查不到，前四位查不到城市
        //接口返回罚款金额为0，商户ID为-2代表没有可用供应商，城市码为-1代表没有查到相关城市
        runTools.runGetApi("POTdetail","POTdetail",testLoginpara)
                .then()
                .spec(runTools.respGetApi("POTdetail","POTdetail",testLoginpara))
                .body("p2pdata.fkje",equalTo(0))
                .body("p2pdata.ticketMerchantId",equalTo(-2))
                .body("p2pdata.areaId",equalTo(-1));

    }
    @Test
    public void testPaymentOfTicket00101(){
        //查询罚单详情
        //验证罚单编号在第三方查不到，前四位能查到，对应城市没有商户，商户不存在
        //接口返回罚款金额为0，商户ID为-1代表城市能查到时没有可用供应商，城市名称和城市码都有对应的值
        runTools.runGetApi("POTdetail","POTdetail01",testLoginpara)
                .then()
//                .spec(runTools.respGetApi("POTdetail","POTdetail01",phone))
                .body("p2pdata.fkje",equalTo(0))
                .body("p2pdata.ticketMerchantId",equalTo(-1))
                //测试环境没有郑州的商户
                .body("p2pdata.areaId",equalTo(410100))
                .body("p2pdata.areaName",equalTo("郑州"));
    }
    @Test
    public void testPaymentOfTicket00102(){
        //查询罚单详情
        //验证罚单编号在第三方查不到，前四位能查到，对应城市有商户
        //接口返回罚款金额为0，服务费有值，商户ID有值，城市名称和城市编码都有值
        runTools.runGetApi("POTdetail","POTdetail02",testLoginpara)
                .then()
                .spec(runTools.respGetApi("POTdetail","POTdetail02",testLoginpara))
                .body("p2pdata.fkje",equalTo(0))
                //罚单编号是周口的，对应后台管理台商户id是64,服务费是10
                .body("p2pdata.ticketMerchantId",equalTo(64))
                .body("p2pdata.serviceFee",equalTo(10))
                .body("p2pdata.areaId",equalTo(411600))
                .body("p2pdata.areaName",equalTo("周口"));
    }
    @Test
    public void testPaymentOfTicket00103(){
        //查询罚单详情
        //验证罚单编号在第三方能查到，前四位查不到
        //

    }
    @Test
    public void testPaymentOfTicket00104(){
        //查询罚单详情
        //验证罚单编号在第三方能查到，前四位能查到，商户不存在
        //

    }
    @Test
    public void testPaymentOfTicket00105(){
        //查询罚单详情
        //验证罚单编号在第三方能查到，前四位能查到，商户存在
        //

    }



}

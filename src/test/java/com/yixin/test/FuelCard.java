package com.yixin.test;
import com.yixin.dependency.FuelCardDependency;
import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/*
 * 类名:FuelCard
 * 描述：加油卡充值接口测试
 * 作者：秦振霞
 * 创建时间：2018年6月19号
 * 版本：v1.0.0
 *
 */

public class FuelCard {

    FuelCardDependency fuelCardDependency=new FuelCardDependency();
    RunTools runTools =new RunTools();
    private static final String testLoginpara="testLoginpara8767";


    @BeforeTest
    public void setUp(){
        //加油卡充值接口地址
        RestAssured.baseURI="http://test-cfw.yxyongche.cn/cardapi";
    }

    @BeforeMethod
    public void testStart(Method method){
        System.out.println("-------------TestCase:"+method.getName()+"-------------");
    }
    @Test
    public void testFuelCard001(){
        //H5-活动查询，testFuelCard001，
        // 验证点：有两张轮播图，验证参数:1、搜索有2个id,id的值等于后台配置的
        //2、id=34,有链接地址，验证activityUrl不为空，等于http://www.baidu.com
        runTools.runGetApiWithoutParam("FCgetActivityList",testLoginpara)
                .then()
                .body("p2pdata.id",hasItems(32,34))
                .body("p2pdata.find{it.id==34}.activityUrl",equalTo("http://www.baidu.com"))
                .spec(runTools.respGetWithoutParam("FCgetActivityList",testLoginpara));

    }

    @Test
    public void testFuelCard002(){
        //H5-加油卡价格配置，testFuelCard002，
        // 验证点：验证输入参数cardType=1返回中石化加油卡
        // 验证参数:验证参数如下
        runTools.runGetApi("FCpriceList","FCpriceList",testLoginpara)
                .then()
                .body("p2pdata.status[0]",equalTo(1))//判断100元加油卡状态为1，暂停
                .body("p2pdata.status[1]",equalTo(0))//判断200元加油卡状态为0，正常
                .body("p2pdata.productName",hasItems(null,"中石化200元加油卡","中石化500元加油卡","中石化1000元加油卡"))
                //用new BigDecimal强制转换成bigdecimal类型的才能匹配上,100的值比较特殊，开发把暂停的加油卡商品价格直接赋值为100，其他商品价格后面有两位小数
                .body("p2pdata.fixedPrice",hasItems(100,new BigDecimal("200.00"),new BigDecimal("500.00"),new BigDecimal("1000.00")))
                .body("p2pdata.find{it.merchantId==2}.salesPrice",is(new BigDecimal("180.00")))//判断商户2的中石化200元加油卡价格最优是后台输入的价格
                .body("p2pdata.salesPrice.collect{it}.sum()",lessThan(new BigDecimal("1800.00")))//判断售价总和不能大于原价总和
                .body("p2pdata.cardType",hasItems(1,2,3,4))
                .spec(runTools.respGetApi("FCpriceList","FCpriceList",testLoginpara));//判断中石化cardType的值为 1，2，3，4

    }
    @Test
    public void testFuelCard00201(){
//        H5-加油卡价格配置，testFuelCard00201，
//         验证点：验证输入参数cardType=2返回中石油加油卡
//         验证参数:验证参数如下

        runTools.runGetApi("FCpriceList","FCpriceList01",testLoginpara)
                .then()
                .body("p2pdata.status",hasItems(0,0,0,0))//判断加油卡状态为0，正常
                .body("p2pdata.productName",hasItems("中石油100元加油卡","中石油200元加油卡","中石油500元加油卡","中石油1000元加油卡"))
                //用new BigDecimal强制转换成bigdecimal类型的才能匹配上
                .body("p2pdata.fixedPrice",hasItems(new BigDecimal("100.00"),new BigDecimal("200.00"),new BigDecimal("500.00"),new BigDecimal("1000.00")))
                .body("p2pdata.findAll{it.merchantId==1}.salesPrice[2]",is(new BigDecimal("470.00")))//判断商户1的中石油500元加油卡价格最优是后台输入的价格
                .body("p2pdata.salesPrice.collect{it}.sum()",lessThan(new BigDecimal(1800.00)))//判断售价总和不能大于原价总和
                .body("p2pdata.cardType",hasItems(5,6,7,8))
                .spec(runTools.respGetApi("FCpriceList","FCpriceList01",testLoginpara));//判断中石化cardType的值为 1，2，3，4


    }

    @Test
    public void testFuelCard003(){
        //H5-加油卡查询-车机接口，testFuelCard003，
        // 验证点：返回已添加的加油卡，验证参数:1、验证cardType=1，cardNo油卡长度为100011开头的19位数字
        //2、验证cardType=2，cardNo油卡长度为9开头的16位数字
        //3、验证defaultFlag等于Y的只有一个
        runTools.runGetApiWithoutParam("FCqueryOilCardCheJi",testLoginpara)
                .then()
                .body("p2pdata.findAll{it.cardType==1}.cardNo[0]", startsWith("100011"))
                .body("p2pdata.findAll{it.cardType==1}.cardNo[0].length()", equalTo(19))
                .body("p2pdata.findAll{it.cardType==2}.cardNo[0]", startsWith("9"))
                .body("p2pdata.findAll{it.cardType==2}.cardNo[0].length()", equalTo(16))
//                .body("p2pdata.findAll{it.defaultFlag=='Y'}.size()",is(1))
                .body("p2pdata.findAll{it.defaultFlag=='Y'}", hasSize(1))//两种方式验证defaultFlag等于Y的只有一个
                .spec(runTools.respGetWithoutParam("FCqueryOilCardCheJi",testLoginpara));

    }
    @Test
    public void testFuelCard004(){
        //H5-获取用户默认卡片-车机接口，testFuelCard004，
        // 验证点：从H5-加油卡查询-车机接口中查到defaultFlag等于Y的cardNo与本接口中的cardNo比对
        // 验证参数:验证cardNo的值
        runTools.runGetApiWithoutParam("FCgetDefaultCardCheJi",testLoginpara)
                .then()
                .body("p2pdata.cardNo",is(fuelCardDependency.getFCcardNo()))
                .spec(runTools.respGetWithoutParam("FCgetDefaultCardCheJi",testLoginpara));

    }
    @Test(enabled = false)
    public void testFuelCard005(){
        //H5-加油卡验证,testFuelCard005
        //该接口需要调第三方，调第三方一直返回查询无结果，开发魏新明指定该接口默认返回ok
        runTools.runPostApi("FCoilCardValidation","FCoilCardValidation",testLoginpara)
                .then()
                .spec(runTools.respPostApi("FCoilCardValidation","FCoilCardValidation",testLoginpara));
    }
    @Test(enabled = true)
    public void testFuelCard006(){
        //H5-加油卡添加-车机接口,testFuelCard006
        //验证点：添加中石化加油卡
        //验证参数：1、验证"statuscode"=200，
        //也需要验证添加3张后不能再添加的情况
        runTools.runPostApi("FCaddOilCardCheJi","FCaddOilCardCheJi",testLoginpara)
                .then()
                .spec(runTools.respPostApi("FCaddOilCardCheJi","FCaddOilCardCheJi",testLoginpara));
    }
    @Test
    public void testFuelCard007() {
        //H5-更新默认加油卡-车机接口,testFuelCard007
        //验证点：从H5-加油卡查询-车机接口,返回defaultFlag=='N'的cardid来更新默认加油卡，然后查询默认加油卡，获取id，比对更新的是正确的

        //先获取要更新成默认加油卡的cardId
        int cardId= fuelCardDependency.getFCcardId();

        //创建map对象，存储参数值
        Map<String, Object> map = new HashMap<>();
        map.put("cardId", fuelCardDependency.getFCcardId());
        runTools.runPostApiAndDepend("FCupdateDefaultOilCardCheJi", map,testLoginpara)
                .then()
                .spec(runTools.respPostAndDepend("FCupdateDefaultOilCardCheJi", map,testLoginpara));

        //调H5-获取用户默认卡片-车机接口查看id是否是更新的默认卡id
        runTools.runGetApiWithoutParam("FCgetDefaultCardCheJi",testLoginpara)
                .then()
                .body("p2pdata.id",equalTo(cardId));

    }
    @Test
    public void testFuelCard008(){
        //H5-加油卡删除-车机接口,testFuelCard008
        //验证点：删除指定id的加油卡，删除成功后调查询加油卡接口，验证不包含该加油卡id

        //先获取要删除的加油卡的cardId
        int cardId= fuelCardDependency.getFCcardId();

        //创建map对象，存储参数值
        Map<String,Object> map = new HashMap<>();
        map.put("id", fuelCardDependency.getFCcardId());
        runTools.runGetApiAndDepend("FCdeleteOilCardCheJi",map,testLoginpara)
                .then()
                .spec(runTools.respGetAndDepend("FCdeleteOilCardCheJi",map,testLoginpara));

        //调查询加油卡接口，删除的加油卡查询不到
        runTools.runGetApiWithoutParam("FCqueryOilCardCheJi",testLoginpara)
                .then()
                .body("p2pdata.id",not(cardId));

    }

}

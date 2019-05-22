package com.yixin.test;
import com.yixin.dependency.CarWashDependency;
import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.*;

/*
 * 类名:CarWash
 * 描述：洗车接口测试
 * 作者：秦振霞
 * 创建时间：2018年7月19号
 * 版本：v1.0.0
 *
 */
public class CarWash {
    RunTools runTools=new RunTools();
    CarWashDependency carWashDependency =new CarWashDependency();
    private static final  String testLoginpara="testLoginpara8767";

    @BeforeTest
    public void setUp(){
        //洗车接口地址
        RestAssured.baseURI="http://test-cfw.yxyongche.cn/cmapi";
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
    public void testQZXCarWashAndUpkeep001(){
        //洗车活动图接口
        //验证点：返回正确
        runTools.runGetApi("QZXCWAUgetActivityList","QZXCWAUgetActivityList",testLoginpara)
                .then()
                .spec(runTools.respGetApi("QZXCWAUgetActivityList","QZXCWAUgetActivityList",testLoginpara));

    }

    @Test
    public void testQZXCarWashAndUpkeep002(){
        //洗车商户列表
        //验证点：1、服务大厅首页判断一页显示10条记录
        //2、判断从第3位开始距离由近及远显示
        runTools.runGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList",testLoginpara)
                .then()
                .body("p2pdata.data.id.size()",equalTo(10)) //判断一页显示10条记录
                .spec(runTools.respGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList",testLoginpara));


        //创建list序列
        List CarWashMerchantdistance=new ArrayList();
        //往list中添加distance对象列表
        CarWashMerchantdistance.addAll(
                runTools.runGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList",testLoginpara)
                        .body()
                        .path("p2pdata.data.distance"));
        //定义一个for循环，从第3位开始比较距离，最后一位不比较
        for(int i=2;i<CarWashMerchantdistance.size()-1;i++){
            int j=i+1;
            //比较距离，由近及远显示
            if((Integer)CarWashMerchantdistance.get(i) <= (Integer)CarWashMerchantdistance.get(j)){
                System.out.println("比对成功");
            }else {
                System.out.println("比对失败");
            }
        }

    }
    @Test
    public void testQZXCarWashAndUpkeep00201(){
        //洗车商户列表
        //验证点：1、从服务大厅首页点击查看全部洗车列表，判断一页显示20条记录
        //2、从第3位之后距离由近及远显示
        runTools.runGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList01",testLoginpara)
                .then()
                .body("p2pdata.data.id.size()",equalTo(20))
                .spec(runTools.respGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList01",testLoginpara));

        //创建list序列
        List CarWashMerchantdistance=new ArrayList();
        //往list中添加distance对象列表
        CarWashMerchantdistance.addAll(
                runTools.runGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList01",testLoginpara)
                        .body()
                        .path("p2pdata.data.distance"));
        //定义一个for循环，从第3位开始比较距离，最后一位不比较
        for(int i=2;i<CarWashMerchantdistance.size()-1;i++){
            int j=i+1;
            //比较距离，由近及远显示
            if((Integer)CarWashMerchantdistance.get(i) <= (Integer)CarWashMerchantdistance.get(j)){
                System.out.println("比对成功");
            }else {
                System.out.println("比对失败");
            }
        }


    }

    @Test
    public void testQZXCarWashAndUpkeep003(){
        //洗车门店详情列表
        //验证点：1、商品id与查询的id相同
        //2、检查有4个sku，验证sku价格和内容

        //获取返回的商户id
        int merchantid= carWashDependency.getmerchantid();
        //创建map对象，存储依赖数据
        Map<String,Object> map=new HashMap<>();
        map.put("merchantId", carWashDependency.getmerchantid());

        runTools.runGetApiAndDepend("QZXCWAUgetCarWashDetail",map,testLoginpara)
                .then()
                .body("p2pdata.merchant.id",equalTo(merchantid))
                .body("p2pdata.goodsDetail.skuInfo.price",hasItems(new BigDecimal("0.15"),new BigDecimal("0.54"),new BigDecimal("40.0"),new BigDecimal("51.0")))
                .body("p2pdata.goodsDetail.skuInfo.standards",hasItems("普通洗车-适合5座及以下","普通洗车-适合7座及以下","精细--适合5座及以下","精细-适合7座及以下"))
                .spec(runTools.respGetAndDepend("QZXCWAUgetCarWashDetail",map,testLoginpara));
    }
    /*
    testQZXCarWashAndUpkeep004主要验证
     */
    @Test
    public void testQZXCarWashAndUpkeep004(){
        //搜索洗车商户列表
        //验证点：1、验证shopName或shopAddr包含搜索的内容

        //定义一个变量存储搜索的内容
        String content="秦振霞";


        //判断接口返回200正确
        runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList",testLoginpara)
                .then()
                .spec(runTools.respGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList",testLoginpara));

        //判断shopName中有包含查询的内容
        //定义一个list用来存储搜索结果中所有门店名称
        List<String> Merchantshopname=new ArrayList<String>();
        Merchantshopname.addAll(
                runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList",testLoginpara)
        .body().path("p2pdata.search.data.shopName"));

        //判断shopAddr中有包含查询的内容
        //定义一个list用来存储搜索结果中所有门店地址
        List<String> MerchantshopAddr=new ArrayList<String>();
        MerchantshopAddr.addAll(
                runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList",testLoginpara)
                .body().path("p2pdata.search.data.shopAddr"));

        for(int i=0;i<Merchantshopname.size();i++){
            if(Merchantshopname.get(i).indexOf(content)!= -1 || MerchantshopAddr.get(i).indexOf(content)!=-1){
                System.out.println("结果正确");
            }else {
                System.out.println("结果不正确");
            }
        }

    }

    @Test
    public void testQZXCarWashAndUpkeep00401(){
        //搜索洗车商户列表
        //验证点：1、搜索少于3条结果，有5条推荐数据

        runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList01",testLoginpara)
                .then()
                .body("p2pdata.search.data.id.size()",lessThan(3))
                .body("p2pdata.recommend.id",hasSize(5))
                .spec(runTools.respGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList01",testLoginpara));
    }

    @Test
    public void testQZXCarWashAndUpkeep00402(){
        //搜索洗车商户列表
        //验证点：1、搜索无结果，有10条推荐数据

        runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList02",testLoginpara)
                .then()
                .body("p2pdata.recommend.id",hasSize(10))
                .spec(runTools.respGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList02",testLoginpara));
    }

    @Test
    public void testQZXCarWashAndUpkeep00403(){
        //搜索洗车商户列表
        //验证点：1、根据shopAddr搜索，验证shopAddr包含搜索的内容

        runTools.runGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList03",testLoginpara)
                .then()
                .body("p2pdata.search.data.shopAddr[0]",containsString("知春路"))
                .spec(runTools.respGetApi("QZXCWAUsearchMerchantList","QZXCWAUsearchMerchantList03",testLoginpara));
    }

    @Test
    public void testQZXCarWashAndUpkeep005(){
        //获取搜索记录列表
        //验证点：1、搜索类型：0洗车
        runTools.runGetApi("QZXCWAUgetSearchHistory","QZXCWAUgetSearchHistory",testLoginpara)
                .then()
                .spec(runTools.respGetApi("QZXCWAUgetSearchHistory","QZXCWAUgetSearchHistory",testLoginpara));
    }
    @Test
    public void testQZXCarWashAndUpkeep00501(){
        //获取搜索记录列表
        //验证点：1、搜索类型：1保养
        runTools.runGetApi("QZXCWAUgetSearchHistory","QZXCWAUgetSearchHistory01",testLoginpara)
                .then()
                .spec(runTools.respGetApi("QZXCWAUgetSearchHistory","QZXCWAUgetSearchHistory01",testLoginpara));
    }

}

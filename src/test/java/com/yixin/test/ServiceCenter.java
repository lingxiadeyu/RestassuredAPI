//package com.yixin.test;
//
//import com.yixin.dependency.CarMaintenanceDenpendency;
//import com.yixin.util.RunTools;
//
//import io.restassured.RestAssured;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.RestAssured.post;
//import static org.hamcrest.Matchers.*;
//
//
//public class ServiceCenter {
//    CarMaintenanceDenpendency CWAUDataDependency =new CarMaintenanceDenpendency();
//    RunTools runTools =new RunTools();
//    //    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @BeforeTest
//    public void setUp(){
//        //服务大厅
//        RestAssured.baseURI="http://test-cfw.yxyongche.cn/shapi";
//    }
//    @BeforeMethod
//    public void testStart(Method method){
//        System.out.println("-------------TestCase:"+method.getName()+"-------------");
//    }
//    @Test
//    public void testServiceCenter001(){
//        //获取活动列表信息+/activity/getActivityList
//        //验证点：活动list有内容;验证活动图片的后缀名为.png
//        runTools.runGetApiWithoutParam("SCGetActivityList","account3790").then().spec(runTools.respGetWithoutParam("SCGetActivityList","account3790"))
//                .body("p2pdata.size()",greaterThan(0))
//                .body("p2pdata.activityImg[0]",endsWith(".png"));
//    }
//    @Test
//    public void testServiceCenter002(){
//        //获取车辆列表信息+/car/getCarList
//        //验证点：车辆列表数大于零；
//        runTools.runGetApiWithoutParam("SCGetCarList","account3790").then().spec(runTools.respGetWithoutParam("SCGetCarList","account3790"))
//                .body("p2pdata.size()",greaterThan(0));
//    }
//    @Test
//    public void testServiceCenter003(){
//        //获取所有品牌信息，通过字母排序+/car/brand/getAllInfoGroupByLetter
//        //验证点：列表个数为22，即车品牌首字母个数为22
//        runTools.runGetApiWithoutParam("SCGetAllInfoGroupByLetter","account3790").then().spec(runTools.respGetWithoutParam("SCGetAllInfoGroupByLetter","account3790"))
//                .body("p2pdata.size()",is(22));
//    }
//    @Test
//    public void testServiceCenter004(){
//        //所有品牌信息接口中获取brandId,然后讲brandId传入通过品牌brandId询⻋型信息+/car/brand/getModelFirmGroupByBrand
//        //brandId=9
//        //验证品牌id=9时，firmId及firmName是否一致;厂商车型总数为39
//        Map<String,Object> map=new HashMap<>();
//        map.put("brandId", CWAUDataDependency.getBrandId());
//        runTools.runGetApiAndDepend("SCGetModelFirmGroupByBrand",map,"account3790").then().spec(runTools.respGetAndDepend("SCGetModelFirmGroupByBrand",map,"account3790"))
//                .body("p2pdata.firmId",hasItems(10000,20004,20219,20338,20347))
//                .body("p2pdata.firmName",hasItems("一汽-大众奥迪","奥迪","Audi Sport","奥迪新能源","一汽-大众奥迪新能源"))
//                .body("p2pdata.modelList.collect{it.size()}.sum()",is(39));
//    }
//    @Test
//    public void testServiceCenter005(){
//        //车型-车款-通过⻋型ID查询⻋款信息通过年款聚类+/car/unit/getUnitYearGroupByModel
//        //modelId=3999
//        //验证点：年份聚合数量为5；年份车型总数为56；具体年份有：2018~2014年
//        Map<String,Object> map=new HashMap<>();
//        map.put("modelId", CWAUDataDependency.getModelId());
//        runTools.runGetApiAndDepend("SCGetUnitYearGroupByModel",map,"account3790")
//                .then().spec(runTools.respGetAndDepend("SCGetUnitYearGroupByModel",map,"account3790"))
//                .body("p2pdata.size()",is(5))
//                .body("p2pdata.unitInfoList.collect{it.size()}.sum()",is(56))
//                .body("p2pdata.unitYear",hasItems("2018","2017","2016","2015","2014"));
//    }
//}

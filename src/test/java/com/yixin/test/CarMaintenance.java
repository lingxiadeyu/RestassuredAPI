//package com.yixin.test;
//import com.yixin.dependency.CarMaintenanceDenpendency;
//import com.yixin.util.RunTools;
//import com.yixin.util.Account;
//import com.yixin.util.ReadConfigFiles;
//import io.restassured.RestAssured;
//import org.testng.Reporter;
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
//public class CarMaintenance {
//    CarMaintenanceDenpendency CarMaintenanceDenpendency =new CarMaintenanceDenpendency();
//    RunTools runTools =new RunTools();
//    ReadConfigFiles readConfigFiles=new ReadConfigFiles();
//    Account account=new Account();
//    @BeforeTest
//    public void setUp(){
//        //洗车&保养
//        RestAssured.baseURI="http://test-cfw.yxyongche.cn/cmapi";
//    }
//    @BeforeMethod
//    public void testStart(Method method){
//        System.out.println("-------------TestCase:"+method.getName()+"-------------");
//    }
//    @Test
//    public void testCarWashAndUpkeep001(){
//        //获取洗车商户列表+/merchant/getMerchantList
//        //验证洗车商户列表不为空
//        Reporter.log("获取洗车商户列表");
//        runTools.runGetApi("WUGetMerchantList","WUGetMerchantList","userId3790")
//                .then().spec(runTools.respGetApi("WUGetMerchantList","WUGetMerchantList","account3790"))
//                .body("p2pdata.size",greaterThan(0))  //列表size>0
//                .body("p2pdata.data[0].shopLogo",endsWith(".png")) //图片连接结尾为.png
//                .log().ifError();
//    }
////    @Test
////    public void testCarWashAndUpkeep002(){
////        //活动接口+/activity/getActivityList #businessLine 1：保养  showPosition : 0 #0顶部
////        Reporter.log("活动接口-洗车-顶部");
////        runTools.runGetApi("WUGetActivityList","WUGetActivityList","account3790")
////                .then().spec(runTools.respGetApi("WUGetActivityList","WUGetActivityList","account3790"))
////                .body("p2pdata[0].showPosition",equalTo(0))//位置是否为顶部
////                .body("p2pdata[0].businessLineId",equalTo(1))//保养活动
////                .body("p2pdata[0].activityImg",endsWith(".png"));//验证图片
////    }
////    @Test
////    public void testCarWashAndUpkeep002001(){
////        //活动接口+/activity/getActivityList #businessLine 1:1：保养  showPosition : 0 #0顶部
////        Reporter.log("活动接口-保养-顶部");
////        runTools.runGetApi("WUGetActivityList","WUGetActivityList001","account3790")
////                .then().spec(runTools.respGetApi("WUGetActivityList","WUGetActivityList001","account3790"))
////                .body("p2pdata[0].showPosition",equalTo(0))//位置是否为顶部
////                .body("p2pdata[0].businessLineId",equalTo(1))//保养活动
////                .body("p2pdata[0].channel",equalTo(5))//蘑菇养车公众号渠道
////                .body("p2pdata[0].activityImg",endsWith(".png"));//验证图片
////    }
////    @Test
////    public void testCarWashAndUpkeep003(){
////        //搜索洗车商户列表+/merchant/searchMerchantList
////        Reporter.log("搜索洗车商户列表");
////        runTools.runGetApi("WUSearchMerchantList","WUSearchMerchantList","account3790")
////                .then().spec(runTools.respGetApi("WUSearchMerchantList","WUSearchMerchantList","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep004(){
////        //组合商品-列表+/assemble/recommendList
////        Reporter.log("组合商品-列表");
////        runTools.runGetApiWithoutParam("WURecommendList","account3790")
////                .then().spec(runTools.respGetWithoutParam("WURecommendList","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep005(){
////        //组合商品-单品详情+/goods/detail
////        Reporter.log("组合商品-单品详情");
////        Map<String,Object> map=new HashMap<>();
////        map.put("id", CarMaintenanceDenpendency.getGoodsId());
////        runTools.runGetApiAndDepend("WUGoodsDetail",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUGoodsDetail",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep006(){
////        //组合商品-库存减（微服务）+/stock/reduceStock------??????????????????????????????????
////        Reporter.log("组合商品-库存减（微服务）");
////        Map<String,Object> map=new HashMap<>();
////        map.put("merchantId", CarMaintenanceDenpendency.getMerchantId());
//////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        runTools.runPostApiAndDepend("WUReduceStock",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUReduceStock",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep007(){
////        //组合商品-库存还原（微服务）+/stock/revertStock----???????????????????????????????????
////        Reporter.log("组合商品-库存还原（微服务）");
////        Map<String,Object> map=new HashMap<>();
////        map.put("merchantId", CarMaintenanceDenpendency.getMerchantId());
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        runTools.runPostApiAndDepend("WURevertStock",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WURevertStock",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep008(){
////        // 组合商品-查询可替换商品列表+/goods/getAlternativeList
////        Reporter.log("组合商品-查询可替换商品列表");
////        Map<String,Object> map=new HashMap<>();
////        map.put("goodsId", CarMaintenanceDenpendency.getGoodsId());
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        map.put("selectedId", CarMaintenanceDenpendency.getGoodsId());
////        runTools.runGetApiAndDepend("WUGetAlternativeList",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUGetAlternativeList",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep009(){
////        // 组合商品-获取商铺列表+/merchant/getAssembleManagerList mapType : 1    #地图类型 1-百度地图 2-腾讯地图
////        Reporter.log("组合商品-获取商铺列表-百度地图");
////        Map<String,Object> map=new HashMap<>();
////        map.put("merchantId", CarMaintenanceDenpendency.getMerchantId());
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        runTools.runGetApiAndDependMore("WUGetAssembleManagerList",map,"WUGetAssembleManagerList","account3790")
////                .then().spec(runTools.respGetAndDependMore("WUGetAssembleManagerList",map,"WUGetAssembleManagerList","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep009001(){
////        // 组合商品-获取商铺列表+/merchant/getAssembleManagerList mapType : 2    #地图类型 1-百度地图 2-腾讯地图
////        Reporter.log("组合商品-获取商铺列表-腾讯地图");
////        Map<String,Object> map=new HashMap<>();
////        map.put("merchantId", CarMaintenanceDenpendency.getMerchantId());
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        runTools.runGetApiAndDependMore("WUGetAssembleManagerList",map,"WUGetAssembleManagerList001","account3790")
////                .then().spec(runTools.respGetAndDependMore("WUGetAssembleManagerList",map,"WUGetAssembleManagerList001","account3790"))
////                .body("p2pdata.search.data[0].id",equalTo(CarMaintenanceDenpendency.getDefaltMerchant()));
////    }
////    @Test
////    public void testCarWashAndUpkeep010(){
////        // 组合商品-获取默认商铺信息+ /assemble/getDefaultMerchant mapType : 1    #地图类型 1-百度地图 2-腾讯地图
////        Reporter.log("组合商品-获取默认商铺信息");
////        Map<String,Object> map=new HashMap<>();
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        map.put("userId",account.getUserId("18610413790"));
////        runTools.runGetApiAndDependMore("WUGetDefaultMerchant",map,"WUGetDefaultMerchant","account3790")
////                .then().spec(runTools.respGetAndDependMore("WUGetDefaultMerchant",map,"WUGetDefaultMerchant","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep010001(){
////        // 组合商品-获取默认商铺信息+ /assemble/getDefaultMerchant mapType : 2    #地图类型 1-百度地图 2-腾讯地图
////        Reporter.log("组合商品-获取默认商铺信息");
////        Map<String,Object> map=new HashMap<>();
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        map.put("userId",account.getUserId("18610413790"));
////        runTools.runGetApiAndDependMore("WUGetDefaultMerchant",map,"WUGetDefaultMerchant01","account3790")
////                .then().spec(runTools.respGetAndDependMore("WUGetDefaultMerchant",map,"WUGetDefaultMerchant01","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep011(){
////        //组合商品-详情+/assemble/detail
////        Reporter.log("组合商品-详情");
////        Map<String,Object> map=new HashMap<>();
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        runTools.runGetApiAndDepend("WUAssembleDetail",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUAssembleDetail",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep012(){
////        //获取搜索记录列表+/searchHistory/getSearchHistory  #0洗车1保养
////        Reporter.log("获取搜索记录列表-洗车");
////        runTools.runGetApi("WUGetSearchHistory","WUGetSearchHistory","account3790")
////                .then().spec(runTools.respGetApi("WUGetSearchHistory","WUGetSearchHistory","account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep012001(){
////        //获取搜索记录列表+/searchHistory/getSearchHistory  #0洗车1保养
////        Reporter.log("获取搜索记录列表-保养");
////        runTools.runGetApi("WUGetSearchHistory","WUGetSearchHistory001","account3790")
////                .then().spec(runTools.respGetApi("WUGetSearchHistory","WUGetSearchHistory001","account3790"));
////    }
////
////    @Test
////    public void testCarWashAndUpkeep013(){
////        //获取洗车商户详情+ /merchant/getCarWashDetail
////        Reporter.log("获取洗车商户详情");
////        Map<String,Object> map=new HashMap<>();
////        map.put("merchantId", CarMaintenanceDenpendency.getMerchantId());
////        runTools.runGetApiAndDepend("WUgetCarWashDetail",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUgetCarWashDetail",map,"account3790"));
////    }
////    @Test
////    public void testCarWashAndUpkeep014(){
////        //可替换商品列表+ /goods/getAlternativeBySortList
////        Reporter.log("可替换商品列表");
////        Map<String,Object> map=new HashMap<>();
////        map.put("assembleId", CarMaintenanceDenpendency.getAssembleId());
////        map.put("selectedId", CarMaintenanceDenpendency.getrelaceFlag());
////        runTools.runGetApiAndDepend("WUgetCarWashDetail",map,"account3790")
////                .then().spec(runTools.respGetAndDepend("WUgetCarWashDetail",map,"account3790"));
////    }
//}

//package com.yixin.dependency;
//
//import com.yixin.util.Account;
//import com.yixin.util.ReadConfigFiles;
//import com.yixin.util.RunTools;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//
//public class CarMaintenanceDenpendency {
//    RunTools runTools = new RunTools();
//    ReadConfigFiles readConfigFiles = new ReadConfigFiles();
//    Account account=new Account();
//
//    //-----------------------服务大厅------------------------
////    获取车辆品牌Id
//    public int getBrandId() {
//        //所有品牌信息接口中获取brandId,然后讲brandId传入通过品牌brandId询⻋型信息
//        int brandId = runTools.runGetApiWithoutParam("SCGetAllInfoGroupByLetter","18610413790").path("p2pdata[0].carBrandInfoList[0].brandId");
//        System.out.println("brandId=" + brandId);
//        return brandId;
//    }
//
//    //    获取车型Id
//    public int getModelId() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("brandId", getBrandId());
//        int modelId = runTools.runGetApiAndDepend("SCGetModelFirmGroupByBrand", map,"account3790").body().path("p2pdata[0].modelList[0].modelId");
//        System.out.println("modelId=" + modelId);
//        return modelId;
//    }
//    //-----------------------洗车保养------------------------
////获取单品ID
//    public int getGoodsId() {
//        int goodsId = runTools.runGetApiWithoutParam("WURecommendList","account3790").body().path("p2pdata[0].data[0].data.goodsList[0].id");
//        System.out.println("goodsId=" + goodsId);
//        return goodsId;
//    }
//
//    //获取商户ID
//    public int getMerchantId() {
//        int merchantId = runTools.runGetApi("WUGetMerchantList", "WUGetMerchantList","account3790").body().path("p2pdata.data[0].id");
//        System.out.println("merchantId=" + merchantId);
//        return merchantId;
//    }
//
//    //获取组合ID
//    public int getAssembleId() {
//        int assembleId = runTools.runGetApiWithoutParam("WURecommendList","account3790").body().path("p2pdata[0].data[0].data.id");
//        System.out.println("assembleId=" + assembleId);
//        return assembleId;
//    }
//    //获取默认商户id
//    public int getDefaltMerchant(){
//        Map<String,Object> map=new HashMap<>();
//        map.put("assembleId", getAssembleId());
//        map.put("userId",account.getUserId("18610413790"));
//        int defaltMerchant=runTools.runGetApiAndDependMore("WUGetDefaultMerchant",map,"WUGetDefaultMerchant","account3790")
//                .path("p2pdata.id");
//        return defaltMerchant;
//    }
//    public int getrelaceFlag() {
//        List a =runTools.runGetApiWithoutParam("WURecommendList","account3790")
//                .path("p2pdata[0].data[0].data.goodsList.findAll{it.relaceFlag==1}.id");
//        int relaceFlag= (int) a.get(0);
//        System.out.println("relaceFlag="+relaceFlag);
//        return relaceFlag;
//
//    }
//}

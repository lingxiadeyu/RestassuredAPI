package com.yixin.dependency;

import com.yixin.util.RunTools;

/*
 * 类名:CarWashDependency
 * 描述：洗车接口测试数据依赖类
 * 作者：秦振霞
 * 创建时间：2018年7月19号
 * 版本：v1.0.0
 *
 */
public class CarWashDependency {
    RunTools runTools=new RunTools();
    String testLoginpara="testLoginpara8767";

    //获取商户id，供查询洗车门店详情列表使用
    public int getmerchantid(){
        int merchantid =runTools.runGetApi("QZXCWAUgetMerchantList","QZXCWAUgetMerchantList",testLoginpara)
                .body()
                .path("p2pdata.data.id[0]");
        return merchantid;
    }

    //获取单品id
    public int getdetailid(){
        int id=runTools.runGetApiWithoutParam("QZXCWAUrecommendList",testLoginpara)
                .body()
                .path("p2pdata[0].data[0].data.goodsList[0].id");
        return id;

    }


}

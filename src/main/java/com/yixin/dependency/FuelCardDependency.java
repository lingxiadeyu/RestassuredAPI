package com.yixin.dependency;

import com.yixin.util.Account;
import com.yixin.util.ReadConfigFiles;
import com.yixin.util.RunTools;
/*
 * 类名:FuelCardDependency
 * 描述：加油卡充值接口数据依赖类
 * 作者：秦振霞
 * 创建时间：2018年7月19号
 * 版本：v1.0.0
 *
 */

public class FuelCardDependency {
    Account account =new Account();
    ReadConfigFiles readConfigFiles=new ReadConfigFiles();
    RunTools runTools=new RunTools();
    String testLoginpara="testLoginpara8767";


    /*
     * 方法名:getFCcardNo
     * 描述：H5-加油卡查询-车机接口,返回defaultFlag=='Y'的cardNo，供H5-获取用户默认卡片-车机接口比对展示的cardNo是否正确
     * 作者：秦振霞
     * 创建时间：2018年6月22号
     * 版本：v1.0.0
     *
     */
    public String getFCcardNo(){
        String cardNo=runTools.runGetApiWithoutParam("FCqueryOilCardCheJi",testLoginpara)
                .body()
                .path("p2pdata.find{it.defaultFlag=='Y'}.cardNo");
        return cardNo;
    }
    /*
     * 方法名:getFCcardId
     * 描述：H5-加油卡查询-车机接口,返回defaultFlag=='N'的cardid，供H5-更新默认加油卡-车机接口入参使用
     * 作者：秦振霞
     * 创建时间：2018年6月25号
     * 版本：v1.0.0
     *
     */
    public Integer getFCcardId(){
        Integer cardId=runTools.runGetApiWithoutParam("FCqueryOilCardCheJi",testLoginpara)
                .body()
                .path("p2pdata.find{it.defaultFlag=='N'}.id");
        System.out.println(cardId);
        return cardId;
    }

}

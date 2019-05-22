package com.yixin.PrivilegeRefuelMerchantBusiness;
import com.yixin.util.ReadConfigFiles;

import static io.restassured.RestAssured.given;
/*
 * 类名:PRMBDataDependency
 * 描述：特权加油商户APP端数据依赖
 * 作者：秦振霞
 * 创建时间：2018年9月7号
 * 版本：v1.0.0
 *
 */
public class PRMBDataDependency {
    PRMBRunTools prmbRunTools=new PRMBRunTools();
    ReadConfigFiles readConfigFiles=new ReadConfigFiles();

    //获取用户id,用来查看数据库中数据
    public int getuserid(){
        int userid=given()
                .header("Content-Type","application/json")
                .body(readConfigFiles.findAccount("account8767"))
                .when()
                .post(readConfigFiles.findUrl("PRMloginByPassword"))
                .body()
                .path("p2pdata.id");
        return userid;
    }

    //获取商户id
    public int getMerchantid(){
        int Merchantid=prmbRunTools.runGetApiWithoutParam("PRMconfig","account8767")
                .body()
                .path("p2pdata.merchantsId");
        System.out.println(Merchantid);

        return Merchantid;
    }







}

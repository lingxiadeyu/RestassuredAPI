package com.yixin.util;


import com.yixin.util.Account;
import com.yixin.util.ReadConfigFiles;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class RunTools {
    ReadConfigFiles readConfigFiles =new ReadConfigFiles();
    Account account = new Account();

    //    public Response runGivenApi(String Url,String param){
//        Response response = given()
//                .header("token",account.getTokenString())
//                .param(readConfigFiles.findPara(param))
//                .when()
//                .post(readConfigFiles.findUrl(Url));
//        return response;
//    }
//    public JsonNode JsonCode(String param, String Url){
//        return readConfigFiles.readJsonInfo(runGivenApi(param,Url).body().asString());
//    }
//    public JsonNode JsonSchemeCode(String jsonFile){
//        return readConfigFiles.readSchemaInfo(jsonFile);
//    }

    public Response runGetApi(String Url,String param,String testLoginpara){
        Response response = given()
                .header("token",account.getTokenAPP(testLoginpara))
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .params(readConfigFiles.findGetPara(param))
                .when()
                .get(readConfigFiles.findUrl(Url));
        return response;
    }
    //传参且数据依赖、get方法封装
    public Response runGetApiAndDepend(String Url,Map param,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .params(param)
                .when()
                .get(readConfigFiles.findUrl(Url));
        return response;
    }
    //传参且数据依赖且从param中读取、get方法封装
    public Response runGetApiAndDependMore(String Url,Map param,String param01,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .params(param)
                .params(readConfigFiles.findGetPara(param01))
                .when()
                .get(readConfigFiles.findUrl(Url));
        return response;
    }
    //传参、post方法封装
    public Response runPostApi(String Url,String param,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .contentType("application/json;charset=UTF-8")
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .body(readConfigFiles.findPostPara(param))
//                .params(readConfigFiles.findGetPara(param))
                .when()
                .post(readConfigFiles.findUrl(Url));

        return response;
    }
    //传参且数据依赖、post方法封装
    public Response runPostApiAndDepend(String Url,Map param,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .contentType("application/json;charset=UTF-8")
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .body(param)
                .when()
                .post(readConfigFiles.findUrl(Url));
        return response;
    }
    //传参且数据依赖且需从param中读取、post方法封装
    public Response runPostApiAndDependMore(String Url,String param01,Map param,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .contentType("application/json;charset=UTF-8")
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .body(param)
                .body(readConfigFiles.findPostPara(param01))
                .when()
                .post(readConfigFiles.findUrl(Url));
        return response;
    }
    //无参数、get方法封装
    public Response runGetApiWithoutParam(String Url,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when()
                .get(readConfigFiles.findUrl(Url));
        return response;
    }
    //无参数、post方法封装
    public Response runPostApiWithoutParam(String Url,String testLoginpara){
        Response response = given()
                .header("token", account.getTokenAPP(testLoginpara))
                .contentType("application/json;charset=UTF-8")
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when()
                .post(readConfigFiles.findUrl(Url));
        return response;
    }

    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 get方法 无参
    public ResponseSpecification respGetWithoutParam(String url,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runGetApiWithoutParam(url,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;

    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 get 传参
    public ResponseSpecification respGetApi(String url,String param,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runGetApi(url,param,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;

    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 post 传参
    public ResponseSpecification respPostApi(String url,String param,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runPostApi(url,param,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;

    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 post 无参
    public ResponseSpecification respPostWithoutParam(String url,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runPostApiWithoutParam(url,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;
    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 get  传参且数据依赖
    public ResponseSpecification respGetAndDepend(String url,Map param,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runGetApiAndDepend(url,param,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;
    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 post  传参且数据依赖
    public ResponseSpecification respPostAndDepend(String url,Map param,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runPostApiAndDepend(url,param,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;
    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 post  传参且数据依赖且从param中读取
    public ResponseSpecification respPostAndDependMore(String url,String param01,Map param,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runPostApiAndDependMore(url,param01,param,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;
    }
    //对每个方法的响应体进行ResponseCode=200及响应体字段errcode=0进行验证 get  传参且数据依赖且从param中读取
    public ResponseSpecification respGetAndDependMore(String url,Map param,String param01,String testLoginpara){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("errcode",equalTo(0));
        String errmsg= runGetApiAndDependMore(url,param,param01,testLoginpara).body().path("errmsg");
        System.out.println("errmsg:"+errmsg);
        Reporter.log("\r errmsg:"+errmsg);
        ResponseSpecification responseSpecification=builder.build();
        return responseSpecification;
    }
}

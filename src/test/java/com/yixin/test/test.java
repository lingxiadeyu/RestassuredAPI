package com.yixin.test;


import com.yixin.util.Account;
import org.testng.annotations.Test;
import com.yixin.util.RunTools;
import com.yixin.util.ReadConfigFiles;

import java.sql.*;

import static io.restassured.RestAssured.given;

public class test {
    Account account =new Account();
    RunTools runTools=new RunTools();
    ReadConfigFiles readConfigFiles=new ReadConfigFiles();

    //    @Test
//    public void test001(){
//        int brandId=given()
//                .header("token", account.getTokenWithLogin("15911020245","123456"))
//                .when()
//                .get("http://test-cfw.yxyongche.cn/shapi/car/brand/getAllInfoGroupByLetter")
//                .path("p2pdata[0].carBrandInfoList[0].brandId");
//
//                 given()
//                .header("token", account.getTokenWithLogin("15911020245","123456"))
//                .param("brandId",brandId)
//                .when()
//                .get("http://test-cfw.yxyongche.cn/shapi/car/brand/getModelFirmGroupByBrand")
//                .then()
//                .statusCode(200);
//    }
//    @Test
//    public void test002(){
////        List<Object>values= new ArrayList<Object>();
////        values.add(readConfigFiles.findPara("login"));
////        values.add("phone");
////        values.add("18610413790");
//        Map<String,Object> values =new HashMap<>();
////        values.put("phone","18610413790");
//        values.put("passwd","wodemima11");
//
//
////       runTools.runGetApi("testLoginUrl","login",values).getBody().prettyPrint();
//        System.out.println(readConfigFiles.findPara("login"));
//        System.out.println(values);
//       given()
//               .params(readConfigFiles.findPara("login"),values)
//               .post(readConfigFiles.findUrl("testLoginUrl"))
//               .getBody().prettyPrint();
//    }
//    @Test
//    public void test003(){
//        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
//                .param("cardType",1)
//                .get("http://test-cfw.yxyongche.cn/cardapi/oilCard/priceList");
//
//    }
//    @Test
//    public void testFuelCard003(){
//        //H5-加油卡查询-车机接口，testFuelCard002，
//        // 1、验证点：返回已添加的加油卡，验证参数:1、验证cardType=1，cardNo油卡长度为100011开头的19位数字
//        //2、验证cardType=2，cardNo油卡长度为9开头的16位数字
//        //3、验证defaultFlag等于Y的只有一个
//        Response response=
//                runTools.runGetApiWithoutParam("FCqueryOilCardCheJi");
////                .then()
////                .body("p2pdata.findAll{it.cardType==1}.cardNo",hasItem("1000111236547896325"))
////                .body("p2pdata.cardNo[0].length()",equalTo(19))
////                .body("p2pdata.findAll{it.cardType==2}.cardNo",hasItem("9365214785236985"))
////                .body("p2pdata.cardNo[1].length()",equalTo(16))
////                .body("p2pdata.findAll{it.defaultFlag=='Y'}.size()",is(1))//模糊查询
////                  .body("p2pdata.findAll{it.cardType==1}.cardNo",startsWith("100011"));
//        String b= response.getBody().path("p2pdata.findAll{it.cardType==1}.cardNo[0]");
//        String b1= response.getBody().path("p2pdata.findAll{it.cardType==1}.cardNo[1]");
//
//        System.out.println(b);
//        System.out.println(b1);
//    }
//    @Test
//    public void testFuelCard004(){
        //H5-加油卡价格配置，testFuelCard002，
        // 验证点：验证输入参数cardType=1返回中石化加油卡
        // 验证参数:验证参数如下
//        BigDecimal bOneH=new BigDecimal("100.00");
//        BigDecimal bTwoH=new BigDecimal("200.00");
//        BigDecimal bFiveH=new BigDecimal("500.00");
//        BigDecimal bTenH=new BigDecimal("1000.00");



        //需要强制转换返回的金额类型都为bigdecimal类型
//                given()
//                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
//                .header("token",account.getTokenWithLogin("18610413790","wodemima11"))
//                .when()
//                .get(readConfigFiles.findUrl("FCpriceList01"))
//                .then()
//                .body("p2pdata.findAll{it.status==0}.fixedPrice"
//                        , hasItems(new BigDecimal("100.00"),new BigDecimal("200.00"),new BigDecimal("500.00")
//                ,new BigDecimal("1000.00")));
//        Response response=
//        given()
//                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
//                .header("token", account.getTokenWithLogin("15911020245","123456"))
//                .when()
//                .get(readConfigFiles.findUrl("FCpriceList"))
//                .then()
//                .body("p2pdata.fixedPrice"
//                        , hasItems(new BigDecimal(100),new BigDecimal("200.00"),new BigDecimal("500.00"),
//                                new BigDecimal("1000.00")));
//        String b=response.getBody().path("p2pdata.findAll{it.status==0}.fixedPrice").toString();
//        response.getBody().prettyPrint();
//        System.out.println(b);
//
//                .body("p2pdata.status[0]",equalTo(1))//判断100元加油卡状态为1，暂停
//                .body("p2pdata.status[1]",equalTo(0))//判断200元加油卡状态为0，正常
//                .body("p2pdata.productName",hasItems(null,"中石化200元加油卡","中石化500元加油卡","中石化1000元加油卡"))
//                .body("p2pdata.fixedPrice[0]",equalTo(100))//100的卡因为是暂停状态，开发直接赋值为100，没写成100.00,遗留问题
//                .body("p2pdata.fixedPrice[1]",is(new BigDecimal("200.00")))//其他值必须用is，并且用new BigDecimal强制转换成bigdecimal类型的才能匹配上
//                .body("p2pdata.fixedPrice[2]",is(new BigDecimal("500.00")))
//                .body("p2pdata.fixedPrice[3]",is(new BigDecimal("1000.00")))
//                .body("p2pdata.merchantId[1]",equalTo(2))//判断商户2的中石化200元加油卡价格是最优价格
//                .body("p2pdata.salesPrice[1]",is(new BigDecimal("180.00")))//判断商户2的中石化200元加油卡价格是后台输入的价格
//                .body("p2pdata.salesPrice.collect{it}.sum()",lessThan(new BigDecimal(1800.00)))//判断售价总和不能大于原价总和
//                .body("p2pdata.cardType",hasItems(1,2,3,4));//判断中石化cardType的值为 1，2，3，4

//    }
//    @Test
//    public void test005(){
////        runTools.runPostApi("FCaddOilCardCheJi","FCaddOilCardCheJi").getBody().prettyPrint();
//        runTools.runGetApiWithoutParam("FCaddOilCardCheJi").getContentType();
//    }
//    @Test
//    public void test006(){
////       runTools.runPostApi("FCaddOilCardCheJi","FCaddOilCardCheJi").getBody().prettyPrint();
//        runTools.runGetApi("FCpriceList01","priceList").getBody().prettyPrint();
//        System.out.println(readConfigFiles.findGetPara("priceList"));
//
//    }

//    @Test
//    public void test001(){
//        System.out.println(fuelCardDependency.getTokenWithLogin("18610413790","wodemima11"));
//
//
//    }
//
//    @Test
//    public void testQZX001(){
//        int id=given().
//                param("longitude","116.3241195678711").
//                param("latitude","39.937862396240234").
//                param("homeLongitude","").
//                param("homeLatitude","").
//                param("companyLongitude","").
//                param("companyLatitude","").
//                param("pageNumber","1").
//                param("pageSize","20").
//                when().
//                get("http://test-cfw.yxyongche.cn/cmapi/merchant/getMerchantList").
//                path("p2pdata.data.id[0]");
//        System.out.println(id);
//
//
//    }
//
//    @Test
//    public void testQZXtwo(){
//        //创建数据库驱动
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//
//        }catch (ClassNotFoundException e){
//            System.out.println("找不到驱动程序类，加载数据库失败");
//            e.printStackTrace();
//        }
//        //配置数据库url、用户名和密码连接数据
//        String url="jdbc:mysql://10.2.101.6:3306";
//        String username="baoxian_admin";
//        String password="baoxian_admin";
//        try{
//            Connection con=DriverManager.getConnection(url,username,password);
//            Statement stmt=con.createStatement();
////            PreparedStatement pstmt=con.prepareStatement(sql);
////            CallableStatement cstmt=con.prepareCall("{CALL demoSp(? , ?)}");
//
//            ResultSet rs=stmt.executeQuery("select * from order_center.order_info where order_id='14101002579';");
//
//            while (rs.next()){
//                String sellername=rs.getString(1);
//                System.out.println(sellername);
//            }
//            if(rs != null){   // 关闭记录集
//                try{
//                    rs.close() ;
//                }catch(SQLException e){
//                    e.printStackTrace() ;
//                }
//            }
//            if(stmt != null){   // 关闭声明
//                try{
//                    stmt.close() ;
//                }catch(SQLException e){
//                    e.printStackTrace() ;
//                }
//            }
//            if(con != null){  // 关闭连接对象
//                try{
//                    con.close() ;
//                }catch(SQLException e){
//                    e.printStackTrace() ;
//                }
//            }
//        }catch (SQLException se){
//            System.out.println("数据库连接失败");
//            se.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getTokenWithLogin() {
//        String token = given()
//                .params(readConfigFiles.findAccount("account8767"))
//                .when()
//                .post(readConfigFiles.findUrl("testLoginUrl"))
//                .body()
//                .prettyPrint();
////                .path("result.token");
//    }
//
//    @Test
//    public void testqzx009(){
////        RequestContext context = RequestContext.getCurrentContext();
////        HttpServletRequest request = context.getRequest();
////
////        Map<String, Object> paramsMap;
////        try {
////            paramsMap = SignUtil.showParams(request);
////            }
////        SignUtil.auth(paramsMap);
////        需要询问卢振，怎么用加密的方法，怎么利用ASCII码从小到大排序，怎么去调用。
//    }

    @Test
    public void testgettoken(){
        System.out.println(account.getTokenAPP("testLoginpara8767"));
    }



}

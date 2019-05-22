package com.yixin.test;

import com.yixin.util.RunTools;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class PrivilegeRefuel {
    RunTools runTools = new RunTools();

    @BeforeTest
    public void setUp() {
        //特权加油
        RestAssured.baseURI = "";
    }
}